package dev.floffah.gamermode.server.packet.login;

import com.google.common.io.ByteArrayDataInput;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.PacketType;
import dev.floffah.gamermode.util.Strings;

import java.io.IOException;

public class LoginStart extends BasePacket {
    public LoginStart() {
        super("LoginStart", 0x00,PacketType.INBOUND);
    }

    @Override
    public void process(int len, ByteArrayDataInput in) throws IOException {
        String username = Strings.readUTF(in);
        conn.playername = username;
        conn.send(new EncryptionRequest());
    }
}
