package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootSubProvider {
    public ModLootTableProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        BlockDyeMap.BRICKS.values().forEach(this::dropSelf);
        BlockDyeMap.BRICK_SLAB.values().forEach(this::dropSelf);
        BlockDyeMap.BRICK_STAIRS.values().forEach(this::dropSelf);
        BlockDyeMap.BRICK_WALL.values().forEach(this::dropSelf);
        BlockDyeMap.SPOTTED_WOOL.values().forEach(this::dropSelf);
        BlockDyeMap.SPOTTED_CARPET.values().forEach(this::dropSelf);

        dropSelf(BlockRegistry.SULFUR_LANTERN);
        dropSelf(BlockRegistry.GOLD_CHAIN);
        add(BlockRegistry.TERRACOTTA_DECORATED_POT, this::createDecoratedPotTable);


    }

    private LootTable.Builder createDecoratedPotTable(final Block original) {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        DynamicLoot.dynamicEntry(DecoratedPotBlock.SHERDS_DYNAMIC_DROP_ID)
                                                .when(
                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(original)
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DecoratedPotBlock.CRACKED, true))
                                                )
                                                .otherwise(
                                                        LootItem.lootTableItem(original)
                                                                .apply(CopyComponentsFunction.copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY).include(DataComponents.POT_DECORATIONS).include(ComponentRegistry.POT_ENGRAVED_DECORATIONS).include(ComponentRegistry.POT_FACE_BLOCK))
                                                )
                                )
                );
    }
}