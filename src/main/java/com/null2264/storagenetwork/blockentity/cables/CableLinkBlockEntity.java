package com.null2264.storagenetwork.blockentity.cables;

import com.null2264.storagenetwork.registry.BlockEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;

public class CableLinkBlockEntity extends BlockEntity
{
    public CableLinkBlockEntity() {
        super(BlockEntityRegistry.CABLE_LINK_BLOCK_ENTITY);
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
