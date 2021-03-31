package com.null2264.storagenetwork;

import com.null2264.storagenetwork.registry.BlockEntityRegistry;
import com.null2264.storagenetwork.registry.BlockRegistry;
import net.fabricmc.api.ModInitializer;

public class ZiroStorageNetwork implements ModInitializer
{
	public static final String MODID = "zirostoragenetwork";

	@Override
	public void onInitialize() {
		BlockRegistry.register();
		BlockEntityRegistry.register();
		System.out.println("Hello Fabric world!");
	}

}
