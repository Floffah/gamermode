import Packet from "../../../Packet";

export default class ServerboundLoginStart extends Packet {
    constructor() {
        super();
    }

    run() {
        if (
            !this.conn.encrypted &&
            this.conn.addrUsed !== "localhost" &&
            this.conn.addrUsed !== "127.0.0.1"
        ) {
            this.conn.requestEncryption();
        } else {
            this.conn.sendLoginSuccess(this.buf);
        }
    }
}
