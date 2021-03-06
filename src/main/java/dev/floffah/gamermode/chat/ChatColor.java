package dev.floffah.gamermode.chat;

import java.awt.*;

public enum ChatColor {
    BLACK("black", "0", "#000000"),
    DARK_BLUE("dark_blue", "1", "#0000aa"),
    DARK_GREEN("dark_green", "2", "#00aa00"),
    DARK_CYAN("dark_cyan", "3", "#00aaaa"),
    DARK_RED("dark_red", "4", "#aa0000"),
    PURPLE("dark_purple", "5", "#aa00aa"),
    GOLD("gold", "6", "#ffaa00"),
    GRAY("gray", "7", "#aaaaaa"),
    DARK_GRAY("dark_gray", "8", "#555555"),
    BLUE("blue", "9", "#5555ff"),
    GREEN("green", "a", "#55ff55"),
    CYAN("aqua", "b", "#55ffff"),
    RED("red", "c", "#ff5555"),
    PINK("light_purple", "d", "#ff55ff"),
    YELLOW("yellow", "e", "#ffff55"),
    WHITE("white", "f", "#ffffff"),
    OBFUSCATED("obfuscated", "k"),
    BOLD("bold", "l"),
    STRIKETHROUGH("strikethrough", "m"),
    UNDERLINED("underline", "n"),
    ITALIC("italic", "o"),
    RESET("reset", "r");

    String name;
    String code;
    String hex;

    ChatColor(String name, String code, String hex) {
        this.name = name;
        this.code = code;
        this.hex = hex;
    }

    ChatColor(String name, String code) {
        this.name = name;
        this.code = code;
        this.hex = null;
    }
}
