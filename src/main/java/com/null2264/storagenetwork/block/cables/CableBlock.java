package com.null2264.storagenetwork.block.cables;

import com.google.common.collect.Maps;
import com.null2264.storagenetwork.Tags;
import com.null2264.storagenetwork.block.ModBlockWithEntity;
import com.null2264.storagenetwork.blockentity.cables.CableBlockEntity;
import com.null2264.storagenetwork.registry.BlockRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.Map;
import java.util.Objects;

public class CableBlock extends ModBlockWithEntity
{
    public static final BooleanProperty NORTH = ConnectingBlock.NORTH;
    public static final BooleanProperty EAST = ConnectingBlock.EAST;
    public static final BooleanProperty SOUTH = ConnectingBlock.SOUTH;
    public static final BooleanProperty WEST = ConnectingBlock.WEST;
    public static final BooleanProperty UP = ConnectingBlock.UP;
    public static final BooleanProperty DOWN = ConnectingBlock.DOWN;

    protected static final Map<Direction, BooleanProperty> FACING_PROPERTIES = Util.make(Maps.newEnumMap(Direction.class), (enumMap) -> {
        enumMap.put(Direction.NORTH, NORTH);
        enumMap.put(Direction.EAST, EAST);
        enumMap.put(Direction.SOUTH, SOUTH);
        enumMap.put(Direction.WEST, WEST);
        enumMap.put(Direction.UP, UP);
        enumMap.put(Direction.DOWN, DOWN);
    });

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
        VoxelShape centerShape = VoxelShapes.cuboid(0.34f, 0.34f, 0.34f, 0.66f, 0.66f, 0.66f);
        VoxelShape northShape = VoxelShapes.cuboid(0.43f, 0.43f, 0.0f, 0.57f, 0.57f, 0.57f);
        VoxelShape southShape = VoxelShapes.cuboid(0.43f, 0.43f, 0.57f, 0.57f, 0.57f, 1.0f);
        VoxelShape westShape = VoxelShapes.cuboid(0.0f, 0.43f, 0.43f, 0.57f, 0.57f, 0.57f);
        VoxelShape eastShape = VoxelShapes.cuboid(0.57f, 0.43f, 0.43f, 1.0f, 0.57f, 0.57f);
        VoxelShape upShape = VoxelShapes.cuboid(0.43f, 0.57f, 0.43f, 0.57f, 1.0f, 0.57f);
        VoxelShape downShape = VoxelShapes.cuboid(0.43f, 0.0f, 0.43f, 0.57f, 0.57f, 0.57f);

        VoxelShape shape = centerShape;
        for ( Direction direction : Direction.values() ) {
            BooleanProperty booleanProperty = FACING_PROPERTIES.get(direction);
            if (!state.get(booleanProperty))
                continue;

            if (NORTH.equals(booleanProperty)) {
                shape = VoxelShapes.union(shape, northShape);
            } else if (SOUTH.equals(booleanProperty)) {
                shape = VoxelShapes.union(shape, southShape);
            } else if (WEST.equals(booleanProperty)) {
                shape = VoxelShapes.union(shape, westShape);
            } else if (EAST.equals(booleanProperty)) {
                shape = VoxelShapes.union(shape, eastShape);
            } else if (UP.equals(booleanProperty)) {
                shape = VoxelShapes.union(shape, upShape);
            } else if (DOWN.equals(booleanProperty)) {
                shape = VoxelShapes.union(shape, downShape);
            }
        }
        return shape;
    }

    public boolean canConnect(WorldAccess world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        return block.isIn(Tags.CABLES) || block.is(BlockRegistry.MASTER_BLOCK) || block.is(BlockRegistry.REQUEST_BLOCK);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        WorldAccess world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();

        return Objects.requireNonNull(super.getPlacementState(ctx))
            .with(NORTH, canConnect(world, pos.north()))
            .with(EAST, canConnect(world, pos.east()))
            .with(SOUTH, canConnect(world, pos.south()))
            .with(WEST, canConnect(world, pos.west()))
            .with(UP, canConnect(world, pos.up()))
            .with(DOWN, canConnect(world, pos.down()));
    }

    @SuppressWarnings("deprecation")
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        return state.with(FACING_PROPERTIES.get(direction), canConnect(world, posFrom));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, WEST, SOUTH, UP, DOWN);
    }
}
