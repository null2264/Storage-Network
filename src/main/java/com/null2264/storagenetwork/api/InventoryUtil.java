package com.null2264.storagenetwork.api;

import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class InventoryUtil
{
    public static BlockPos getInventoryPos(CompoundTag tag) {
        // Get inventory position if any from tag
        DimPos dimPos = new DimPos(tag);

        return dimPos.getBlockPos();
    }

    public static BlockPos getInventoryPos(World world, BlockPos pos) {
        // Get inventory position if any
        if (getInventory(world, pos) != null) {
            return pos;
        }
        return null;
    }

    public static Inventory getInventory(World world, CompoundTag tag) {
        // Get inventory from tag
        BlockPos pos = getInventoryPos(tag);
        return getInventory(world, pos);
    }

    public static Inventory getInventory(World world, BlockPos pos) {
        // Get inventory from a position
        if (!world.isClient) {
            try {
                return (Inventory) world.getBlockEntity(pos);
            } catch (ClassCastException e) {
                // e.printStackTrace();
            }
        }
        return null;
    }

    public static IntStream getAvailableSlots(Inventory inventory, Direction side) {
        return (inventory instanceof SidedInventory && side != null) ? IntStream.of(((SidedInventory)inventory).getAvailableSlots(side)) : IntStream.range(0, inventory.size());
    }

    public static ArrayList<ItemStack> getItems(Inventory inv, Direction direction) {
        // Get array of items from inv and return the array list
        ArrayList<ItemStack> items = new ArrayList<>();
        getAvailableSlots(inv, direction).forEach( i -> {
            ItemStack item = inv.getStack(i);
            if (!item.isEmpty()) {
                items.add(item);
            }
        });
        return items;
    }

    public static ArrayList<ItemStack> getItems(Inventory inv) {
        return getItems(inv, null);
    }
}
