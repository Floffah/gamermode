package dev.floffah.gamermode.events.message;

import dev.floffah.gamermode.events.Event;
import dev.floffah.gamermode.server.packet.play.message.PluginMessage;

public class PluginMessageReceivedEvent extends Event {
    PluginMessage packet;

    public PluginMessageReceivedEvent(PluginMessage packet) {
        this.packet = packet;
    }

    public byte[] getBytes() {
        return packet.bytesread;
    }

    public String getChannel() {
        return packet.channel;
    }
}
