package dev.floffah.gamermode.events.network;

import com.google.common.io.ByteArrayDataOutput;
import dev.floffah.gamermode.events.Event;
import dev.floffah.gamermode.server.packet.BasePacket;

public class PacketEvent extends Event {
    BasePacket packet;
    ByteArrayDataOutput unencrypted;

    public PacketEvent(BasePacket packet, ByteArrayDataOutput bytes) {
        this.packet = packet;
        this.unencrypted = bytes;
    }

    /**
     * Get the byte array being sent or that was sent. This byte array is the unencrypted version.
     *
     * @return unencrypted byte array
     */
    public ByteArrayDataOutput getBytes() {
        return unencrypted;
    }

    public BasePacket getPacket() {
        return packet;
    }

    public void setPacket(BasePacket packet) {
        this.packet = packet;
    }
}
