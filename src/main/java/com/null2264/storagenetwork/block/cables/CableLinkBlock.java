package com.null2264.storagenetwork.block.cables;

import com.null2264.storagenetwork.api.DimPos;
import com.null2264.storagenetwork.blockentity.cables.CableBaseBlockEntity;
import com.null2264.storagenetwork.blockentity.cables.CableLinkBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.world.WorldAccess;

import java.util.ArrayList;
import java.util.Objects;

import static com.null2264.storagenetwork.api.InventoryUtil.*;
import static com.null2264.storagenetwork.api.ItemUtil.merge;

public class CableLinkBlock extends CableBlock
{
    public CableLinkBlock() {
        super();
    }

    public BlockEntity createBlockEntity(BlockView world) {
        return new CableLinkBlockEntity();
    }

    public DimPos findInventory(World world, BlockPos pos) {
        // Find neighbors' inventory
        if (!world.isClient) {
            for (Direction dir : Direction.values()) {
                if (getInventoryPos(world, pos.offset(dir)) != null) {
                    return new DimPos(world, pos.offset(dir));
                }
            }
        }
        // Return "invalid" DimPos
        // TODO: Find "invalid" position for 1.17, since its height & depth limit is changed in 1.17
        return new DimPos(world, new BlockPos(0,0,0));
    }

    public boolean canConnect(WorldAccess world, BlockPos pos) {
        return super.canConnect(world, pos) || getInventory((World) world, pos) != null;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack item) {
        if (!world.isClient) {
            // Get neighbor block's inventory upon placing
            BlockEntity selfEntity = world.getBlockEntity(pos);
            CompoundTag selfTag = new CompoundTag();
            if (selfEntity != null)
                selfTag = selfEntity.toTag(selfTag);

            DimPos invPos = findInventory(world, pos);
            if (invPos != null)
                if (selfEntity != null)
                    selfEntity.fromTag(state, invPos.toTag(selfTag));
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        /* Get neighbor's inventory if exist when it placed */
        if (!world.isClient) {
            CableBaseBlockEntity selfEntity = (CableBaseBlockEntity) world.getBlockEntity(pos);
            CompoundTag selfTag = new CompoundTag();
            if (selfEntity != null) {
                selfTag = selfEntity.toTag(selfTag);
                // Check if cable already have inventory attached
                if (selfEntity.hasInventory()) {
                    // Check block "properties" if the block position is the same as cable's inventory position
                    if (Objects.equals(selfEntity.storagePos.getBlockPos(), fromPos)) {
                        // Update cable's DimPos if block no longer an inventory block
                        BlockState fromBlock = world.getBlockState(fromPos);
                        if (fromBlock.isOf(Blocks.AIR) || getInventoryPos(world, fromPos) == null) {
                            DimPos invPos = findInventory(world, pos);
                            selfEntity.fromTag(state, invPos.toTag(selfTag));
                        }
                    }
                    return;
                }
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
