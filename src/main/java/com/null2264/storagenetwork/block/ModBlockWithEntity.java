package com.null2264.storagenetwork.block;

import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

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
