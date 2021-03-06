package dev.floffah.gamermode.chat;

public class ChatColors {
    public static char COLOR_CHAR = '§';

    public static String legacyAmbersand(String message) {
        return message.replace('&', COLOR_CHAR);
    }

    public static String legacyAny(char replacer, String message) {
        return message.replace(replacer, COLOR_CHAR);
    }

    public static String legacyBasic(char replacer, String message) {
        return message.replace(Character.toString(replacer), "\\u00A7");
    }

    public static Component translateLegacy(String message, char colorchar) {
        if (message.contains("&")) message = legacyAmbersand(message);
        ComponentBuilder b = new ComponentBuilder("");
        boolean base = false;

        StringBuilder current = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
//            if (!current.toString().equals("")) {
//                b.getCurrent().text = current.toString();
//                current = new StringBuilder();
//                b.append("");
//            }
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

    public static Component translateLegacy(String message) {
        return translateLegacy(message, '&');
    }

    public static ChatColor translateSingleChar(char code) {
        for (ChatColor c : ChatColor.values()) {
            if (c.code.equals(String.valueOf(code))) {
                return c;
            }
        }
        return null;
    }
}
