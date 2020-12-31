import Packet from "../../../Packet";
import { decode } from "varint";

export default class ServerboundSListHandshake extends Packet {
    constructor() {
        super();
    }

    run() {
        this.conn.clientProtocol = decode(this.buf.slice(2));
        const addrlen = decode(this.buf.slice(4));
        const addrbytes = [];
        for (let i = 0; i < addrlen; i++) {
            addrbytes.push(this.buf[5 + i]);
        }
        this.conn.addrUsed = Buffer.of(...addrbytes).toString("utf8");
        this.conn.status = decode(this.buf.slice(this.buf.length - 1));
        this.conn.slist = this.conn.status === 1;
    }
}
