package net.greenjab.nekomasfixed.datagen;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.function.BiConsumer;

public class ModGameplayLootTableProvider implements LootTableSubProvider {

    public static final ResourceKey<LootTable> CLAM = ResourceKey.create(
            Registries.LOOT_TABLE, Identifier.of("nekomasfixed", "gameplay/clam"));

    public static final ResourceKey<LootTable> SUPER_CHARGED_CREEPER_ENDERMAN = ResourceKey.create(
            Registries.LOOT_TABLE, Identifier.of("nekomasfixed", "gameplay/super_charged_creeper_enderman"));

    public ModGameplayLootTableProvider(HolderLookup.Provider registries) {}

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> exporter) {
        exporter.accept(CLAM, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(100))
                        .add(LootItem.lootTableItem(Items.HEART_OF_THE_SEA).setWeight(1).setQuality(3))
                        .add(LootItem.lootTableItem(ItemRegistry.PEARL).setWeight(20).setQuality(20)) // fix reference
                        .add(LootItem.lootTableItem(Items.COAL).setWeight(30).setQuality(1))));

        exporter.accept(SUPER_CHARGED_CREEPER_ENDERMAN, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ItemRegistry.ENDERMAN_HEAD)))); // fix reference
    }

    @Override
    public void run() {

    }
}