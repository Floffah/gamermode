package dev.floffah.gamermode.server.packet.play.window;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.player.inventory.BaseInventory;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.PacketType;
import dev.floffah.gamermode.util.Strings;
import dev.floffah.gamermode.util.VarInt;

import java.io.IOException;

public class OpenWindow extends BasePacket {
    BaseInventory inv;

    public OpenWindow(BaseInventory inv) {
        super("OpenWindow", 0x2D, PacketType.OUTBOUND);
        this.inv = inv;
    }

    @Override
    public ByteArrayDataOutput buildOutput() throws IOException {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        VarInt.writeVarInt(out, inv.getInvId());
        VarInt.writeVarInt(out, inv.getType().ordinal());
        Strings.writeUTF("", out);

        return out;
    }
}
