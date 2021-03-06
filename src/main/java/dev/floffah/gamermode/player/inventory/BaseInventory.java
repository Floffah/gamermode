package dev.floffah.gamermode.player.inventory;

public class BaseInventory {
    public static int id = 0;

    public int invId;
    public String title;
    public InventoryType type;

    public BaseInventory(InventoryType type, String title) {
        BaseInventory.id++;
        this.invId = BaseInventory.id;
        this.title = title;
        this.type = type;
        // never do processing based in the type here as other inventory implementations such as Inventory change the type in its constructor.
    }
}
