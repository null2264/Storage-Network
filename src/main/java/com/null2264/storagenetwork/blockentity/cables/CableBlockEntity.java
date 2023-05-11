package com.null2264.storagenetwork.blockentity.cables;

import com.null2264.storagenetwork.registry.BlockEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class CableBlockEntity extends CableBaseBlockEntity
{
    public CableBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.CABLE_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
    }
}