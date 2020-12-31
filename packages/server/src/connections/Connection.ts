import { Socket } from "net";
import { decode, encode } from "varint";
import chalk from "chalk";
import decidePacket, { ClientboundPackets } from "./packets";
import Server from "../server/Server";

export default class Connection {
    socket: Socket;
    server: Server;

    communicated = false;
    status = 0;
    received = 0;

    clientProtocol = 0;
    addrUsed = "unknown";

    slist: boolean;

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

    parsePackets(buf: Buffer) {
        if (this.server.options.protocol?.debug) {
            console.log(chalk`{blue.bold RECEIVE} {gray ${[...buf]}}`);
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
            console.log(chalk`{green SEND} {gray ${[...buf]}}`);
        }

        this.socket.write(buf);
    }

    close() {
        console.log("closed");
    }

    error(err: Error) {
        console.log(err.message);
    }
}

export type ConnectionTypes = Connection;
