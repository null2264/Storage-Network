package com.null2264.storagenetwork.blockentity;

import com.null2264.storagenetwork.api.DimPos;
import com.null2264.storagenetwork.registry.BlockEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;

// Idk if request block need entity yet, but i assume it need entity so...
public class RequestBlockEntity extends BlockEntity
{
    public RequestBlockEntity() {
        super(BlockEntityRegistry.REQUEST_BLOCK_ENTITY);
    }

    // masterPos will be set by Master Block's "block updates"
    public DimPos masterPos = null;

    public void setMasterPos(DimPos dimPos) {
        masterPos = dimPos;
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
