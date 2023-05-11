package com.null2264.storagenetwork.blockentity.cables;

import com.null2264.storagenetwork.lib.DimPos;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class CableBaseBlockEntity extends BlockEntity
{
    public CableBaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public DimPos storagePos = null;
    public DimPos masterPos = null;

    public boolean isInventoryValid() {
        return !storagePos.equals(DimPos.INVALID);
    }

    public boolean hasInventory() {
        /* Check if link cable already has inventory */
        if (storagePos == null)
            return false;
        return isInventoryValid();
    }

    public void setMasterPos(DimPos dimPos) {
        masterPos = dimPos;
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