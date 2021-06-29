package com.null2264.storagenetwork.block.cables;

import com.null2264.storagenetwork.api.DimPos;
import com.null2264.storagenetwork.api.MiscUtil;
import com.null2264.storagenetwork.blockentity.cables.CableBaseBlockEntity;
import com.null2264.storagenetwork.blockentity.cables.CableLinkBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Objects;

import static com.null2264.storagenetwork.api.InventoryUtil.getInventoryPos;

public class CableLinkBlock extends CableBlock
{
    public CableLinkBlock() {
        super();
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CableLinkBlockEntity(pos, state);
    }

    public BlockState findInventory(World world, BlockEntity cableEntity, BlockPos pos, BlockState cableState, NbtCompound cableTag, Direction direction) {
        // Find neighbors' inventory
        BlockState newState = null;

        // "invalid" DimPos (Coordinate "x:0, y:0, z:0")
        // TODO: Find "invalid" position for 1.18, since its height & depth limit is changed in 1.18
        DimPos invPos = new DimPos(world, MiscUtil.invalidPos);
        if (!world.isClient) {
            Direction invDir = null;

            // Trying to get inv position if player place link cable facing an inv block
            if (direction != null && getInventoryPos(world, pos.offset(direction)) != null)
                invDir = direction;

            // No inv block found lets try getting it by looping direction around the block
            if (invDir == null) {
                for (Direction loopDir : Direction.values()) {
                    if (getInventoryPos(world, pos.offset(loopDir)) != null) {
                        invDir = loopDir;
                        // Break the loop, we already got an inventory block
                        break;
                    }
                }
            }

            // Save the position and new state
            if (invDir != null) {
                if (cableState != null) {
                    // Since its inventory, canConnect always return true
                    newState = cableState.with(FACING_PROPERTIES.get(invDir), true);
                }
                invPos = new DimPos(world, pos.offset(invDir));
            }
        }

        // Save invPos to an nbt tag
        if (cableEntity != null) {
            cableTag = cableEntity.writeNbt(cableTag);
            cableEntity.readNbt(invPos.toTag(cableTag));
        }

        // Return the new state
        return newState == null ? cableState : newState;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        BlockState originalState = super.getPlacementState(ctx);

        return findInventory(world, world.getBlockEntity(pos), pos, originalState, new NbtCompound(), ctx.getSide().getOpposite());
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        /* Get neighbor's inventory if exist when it placed */
        if (!world.isClient) {
            CableBaseBlockEntity selfEntity = (CableBaseBlockEntity) world.getBlockEntity(pos);
            NbtCompound selfTag = new NbtCompound();
            if (selfEntity != null) {
                selfTag = selfEntity.writeNbt(selfTag);
                // Check if cable already have inventory attached
                if (selfEntity.hasInventory()) {
                    // Check block "properties" if the block position is the same as cable's inventory position
                    if (Objects.equals(selfEntity.storagePos.getBlockPos(), fromPos)) {
                        // Update cable's DimPos if block no longer an inventory block
                        BlockState fromBlock = world.getBlockState(fromPos);
                        if (fromBlock.isOf(Blocks.AIR) || getInventoryPos(world, fromPos) == null) {
                            BlockState newState = findInventory(world, selfEntity, pos, state, selfTag, null);
                            world.setBlockState(pos, newState);
                        }
                    }
                    return;
                }
                DimPos invPos;
                if (getInventoryPos(world, fromPos) != null) {
                    invPos = new DimPos(world, fromPos);
                    selfEntity.readNbt(invPos.toTag(selfTag));
                }
            }
        }
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        // TODO: Fix issue when chest turns into double chest or vice versa, cable connection disappear
        CableBaseBlockEntity cableEntity = (CableBaseBlockEntity) world.getBlockEntity(pos);
        boolean canConnect = canConnect(world, posFrom);
        BlockPos neighborInvPos = getInventoryPos((World)world, posFrom);
        if (cableEntity != null)
            canConnect = canConnect || (cableEntity.hasInventory() && cableEntity.storagePos.getBlockPos().equals(neighborInvPos));
        return state.with(FACING_PROPERTIES.get(direction), canConnect);
    }
}
