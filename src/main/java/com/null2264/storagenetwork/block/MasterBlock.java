package com.null2264.storagenetwork.block;

import com.null2264.storagenetwork.blockentity.MasterBlockEntity;
import com.null2264.storagenetwork.blockentity.cables.CableBaseBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Tickable;
import net.minecraft.world.BlockView;

import java.util.Set;

public class MasterBlock extends ModBlockWithEntity
{
    public MasterBlock() {
        super(FabricBlockSettings.of(Material.METAL).strength(4.0f));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new MasterBlockEntity();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}