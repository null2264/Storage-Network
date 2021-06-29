package com.null2264.storagenetwork.blockentity.cables;

import com.null2264.storagenetwork.api.DimPos;
import com.null2264.storagenetwork.api.MiscUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class CableBaseBlockEntity extends BlockEntity
{
    public CableBaseBlockEntity(BlockEntityType<?> type) {
        super(type);
    }

    public DimPos storagePos = null;
    public DimPos masterPos = null;

    public boolean isValidInventory(BlockPos blockPos) {
        return !Objects.equals(blockPos, MiscUtil.invalidPos);
    }

    public boolean hasInventory() {
        /* Check if link cable already has inventory */
        if (storagePos == null)
            return false;
        BlockPos pos = storagePos.getBlockPos();
        return isValidInventory(pos);
    }

    public void setMasterPos(DimPos dimPos) {
        masterPos = dimPos;
    }

    @Override
    public void readNbt(BlockState state, NbtCompound tag) {
        super.readNbt(state, tag);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        return super.writeNbt(tag);
    }
}
