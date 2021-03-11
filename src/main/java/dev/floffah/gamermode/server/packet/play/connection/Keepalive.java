package dev.floffah.gamermode.server.packet.play.connection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.events.network.PacketSentEvent;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.PacketType;

import java.io.IOException;

public class Keepalive extends BasePacket {
    public Keepalive(int confirm) {
        super("KeepAliveOut", 0x1F, PacketType.OUTBOUND);
    }

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
