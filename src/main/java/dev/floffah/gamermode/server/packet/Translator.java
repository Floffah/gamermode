package dev.floffah.gamermode.server.packet;

import com.google.common.io.ByteArrayDataInput;
import dev.floffah.gamermode.server.packet.login.EncryptionResponse;
import dev.floffah.gamermode.server.packet.login.LoginStart;
import dev.floffah.gamermode.server.packet.serverlist.Handshake;
import dev.floffah.gamermode.server.packet.serverlist.Ping;
import dev.floffah.gamermode.server.packet.serverlist.Request;
import dev.floffah.gamermode.server.socket.ConnectionState;
import dev.floffah.gamermode.server.socket.SocketConnection;

import java.io.IOException;

public class Translator {
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

        if (conn.state == ConnectionState.HANDSHAKE) {
            if (id == 0x00) {
                b = new Handshake();
            }
        } else if (conn.state == ConnectionState.STATUS) {
            if (id == 0x00) {
                b = new Request();
            } else if (id == 0x01) {
                b = new Ping();
            }
        } else if (conn.state == ConnectionState.LOGIN) {
            if (id == 0x00) {
                b = new LoginStart();
            } else if (id == 0x01) {
                b = new EncryptionResponse();
            }
        }
        b.conn = conn;
        return b;
    }
}
