package dev.floffah.gamermode.player;

import com.google.common.io.ByteArrayDataOutput;
import dev.floffah.gamermode.chat.Component;
import dev.floffah.gamermode.server.packet.play.message.PluginMessage;
import dev.floffah.gamermode.server.socket.SocketConnection;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.UUID;

public class Player {
    @Getter
    protected Profile profile;
    @Getter
    @Setter
    protected String brand;
    @Getter
    protected SocketConnection conn;
    @Getter
    protected UUID uniqueId;
    @Getter
    protected String username;
    /**
     * The client's settings
     * -- SETTER --
     * Update the settings<br/>This setter is for internal protocol only - should not be used and will not work
     *
     * @param settings The new client settings
     * -- GETTER --
     * Get the client's settings.
     */
    @Getter
    @Setter
    protected ClientSettings settings;

    public Player(SocketConnection conn, String username) {
        this.conn = conn;
        this.username = username;
        profile = new Profile(this);
    }

    public void kick(String message) {
        conn.disconnect(message);
    }

    public void kick(Component reason) {
        conn.disconnect(reason);
    }

    public void sendPluginMessage(String channel, ByteArrayDataOutput bytes) throws IOException {
        conn.send(new PluginMessage(channel, bytes));
    }

    public void translateSkinParts(int bitmask) {
        settings.cape = (bitmask & ClientSettings.CAPE_ENABLED) == ClientSettings.CAPE_ENABLED;
        settings.jacket = (bitmask & ClientSettings.JACKET_ENABLED) == ClientSettings.JACKET_ENABLED;
        settings.leftSleeve = (bitmask & ClientSettings.LEFT_SLEEVE_ENABLED) == ClientSettings.LEFT_SLEEVE_ENABLED;
        settings.rightSleeve = (bitmask & ClientSettings.RIGHT_SLEEVE_ENABLED) == ClientSettings.RIGHT_SLEEVE_ENABLED;
        settings.leftPant = (bitmask & ClientSettings.LEFT_PANTS_LEG_ENABLED) == ClientSettings.LEFT_PANTS_LEG_ENABLED;
        settings.rightPant = (bitmask & ClientSettings.RIGHT_PANTS_LEG_ENABLED) == ClientSettings.RIGHT_PANTS_LEG_ENABLED;
        settings.hat = (bitmask & ClientSettings.HAT_ENABLED) == ClientSettings.HAT_ENABLED;
        System.out.println(settings.toString());
    }

    public static class ClientSettings {
        public static byte CAPE_ENABLED = 0x01;
        public static byte JACKET_ENABLED = 0x02;
        public static byte LEFT_SLEEVE_ENABLED = 0x04;
        public static byte RIGHT_SLEEVE_ENABLED = 0x08;
        public static byte LEFT_PANTS_LEG_ENABLED = 0x10;
        public static byte RIGHT_PANTS_LEG_ENABLED = 0x20;
        public static byte HAT_ENABLED = 0x40;

        public String locale;
        public byte viewDistance;
        public int chatMode;
        public int mainHand;

        public boolean colors;
        public boolean cape;
        public boolean jacket;
        public boolean leftSleeve;
        public boolean rightSleeve;
        public boolean leftPant;
        public boolean rightPant;
        public boolean hat;

        @Override
        public String toString() {
            return "ClientSettings{" +
                    "locale='" + locale + '\'' +
                    ", viewDistance=" + viewDistance +
                    ", chatMode=" + chatMode +
                    ", colors=" + colors +
                    ", cape=" + cape +
                    ", jacket=" + jacket +
                    ", leftSleeve=" + leftSleeve +
                    ", rightSleeve=" + rightSleeve +
                    ", leftPant=" + leftPant +
                    ", rightPant=" + rightPant +
                    ", hat=" + hat +
                    '}';
        }
    }
}
