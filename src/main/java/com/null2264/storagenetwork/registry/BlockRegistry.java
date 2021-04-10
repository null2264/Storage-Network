package com.null2264.storagenetwork.registry;

import com.null2264.storagenetwork.ZiroStorageNetwork;
import com.null2264.storagenetwork.block.RequestBlock;
import com.null2264.storagenetwork.block.cables.CableBlock;
import com.null2264.storagenetwork.block.MasterBlock;
import com.null2264.storagenetwork.block.cables.CableLinkBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.null2264.storagenetwork.ZiroStorageNetwork.MODID;

public class BlockRegistry {
    public static final Block MASTER_BLOCK = new MasterBlock();
    public static final Block CABLE_BLOCK = new CableBlock();
    public static final Block CABLE_LINK_BLOCK = new CableLinkBlock();
    public static final Block REQUEST_BLOCK = new RequestBlock();

    public static void register(){
        // Register block
        Registry.register(Registry.BLOCK, new Identifier(MODID, "master"), MASTER_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(MODID, "cable"), CABLE_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(MODID, "cable_link"), CABLE_LINK_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(MODID, "request"), REQUEST_BLOCK);

        // Register block's item
        // TODO: Find out how to make custom ItemGroup
        Registry.register(Registry.ITEM, new Identifier(MODID, "master"), new BlockItem(MASTER_BLOCK, new FabricItemSettings().group(ZiroStorageNetwork.MOD_GROUP)));
        Registry.register(Registry.ITEM, new Identifier(MODID, "cable"), new BlockItem(CABLE_BLOCK, new FabricItemSettings().group(ZiroStorageNetwork.MOD_GROUP)));
        Registry.register(Registry.ITEM, new Identifier(MODID, "cable_link"), new BlockItem(CABLE_LINK_BLOCK, new FabricItemSettings().group(ZiroStorageNetwork.MOD_GROUP)));
        Registry.register(Registry.ITEM, new Identifier(MODID, "request"), new BlockItem(REQUEST_BLOCK, new FabricItemSettings().group(ZiroStorageNetwork.MOD_GROUP)));
    }
}
