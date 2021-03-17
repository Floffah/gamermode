package dev.floffah.gamermode.chat;

import javax.annotation.Nullable;

/**
 * Chat util
 */
public class ChatColors {
    /**
     * The color char
     */
    public static char COLOR_CHAR = '§';

    /**
     * Replace all ambersands in a string with the color char
     * @param message Message to replace from
     * @return New message with color chars
     */
    public static String legacyAmbersand(String message) {
        return message.replace('&', COLOR_CHAR);
    }

    /**
     * Same as {@link dev.floffah.gamermode.chat.ChatColors#legacyAmbersand(String)} except you pass a character to replace with the color char
     * @param replacer Character to replace with the color char
     * @param message Message to replace in
     * @return New message with color chars
     */
    public static String legacyAny(char replacer, String message) {
        return message.replace(replacer, COLOR_CHAR);
    }

    /**
     * Same as {@link dev.floffah.gamermode.chat.ChatColors#legacyAny(char, String)} except replaces it with the unicode name for the color char (used for motds)
     * @param replacer Character to replace with the unicode color char
     * @param message Message to replace in
     * @return New message with unicode color char
     */
    public static String legacyBasic(char replacer, String message) {
        return message.replace(Character.toString(replacer), "\\u00A7");
    }

    /**
     * Translates/parses a message with a specific color char into a component
     * @param message Message to parse
     * @param colorchar Color char to parse
     * @return Constructed component
     */
    public static Component translateLegacy(String message, char colorchar) {
        if (message.contains("&")) message = legacyAmbersand(message);
        ComponentBuilder b = new ComponentBuilder("");

        StringBuilder current = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (c == COLOR_CHAR) {
                if (i + 1 > message.length()) break;
                i++;
                char co = Character.toLowerCase(message.charAt(i));
                // todo: support hex colours
                ChatColor color = translateSingleChar(co);
                if (color == null) {
                    current.append(c);
                    current.append(co);
                    continue;
                }
                if (color == ChatColor.RESET) color = ChatColor.WHITE;
                else if (color == ChatColor.OBFUSCATED) b.obfuscated(true);
                else if (color == ChatColor.BOLD) b.bold(true);
                else if (color == ChatColor.STRIKETHROUGH) b.strikethrough(true);
                else if (color == ChatColor.UNDERLINED) b.underline(true);
                else if (color == ChatColor.ITALIC) b.italic(true);
                else {
                    if (!current.toString().equals("")) {
                        b.getCurrent().text = current.toString();
                        current = new StringBuilder();
                        b.append("");
                    }
                    b.color(color);
                }
            } else {
                current.append(c);
            }
        }

        if (!current.toString().equals("")) {
            b.getCurrent().text = current.toString();
        }

        return b.build();
    }

    /**
     * Same as {@link dev.floffah.gamermode.chat.ChatColors#translateLegacy(String, char)} )}
     * @param message Message to parse
     * @return Constructed Component
     */
    public static Component translateLegacy(String message) {
        return translateLegacy(message, '&');
    }

    /**
     * Get the chatcolor of a specific color char
     * @param code Color char to search
     * @return The color char
     */
    @Nullable
    public static ChatColor translateSingleChar(char code) {
        for (ChatColor c : ChatColor.values()) {
            if (c.code.equals(String.valueOf(code))) {
                return c;
            }
        }
        return null;
    }
}
