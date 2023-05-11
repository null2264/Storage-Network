package com.null2264.storagenetwork.blockentity;

import com.null2264.storagenetwork.Tags;
import com.null2264.storagenetwork.ZiroStorageNetwork;
import com.null2264.storagenetwork.lib.DimPos;
import com.null2264.storagenetwork.lib.InventoryUtil;
import com.null2264.storagenetwork.blockentity.cables.CableBaseBlockEntity;
import com.null2264.storagenetwork.registry.BlockEntityRegistry;
import com.null2264.storagenetwork.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MasterBlockEntity extends BlockEntity
{
    public MasterBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.MASTER_BLOCK_ENTITY, pos, state);
    }

    private static Set<DimPos> connectables;
    private static boolean shouldRefresh = true;

    private static DimPos getDimPos(World world, BlockPos pos) {
        return new DimPos(world, pos);
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
    }

    private static Set<DimPos> getCables(DimPos dimPos) {
        HashSet<DimPos> result = new HashSet<>();
        addCables(dimPos, dimPos.getBlockPos(), result);
        return result;
    }

    private static void addCables(DimPos dimPos, BlockPos pos, Set<DimPos> set) {
        if (dimPos == null || dimPos.getWorld() == null)
            return;
        for (Direction dir : Direction.values()) {
            DimPos lookup = dimPos.offset(dir);
            World world = dimPos.getWorld();
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
                if (neighborEntity instanceof RequestBlockEntity neighborEntity2) {
                    neighborEntity2.setMasterPos(getDimPos(world, pos));
                } else if (neighborEntity instanceof CableBaseBlockEntity neighborEntity2) {
                    neighborEntity2.setMasterPos(getDimPos(world, pos));
                }
                set.add(lookup);
                addCables(lookup, pos, set);
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

    public static void tick(World world, BlockPos pos) {
        if (world == null) {
            return;
        }
        // TODO: Change shouldRefresh when network need to be refreshed "forcefully"
        if (world.getTime() % 200 == 0 || shouldRefresh) {
            try {
                if (!world.isClient())
                    connectables = getCables(getDimPos(world, pos));
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

    public List<Inventory> getInventories() {
        ArrayList<Inventory> inventories = new ArrayList<>();
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

    public List<ItemStack> getItems() {
        ArrayList<ItemStack> result = new ArrayList<>();
        for ( Inventory inv : getInventories() ) {
            List<ItemStack> itemList = InventoryUtil.getItems(inv);
            if (!itemList.isEmpty())
                result.addAll(itemList);
        }
        return result;
    }
}