import Packet from "../../../Packet";
import { decode } from "varint";

export default class ServerboundLoginStart extends Packet {
    constructor() {
        super();
    }

    run() {
        const ulen = decode(this.buf.slice(3));
        console.log(this.buf.slice(4, ulen));
    }
}
