package dev.floffah.gamermode.server.packet.login;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.PacketType;
import dev.floffah.gamermode.util.Strings;
import dev.floffah.gamermode.util.VarInt;

import java.io.IOException;

public class JoinGame extends BasePacket {
    public JoinGame() {
        super("LoginJoinGame", 0x24, PacketType.OUTBOUND);
    }

    @Override
    public ByteArrayDataOutput buildOutput() throws IOException {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeInt(20);
        out.writeBoolean(false);
        out.write(((byte)0) & 0xFF);
        out.writeByte(-1);
        VarInt.writeVarInt(out, 0);

        return out;
    }
}
