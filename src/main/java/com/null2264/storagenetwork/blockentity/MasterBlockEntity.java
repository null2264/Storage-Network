package com.null2264.storagenetwork.blockentity;

import com.null2264.storagenetwork.api.DimPos;
import com.null2264.storagenetwork.registry.BlockEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;

public class MasterBlockEntity extends BlockEntity
{
    public MasterBlockEntity() {
        super(BlockEntityRegistry.MASTER_BLOCK_ENTITY);
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
