package net.greenjab.nekomasfixed;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.greenjab.nekomasfixed.network.SyncHandler;
import net.greenjab.nekomasfixed.registry.block.cauldron.CauldronBehaviour;
import net.greenjab.nekomasfixed.registry.registries.*;
import net.greenjab.nekomasfixed.util.ModTrunkPlacers;
import net.greenjab.nekomasfixed.world.ModWorldGeneration;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NekomasFixed implements ModInitializer {
	public static final String MOD_NAME = "Nekomas' Fixed Minecraft";
	public static final String NAMESPACE = "nekomasfixed";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAMESPACE);

	@Override
	public void onInitialize() {
		BlockEntityTypeRegistry.registerBlockEntityType();
		BlockRegistry.registerBlocks();
		ItemRegistry.registerItems();
		ItemGroupRegistry.registerItemGroup();
		EntityTypeRegistry.registerEntityType();
		ModTrunkPlacers.register();
		ModWorldGeneration.generateModWorldGen();
		OtherRegistry.registerOther();
		RecipeRegistry.registerRecipes();
		EntityTypeRegistry.init();
		SyncHandler.init();
		CauldronBehaviour.register();
		ScreenHandlerRegistry.registerScreenHandlers();
		FabricDefaultAttributeRegistry.register(EntityTypeRegistry.JUNGLE_ZOMBIE, ZombieEntity.createZombieAttributes());
		FabricDefaultAttributeRegistry.register(EntityTypeRegistry.SNOW_ZOMBIE, ZombieEntity.createZombieAttributes());
		FabricDefaultAttributeRegistry.register(EntityTypeRegistry.DRENCHED, AbstractSkeletonEntity.createAbstractSkeletonAttributes());
	}


	public static Identifier id(String path) {
		return Identifier.of(NAMESPACE, path);
	}

	public static int enchantLevel(ItemStack stack, String name) {
		int level = 0;
		ItemEnchantmentsComponent itemEnchantmentsComponent = stack.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT);
		for (RegistryEntry<Enchantment> e : stack.getEnchantments().getEnchantments()) {
			if (e.getIdAsString().toLowerCase().contains(name.toLowerCase())) {
				level += itemEnchantmentsComponent.getLevel(e);
			}
		}
		return level;
	}
}