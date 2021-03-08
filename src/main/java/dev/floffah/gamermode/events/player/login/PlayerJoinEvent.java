package dev.floffah.gamermode.events.player.login;

import dev.floffah.gamermode.events.player.PlayerEvent;
import dev.floffah.gamermode.player.Player;

public class PlayerJoinEvent extends PlayerEvent {
    String cancelReason;

    public PlayerJoinEvent(Player player) {
        super(player);
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }
}
