package com.null2264.storagenetwork.block.cables;

import com.null2264.storagenetwork.lib.DimPos;
import com.null2264.storagenetwork.blockentity.cables.CableBaseBlockEntity;
import com.null2264.storagenetwork.blockentity.cables.CableLinkBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static com.null2264.storagenetwork.lib.InventoryUtil.getInventoryPos;

public class CableLinkBlock extends CableBlock
{
    public CableLinkBlock() {
        super();
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CableLinkBlockEntity(pos, state);
    }

    public BlockState findInventory(World world, BlockEntity cableEntity, BlockPos pos, BlockState cableState, NbtCompound cableTag, Direction direction) {
        // Find neighbors' inventory
        BlockState newState = null;

        // "invalid" DimPos (Coordinate "x:0, y:0, z:0")
        // TODO: Find "invalid" position for 1.18, since its height & depth limit is changed in 1.18
        DimPos invPos = DimPos.INVALID;
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
            ((CableBaseBlockEntity) cableEntity).writeNbt(cableTag);
            invPos.putToNbt(cableTag);
            cableEntity.readNbt(cableTag);
        }

        // Return the new state
        return newState == null ? cableState : newState;
    }

    @Override
    public void onPlaced(
            World world,
            BlockPos pos,
            BlockState state,
            @Nullable LivingEntity placer,
            ItemStack itemStack
    ) {
        if (world.isClient) return;
        BlockEntity cableEntity = world.getBlockEntity(pos);
        Direction dir = null;
        if (placer != null)
            dir = placer.getHorizontalFacing();

        BlockState newState = findInventory(world, cableEntity, pos, state, new NbtCompound(), dir);
        world.setBlockState(pos, newState);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        /* Get neighbor's inventory if exist when it placed */
        if (!world.isClient) {
            CableBaseBlockEntity selfEntity = (CableBaseBlockEntity) world.getBlockEntity(pos);
            NbtCompound selfTag = new NbtCompound();
            if (selfEntity != null) {
                selfEntity.writeNbt(selfTag);
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
                    invPos.putToNbt(selfTag);
                    selfEntity.readNbt(selfTag);
                }
            }
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (world.isClient()) return state;
        CableBaseBlockEntity cableEntity = (CableBaseBlockEntity) world.getBlockEntity(pos);
        if (cableEntity != null) {
            BlockPos neighbourInvPos = getInventoryPos((World) world, posFrom);
            if (cableEntity.hasInventory() && cableEntity.storagePos.getBlockPos().equals(neighbourInvPos))
                // cable has storage inventory attached and the position of the inventory is the same as this inv
                return state.with(FACING_PROPERTIES.get(direction), true);
            return state.with(FACING_PROPERTIES.get(direction), canConnect(world, posFrom));
        }
        return state;
    }
}