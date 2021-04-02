package com.null2264.storagenetwork.block;

import com.null2264.storagenetwork.api.DimPos;
import com.null2264.storagenetwork.blockentity.MasterBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.ArrayList;

public class MasterBlock extends ModBlockWithEntity
{
    public MasterBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new MasterBlockEntity();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack item) {
        if (!world.isClient) {
            // Get neighbor block's inventory upon placing
            BlockEntity selfEntity = world.getBlockEntity(pos);
            CompoundTag selfTag = new CompoundTag();
            if (selfEntity != null)
                selfTag = selfEntity.toTag(selfTag);

            BlockPos[] positions = new BlockPos[]{
                pos.up(), pos.down(),
                pos.north(), pos.south(),
                pos.west(), pos.east()
            };
            for (BlockPos position : positions) {
                DimPos invPos;
                if (getInventoryPos(world, position) != null) {
                    invPos = new DimPos(world, position);
                    selfEntity.fromTag(state, invPos.toTag(selfTag));
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        /* Get neighbor's inventory if exist when it placed */
        if (!world.isClient) {
            BlockEntity selfEntity = world.getBlockEntity(pos);
            CompoundTag selfTag = new CompoundTag();
            if (selfEntity != null) {
                selfTag = selfEntity.toTag(selfTag);
                DimPos invPos;
                if (getInventoryPos(world, fromPos) != null) {
                    invPos = new DimPos(world, fromPos);
                    selfEntity.fromTag(state, invPos.toTag(selfTag));
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            BlockEntity selfEntity = world.getBlockEntity(pos);
            CompoundTag selfTag = new CompoundTag();
            if (selfEntity != null) {
                selfTag = selfEntity.toTag(selfTag);
                ArrayList<ItemStack> items = getItems(getInventory(world, selfTag));
                if (!items.isEmpty()) {
                    /* Message send loop */
                    player.sendMessage(Text.of("---"), false);
                    for (ItemStack item:items)
                        player.sendMessage(
                            Text.of(String.format("- %s (%s)", item.getName().getString(), item.getCount())),
                            false
                        );
                }
            }
        }
        return ActionResult.SUCCESS;
    }
}
