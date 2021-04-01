package com.null2264.storagenetwork.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/* Dimension Position, similar to BlockPos but also keep dimension data*/
public class DimPos
{
    private String dimension;
    private BlockPos pos = new BlockPos(0, 0, 0);
    private World world;

    public DimPos(CompoundTag tag) {
        // Fetch DimPos from nbt tag
        int x = tag.getInt("X");
        int y = tag.getInt("Y");
        int z = tag.getInt("Z");
        pos = new BlockPos(x, y, z);
        dimension = tag.getString("dimension");
    }

    public DimPos(World world, BlockPos pos) {
        // Fetch DimPos from world and blockpos
        this.pos = pos;
        this.world = world;
        if (world != null)
            dimension = dimToString(world);
    }

    public String dimToString(World world) {
        // Turn dimension into string value
        return world.getDimension().toString();
    }

    @Nullable
    public World getWorld() {
        return world;
    }

    public BlockPos getBlockPos() {
        return pos;
    }
}