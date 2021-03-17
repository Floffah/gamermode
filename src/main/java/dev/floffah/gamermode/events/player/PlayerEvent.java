package dev.floffah.gamermode.events.player;

import dev.floffah.gamermode.events.types.Cancellable;
import dev.floffah.gamermode.player.Player;
import lombok.Getter;
import lombok.Setter;

/**
 * An event class that should be extended upon in playe related events
 */
public class PlayerEvent extends Cancellable {
    /**
     * The player associated with the event
     * -- GETTER --
     * Get the player associated with the event
     * @return The player associated with the event
     * -- SETTER --
     * Set the player associated with the event
     * @param player The new player associated with the event
     */
    @Getter
    @Setter
    Player player;

    /**
     * Construct a PlayerEvent
     * @param player The player associated
     */
    public PlayerEvent(Player player) {
        super();
        this.player = player;
    }
}
