import Packet from "./Packet";
import { decode } from "varint";
import Connection from "./Connection";
import ServerboundSListHandshake from "./packets/serverbound/slist/Handshake";
import ServerboundSListRequest from "./packets/serverbound/slist/Request";
import chalk from "chalk";
import ServerboundLoginStart from "./packets/serverbound/login/LoginStart";

export enum ServerboundPackets {
    SListHandshake = 0x00,
    SListRequest = 0x00,
    LoginStart = 0x00,
    SListPing,
    EncryptionResponse = 0x01,
    LoginPluginResponse,
}

export enum ClientboundPackets {
    SListResponse = 0x00,
    Disconnect = 0x00,
    SListPong,
    EncryptionRequest = 0x01,
    LoginSuccess,

    JoinGame = 0x24,

    PlayerInfo = 0x32,

    PositionAndLook = 0x34,
}

export default function decidePacket(
    length: number,
    buf: Buffer,
    conn: Connection,
): Packet | null {
    conn.received = conn.received + 1;

    const id = decode(buf.slice(1));

    let p: Packet | null = null;
    let pname: string | null = null;

    console.log(conn.slist);

    if (
        (id === ServerboundPackets.SListHandshake ||
            id === ServerboundPackets.SListRequest) &&
        conn.status !== 2
    ) {
        if (length > 1) {
            p = new ServerboundSListHandshake();
        } else {
            p = new ServerboundSListRequest();
        }
    } else if (id === ServerboundPackets.SListPing && conn.slist) {
        pname = "ServerboundSListPing";
        conn.sendPacket(ClientboundPackets.SListPong, buf.slice(2));
    } else if (id === ServerboundPackets.LoginStart && !conn.slist) {
        p = new ServerboundLoginStart();
    }

    if (p) {
        p.buf = buf;
        p.length = length;
        p.id = id;
        p.conn = conn;
    }

    if (conn.server.options.protocol?.debug) {
        let name = "... was not found";
        if (p) {
            name = p.constructor.name;
        } else if (pname) {
            name = pname;
        }
        console.log(chalk`{cyan.bold FOUND PACKET} {gray ${name}}`);
    }

    return p;
}
