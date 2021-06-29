package com.null2264.storagenetwork.blockentity;

import com.null2264.storagenetwork.api.DimPos;
import com.null2264.storagenetwork.registry.BlockEntityRegistry;
import com.null2264.storagenetwork.screen.request.RequestScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;

public class RequestBlockEntity extends BlockEntity implements NamedScreenHandlerFactory
{
    public RequestBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.REQUEST_BLOCK_ENTITY, pos, state);
    }

    // masterPos will be set by Master Block's "block updates"
    public DimPos masterPos = null;

    public void setMasterPos(DimPos dimPos) {
        masterPos = dimPos;
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        //We provide *this* to the screenHandler as our class Implements Inventory
        //Only the Server has the Inventory at the start, this will be synced to the client in the ScreenHandler
        return new RequestScreenHandler(syncId, playerInventory, ((MasterBlockEntity)this.masterPos.getBlockEntity()).getInventories());
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        return super.writeNbt(tag);
    }
}
