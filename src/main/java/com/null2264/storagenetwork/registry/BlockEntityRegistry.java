package com.null2264.storagenetwork.registry;

import com.null2264.storagenetwork.block.entity.MasterBlockEntity;
import com.null2264.storagenetwork.block.entity.TestBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

import static com.null2264.storagenetwork.ZiroStorageNetwork.MODID;

public class BlockEntityRegistry
{
    public static BlockEntityType<TestBlockEntity> TEST_BLOCK_ENTITY;
    public static BlockEntityType<MasterBlockEntity> MASTER_BLOCK_ENTITY;

    public static void register() {
        TEST_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MODID + ":test_block", BlockEntityType.Builder.create(TestBlockEntity::new, BlockRegistry.TEST_BLOCK).build(null));
        MASTER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MODID + ":master_block", BlockEntityType.Builder.create(MasterBlockEntity::new, BlockRegistry.MASTER_BLOCK).build(null));
    }
}
