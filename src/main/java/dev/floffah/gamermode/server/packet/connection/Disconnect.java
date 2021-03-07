package dev.floffah.gamermode.server.packet.connection;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.chat.Component;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.PacketType;
import dev.floffah.gamermode.util.Strings;

import java.io.IOException;

public class Disconnect extends BasePacket {
    Component chat;

    public Disconnect(Component chat) {
        super("Disconnect", 0x19, PacketType.OUTBOUND);
        this.chat = chat;
    }

    @Override
    public ByteArrayDataOutput buildOutput() throws IOException {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        conn.main.server.logger.info(chat.toString());
        Strings.writeUTF(chat.toString(), out);

        return out;
    }
}
