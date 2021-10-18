package dev.floffah.gamermode.player.inventory;

import lombok.Getter;
import lombok.Setter;

/**
 * Base inventory class that other inventory types extend upon
 */
public class BaseInventory {
    /**
     * ID that increments every time a new inventory is made
     */
    public static int id = 0;
    /**
     * The ID of the current inventory
     * -- GETTER --
     * Get the ID of the current inventory
     *
     * @return The ID of the current inventory
     */
    @Getter
    int invId;
    /**
     * The inventory's title
     * -- GETTER --
     * Get the inventory's title
     *
     * @return The inventory's title
     * -- SETTER --
     * Set the inventory's title
     * @param title The new title
     */
    @Getter
    @Setter
    String title;
    /**
     * The inventory type
     * -- GETTER --
     * Get the inventory type
     * @return The inventory type
     */
    @Getter
    InventoryType type;

    /**
     * Construct a base inventory<br/>
     * Do not use this unless you are extending upon this
     * @param type Inventory type
     * @param title Inventory title
     */
    public BaseInventory(InventoryType type, String title) {
        BaseInventory.id++;
        this.invId = BaseInventory.id;
        this.title = title;
        this.type = type;
        // never do processing based in the type here as other inventory implementations such as Inventory change the type in its constructor.
    }
}
