package com.null2264.storagenetwork.api;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
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
        return new DimPos(tag).getBlockPos();
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
        return getInventory(world, getInventoryPos(tag));
    }

    public static Inventory getInventory(World world, BlockPos pos) {
        // Get inventory from a position
        Inventory inventory = null;
        if (!world.isClient) {
            BlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            if (block instanceof InventoryProvider) {
                inventory = ((InventoryProvider)block).getInventory(state, world, pos);
            } else if (block.hasBlockEntity()) {
                BlockEntity entity = world.getBlockEntity(pos);
                if (entity instanceof Inventory) {
                    inventory = (Inventory)entity;
                    if (inventory instanceof ChestBlockEntity && block instanceof ChestBlock)
                        inventory = ChestBlock.getInventory((ChestBlock)block, state, world, pos, true);
                }
            }
        }
        return inventory;
    }

    public static IntStream getAvailableSlots(Inventory inventory, Direction side) {
        return (inventory instanceof SidedInventory && side != null) ? IntStream.of(((SidedInventory)inventory).getAvailableSlots(side)) : IntStream.range(0, inventory.size());
    }

    public static ArrayList<ItemStack> getItems(Inventory inv, Direction direction) {
        // Get array of items from inv and return the array list
        ArrayList<ItemStack> items = new ArrayList<>();
        IntStream slots = getAvailableSlots(inv, direction);
        System.out.println(slots);
        slots.forEach( i -> {
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
