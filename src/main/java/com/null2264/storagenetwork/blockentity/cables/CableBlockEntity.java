package com.null2264.storagenetwork.blockentity.cables;

import com.null2264.storagenetwork.api.DimPos;
import com.null2264.storagenetwork.block.cables.CableBlock;
import com.null2264.storagenetwork.registry.BlockEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

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
    public NbtCompound writeNbt(NbtCompound tag) {
        return super.writeNbt(tag);
    }
}
