package com.null2264.storagenetwork.registry;

import com.null2264.storagenetwork.ZiroStorageNetwork;
import com.null2264.storagenetwork.screen.request.RequestScreen;

public class ScreenRegistry
{
    public static void registerClient() {
        net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry.register(ZiroStorageNetwork.REQUEST_SCREEN_HANDLER, RequestScreen::new);
    }
}
