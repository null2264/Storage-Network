package com.null2264.storagenetwork.screen.request;

import com.null2264.storagenetwork.ZiroStorageNetwork;
import com.null2264.storagenetwork.blockentity.MasterBlockEntity;
import com.null2264.storagenetwork.registry.ScreenRegistry;
import com.null2264.storagenetwork.screen.NetworkScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.CraftingScreenHandler;

import java.util.List;

public class RequestScreenHandler extends NetworkScreenHandler
{
    List<Inventory> inventories;

    public RequestScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ZiroStorageNetwork.REQUEST_SCREEN_HANDLER, syncId);
    }

    public RequestScreenHandler(int syncId, PlayerInventory playerInventory, List<Inventory> inventories) {
        this(syncId, playerInventory);
        this.inventories = inventories;
    }

    @Override
    public MasterBlockEntity getMasterEntity() {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public boolean isCrafting() {
        return false;
    }
}