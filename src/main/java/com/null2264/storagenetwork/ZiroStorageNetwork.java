package com.null2264.storagenetwork;

import com.null2264.storagenetwork.config.ConfigHelper;
import com.null2264.storagenetwork.registry.BlockEntityRegistry;
import com.null2264.storagenetwork.registry.BlockRegistry;
import com.null2264.storagenetwork.screen.request.RequestScreenHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.null2264.storagenetwork.lib.IdentifierUtil.identifierOf;

public class ZiroStorageNetwork implements ModInitializer
{
	public static final String MOD_ID = "zirostoragenetwork";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	// Custom item group
	public static final ItemGroup MOD_GROUP = FabricItemGroupBuilder.build(
			identifierOf("itemgroup"),
			() -> new ItemStack(BlockRegistry.REQUEST_BLOCK));

	// Register Screen Handler
	public static final ScreenHandlerType<RequestScreenHandler> REQUEST_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(RequestScreenHandler::new);

	@Override
	public void onInitialize() {
		ConfigHelper.loadOrDefault();
		BlockRegistry.register();
		BlockEntityRegistry.register();
		Registry.register(Registry.SCREEN_HANDLER, identifierOf("request_screen_handler"), REQUEST_SCREEN_HANDLER);
		log("Hello World");
	}

	public static void log(String s) {
		if (ConfigHelper.get().logSpam) {
			LOGGER.info(s);
		}
	}
}