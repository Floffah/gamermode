package dev.floffah.gamermode.player.inventory;

public enum InventoryType {
    GENERIC_9x1("minecraft:generic_9x1"),
    GENERIC_9x2("minecraft:generic_9x2"),
    GENERIC_9x3("minecraft:generic_9x3"),
    GENERIC_9x4("minecraft:generic_9x4"),
    GENERIC_9x5("minecraft:generic_9x5"),
    GENERIC_9x6("minecraft:generic_9x6"),
    GENERIC_3x3("minecraft:generic_3x3"),
    ANVIL("minecraft:anvil"),
    BEACON("minecraft:beacon"),
    BLAST_FURNACE("minecraft:blast_furnace"),
    BREWING_STAND("minecraft:brewing_stand"),
    CRAFTING_TABLE("minecraft:crafting"),
    ENCHANTMENT_TABLE("minecraft:enchantment"),
    FURNACE("minecraft:furnace"),
    GRINDSTONE("minecraft:grindstone"),
    HOPPER("minecraft:hopper"),
    LECTERN("minecraft:lectern"),
    LOOM("minecraft:loom"),
    VILLAGER("minecraft:merchant"),
    SHULKER_BOX("minecraft:shulker_box"),
    SMOKER("minecraft:smoker"),
    CARTOGRAPHY_TABLE("minecraft:cartography"),
    STONECUTTER("minecraft:stonecutter"),
    PLAYER(null);

    public String name;

    InventoryType(String name) {
        this.name = name;
    }
}
