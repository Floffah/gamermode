package dev.floffah.gamermode.events.player.login;

import dev.floffah.gamermode.events.player.PlayerEvent;
import dev.floffah.gamermode.player.Player;
import lombok.Getter;
import lombok.Setter;

/**
 * An event fired right after the player has joined the game<br/>
 * More specifically- right after the {@link dev.floffah.gamermode.server.packet.login.JoinGame} packet is sent
 */
public class PlayerJoinEvent extends PlayerEvent {
    /**
     * The reason for cancelling the event
     * -- GETTER --
     * Get the reason for cancelling the event
     * @return The reason for cancelling the event
     * -- SETTER --
     * Set the reason for cancelling the event
     * @param cancelReason New cancel reason
     */
    @Getter
    @Setter
    String cancelReason;

    /**
     * Construct a PlayerJoinEvent
     * @param player The player associated
     */
    public PlayerJoinEvent(Player player) {
        super(player);
    }
}
