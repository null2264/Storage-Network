package com.null2264.storagenetwork.blockentity;

import com.null2264.storagenetwork.Tags;
import com.null2264.storagenetwork.ZiroStorageNetwork;
import com.null2264.storagenetwork.api.DimPos;
import com.null2264.storagenetwork.api.InventoryUtil;
import com.null2264.storagenetwork.blockentity.cables.CableBaseBlockEntity;
import com.null2264.storagenetwork.registry.BlockEntityRegistry;
import com.null2264.storagenetwork.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public class MasterBlockEntity extends BlockEntity implements Tickable
{
    public MasterBlockEntity() {
        super(BlockEntityRegistry.MASTER_BLOCK_ENTITY);
    }

    Set<DimPos> connectables;
    boolean shouldRefresh = true;

    private DimPos getDimPos() {
        return new DimPos(world, pos);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        return super.toTag(tag);
    }

    private Set<DimPos> getCables(DimPos dimPos) {
        HashSet<DimPos> result = new HashSet<>();
        addCables(dimPos, result);
        return result;
    }

    private void addCables(DimPos dimPos, Set<DimPos> set) {
        if (dimPos == null || dimPos.getWorld() == null)
            return;
        for (Direction dir : Direction.values()) {
            DimPos lookup = dimPos.offset(dir);
            // Check if neighbor is a master block.
            BlockEntity maybeMaster = lookup.getBlockEntity();
            if (maybeMaster instanceof MasterBlockEntity && !lookup.equals(pos)) {
                nukeAndDrop(lookup);
                continue;
            }
            // Getting cables
            BlockEntity neighborEntity = lookup.getBlockEntity();
            if (neighborEntity == null)
                continue;
            boolean alreadyChecked = set.contains(lookup);
            if (alreadyChecked)
                continue;
            BlockState neighborState = lookup.getBlockState();
            if (neighborState.isIn(Tags.CABLES) || neighborState.isOf(BlockRegistry.REQUEST_BLOCK)) {
                if (neighborEntity instanceof RequestBlockEntity) {
                    RequestBlockEntity neighborEntity2 = (RequestBlockEntity) neighborEntity;
                    neighborEntity2.setMasterPos(getDimPos());
                } else if (neighborEntity instanceof CableBaseBlockEntity) {
                    CableBaseBlockEntity neighborEntity2 = (CableBaseBlockEntity) neighborEntity;
                    neighborEntity2.setMasterPos(getDimPos());
                }
                set.add(lookup);
                addCables(lookup, set);
                neighborEntity.markDirty();
            }
        }
    }

    private static void nukeAndDrop(DimPos lookup) {
        World lookupWorld = lookup.getWorld();
        if (lookupWorld != null) {
            lookupWorld.breakBlock(lookup.getBlockPos(), true);
            lookupWorld.removeBlockEntity(lookup.getBlockPos());
        }
    }

    @Override
    public void tick() {
        if (world == null) {
            return;
        }
        // TODO: Change shouldRefresh when network need to be refreshed "forcefully"
        if (world.getTime() % 200 == 0 || shouldRefresh) {
            try {
                if (!world.isClient())
                    connectables = getCables(getDimPos());
            } catch (Throwable e) {
//                e.printStackTrace();
                ZiroStorageNetwork.log(e.toString());
            }
            shouldRefresh = false;
        }
    }

    public Set<DimPos> getCablePositions() {
        if (connectables == null) {
            connectables = new HashSet<>();
        }
        return new HashSet<>(connectables);
    }

    public Set<Inventory> getInventories() {
        HashSet<Inventory> inventories = new HashSet<>();
        if (world != null) {
            for (DimPos dimPos : getCablePositions()) {
                CableBaseBlockEntity cable = null;
                if (dimPos.getBlockEntity() instanceof CableBaseBlockEntity)
                    cable = (CableBaseBlockEntity) dimPos.getBlockEntity();
                if (cable == null || !cable.hasInventory())
                    continue;
                inventories.add(InventoryUtil.getInventory(world, cable.storagePos.getBlockPos()));
            }
        }
        return inventories;
    }
}
