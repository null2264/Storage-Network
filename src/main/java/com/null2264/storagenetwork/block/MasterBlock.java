package com.null2264.storagenetwork.block;

import com.null2264.storagenetwork.blockentity.MasterBlockEntity;
import com.null2264.storagenetwork.blockentity.cables.CableBaseBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

import java.util.Set;

public class MasterBlock extends ModBlockWithEntity
{
    public MasterBlock(Settings settings) {
        super(settings);
    }

    // TODO: tick function that update "cables" every 20 ticks
    Set<?> cables;

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new MasterBlockEntity();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}