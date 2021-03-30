package com.null2264.storagenetwork.block;

import com.null2264.storagenetwork.block.entity.TestBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class TestBlock extends BlockWithEntity
{
    public TestBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new TestBlockEntity();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            // NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
            Inventory blockEntity = (Inventory) world.getBlockEntity(pos);

            System.out.println("onUse");
            if (!player.getStackInHand(hand).isEmpty()) {
                // Check what is the first open slot and put an item from the player's hand there
                if (blockEntity.getStack(0).isEmpty()) {
                    // Put the stack the player is holding into the inventory
                    blockEntity.setStack(0, player.getStackInHand(hand).copy());
                    // Remove the stack from the player's hand
                    player.getStackInHand(hand).setCount(0);
                } else if (blockEntity.getStack(0).isItemEqual(player.getStackInHand(hand))) {
                    ItemStack blockStack = blockEntity.getStack(0);
                    if (!(blockStack.getCount() >= 64)) {
                        ItemStack playerStack = player.getStackInHand(hand);
                        int playerAndBlockStackCount = blockStack.getCount() + playerStack.getCount();
                        int realCount = 0;

                        if (playerAndBlockStackCount > 64)
                            realCount = playerAndBlockStackCount - 64;
                        blockStack.setCount(blockStack.getCount() + realCount);

                        playerStack.setCount(realCount);
                    }
                }
//                if (blockEntity.getStack(1).isEmpty()) {
//                    blockEntity.setStack(1, player.getStackInHand(hand).copy());
//                    player.getStackInHand(hand).setCount(0);
//                } else {
//                    // If the inventory is full we'll print it's contents
//                    System.out.println("The first slot holds "
//                            + blockEntity.getStack(0) + " and the second slot holds " + blockEntity.getStack(1));
//                }
            }

            /*
            if (screenHandlerFactory != null) {
                System.out.println("onUse Screen");
                player.openHandledScreen(screenHandlerFactory);
            }
            */
        }
        return ActionResult.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }
}
