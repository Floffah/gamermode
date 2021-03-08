package dev.floffah.gamermode.events.player;

import dev.floffah.gamermode.events.types.Cancellable;
import dev.floffah.gamermode.player.Player;

public class PlayerEvent extends Cancellable {
    Player player;

    public PlayerEvent(Player player) {
        super();
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
