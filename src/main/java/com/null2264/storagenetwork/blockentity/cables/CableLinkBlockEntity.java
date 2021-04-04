package com.null2264.storagenetwork.blockentity.cables;

import com.null2264.storagenetwork.api.DimPos;
import com.null2264.storagenetwork.registry.BlockEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundTag;

public class CableLinkBlockEntity extends CableBaseBlockEntity
{
    public CableLinkBlockEntity() {
        super(BlockEntityRegistry.CABLE_LINK_BLOCK_ENTITY);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        storagePos = new DimPos(tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        if (hasInventory())
            storagePos.toTag(tag);
        return super.toTag(tag);
    }
}
