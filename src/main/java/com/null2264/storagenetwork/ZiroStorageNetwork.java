package com.null2264.storagenetwork;

import com.null2264.storagenetwork.config.Config;
import com.null2264.storagenetwork.registry.BlockEntityRegistry;
import com.null2264.storagenetwork.registry.BlockRegistry;
import com.null2264.storagenetwork.screen.request.RequestScreenHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ZiroStorageNetwork implements ModInitializer
{
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "zirostoragenetwork";

	// Custom item group
	public static final ItemGroup MOD_GROUP = FabricItemGroupBuilder.build(
		new Identifier(MODID, "itemgroup"),
		() -> new ItemStack(BlockRegistry.REQUEST_BLOCK));

	// Register Screen Handler
	public static final ScreenHandlerType<RequestScreenHandler> REQUEST_SCREEN_HANDLER;

	static {
		REQUEST_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(
			new Identifier(MODID, "request_screen_handler"), RequestScreenHandler::new);
	}


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
