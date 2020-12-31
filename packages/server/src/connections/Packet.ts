import Connection from "./Connection";

export default class Packet {
    buf: Buffer;

    length: number;
    id: number;

    conn: Connection;

    run(): unknown {
        return null;
    }
}
