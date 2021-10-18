package dev.floffah.gamermode.server.packet.play.connection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.events.network.PacketSentEvent;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.PacketType;

import java.io.IOException;

/**
 * Incoming & outgoing play packet-<br/>
 * The outgoing packet is sent to request a keepalive packet from the client to ensure
 * it is still connected and working<br/>
 * The incoming packet is received in response to the outgoing keepalive packet
 */
public class Keepalive extends BasePacket {
    /**
     * Construct an outgoing keepalive packet
     * @param confirm Set this to anything, just to make sure its an outgoing packet
     */
    public Keepalive(int confirm) {
        super("KeepAliveOut", 0x1F, PacketType.OUTBOUND);
    }

    /**
     * Construct an incoming keepalive packet
     */
    public Keepalive() {
        super("KeepAliveIn", 0x10, PacketType.INBOUND);
    }

    @Override
    public ByteArrayDataOutput buildOutput() throws IOException {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        long id = System.currentTimeMillis();
        out.writeLong(id);
        conn.currentkeepaliveid = id;

        return out;
    }

    @Override
    public void postSend(PacketSentEvent e) throws IOException {
        conn.lastkeepalivesend = System.currentTimeMillis();
    }

    @Override
    public void process(int len, ByteArrayDataInput in) throws IOException {
        long id = in.readLong();

        if(id != conn.currentkeepaliveid) {
            conn.disconnect("&cInvalid keepalive ID");
            return;
        }

        conn.lastkeepalivereceive = System.currentTimeMillis();
    }
}
