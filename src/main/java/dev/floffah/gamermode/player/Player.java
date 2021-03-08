package dev.floffah.gamermode.player;

import com.google.common.io.ByteArrayDataOutput;
import dev.floffah.gamermode.chat.Component;
import dev.floffah.gamermode.server.socket.SocketConnection;

import java.util.UUID;

public class Player {
    public Profile profile;
    public UUID uuid;
    public String username;

    public SocketConnection conn;

    public Player(SocketConnection conn) {
        this.conn = conn;
        profile = new Profile(this);
    }

    public void kick(String message) {
        conn.disconnect(message);
    }

    public void kick(Component reason) {
        conn.disconnect(reason);
    }

    public void sendPluginMessage(String channel, ByteArrayDataOutput bytes) {

    }
}
