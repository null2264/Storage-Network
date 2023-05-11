package com.null2264.storagenetwork.screen;

import com.null2264.storagenetwork.blockentity.MasterBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public abstract class NetworkScreenHandler extends ScreenHandler
{
    public abstract MasterBlockEntity getMasterEntity();

    protected final CraftingResultInventory resultInv;
    protected boolean recipeLocked = false;
    protected CraftingRecipe currentRecipe;
    protected PlayerEntity player;
    protected ServerWorld world;
    public NetworkCraftingInventory craftingInv;

    protected NetworkScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
        this.resultInv = new CraftingResultInventory();
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return false;
    }

    protected void bindPlayerInv(PlayerInventory playerInventory) {
        for (int m = 0; m < 3; ++m) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 96 + m * 18));
            }
        }
    }

    public void bindHotbar(PlayerInventory playerInventory) {
        for (int m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142 + 3 * 18));
        }
    }

    protected void bindGrid(CraftingInventory craftingInventory) {
        int i = 0;
        for(int m = 0; m < 3; ++m) {
            for(int l = 0; l < 3; ++l) {
                this.addSlot(new Slot(craftingInventory, l + m * 3, 30 + l * 18, 17 + m * 18));
            }
        }
    }

    public abstract boolean isCrafting();

    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        if (this.isCrafting())
            return canInsertIntoSlot(stack, slot);
        return slot.inventory != this.resultInv && super.canInsertIntoSlot(stack, slot);
    }

    public void close(PlayerEntity player) {
        // slotChanged();
        super.close(player);
    }
    public void onContentChanged(Inventory inventory) {
        if (recipeLocked)
            return;
        super.onContentChanged(inventory);
        this.currentRecipe = null;
        updateResult(this.syncId, this.world, this.player, this.craftingInv, this.resultInv);
    }

    protected void updateResultClient(ServerWorld world, CraftingInventory craftingInventory) {
        Optional<CraftingRecipe> optional = world.getServer().getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingInventory, world);
        optional.ifPresent(craftingRecipe -> this.currentRecipe = craftingRecipe);
    }

    protected void updateResult(int syncId, ServerWorld world, PlayerEntity player, CraftingInventory craftingInventory, CraftingResultInventory resultInventory) {
        if (!world.isClient) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
            ItemStack itemStack = ItemStack.EMPTY;
            Optional<CraftingRecipe> optional = world.getServer().getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingInventory, world);
            if (optional.isPresent()) {
                CraftingRecipe craftingRecipe = optional.get();
                if (resultInventory.shouldCraftRecipe(world, serverPlayerEntity, craftingRecipe)) {
                    itemStack = craftingRecipe.craft(craftingInventory);
                    this.currentRecipe = craftingRecipe;
                }
            }

            resultInventory.setStack(0, itemStack);
            serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(syncId, 0, 0, itemStack));
        }
    }

    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        if (player.world.isClient)
            return itemStack;
        Slot slot = this.slots.get(index);
        if (slot.hasStack()) {
            ItemStack itemStack1 = slot.getStack();
            itemStack = itemStack1.copy();
            MasterBlockEntity masterBlockEntity = this.getMasterEntity();
            if (this.isCrafting() && index == 0) {
                craftShift(player, masterBlockEntity);
                return ItemStack.EMPTY;
            } else if (masterBlockEntity != null) {
                int remaining = 5; // = masterBlockEntity.insertStack(itemStack1, false);
                ItemStack itemStack2;
                if (remaining == 0)
                    itemStack2 = ItemStack.EMPTY;
                else {
                    itemStack2 = itemStack1.copy();
                    itemStack2.setCount(remaining);
                }
                slot.setStack(itemStack2);
                // detectAndSendChanges();
                List<ItemStack> list = masterBlockEntity.getItems();
                if (player instanceof ServerPlayerEntity) {
                    ServerPlayerEntity servPlayer = (ServerPlayerEntity) player;
                    // PacketRegistry.INSTANCE.sendTo(new StackRefreshClientMessage(list, new ArrayList<>()),
                    //    sp.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
                }
                if (itemStack2.isEmpty())
                    return ItemStack.EMPTY;
                slot.onTakeItem(player, itemStack1);
                return ItemStack.EMPTY;
            }
            if (itemStack1.getCount() == 0)
                return ItemStack.EMPTY;
            // else
            //     slot.onSlotChanged();
            if (itemStack1.getCount() == itemStack.getCount())
                return ItemStack.EMPTY;
            slot.onTakeItem(player, itemStack1);
        }
        return itemStack;
    }

    protected void craftShift(PlayerEntity player, MasterBlockEntity masterBlock) {
        // TODO: Add function
    }
}