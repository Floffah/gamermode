package dev.floffah.gamermode.chat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class ChatTest {
    public Component buildReference() {
        return new ComponentBuilder("hello ")
                .color(ChatColor.GOLD).bold(true).underline(true)
                .append("world ").italic(true).strikethrough(true).color(ChatColor.RED)
                .append("obfuscated").obfuscated(true).color(ChatColor.CYAN)
                .build();
    }

    @Test
    @DisplayName("Chat component builder test with extras")
    @Order(1)
    public void extraTest() {
        String built = buildReference().toString();
        System.out.println(built);
        Assertions.assertNotNull(built);
    }

    @Test
    @DisplayName("Chat componnet builder test using color code translation")
    @Order(2)
    public void translationTest() {
        String translated = ChatColors.translateLegacy("&6&l&nhello &c&o&mworld &b&kobfuscated", '&').toString();
        System.out.println(translated);
        Assertions.assertEquals(buildReference().toString(), translated);
    }
}
