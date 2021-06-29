package com.null2264.storagenetwork.blockentity.cables;

import com.null2264.storagenetwork.api.DimPos;
import com.null2264.storagenetwork.registry.BlockEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;

public class CableLinkBlockEntity extends CableBaseBlockEntity
{
    public CableLinkBlockEntity() {
        super(BlockEntityRegistry.CABLE_LINK_BLOCK_ENTITY);
    }

    @Override
    public void readNbt(BlockState state, NbtCompound tag) {
        super.readNbt(state, tag);
        storagePos = new DimPos(tag);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        if (hasInventory())
            storagePos.toTag(tag);
        return super.writeNbt(tag);
    }
}
