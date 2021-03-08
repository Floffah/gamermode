package dev.floffah.gamermode.events.player.login;

import dev.floffah.gamermode.events.player.PlayerEvent;
import dev.floffah.gamermode.player.Player;

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
