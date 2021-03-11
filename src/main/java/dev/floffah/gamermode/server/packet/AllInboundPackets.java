package dev.floffah.gamermode.server.packet;

import dev.floffah.gamermode.server.packet.login.EncryptionResponse;
import dev.floffah.gamermode.server.packet.login.LoginStart;
import dev.floffah.gamermode.server.packet.play.message.PluginMessage;
import dev.floffah.gamermode.server.packet.play.misc.ClientSettings;
import dev.floffah.gamermode.server.packet.serverlist.Handshake;
import dev.floffah.gamermode.server.packet.serverlist.Ping;
import dev.floffah.gamermode.server.packet.serverlist.Request;
import dev.floffah.gamermode.server.socket.ConnectionState;

public enum AllInboundPackets {
    // HANDSHAKE
    HANDSHAKE(0x00, ConnectionState.HANDSHAKE, Handshake.class),

    // STATUS
    REQUEST(0x00, ConnectionState.STATUS, Request.class),
    PING(0x01, ConnectionState.STATUS, Ping.class),

    // LOGIN
    LOGIN_START(0x00, ConnectionState.LOGIN, LoginStart.class),
    ENCRYPTION_RESPONSE(0x01, ConnectionState.LOGIN, EncryptionResponse.class),

    // PLAY
    PLUGIN_MESSAGE(0x0B, ConnectionState.PLAY, PluginMessage.class),
    CLIENT_SETTINGS(0x05, ConnectionState.PLAY, ClientSettings.class);

    public int id;
    public ConnectionState state;
    public Class<? extends BasePacket> packet;

    AllInboundPackets(int id, ConnectionState state, Class<? extends BasePacket> packet) {
        this.id = id;
        this.state = state;
        this.packet = packet;
    }
}
