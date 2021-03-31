package com.null2264.storagenetwork.registry;

import com.null2264.storagenetwork.block.CableBlock;
import com.null2264.storagenetwork.block.MasterBlock;
import com.null2264.storagenetwork.block.TestBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.null2264.storagenetwork.ZiroStorageNetwork.MODID;

public class BlockRegistry {
    public static final Block TEST_BLOCK = new TestBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f));
    public static final Block MASTER_BLOCK = new MasterBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f));
    public static final Block CABLE_BLOCK = new CableBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f));

    public static void register(){
        // Register block
        Registry.register(Registry.BLOCK, new Identifier(MODID, "test_block"), TEST_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(MODID, "master"), MASTER_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(MODID, "cable"), CABLE_BLOCK);

        // Register block's item
        Registry.register(Registry.ITEM, new Identifier(MODID, "test_block"), new BlockItem(TEST_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier(MODID, "master"), new BlockItem(MASTER_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, new Identifier(MODID, "cable"), new BlockItem(CABLE_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));
    }
}
