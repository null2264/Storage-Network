package com.null2264.storagenetwork.block.cables;

import com.null2264.storagenetwork.api.DimPos;
import com.null2264.storagenetwork.blockentity.cables.CableLinkBlockEntity;
import net.minecraft.block.Block;
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
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.ArrayList;

import static com.null2264.storagenetwork.api.InventoryUtil.*;
import static com.null2264.storagenetwork.api.ItemUtil.merge;

public class CableLinkBlock extends CableBlock
{
    // TODO: Move inventory stuff from masterBlock to here
    public CableLinkBlock() {
        super();
    }

    public BlockEntity createBlockEntity(BlockView world) {
        return new CableLinkBlockEntity();
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack item) {
        if (!world.isClient) {
            // Get neighbor block's inventory upon placing
            BlockEntity selfEntity = world.getBlockEntity(pos);
            CompoundTag selfTag = new CompoundTag();
            if (selfEntity != null)
                selfTag = selfEntity.toTag(selfTag);

            for (Direction dir : Direction.values()) {
                DimPos invPos;
                if (getInventoryPos(world, pos.offset(dir)) != null) {
                    invPos = new DimPos(world, pos.offset(dir));
                    if (selfEntity != null)
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

                // Merge item together
                ArrayList<ItemStack> items = new ArrayList<>();
                for (ItemStack item : getItems(getInventory(world, selfTag)))
                    merge(item.copy(), items);

                // Send merged items as message
                if (!items.isEmpty()) {
                    player.sendMessage(Text.of("---"), false);
                    for (ItemStack item : items) {
                        player.sendMessage(
                            Text.of(String.format("- %s (%s)", item.getName().getString(), item.getCount())),
                            false
                        );
                    }
                }
            }
        }
        return ActionResult.SUCCESS;
    }
}
