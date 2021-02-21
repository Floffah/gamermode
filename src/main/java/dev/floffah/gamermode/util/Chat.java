package dev.floffah.gamermode.util;

public class Chat {
    public static String translateToBasic(char replacer, String message) {
        return message.replace(Character.toString(replacer), "\\u00A7");
    }
}
