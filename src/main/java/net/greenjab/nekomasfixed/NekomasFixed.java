package net.greenjab.nekomasfixed;

import net.fabricmc.api.ModInitializer;
import net.greenjab.nekomasfixed.network.SyncHandler;
import net.greenjab.nekomasfixed.registry.block.cauldron.CauldronBehaviour;
import net.greenjab.nekomasfixed.registry.registries.*;
import net.greenjab.nekomasfixed.registry.worldgen.BiomeAdditions;
import net.greenjab.nekomasfixed.util.ModTreeDecorators;
import net.greenjab.nekomasfixed.util.ModTrunkPlacers;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.greenjab.nekomasfixed.registry.worldgen.ModWorldGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NekomasFixed implements ModInitializer {
	public static final String MOD_NAME = "Nekomas' Fixed Minecraft";
	public static final String NAMESPACE = "nekomasfixed";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAMESPACE);

	@Override
	public void onInitialize() {
		BlockRegistry.registerBlocks();
		BlockEntityTypeRegistry.registerBlockEntityType();
		ItemRegistry.registerItems();
		ItemGroupRegistry.registerItemGroup();
		CustomTrackedDataHandlerRegistry.init();
		EntityTypeRegistry.registerEntityType();
		ModTrunkPlacers.register();
		ModTreeDecorators.register();
		ModWorldGeneration.generateModWorldGen();
		ParticleRegistry.registerParticles();
		EnchantmentRegistry.registerEnchantments();
		EffectRegistry.registerEffects();
		ComponentRegistry.registerComponents();
		LootTableRegistry.registerLootTables();
		OtherRegistry.registerOther();
		RecipeRegistry.registerRecipes();
		SyncHandler.init();
		CauldronBehaviour.register();
		ScreenHandlerRegistry.registerScreenHandlers();

		BiomeAdditions.addSpawns();
		LootTableAdditions.registerLootTableAdds();
	}


	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(NAMESPACE, path);
	}

	public static int enchantLevel(ItemStack stack, String name) {
		int level = 0;
		ItemEnchantments itemEnchantmentsComponent = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
		for (Holder<Enchantment> e : stack.getEnchantments().keySet()) {
			if (e.getRegisteredName().toLowerCase().contains(name.toLowerCase())) {
				level += itemEnchantmentsComponent.getLevel(e);
			}
		}
		return level;
	}
}

//TODO wildfire brain fix