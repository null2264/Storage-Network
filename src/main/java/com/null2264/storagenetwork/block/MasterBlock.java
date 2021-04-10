package com.null2264.storagenetwork.block;

import com.null2264.storagenetwork.api.DimPos;
import com.null2264.storagenetwork.blockentity.MasterBlockEntity;
import com.null2264.storagenetwork.blockentity.cables.CableBaseBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.ArrayList;

import static com.null2264.storagenetwork.api.InventoryUtil.getInventory;
import static com.null2264.storagenetwork.api.InventoryUtil.getItems;
import static com.null2264.storagenetwork.api.ItemUtil.merge;

public class MasterBlock extends ModBlockWithEntity
{
    public MasterBlock() {
        super(FabricBlockSettings.of(Material.METAL).strength(4.0f));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new MasterBlockEntity();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            MasterBlockEntity selfEntity = (MasterBlockEntity) world.getBlockEntity(pos);
            if (selfEntity != null) {
                // Merge item together
                ArrayList<ItemStack> items = new ArrayList<>();
                for (DimPos dimPos : selfEntity.getCablePositions()) {
                    CableBaseBlockEntity cable = null;
                    if (dimPos.getBlockEntity() instanceof CableBaseBlockEntity)
                        cable = (CableBaseBlockEntity) dimPos.getBlockEntity();

                    if (cable == null || !cable.hasInventory())
                        continue;
                    for (ItemStack item : getItems(getInventory(world, cable.storagePos.getBlockPos())))
                        merge(item.copy(), items);
                }

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