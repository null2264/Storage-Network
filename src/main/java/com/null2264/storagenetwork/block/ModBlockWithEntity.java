package com.null2264.storagenetwork.block;

import com.null2264.storagenetwork.api.DimPos;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class ModBlockWithEntity extends BlockWithEntity
{
    protected ModBlockWithEntity(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return null;
    }

    public BlockPos getInventoryPos(World world, BlockPos pos) {
        // Get inventory position if any
        if (!world.isClient) {
            //noinspection RedundantCast
            if ((Inventory) world.getBlockEntity(pos) != null) {
                return pos;
            }
        }
        return null;
    }

    public BlockPos getInventoryPos(CompoundTag tag) {
        // Get inventory position if any from tag
        DimPos dimPos = new DimPos(tag);

        return dimPos.getBlockPos();
    }

    public ArrayList<ItemStack> getItems(Inventory inv) {
        // Get array of items from inv and return the array list
        ArrayList<ItemStack> items = new ArrayList<>();
        if (inv != null) {
            for (int i = 0; i < inv.size(); i++) {
                ItemStack item = inv.getStack(i);
                if (!item.isEmpty())
                    items.add(item);
            }
        }
        return items;
    }

    public Inventory getInventory(World world, CompoundTag tag) {
        // Get inventory from tag
        if (!world.isClient) {
            BlockPos pos = getInventoryPos(tag);
            try {
                return (Inventory) world.getBlockEntity(pos);
            } catch (ClassCastException e) {
                // e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public Inventory getInventory(World world, BlockPos pos) {
        // Get inventory from a position
        if (!world.isClient) {
            try {
                return (Inventory) world.getBlockEntity(pos);
            } catch (ClassCastException e) {
                // e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
