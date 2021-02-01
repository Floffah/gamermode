package dev.floffah.gamermode.server.packet.serverlist;

import com.google.common.io.ByteArrayDataInput;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.PacketType;
import dev.floffah.gamermode.server.socket.ConnectionState;
import dev.floffah.gamermode.util.Strings;
import dev.floffah.gamermode.util.VarInt;

import java.io.IOException;

public class Handshake extends BasePacket {
    public Handshake() {
        super("ServerListHandshake", 0x00, PacketType.INBOUND);
    }

    @Override
    public void process(int len, ByteArrayDataInput in) throws IOException {
        int protocol = VarInt.readVarInt(in);
        String addr = Strings.readUTF(in);
        int port = in.readUnsignedShort();
        int next = VarInt.readVarInt(in);
        conn.protver = protocol;
        conn.addrused = addr;
        conn.portused = port;
        if(next == 1) {
            conn.serverlist = true;
            conn.state = ConnectionState.STATUS;
        } else {
            conn.state = ConnectionState.LOGIN;
        }
    }
}
