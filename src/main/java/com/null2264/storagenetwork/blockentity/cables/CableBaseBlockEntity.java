package com.null2264.storagenetwork.blockentity.cables;

import com.null2264.storagenetwork.api.DimPos;
import com.null2264.storagenetwork.registry.BlockEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class CableBaseBlockEntity extends BlockEntity
{
    public CableBaseBlockEntity(BlockEntityType<?> type) {
        super(BlockEntityRegistry.CABLE_BLOCK_ENTITY);
    }

    public DimPos storagePos = null;

    public boolean isValidInventory(BlockPos blockPos) {
        BlockPos invalidPos = new BlockPos(0, 0, 0);
        return !Objects.equals(blockPos, invalidPos);
    }

    public boolean hasInventory() {
        /* Check if link cable already has inventory */
        if (storagePos == null)
            return false;
        BlockPos pos = storagePos.getBlockPos();
        return isValidInventory(pos);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        return super.toTag(tag);
    }
}
