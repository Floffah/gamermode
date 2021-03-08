package dev.floffah.gamermode.player.inventory;

public class Inventory extends BaseInventory {
    public int rows;

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
