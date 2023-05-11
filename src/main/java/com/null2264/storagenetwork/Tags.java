package com.null2264.storagenetwork;

import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

import static com.null2264.storagenetwork.lib.IdentifierUtil.identifierOf;

public class Tags {
    public static TagKey<Block> CABLES = TagKey.of(Registry.BLOCK_KEY, identifierOf("cables"));
}