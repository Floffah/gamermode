package dev.floffah.gamermode.chat;

import lombok.Getter;

/**
 * Predefined chat colors/formats
 */
public enum ChatColor {
    /**
     * Color 0 black
     */
    BLACK("black", "0", "#000000", 0x00),
    /**
     * Color 1 dark blue
     */
    DARK_BLUE("dark_blue", "1", "#0000aa", 0x01),
    /**
     * Color 2 dark green
     */
    DARK_GREEN("dark_green", "2", "#00aa00", 0x02),
    /**
     * Color 3 dark cyan (dark aqua)
     */
    DARK_CYAN("dark_cyan", "3", "#00aaaa", 0x04),
    /**
     * Color 4 dark red
     */
    DARK_RED("dark_red", "4", "#aa0000", 0x04),
    /**
     * Color 5 dark purple
     */
    PURPLE("dark_purple", "5", "#aa00aa", 0x05),
    /*
     * Color 6 gold
     */
    GOLD("gold", "6", "#ffaa00", 0x06),
    /**
     * Color 7 gray
     */
    GRAY("gray", "7", "#aaaaaa", 0x07),
    /**
     * Color 8 dark gray
     */
    DARK_GRAY("dark_gray", "8", "#555555", 0x08),
    /**
     * Color 9 blue
     */
    BLUE("blue", "9", "#5555ff", 0x09),
    /**
     * Color a green
     */
    GREEN("green", "a", "#55ff55", 0x0A),
    /**
     * Color b aqua
     */
    CYAN("aqua", "b", "#55ffff", 0x0B),
    /**
     * Color c red
     */
    RED("red", "c", "#ff5555", 0x0C),
    /**
     * Color d light_purple
     */
    PINK("light_purple", "d", "#ff55ff", 0x0D),
    /**
     * Color e yellow
     */
    YELLOW("yellow", "e", "#ffff55", 0x0E),
    /**
     * Color f white
     */
    WHITE("white", "f", "#ffffff", 0x0F),
    /**
     * Format k magic
     */
    OBFUSCATED("obfuscated", "k", 0x10),
    /**
     * Format l bold
     */
    BOLD("bold", "l", 0x11),
    /**
     * Format m strikethrough
     */
    STRIKETHROUGH("strikethrough", "m", 0x12),
    /**
     * Format n underline
     */
    UNDERLINED("underline", "n", 0x13),
    /**
     * Format o italic
     */
    ITALIC("italic", "o", 0x14),
    /**
     * r reset
     */
    RESET("reset", "r", 0x15);

    /**
     * Color/format name
     * -- GETTER --
     * Get the color/format's name
     *
     * @return The color/format's name
     */
    @Getter
    String name;
    /**
     * Color/format code
     * -- GETTER --
     * Get the color/format's code
     *
     * @return The color/format's code
     */
    @Getter
    String code;
    /**
     * Color hex code
     * -- GETTER --
     * Get the color's hex code
     *
     * @return The color/format's hex code
     */
    @Getter
    String hex;
    /**
     * Color/format's byte value
     * -- GETTER --
     * Get the color/format's byte value
     *
     * @return The color/format's byte value
     */
    @Getter
    int cint;

    /**
     * Construct a chat color
     * @param name The color's name
     * @param code The color's code
     * @param hex The color's hex code
     * @param cint the color's byte value
     */
    ChatColor(String name, String code, String hex, int cint) {
        this.name = name;
        this.code = code;
        this.hex = hex;
        this.cint = cint;
    }

    /**
     * Construct a chat format
     * @param name The format's name
     * @param code The format's code
     * @param cint The format's byte value
     */
    ChatColor(String name, String code, int cint) {
        this.name = name;
        this.code = code;
        this.hex = null;
        this.cint = cint;
    }
}
