package dev.floffah.gamermode.player.inventory;

import dev.floffah.gamermode.player.Player;

public class PlayerInventory extends BaseInventory{
    public PlayerInventory(Player player) {
        super(InventoryType.PLAYER, player.getUsername());
    }
}
