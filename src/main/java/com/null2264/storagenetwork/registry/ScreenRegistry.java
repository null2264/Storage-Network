package com.null2264.storagenetwork.registry;

import com.null2264.storagenetwork.ZiroStorageNetwork;
import com.null2264.storagenetwork.screen.request.RequestScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ScreenRegistry
{
    public static void registerClient() {
        HandledScreens.register(ZiroStorageNetwork.REQUEST_SCREEN_HANDLER, RequestScreen::new);
    }
}