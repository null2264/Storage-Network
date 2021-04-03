package com.null2264.storagenetwork.blockentity.cables;

import com.null2264.storagenetwork.api.DimPos;
import com.null2264.storagenetwork.registry.BlockEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class CableLinkBlockEntity extends BlockEntity
{
    private boolean hasMaster;
    private DimPos storagePos;

    public CableLinkBlockEntity() {
        super(BlockEntityRegistry.CABLE_LINK_BLOCK_ENTITY);
    }

    public boolean hasInventory(DimPos dimPos) {
        /* Check if link cable already has inventory */
        BlockPos pos = dimPos.getBlockPos();
        BlockPos invalidPos = new BlockPos(0, 0, 0);
        return !Objects.equals(pos, invalidPos);
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
