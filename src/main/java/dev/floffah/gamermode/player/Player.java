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
    /**
     * The gameprofile associated
     * -- GETTER --
     * Get the gameprofile
     */
    @Getter
    protected Profile profile;
    /**
     * The player's broadcasted client brand
     * -- GETTER --
     * Get the player's client brand
     *
     * @return The player's client brand
     * -- SETTER --
     * Set the player's client brand
     * @param brand New brand to set
     */
    @Getter
    @Setter
    protected String brand;
    /**
     * The player's connection
     * -- GETTER --
     * Get the player's connection
     *
     * @return The player's connection
     */
    @Getter
    protected SocketConnection conn;
    /**
     * The player's UUID
     * -- GETTER --
     * Get the player's UUID
     *
     * @return The player's UUID
     */
    @Getter
    protected UUID uniqueId;
    /**
     * The player's username
     * -- GETTER --
     * Get the player's username
     *
     * @return The player's username
     */
    @Getter
    protected String username;
    /**
     * The client's settings
     * -- SETTER --
     * Update the settings<br/>
     * This setter is for internal protocol only - should not be used and will not work
     *
     * @param settings The new client settings
     * -- GETTER --
     * Get the client's settings.
     * @return The client's settings
     */
    @Getter
    @Setter
    protected ClientSettings settings;

    /**
     * Initialise a new player
     * @param conn The player's connection
     * @param username The player's username
     */
    public Player(SocketConnection conn, String username) {
        this.conn = conn;
        this.username = username;
        profile = new Profile(this);
    }

    /**
     * Kick the player with a message (will be translated to a component from \u0026 color codes)
     * @param message Message supporting colour codes
     */
    public void kick(String message) {
        conn.disconnect(message);
    }

    /**
     * Kick the player with a message as a component
     * @param reason Kick reason as a component
     */
    public void kick(Component reason) {
        conn.disconnect(reason);
    }

    /**
     * Send a plugin message via the player
     * @param channel The channel
     * @param bytes Message byte array
     * @throws IOException Throws any exceptions thrown while sending
     */
    public void sendPluginMessage(String channel, ByteArrayDataOutput bytes) throws IOException {
        conn.send(new PluginMessage(channel, bytes));
    }

    /**
     * For internal use only.<br/>
     * Translates a bit mask sent in the {@link ClientSettings} packet to client settings
     * @param bitmask The bit mask to translate
     */
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

    /**
     * Client settings
     */
    public static class ClientSettings {
        /**
         * cape_enabled bit location
         */
        public static byte CAPE_ENABLED = 0x01;
        /**
         * jacket_enabled bit location
         */
        public static byte JACKET_ENABLED = 0x02;
        /**
         * left_sleeve_enabled bit location
         */
        public static byte LEFT_SLEEVE_ENABLED = 0x04;
        /**
         * right_sleeve_enabled bit location
         */
        public static byte RIGHT_SLEEVE_ENABLED = 0x08;
        /**
         * left_pants_leg_enabled bit location
         */
        public static byte LEFT_PANTS_LEG_ENABLED = 0x10;
        /**
         * right_pants_leg_enabled bit location
         */
        public static byte RIGHT_PANTS_LEG_ENABLED = 0x20;
        /**
         * hat_enabled bit location
         */
        public static byte HAT_ENABLED = 0x40;

        /**
         * Client locale
         */
        public String locale;
        /**
         * Client view distance
         */
        public byte viewDistance;
        /**
         * Client chat mode
         */
        public int chatMode;
        /**
         * Client main hand
         */
        public int mainHand;

        /**
         * Client colors
         */
        public boolean colors;
        /**
         * Client cape enabled
         */
        public boolean cape;
        /**
         * Client jacket enabled
         */
        public boolean jacket;
        /**
         * Client left sleeve enabled
         */
        public boolean leftSleeve;
        /**
         * Client right sleeve enabled
         */
        public boolean rightSleeve;
        /**
         * Client left pant enabled
         */
        public boolean leftPant;
        /**
         * Client right pant enabled
         */
        public boolean rightPant;
        /**
         * Client hat enabled
         */
        public boolean hat;
    }
}
