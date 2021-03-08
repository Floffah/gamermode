package dev.floffah.gamermode.server.packet.login;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.PacketType;
import dev.floffah.gamermode.server.socket.ConnectionState;
import dev.floffah.gamermode.util.Strings;

import java.io.IOException;

public class LoginSuccess extends BasePacket {
    public LoginSuccess() {
        super("LoginSuccess", 0x02, PacketType.OUTBOUND);
    }

    @Override
    public ByteArrayDataOutput buildOutput() throws IOException {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        conn.player.profile.authenticate();

        out.writeLong(conn.player.uuid.getMostSignificantBits());
        out.writeLong(conn.player.uuid.getLeastSignificantBits());

        Strings.writeUTF(conn.player.username, out);

        conn.state = ConnectionState.PLAY;

        return out;
    }
}
