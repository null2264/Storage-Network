package com.null2264.storagenetwork.lib;

import com.google.common.base.Objects;
import com.null2264.storagenetwork.ZiroStorageNetwork;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/* Dimension Position, similar to BlockPos but also keep dimension data*/
public class DimPos
{
    public static DimPos INVALID = new DimPos(null, new BlockPos(0, World.MIN_Y, 0), "");
    private String dimension;
    private BlockPos pos = new BlockPos(0, 0, 0);
    private World world;

    public DimPos(NbtCompound nbt) {
        // Fetch DimPos from nbt nbt
        getFromNbt(nbt);
    }

    public DimPos(World world, BlockPos pos) {
        // Fetch DimPos from world and blockpos
        this(world, pos, dimToString(world));
    }

    public DimPos(World world, BlockPos pos, String dimension) {
        this.pos = pos;
        this.world = world;
        this.dimension = dimension;
    }

    public static String dimToString(World world) {
        // Turn dimension into string value
        return world.getRegistryKey().getValue().toString();
    }

    @Nullable
    public World getWorld() {
        return world;
    }

    public BlockPos getBlockPos() {
        return pos;
    }

    public void putToNbt(NbtCompound nbt) {
        nbt.putInt("X", getBlockPos().getX());
        nbt.putInt("Y", getBlockPos().getY());
        nbt.putInt("Z", getBlockPos().getZ());
        nbt.putString("dimension", dimension);
    }

    public void getFromNbt(NbtCompound nbt) {
        int x = nbt.getInt("X");
        int y = nbt.getInt("Y");
        int z = nbt.getInt("Z");
        this.pos = new BlockPos(x, y, z);
        this.dimension = nbt.getString("dimension");
    }

    public DimPos offset(Direction direction) {
        BlockPos pos = getBlockPos();
        if (pos == null || direction == null) {
            ZiroStorageNetwork.LOGGER.info("Error: null offset in DimPos " + direction);
            return null;
        }
        return new DimPos(getWorld(), pos.offset(direction));
    }

    public BlockEntity getBlockEntity() {
        BlockPos pos = getBlockPos();
        World world = getWorld();
        if (pos != null && world != null)
            return world.getBlockEntity(pos);
        return null;
    }

    public BlockState getBlockState() {
        BlockPos pos = getBlockPos();
        World world = getWorld();
        if (pos != null && world != null)
            return world.getBlockState(pos);
        return null;
    }

    public boolean equals(BlockPos pos) {
        return pos.equals(this.pos);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DimPos dimPos = (DimPos) o;
        return dimension.equals(dimPos.dimension) &&
            Objects.equal(pos, dimPos.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dimension, pos);
    }

    @Override
    public String toString() {
        return "[ BlockPos = " + getBlockPos().toString() + ", dimension = " + dimension + ", world = " + (getWorld() != null ? getWorld().toString() : null);
    }
}