package com.null2264.storagenetwork;

import com.null2264.storagenetwork.registry.ScreenRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ZiroStorageNetworkClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient() {
        ScreenRegistry.registerClient();
    }
}
