package com.null2264.storagenetwork.blockentity;

import com.null2264.storagenetwork.api.DimPos;
import com.null2264.storagenetwork.registry.BlockEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;

import java.util.Set;

public class MasterBlockEntity extends BlockEntity implements Tickable
{
    public MasterBlockEntity() {
        super(BlockEntityRegistry.MASTER_BLOCK_ENTITY);
    }

    // TODO: tick function that update "cables" every 200 ticks
    Set<?> cables;

    private DimPos getDimPos() {
        return new DimPos(world, pos);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        return super.toTag(tag);
    }

    public BlockEntity getCables(DimPos dimPos) {
        return null;
    }

    @Override
    public void tick() {
        if (world == null) {
            return;
        }
    }
}
