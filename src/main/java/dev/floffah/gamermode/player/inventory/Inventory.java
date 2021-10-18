package dev.floffah.gamermode.player.inventory;

import lombok.Getter;

/**
 * An inventory type that is used for inventory menus. Don't use this for chest inventories.
 */
public class Inventory extends BaseInventory {
    /**
     * How many rows the inventory has
     * -- GETTER --
     * Get how many rows the inventory has
     * @return The amount of rows the inventory has
     */
    @Getter
    int rows;

    /**
     * Construct an Inventory. Don't use this for chest inventories.
     * @param title The inventory title
     * @param rows How many rows the inventory has
     */
    public Inventory(String title, int rows) {
        super(InventoryType.GENERIC_9x6, title);
        this.rows = rows;
        if (rows == 1) this.type = InventoryType.GENERIC_9x1;
        else if (rows == 2) this.type = InventoryType.GENERIC_9x2;
        else if (rows == 3) this.type = InventoryType.GENERIC_9x3;
        else if (rows == 4) this.type = InventoryType.GENERIC_9x4;
        else if (rows == 5) this.type = InventoryType.GENERIC_9x5;
    }
}
