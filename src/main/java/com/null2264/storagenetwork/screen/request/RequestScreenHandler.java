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
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RequestScreenHandler extends NetworkScreenHandler
{
    MasterBlockEntity masterBlockEntity;

    public RequestScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, null);
//        this.craftingInv = new NetworkCraftingInventory(this);
//        this.bindGrid(craftingInv);
        this.bindPlayerInv(playerInventory);
        this.bindHotbar(playerInventory);
    }

    public RequestScreenHandler(int syncId, PlayerInventory playerInventory, @Nullable MasterBlockEntity masterBlockEntity) {
        super(ZiroStorageNetwork.REQUEST_SCREEN_HANDLER, syncId);
        this.player = playerInventory.player;
        this.masterBlockEntity = masterBlockEntity;
        List<Inventory> inventories = List.of(new SimpleInventory(9));
        if (masterBlockEntity != null)
            inventories = masterBlockEntity.getInventories();
        this.bindInv(inventories);
    }

    protected void bindInv(List<Inventory> inventories) {
        Inventory inventory = inventories.get(0);
//        this.addSlot(new Slot(inventories.get(0), 0, 8, 174));
        int m;
        for(m = 0; m < 9; ++m) {
            this.addSlot(new Slot(inventory, m, 8 + m * 18, 20));
        }
    }

    @Override
    public MasterBlockEntity getMasterEntity() {
        return masterBlockEntity;
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