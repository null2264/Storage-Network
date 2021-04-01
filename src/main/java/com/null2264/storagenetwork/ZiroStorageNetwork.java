package com.null2264.storagenetwork;

import com.null2264.storagenetwork.config.Config;
import com.null2264.storagenetwork.registry.BlockEntityRegistry;
import com.null2264.storagenetwork.registry.BlockRegistry;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ZiroStorageNetwork implements ModInitializer
{
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "zirostoragenetwork";

	@Override
	public void onInitialize() {
		Config.init();
		BlockRegistry.register();
		BlockEntityRegistry.register();
		System.out.println("Hello Fabric world!");
	}

	public static void log(String s)
	{
		if (Config.get().logSpam) {
			LOGGER.info(s);
		}
	}
}
