package com.null2264.storagenetwork.blockentity.cables;

import com.null2264.storagenetwork.lib.DimPos;
import com.null2264.storagenetwork.registry.BlockEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class CableLinkBlockEntity extends CableBaseBlockEntity
{
    public CableLinkBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.CABLE_LINK_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        storagePos = new DimPos(tag);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        if (hasInventory())
            storagePos.putToNbt(nbt);
        super.writeNbt(nbt);
    }
}