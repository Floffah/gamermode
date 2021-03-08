package dev.floffah.gamermode.server.packet.play.message;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.PacketType;
import dev.floffah.gamermode.util.Strings;

import java.io.IOException;

public class PluginMessage extends BasePacket {
    ByteArrayDataOutput bytes;
    String channel;

    public PluginMessage(String channel, ByteArrayDataOutput bytes) {
        super("PluginMessage", 0x17, PacketType.OUTBOUND);
        this.channel = channel;
        this.bytes = bytes;
    }

    @Override
    public ByteArrayDataOutput buildOutput() throws IOException {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        Strings.writeUTF(channel, out);
        out.write(bytes.toByteArray());

        return out;
    }
}
