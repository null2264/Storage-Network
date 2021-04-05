package com.null2264.storagenetwork;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

import static com.null2264.storagenetwork.ZiroStorageNetwork.MODID;

public class Tags {
    public static Tag<Block> CABLES = TagRegistry.block(new Identifier(MODID, "cables"));
}
