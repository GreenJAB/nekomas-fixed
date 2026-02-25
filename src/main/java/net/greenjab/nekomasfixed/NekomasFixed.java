package net.greenjab.nekomasfixed;

import net.fabricmc.api.ModInitializer;
import net.greenjab.nekomasfixed.network.SyncHandler;
import net.greenjab.nekomasfixed.registry.block.cauldron.CauldronBehaviour;
import net.greenjab.nekomasfixed.registry.registries.*;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NekomasFixed implements ModInitializer {
	public static final String MOD_NAME = "Nekomas' Fixed Minecraft";
	public static final String NAMESPACE = "nekomasfixed";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAMESPACE);
	public static final String MOD_ID = "nekomasfixed";

	@Override
	public void onInitialize() {
		BlockEntityTypeRegistry.registerBlockEntityType();
		BlockRegistry.registerBlocks();
		EnchantmentEffectRegistry.register();
		ItemRegistry.registerItems();
		ItemGroupRegistry.registerItemGroup();
		EntityTypeRegistry.registerEntityType();
		OtherRegistry.registerOther();
		RecipeRegistry.registerRecipes();
		EntityTypeRegistry.init();
		SyncHandler.init();
		CauldronBehaviour.register();

	}


	public static Identifier id(String path) {
		return Identifier.of(NAMESPACE, path);
	}
}