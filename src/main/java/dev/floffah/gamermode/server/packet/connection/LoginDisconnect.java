package dev.floffah.gamermode.server.packet.connection;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.PacketType;
import dev.floffah.gamermode.util.Strings;

import java.io.IOException;

public class LoginDisconnect extends BasePacket {
    String chat;

    public LoginDisconnect(String chat) {
        super("LoginDisconnect", 0x00, PacketType.OUTBOUND);
        this.chat = chat;
    }

    @Override
    public ByteArrayDataOutput buildOutput() throws IOException {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        Strings.writeUTF(chat, out);

        return out;
    }
}
