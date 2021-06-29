package com.null2264.storagenetwork.registry;

import com.null2264.storagenetwork.blockentity.RequestBlockEntity;
import com.null2264.storagenetwork.blockentity.cables.CableBlockEntity;
import com.null2264.storagenetwork.blockentity.MasterBlockEntity;
import com.null2264.storagenetwork.blockentity.cables.CableLinkBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

import static com.null2264.storagenetwork.ZiroStorageNetwork.MODID;

public class BlockEntityRegistry
{
    public static BlockEntityType<MasterBlockEntity> MASTER_BLOCK_ENTITY;
    public static BlockEntityType<CableBlockEntity> CABLE_BLOCK_ENTITY;
    public static BlockEntityType<CableLinkBlockEntity> CABLE_LINK_BLOCK_ENTITY;
    public static BlockEntityType<RequestBlockEntity> REQUEST_BLOCK_ENTITY;

    public static void register() {
        MASTER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MODID + ":master", FabricBlockEntityTypeBuilder.create(MasterBlockEntity::new, BlockRegistry.MASTER_BLOCK).build(null));
        CABLE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MODID + ":cable", FabricBlockEntityTypeBuilder.create(CableBlockEntity::new, BlockRegistry.CABLE_BLOCK).build(null));
        CABLE_LINK_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MODID + ":cable_link", FabricBlockEntityTypeBuilder.create(CableLinkBlockEntity::new, BlockRegistry.CABLE_LINK_BLOCK).build(null));
        REQUEST_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MODID + ":request", FabricBlockEntityTypeBuilder.create(RequestBlockEntity::new, BlockRegistry.REQUEST_BLOCK).build(null));
    }
}
