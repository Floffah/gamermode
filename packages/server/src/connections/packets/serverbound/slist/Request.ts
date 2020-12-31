import Packet from "../../../Packet";
import { ClientboundPackets } from "../../../packets";
import { protocolVersion } from "../../../protocol";

export default class ServerboundSListRequest extends Packet {
    constructor() {
        super();
    }

    run() {
        this.conn.sendPacket(
            ClientboundPackets.SListResponse,
            Buffer.from(
                JSON.stringify({
                    version: {
                        name: "GamerMode 1.16.4",
                        protocol: protocolVersion,
                    },
                    players: this.conn.server.options.players.showPlayers
                        ? {
                              max: this.conn.server.options.players.max,
                              online:
                                  this.conn.server.players.players.size +
                                  (this.conn.server.options.players.offset ||
                                      0),
                              sample: [],
                          }
                        : undefined,
                    description: {
                        text: "Hello world",
                    },
                }),
                "utf-8",
            ),
            "string",
        );
    }
}
