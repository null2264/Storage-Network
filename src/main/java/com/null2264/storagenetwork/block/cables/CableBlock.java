package com.null2264.storagenetwork.block.cables;

import com.null2264.storagenetwork.blockentity.cables.CableBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class CableBlock extends BlockWithEntity
{
    public CableBlock() {
        super(FabricBlockSettings.of(Material.METAL).strength(4.0f).nonOpaque());
    }

    public BlockEntity createBlockEntity(BlockView world) {
        return new CableBlockEntity();
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx) {
        return VoxelShapes.cuboid(0.34f, 0.34f, 0.34f, 0.66f, 0.66f, 0.66f);
    }
}
