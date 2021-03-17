package dev.floffah.gamermode.events.network;

import com.google.common.io.ByteArrayDataOutput;
import dev.floffah.gamermode.server.packet.BasePacket;

/**
 * An event fired right after a packet is sent
 */
public class PacketSentEvent extends PacketEvent {
    /**
     * A normal packet event (that is not cancellable) for listening for when a packet is being sent
     * The bytes in this packet <strong>do</strong> have the length and id prefix
     *
     * @param packet The packet
     */
    public PacketSentEvent(BasePacket packet, ByteArrayDataOutput bytes) {
        super(packet, bytes);
    }
}
