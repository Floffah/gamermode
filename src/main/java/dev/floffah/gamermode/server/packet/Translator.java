package dev.floffah.gamermode.server.packet;

import com.google.common.io.ByteArrayDataInput;
import dev.floffah.gamermode.server.socket.ConnectionState;
import dev.floffah.gamermode.server.socket.SocketConnection;

import java.io.IOException;
import java.util.HashMap;

public class Translator {
    public static HashMap<PacketKey, Class<? extends BasePacket>> known = new HashMap<>();

    public static void translate(int len, int id, ByteArrayDataInput in, SocketConnection conn) {
        BasePacket p = identify(id, conn);
        try {
            p.process(len, in);
        } catch (IOException e) {
            conn.main.server.logger.printStackTrace(e);
        }
    }

    public static BasePacket identify(int id, SocketConnection conn) {
        BasePacket b = new BasePacket("UNKNOWN", 0x00, PacketType.UNKNOWN);
        PacketKey k = new PacketKey(id, conn.state);

        if (known.containsKey(k)) {
            try {
                if (known.containsKey(k)) {
                    b = known.get(k).getConstructor().newInstance();
                }
            } catch (Exception e) {
                conn.main.server.logger.printStackTrace(e);
            }
        } else {
            for (AllInboundPackets ap : AllInboundPackets.values()) {
                if (conn.state == ap.state && ap.id == id) {
                    known.put(k, ap.packet);
                    try {
                      b = ap.packet.getConstructor().newInstance();
                    } catch (Exception e) {
                        conn.main.server.logger.printStackTrace(e);
                    }
                }
            }
        }

        b.conn = conn;
        return b;
    }

    public static class PacketKey {
        public int id;
        public ConnectionState state;

        public PacketKey(int id, ConnectionState state) {
            this.id = id;
            this.state = state;
        }
    }
}
