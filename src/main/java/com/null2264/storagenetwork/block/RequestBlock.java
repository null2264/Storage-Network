package com.null2264.storagenetwork.block;

import com.null2264.storagenetwork.blockentity.RequestBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

/*
Block where you pull/push/craft items
*/
public class RequestBlock extends ModBlockWithEntity
{
    public RequestBlock() {
        super(
            FabricBlockSettings
                .of(Material.METAL)
                .strength(4.0f)
        );
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new RequestBlockEntity();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
