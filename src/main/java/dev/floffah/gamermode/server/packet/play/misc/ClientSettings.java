package dev.floffah.gamermode.server.packet.play.misc;

import com.google.common.io.ByteArrayDataInput;
import dev.floffah.gamermode.player.Player;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.PacketType;
import dev.floffah.gamermode.util.Bytes;
import dev.floffah.gamermode.util.Strings;
import dev.floffah.gamermode.util.VarInt;

import java.io.IOException;

public class ClientSettings extends BasePacket {
    public ClientSettings() {
        super("ClientSettings", 0x05, PacketType.OUTBOUND);
    }

    @Override
    public void process(int len, ByteArrayDataInput in) throws IOException {
        Player.ClientSettings settings = new Player.ClientSettings();
        settings.locale = Strings.readUTF(in);
        settings.viewDistance = in.readByte();
        settings.chatMode = VarInt.readVarInt(in);
        settings.colors = in.readByte() == 1;
        settings.mainHand = VarInt.readVarInt(in);
        conn.player.setSettings(settings);
        conn.player.translateSkinParts(in.readUnsignedByte());
    }
}
