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

    DimPos dimPos = null;

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        dimPos = new DimPos(tag);
        System.out.println(tag.toString());
        super.fromTag(state, tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        if (dimPos != null)
            dimPos.toTag(tag);
        System.out.println(tag.toString());
        return super.toTag(tag);
    }
}
