package com.null2264.storagenetwork.screen.request;

import com.null2264.storagenetwork.ZiroStorageNetwork;
import com.null2264.storagenetwork.blockentity.MasterBlockEntity;
import com.null2264.storagenetwork.screen.NetworkScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

import java.util.List;

public class RequestScreenHandler extends NetworkScreenHandler
{
    List<Inventory> inventories;

    public RequestScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, List.of(new SimpleInventory(9)));
//        this.craftingInv = new NetworkCraftingInventory(this);
//        this.bindGrid(craftingInv);
        this.bindHotbar(playerInventory);
    }

    public RequestScreenHandler(int syncId, PlayerInventory playerInventory, List<Inventory> inventories) {
        super(ZiroStorageNetwork.REQUEST_SCREEN_HANDLER, syncId);
        this.player = playerInventory.player;
        this.inventories = inventories;
        this.bindInv(inventories);
    }

    protected void bindInv(List<Inventory> inventories) {
        Inventory inventory = inventories.get(0);
//        this.addSlot(new Slot(inventories.get(0), 0, 8, 174));
        int m;
        for(m = 0; m < 5; ++m) {
            this.addSlot(new Slot(inventory, m, 44 + m * 18, 20));
        }
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