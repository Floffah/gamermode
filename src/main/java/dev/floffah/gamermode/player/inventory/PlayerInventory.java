package dev.floffah.gamermode.player.inventory;

import dev.floffah.gamermode.player.Player;

/**
 * Inventory used to represent Player inventories
 */
public class PlayerInventory extends BaseInventory {
    /**
     * Construct a player inventory for a player
     *
     * @param player The player associated
     */
    public PlayerInventory(Player player) {
        super(InventoryType.PLAYER, player.getUsername());
    }
}
