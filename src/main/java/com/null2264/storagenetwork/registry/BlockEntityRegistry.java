package com.null2264.storagenetwork.registry;

import com.null2264.storagenetwork.blockentity.RequestBlockEntity;
import com.null2264.storagenetwork.blockentity.cables.CableBlockEntity;
import com.null2264.storagenetwork.blockentity.MasterBlockEntity;
import com.null2264.storagenetwork.blockentity.TestBlockEntity;
import com.null2264.storagenetwork.blockentity.cables.CableLinkBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

import static com.null2264.storagenetwork.ZiroStorageNetwork.MODID;

public class BlockEntityRegistry
{
    public static BlockEntityType<TestBlockEntity> TEST_BLOCK_ENTITY;
    public static BlockEntityType<MasterBlockEntity> MASTER_BLOCK_ENTITY;
    public static BlockEntityType<CableBlockEntity> CABLE_BLOCK_ENTITY;
    public static BlockEntityType<CableLinkBlockEntity> CABLE_LINK_BLOCK_ENTITY;
    public static BlockEntityType<RequestBlockEntity> REQUEST_BLOCK_ENTITY;

    public static void register() {
        TEST_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MODID + ":test_block", BlockEntityType.Builder.create(TestBlockEntity::new, BlockRegistry.TEST_BLOCK).build(null));
        MASTER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MODID + ":master", BlockEntityType.Builder.create(MasterBlockEntity::new, BlockRegistry.MASTER_BLOCK).build(null));
        CABLE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MODID + ":cable", BlockEntityType.Builder.create(CableBlockEntity::new, BlockRegistry.CABLE_BLOCK).build(null));
        CABLE_LINK_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MODID + ":cable_link", BlockEntityType.Builder.create(CableLinkBlockEntity::new, BlockRegistry.CABLE_LINK_BLOCK).build(null));
        REQUEST_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MODID + ":request", BlockEntityType.Builder.create(RequestBlockEntity::new, BlockRegistry.REQUEST_BLOCK).build(null));
    }
}
