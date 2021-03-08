package dev.floffah.gamermode.events.types;

import dev.floffah.gamermode.events.Event;

public class Cancellable extends Event {
    boolean cancelled = false;

    public void setCancelled() {
        cancelled = true;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
