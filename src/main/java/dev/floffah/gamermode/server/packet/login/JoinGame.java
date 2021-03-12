package dev.floffah.gamermode.server.packet.login;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.events.network.PacketSentEvent;
import dev.floffah.gamermode.events.player.login.PlayerJoinEvent;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.PacketType;
import dev.floffah.gamermode.server.packet.play.connection.Keepalive;
import dev.floffah.gamermode.server.packet.play.misc.ServerDifficulty;
import dev.floffah.gamermode.server.packet.play.player.PlayerAbillities;
import dev.floffah.gamermode.util.Bytes;
import dev.floffah.gamermode.util.Strings;
import dev.floffah.gamermode.util.VarInt;
import dev.floffah.gamermode.world.World;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class JoinGame extends BasePacket {
    public JoinGame() {
        super("JoinGame", 0x24, PacketType.OUTBOUND);
    }

    @Override
    public ByteArrayDataOutput buildOutput() throws IOException {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeInt(20); // entity id
        out.writeBoolean(false); // is hardcore
        out.writeByte(0 & 0xff); // gamemode
        out.writeByte(-1); // previous gamemode
        VarInt.writeVarInt(out, 1); // world count
        Strings.writeUTF("minecraft:world", out); // array index 0 of identifier
        conn.main.server.wm.codec.write(out); // dimension codec
        World defworld = conn.main.server.wm.getDefaultWorld();
        conn.main.server.wm.getPlainDimType(defworld).write(out); // dimension
        Strings.writeUTF(defworld.name, out); // world name
        long seed = 2398456723252352352L;
        out.writeLong(seed); // hashed seed
        VarInt.writeVarInt(out, conn.main.server.conf.players.max); // max players
        VarInt.writeVarInt(out, conn.main.server.conf.worlds.renderDistance); // render distance
        out.writeByte(Bytes.bool(false)); // reduced debug info
        out.writeByte(Bytes.bool(true)); // enable respawn screen
        out.writeByte(Bytes.bool(true)); // is debug
        out.writeByte(Bytes.bool(false)); // is flat

        return out;
    }

    @Override
    public void postSend(PacketSentEvent e) throws IOException {
        PlayerJoinEvent joine = new PlayerJoinEvent(conn.player);
        conn.main.server.events.execute(joine);
        if (joine.isCancelled()) {
            String reason = "Kicked";
            if (joine.getCancelReason() != null) {
                reason = joine.getCancelReason();
            }
            conn.disconnect(reason);
            return;
        }

        conn.main.server.scheduler.scheduleAtFixedRate(() -> {
            try {
                conn.send(new Keepalive(0));
            } catch (IOException e2) {
                conn.main.server.logger.printStackTrace(e2);
            }
        }, 0, 1, TimeUnit.SECONDS);

        ByteArrayDataOutput brandout = ByteStreams.newDataOutput();
        Strings.writeUTF("gamermode", brandout);
        //brandout.writeUTF("gamermode");
        conn.player.sendPluginMessage("minecraft:brand", brandout);
        conn.send(new ServerDifficulty());
        conn.send(new PlayerAbillities());
    }
}
