package net.greenjab.nekomasfixed.registry.registries;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemEnchantmentsPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemSubPredicates;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.AbstractBannerBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  For making modifications to vanilla loot tables
 *  (in order not prevent conflicts with overriding same loot table with other mods)
 */
public class LootTableModifierRegistry {
    private static final Set<ResourceKey<LootTable>> BANNERS_LOOT_TABLE_IDS = BuiltInRegistries.BLOCK.stream()
            .filter((block -> block instanceof AbstractBannerBlock))
            .map(BlockBehaviour::getLootTable)
            .collect(Collectors.toSet());

    public static void registerLootTableModifiers() {
        NekomasFixed.LOGGER.info("Registering loot table modifiers");

        // Modify banner loot tables, to drop with effects
        LootTableEvents.MODIFY.register((registryKey, tableBuilder, source, registries) -> {
            // Let's only modify built-in loot tables and leave data pack loot tables untouched by checking the source.
            var optionalEnchantment = registries.asGetterLookup().get(Registries.ENCHANTMENT, Enchantments.SILK_TOUCH);
            if (optionalEnchantment.isEmpty()) return;
            Holder<Enchantment> silkTouch = optionalEnchantment.get();

            if (source.isBuiltin())
                if (registryKey.equals(Blocks.DECORATED_POT.getLootTable())) {
                    LootItemFunction function = CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                            .include(ComponentRegistry.SHERD_GLOW_OVERRIDES)
                            .when(
                                    MatchTool.toolMatches(
                                            ItemPredicate.Builder.item()
                                                    .withSubPredicate(ItemSubPredicates.ENCHANTMENTS, ItemEnchantmentsPredicate.Enchantments.enchantments(
                                                            List.of(new EnchantmentPredicate(
                                                                    silkTouch,
                                                                    Ints.atLeast(1)
                                                            ))
                                                    ))
                                    )
                            ).build();
                    tableBuilder.apply(function);
                }
                else if (BANNERS_LOOT_TABLE_IDS.contains(registryKey)) {
                    //System.out.println("Changed loot table: " + registryKey.location());
                    LootItemFunction function = CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                            .include(ComponentRegistry.BANNER_EFFECTS)
                            .when(
                                    MatchTool.toolMatches(
                                            ItemPredicate.Builder.item()
                                                    .withSubPredicate(ItemSubPredicates.ENCHANTMENTS, ItemEnchantmentsPredicate.Enchantments.enchantments(
                                                            List.of(new EnchantmentPredicate(
                                                                    silkTouch,
                                                                    Ints.atLeast(1)
                                                            ))
                                                    ))
                                    )
                            ).build();
                    tableBuilder.apply(function);
                }
        });
    }
}
