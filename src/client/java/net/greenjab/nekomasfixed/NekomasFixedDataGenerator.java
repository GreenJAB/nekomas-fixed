package net.greenjab.nekomasfixed;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.greenjab.nekomasfixed.datagen.*;
import net.greenjab.nekomasfixed.datagen.villager.ModPOITags;
import net.greenjab.nekomasfixed.datagen.villager.ModVillagerTradeTags;
import net.greenjab.nekomasfixed.datagen.villager.ModVillagerTrades;
import net.greenjab.nekomasfixed.datagen.villager.ModVillagerTradesets;
import net.greenjab.nekomasfixed.registry.registries.ItemGroupRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.RandomSequence;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Collections;
import java.util.List;
import java.util.Set;


public class NekomasFixedDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModItemTagProvider::new);
		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModRecipeProvider::new);
		pack.addProvider(ModLootTableProvider::new);
		pack.addProvider(ModRegistryDataGenerator::new);
		pack.addProvider(ModVillagerTradeTags::new);
		pack.addProvider(ModPOITags::new);
		pack.addProvider(ModAdvancementProvider::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.VILLAGER_TRADE, ModVillagerTrades::bootstrap);
		registryBuilder.add(Registries.TRADE_SET, ModVillagerTradesets::bootstrap);
	}


	//registryBuilder.add(Registries.LOOT_TABLE, bootstrap -> {
	//			bootstrap.register(
	//					ResourceKey.create(Registries.LOOT_TABLE,
	//							Identifier.fromNamespaceAndPath("nekomasfixed", "gameplay/clam")),
	//					LootTable.lootTable().build()
	//			);
	//			bootstrap.register(
	//					ResourceKey.create(Registries.LOOT_TABLE,
	//							Identifier.fromNamespaceAndPath("nekomasfixed", "gameplay/super_charged_creeper_enderman")),
	//					LootTable.lootTable().build()
	//
	//			);
	//		});
}
