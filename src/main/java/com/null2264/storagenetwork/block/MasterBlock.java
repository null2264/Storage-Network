package com.null2264.storagenetwork.block;

import com.null2264.storagenetwork.block.entity.MasterBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.ArrayList;

public class MasterBlock extends BlockWithEntity
{
    private Inventory inv;

    public MasterBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new MasterBlockEntity();
    }

    public void getItems() {
        if (inv != null) {
            ArrayList<ItemStack> items = new ArrayList<>();
            int i;
            for (i = 0; i < inv.size(); i++) {
                ItemStack item = inv.getStack(i);
                if (!item.isEmpty())
                    items.add(item);
            }
            if (!items.isEmpty())
                for (ItemStack item:items)
                    System.out.printf("%s: %s (%s)%n", inv.size(), item.getName().getString(), item.getCount());
        }
    }

    public void getInventory(World world, BlockPos pos) {
        if (!world.isClient) {
            this.inv = (Inventory) world.getBlockEntity(pos);
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack item) {

    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        /* Get neighbor's inventory if exist when it placed */
        getInventory(world, fromPos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            getItems();
        }
        return ActionResult.SUCCESS;
    }
}
