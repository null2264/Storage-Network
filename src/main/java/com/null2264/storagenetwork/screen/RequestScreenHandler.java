package com.null2264.storagenetwork.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.CraftingScreenHandler;

import java.util.Set;

public class RequestScreenHandler extends CraftingScreenHandler
{
    Set<Inventory> inventories;

    public RequestScreenHandler(int syncId, PlayerInventory playerInventory, Set<Inventory> inventorySet) {
        super(syncId, playerInventory);
        inventories = inventorySet;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}