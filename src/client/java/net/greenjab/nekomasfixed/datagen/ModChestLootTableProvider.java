package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.greenjab.nekomasfixed.registry.registries.LootTableRegistry;
import net.greenjab.nekomasfixed.util.RandomDyeModifier;
import net.greenjab.nekomasfixed.util.RandomFireworkModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModChestLootTableProvider extends SimpleFabricLootTableProvider {
    public ModChestLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup, LootContextParamSets.CHEST);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        output.accept(LootTableRegistry.PYROTECHNIST_LOOT, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 5.0F))
                        .add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                        .add(LootItem.lootTableItem(Items.PAPER).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Items.FIREWORK_ROCKET).setWeight(3).apply(RandomFireworkModifier.builder()))
                        .add(LootItem.lootTableItem(Items.RED_DYE).setWeight(3).apply(RandomDyeModifier.builder()))
                        .add(LootItem.lootTableItem(Items.GLOWSTONE_DUST).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Items.DIAMOND))
                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))));

        output.accept(LootTableRegistry.PYROTECHNIST_DISPENSER, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(2.0F, 6.0F))
                        .add(LootItem.lootTableItem(Items.FIREWORK_ROCKET).apply(RandomFireworkModifier.builder()))));
    }
}
