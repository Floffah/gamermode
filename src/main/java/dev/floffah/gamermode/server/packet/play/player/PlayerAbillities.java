package dev.floffah.gamermode.server.packet.play.player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.PacketType;

import java.io.IOException;

/**
 * Outgoing play packet for telling the client the player's creative abilities
 */
public class PlayerAbillities extends BasePacket {
    /**
     * Construct a player abilities packet
     */
    public PlayerAbillities() {
        super("PlayerAbillities", 0x30, PacketType.OUTBOUND);
    }

    @Override
    public ByteArrayDataOutput buildOutput() throws IOException {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeByte(0);
        out.writeFloat(0.05f);
        out.writeFloat(0.1f);

        return out;
    }
}
