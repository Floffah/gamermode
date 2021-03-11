package dev.floffah.gamermode.server.packet.play.misc;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.PacketType;

import java.io.IOException;

public class ServerDifficulty extends BasePacket {
    public ServerDifficulty() {
        super("ServerDifficulty", 0x0D, PacketType.OUTBOUND);
    }

    @Override
    public ByteArrayDataOutput buildOutput() throws IOException {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeByte(conn.main.server.conf.info.difficulty & 0xff);
        out.writeByte(1);

        return out;
    }
}
