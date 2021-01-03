import { Socket } from "net";
import { decode, encode } from "varint";
import chalk from "chalk";
import decidePacket, { ClientboundPackets } from "./packets";
import Server from "../server/Server";
import { randomBytes } from "crypto";
import { ChatComponent, textToChat } from "../utils/chat";
import axios from "axios";
import { parse } from "uuid";
import Player from "../structure/Player";
import { getAllWithValue } from "../utils/enums";
import { numToByte, numToInt, numToLong, numToUByte } from "../utils/numbers";
import { stringArrayToBuf, stringToBuf } from "../utils/strings";

export default class Connection {
    socket: Socket;
    server: Server;

    communicated = false;
    status = 0;
    received = 0;
    joining = false;

    clientProtocol = 0;
    addrUsed = "unknown";

    slist: boolean;

    encrypted = false;
    token: Buffer;

    player: Player;

    constructor(s: Socket, server: Server) {
        this.socket = s;
        this.server = server;

        this.socket.on("data", (d) => this.parsePackets(d));
        this.socket.on("close", () => this.close());
        this.socket.on("error", (err) => this.error(err));

        setTimeout(() => {
            if (!this.communicated) {
                s.end();
            }
        }, 500);
    }

    safeDecode(d: Buffer | number[]): number | null {
        try {
            return decode(d);
        } catch (e) {
            console.log(chalk`{gray ${JSON.stringify(d)}}: {red ${e.message}}`);
        }
        return null;
    }

    toHex(num: number) {
        let h = num.toString(16);
        if (h.length === 1) {
            h = "0" + h;
        }
        return h;
    }

    parsePackets(buf: Buffer) {
        if (this.server.options.protocol?.debug) {
            console.log(
                chalk`{blue.bold RECEIVE} {gray <\Buffer ${[...buf]
                    .map((num) => this.toHex(num))
                    .join(" ")}>}`,
            );
            // console.log(chalk`{blue RECEIVE} {gray ${[...buf]}}`);
        }

        const plen = this.safeDecode(buf);
        if (plen) {
            const alen = buf.byteLength;

            const packet = decidePacket(plen, buf.slice(0, plen + 1), this);
            if (packet) packet.run();

            if (alen > plen + 1) {
                this.parsePackets(buf.slice(plen + 1));
            }
        }
    }

    sendPacket(
        id: ClientboundPackets,
        data: Buffer | number[],
        dataType?: "string" | "varint",
    ) {
        const vid = encode(id);
        let dat = data;
        if (
            data.length > 0 &&
            typeof data[0] === "number" &&
            dataType === "varint"
        ) {
            const write: number[] = [];

            for (const num of data) {
                write.push(...encode(num));
            }

            dat = write;
        }

        let datsend = dat;
        if (dataType === "string") {
            datsend = Buffer.of(...encode((dat as Buffer).length), ...datsend);
        }

        const buf = Buffer.of(
            ...encode(vid.length + datsend.length),
            ...vid,
            ...datsend,
        );

        if (this.server.options.protocol?.debug) {
            console.log(
                chalk`{green SEND} {gray <\Buffer ${[...buf]
                    .map((num) => this.toHex(num))
                    .join(" ")}>} {yellow (Could be ${getAllWithValue(
                    ClientboundPackets,
                    id,
                ).join(", ")})}`,
            );
            // console.log(chalk`{green SEND} {gray ${[...buf]}}`);
        }

        this.socket.write(buf);
    }

    requestEncryption() {
        const pub = this.server.keys.publicKey;
        randomBytes(
            this.server.options.encryption?.tokenSize || 4,
            (err, token) => {
                if (err) {
                    this.disconnect("Could not generate token");
                } else {
                    this.token = token;
                    this.sendPacket(
                        ClientboundPackets.EncryptionRequest,
                        Buffer.of(
                            ...encode(0),
                            ...encode(pub.byteLength),
                            ...pub,
                            ...encode(token.byteLength),
                            ...token,
                        ),
                    );
                }
            },
        );
    }

    disconnect(reason: string | ChatComponent) {
        let r = reason;
        if (typeof reason === "string") {
            r = textToChat(reason);
        }

        this.sendPacket(
            ClientboundPackets.Disconnect,
            Buffer.from(JSON.stringify(r), "utf-8"),
            "string",
        );
        this.socket.end();
    }

    sendLoginSuccess(buf: Buffer) {
        const ulen = decode(buf.slice(3));
        const player = buf.slice(3, ulen).toString("utf-8");
        axios
            .get(`https://api.mojang.com/users/profiles/minecraft/${player}`)
            .then((d) => {
                if (d.data.id && d.data.name) {
                    const uuid = [
                        d.data.id.substr(0, 8),
                        d.data.id.substr(8, 4),
                        d.data.id.substr(12, 4),
                        d.data.id.substr(16, 4),
                        d.data.id.substr(20, 12),
                    ].join("-");

                    this.sendPacket(
                        ClientboundPackets.LoginSuccess,
                        Buffer.of(
                            ...(parse(uuid) as Uint8Array),
                            ...encode(player.length),
                            ...Buffer.from(player, "utf-8"),
                        ),
                    );

                    const p = new Player();
                    p.name = d.data.name;
                    p.uuid = d.data.uuid;
                    p.formatuuid = uuid;
                    p.position = { pitch: 5, x: 10, y: 15, z: 20, yaw: 25 };

                    this.player = p;

                    this.joining = true;
                    this.sendJoinGame();

                    this.status = 3;
                }
            });
    }

    sendPosition() {
        const x = Buffer.allocUnsafe(8);
        const y = Buffer.allocUnsafe(8);
        const z = Buffer.allocUnsafe(8);
        const yaw = Buffer.allocUnsafe(4);
        const pitch = Buffer.allocUnsafe(4);
        x.writeBigUInt64BE(BigInt(this.player.position.x));
        y.writeBigUInt64BE(BigInt(this.player.position.y));
        z.writeBigUInt64BE(BigInt(this.player.position.z));
        yaw.writeUInt32BE(this.player.position.yaw);
        pitch.writeUInt32BE(this.player.position.pitch);

        this.sendPacket(
            ClientboundPackets.PositionAndLook,
            Buffer.of(...x, ...y, ...z, ...yaw, ...pitch, 0x01, ...encode(1)),
        );
    }

    sendJoinGame() {
        const dimcodec = Buffer.from(
            '{"minecraft:dimension_type": {type: "minecraft:dimension_type",value: []},"minecraft:worldgen/biome": {type: "minecraft:worldgen/biome",value: []}}',
            "utf-8",
        );
        const dim = Buffer.from("{}", "utf-8");

        this.sendPacket(
            ClientboundPackets.JoinGame,
            Buffer.of(
                ...numToInt(1),
                0x00,
                ...numToUByte(0),
                ...numToByte(-1),
                ...encode(1),
                ...stringArrayToBuf(["minecraft:world"]),
                ...encode(dimcodec.byteLength),
                ...dimcodec,
                ...encode(dim.byteLength),
                ...dim,
                ...stringToBuf("minecraft:world"),
                ...numToLong(78953265823),
                ...encode(50),
                ...encode(8),
                0x00,
                0x01,
                0x01,
                0x00,
            ),
        );
    }

    close() {
        console.log("closed");
    }

    error(err: Error) {
        console.log(err.message);
    }
}

export type ConnectionTypes = Connection;
