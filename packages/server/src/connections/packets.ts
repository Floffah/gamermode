import Packet from "./Packet";
import { decode } from "varint";
import Connection from "./Connection";
import ServerboundSListHandshake from "./packets/serverbound/slist/Handshake";
import ServerboundSListRequest from "./packets/serverbound/slist/Request";
import chalk from "chalk";
import ServerboundLoginStart from "./packets/serverbound/login/LoginStart";

export enum ServerboundPackets {
    SListHandshake,
    SListRequest = 0,
    LoginStart = 0,
    SListPing,
    EncryptionResponse = 1,
    LoginPlugiNResponse,
}

export enum ClientboundPackets {
    SListResponse,
    SListPong,
}

export default function decidePacket(
    length: number,
    buf: Buffer,
    conn: Connection,
): Packet | null {
    conn.received = conn.received + 1;

    const id = decode(buf.slice(1));

    let p: Packet | null = null;

    if (
        id === ServerboundPackets.SListHandshake ||
        id === ServerboundPackets.SListRequest
    ) {
        if (length > 1) {
            p = new ServerboundSListHandshake();
        } else {
            p = new ServerboundSListRequest();
        }
    } else if (id === ServerboundPackets.SListPing && conn.slist) {
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
        if (id in ServerboundPackets) {
            console.log(
                chalk`{cyan.bold FOUND PACKET} {gray ${ServerboundPackets[id]}}`,
            );
        } else {
            console.log(chalk`{red FOUND UNKNOWN PACKET OF ID} ${id}`);
        }
    }

    return p;
}
