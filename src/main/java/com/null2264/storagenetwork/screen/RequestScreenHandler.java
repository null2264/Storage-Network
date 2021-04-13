package com.null2264.storagenetwork.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.CraftingScreenHandler;

import java.util.List;

public class RequestScreenHandler extends CraftingScreenHandler
{
    List<Inventory> inventories;

    public RequestScreenHandler(int syncId, PlayerInventory playerInventory, List<Inventory> inventories) {
        super(syncId, playerInventory);
        this.inventories = inventories;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}