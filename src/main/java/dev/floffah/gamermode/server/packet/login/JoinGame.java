package dev.floffah.gamermode.server.packet.login;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.PacketType;
import dev.floffah.gamermode.util.Bytes;
import dev.floffah.gamermode.util.Strings;
import dev.floffah.gamermode.util.VarInt;
import dev.floffah.gamermode.world.World;

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
        out.write(((byte) 0) & 0xFF);
        out.writeByte(-1);
        VarInt.writeVarInt(out, 1);
        Strings.writeUTF("minecraft:world", out);
        conn.main.server.wm.codec.write(out);
        World defworld = conn.main.server.wm.getDefaultWorld();
        conn.main.server.wm.getPlainDimType(defworld).write(out);
        Strings.writeUTF(defworld.name, out);
        Long seed = 2398456723252352352L;
        out.writeLong(seed.hashCode());
        VarInt.writeVarInt(out, conn.main.server.conf.players.max);
        VarInt.writeVarInt(out, conn.main.server.conf.worlds.renderDistance);
        out.writeByte(Bytes.bool(false));
        out.writeByte(Bytes.bool(true));
        out.writeByte(Bytes.bool(true));
        out.writeByte(Bytes.bool(false));

        return out;
    }
}
