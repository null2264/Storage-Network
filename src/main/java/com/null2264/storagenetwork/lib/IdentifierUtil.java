package com.null2264.storagenetwork.lib;

import net.minecraft.util.Identifier;

import static com.null2264.storagenetwork.ZiroStorageNetwork.MOD_ID;

public class IdentifierUtil
{
    public static Identifier identifierOf(String id) {
        return new Identifier(MOD_ID, id);
    }
}