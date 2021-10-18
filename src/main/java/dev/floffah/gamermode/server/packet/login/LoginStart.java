package dev.floffah.gamermode.server.packet.login;

import com.google.common.io.ByteArrayDataInput;
import dev.floffah.gamermode.player.Player;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.PacketType;
import dev.floffah.gamermode.util.Strings;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Incoming packet that the client uses to start the login process
 */
public class LoginStart extends BasePacket {
    /**
     * Construct a LoginStart packet
     */
    public LoginStart() {
        super("LoginStart", 0x00, PacketType.INBOUND);
    }

    @Override
    public void process(int len, ByteArrayDataInput in) throws IOException {
        conn.session = Long.toString(ThreadLocalRandom.current().nextLong()).trim();
        conn.player = new Player(conn, Strings.readUTF(in));
        conn.send(new EncryptionRequest());
    }
}
