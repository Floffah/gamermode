package dev.floffah.gamermode.events.player.login;

import dev.floffah.gamermode.events.player.PlayerEvent;
import dev.floffah.gamermode.player.Player;

/**
 * A player event that is fired after a player logs in.<br/>
 * More specifically- right after the {@link dev.floffah.gamermode.server.packet.login.LoginSuccess} packet is sent (but before the {@link dev.floffah.gamermode.server.packet.login.JoinGame} packet)
 */
public class PlayerLoginEvent extends PlayerEvent {
    /**
     * Event fired when the player receives the LoginSuccess packet (has not joined yet)
     *
     * @param player the associated player
     */
    public PlayerLoginEvent(Player player) {
        super(player);
    }
}
