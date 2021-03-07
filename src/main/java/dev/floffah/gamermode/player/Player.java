package dev.floffah.gamermode.player;

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
}
