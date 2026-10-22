package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.registry.loottable.ConfigCondition;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.minecraft.advancements.predicates.BlockPredicate;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.packs.VanillaEntityLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchBlock;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootSubProvider {
    public ModLootTableProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    LootItemCondition.Builder configOn = () -> new ConfigCondition("magmaBlockNeedsSilkTouch");


    @Override
    public void generate() {
        BlockDyeMap.BRICKS.values().forEach(this::dropSelf);
        BlockDyeMap.BRICK_SLAB.values().forEach(this::dropSelf);
        BlockDyeMap.BRICK_STAIRS.values().forEach(this::dropSelf);
        BlockDyeMap.BRICK_WALL.values().forEach(this::dropSelf);
        BlockDyeMap.BRICKS.values().forEach(this::dropSelf);
        BlockDyeMap.SPOTTED_WOOL.values().forEach(this::dropSelf);
        BlockDyeMap.SPOTTED_CARPET.values().forEach(this::dropSelf);

        dropSelf(BlockRegistry.SULFUR_LANTERN);
        dropSelf(BlockRegistry.GOLD_CHAIN);
        dropSelf(BlockRegistry.SULFUR_TORCH);

        dropSelf(BlockRegistry.AMBER_BANNER);
        dropSelf(BlockRegistry.MAROON_BANNER);
        dropSelf(BlockRegistry.INDIGO_BANNER);
        dropSelf(BlockRegistry.AQUA_BANNER);

        dropSelf(BlockRegistry.AMBER_WALL_BANNER);
        dropSelf(BlockRegistry.MAROON_WALL_BANNER);
        dropSelf(BlockRegistry.INDIGO_WALL_BANNER);
        dropSelf(BlockRegistry.AQUA_WALL_BANNER);

        add(BlockRegistry.TERRACOTTA_DECORATED_POT, this::createDecoratedPotTable);
        createSilkTouchOnlyTable(BlockRegistry.SULFUR_CAMPFIRE);

        BlockDyeMap.SPOTTED_WOOL_SLABS.values().forEach(this::dropSelf);
        BlockDyeMap.SPOTTED_WOOL_STAIRS.values().forEach(this::dropSelf);

        dropSelf(BlockRegistry.AMBER_WOOL_SLAB);
        dropSelf(BlockRegistry.AQUA_WOOL_SLAB);
        dropSelf(BlockRegistry.INDIGO_WOOL_SLAB);
        dropSelf(BlockRegistry.MAROON_WOOL_SLAB);
        dropSelf(BlockRegistry.AMBER_WOOL_STAIRS);
        dropSelf(BlockRegistry.AQUA_WOOL_STAIRS);
        dropSelf(BlockRegistry.INDIGO_WOOL_STAIRS);
        dropSelf(BlockRegistry.MAROON_WOOL_STAIRS);
        dropSelf(BlockRegistry.AMBER_CONCRETE_SLAB);
        dropSelf(BlockRegistry.AQUA_CONCRETE_SLAB);
        dropSelf(BlockRegistry.INDIGO_CONCRETE_SLAB);
        dropSelf(BlockRegistry.MAROON_CONCRETE_SLAB);
        dropSelf(BlockRegistry.AMBER_CONCRETE_STAIRS);
        dropSelf(BlockRegistry.AQUA_CONCRETE_STAIRS);
        dropSelf(BlockRegistry.INDIGO_CONCRETE_STAIRS);
        dropSelf(BlockRegistry.MAROON_CONCRETE_STAIRS);

        add(Blocks.MAGMA_BLOCK, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ContextIntProviders.exactly(1))
                        .when(configOn)
                        .when(hasSilkTouch())
                        .add(LootItem.lootTableItem(Blocks.MAGMA_BLOCK)))
                .withPool(LootPool.lootPool()
                        .setRolls(ContextIntProviders.exactly(1))
                        .when(configOn.invert())
                        .add(LootItem.lootTableItem(Blocks.MAGMA_BLOCK))));
    }

    private LootTable.Builder createDecoratedPotTable(final Block original) {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ContextIntProviders.exactly(1))
                                .add(
                                        DynamicLoot.dynamicEntry(DecoratedPotBlock.SHERDS_DYNAMIC_DROP_ID)
                                                .when(
                                                        MatchBlock.blockMatches(
                                                                BlockPredicate.Builder.block().setProperties(
                                                                        StatePropertiesPredicate.Builder.properties().hasProperty(DecoratedPotBlock.CRACKED, true)
                                                                )
                                                        )
                                                )
                                                .otherwise(
                                                        LootItem.lootTableItem(original)
                                                                .apply(CopyComponentsFunction.copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY).include(DataComponents.POT_DECORATIONS).include(ComponentRegistry.POT_ENGRAVED_DECORATIONS).include(ComponentRegistry.POT_FACE_BLOCK))
                                                )
                                )
                );
    }
}