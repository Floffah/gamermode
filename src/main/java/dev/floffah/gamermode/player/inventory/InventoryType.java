package dev.floffah.gamermode.player.inventory;

import lombok.Getter;

/**
 * Types of inventories
 */
public enum InventoryType {
    /**
     * Generic inventory with 1 row
     */
    GENERIC_9x1("minecraft:generic_9x1"),
    /**
     * Generic inventory with 2 rows
     */
    GENERIC_9x2("minecraft:generic_9x2"),
    /**
     * Generic inventory with 3 rows
     */
    GENERIC_9x3("minecraft:generic_9x3"),
    /**
     * Generic inventory with 4 rows
     */
    GENERIC_9x4("minecraft:generic_9x4"),
    /**
     * Generic inventory with 5 rows
     */
    GENERIC_9x5("minecraft:generic_9x5"),
    /**
     * Generic inventory with 6 rows
     */
    GENERIC_9x6("minecraft:generic_9x6"),
    /**
     * Inventory used by a dropper or dispenser
     */
    GENERIC_3x3("minecraft:generic_3x3"),
    /**
     * Inventory used by Anvils
     */
    ANVIL("minecraft:anvil"),
    /**
     * Inventory used by Beacons
     */
    BEACON("minecraft:beacon"),
    /**
     * Inventory used by Blast Furnaces
     */
    BLAST_FURNACE("minecraft:blast_furnace"),
    /**
     * Inventory used by Brewing Stands
     */
    BREWING_STAND("minecraft:brewing_stand"),
    /**
     * Inventory used by Crafting Tables
     */
    CRAFTING_TABLE("minecraft:crafting"),
    /**
     * Inventory used by Enchantment Tables
     */
    ENCHANTMENT_TABLE("minecraft:enchantment"),
    /**
     * Inventory used by Furnaces
     */
    FURNACE("minecraft:furnace"),
    /**
     * Inventory used by Grindstones
     */
    GRINDSTONE("minecraft:grindstone"),
    /**
     * Inventory used by Hoppers
     */
    HOPPER("minecraft:hopper"),
    /**
     * Inventory used by Lecterns
     */
    LECTERN("minecraft:lectern"),
    /**
     * Inventory used by Looms
     */
    LOOM("minecraft:loom"),
    /**
     * Inventory used by Villagers
     */
    VILLAGER("minecraft:merchant"),
    /**
     * Inventory used by Shulker Boxes
     */
    SHULKER_BOX("minecraft:shulker_box"),
    /**
     * Inventory used by Smokers
     */
    SMOKER("minecraft:smoker"),
    /**
     * Inventory used by Cartography Tables
     */
    CARTOGRAPHY_TABLE("minecraft:cartography"),
    /**
     * Inventory used by Stonecutters
     */
    STONECUTTER("minecraft:stonecutter"),
    /**
     * The player inventory
     */
    PLAYER(null);

    /**
     * Inventory identifier
     * -- GETTER --
     * Get the inventory type's identifier
     * @return The inventory identifier
     */
    @Getter
    String name;

    /**
     * Inventory type
     * @param name Inventory identifier
     */
    InventoryType(String name) {
        this.name = name;
    }
}
