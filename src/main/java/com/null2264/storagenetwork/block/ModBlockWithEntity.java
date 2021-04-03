package com.null2264.storagenetwork.block;

import com.null2264.storagenetwork.api.DimPos;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import static net.minecraft.entity.ItemEntity.merge;

import java.util.ArrayList;

public class ModBlockWithEntity extends BlockWithEntity
{
    protected ModBlockWithEntity(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return null;
    }
}
