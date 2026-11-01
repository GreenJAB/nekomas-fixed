package net.greenjab.nekomasfixed.registry.loot;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.greenjab.nekomasfixed.registry.other.SheepPredicate;
import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.CopyComponentsLootFunction;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.NumberRange.IntRange;
import net.minecraft.predicate.component.ComponentsPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.EnchantmentsPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.DyeColor;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static net.minecraft.predicate.component.ComponentPredicateTypes.ENCHANTMENTS;

public class ModLootTableModifiers  {
    private static final Set<RegistryKey<LootTable>> BANNERS_LOOT_TABLE_IDS = Registries.BLOCK.stream()
            .filter((block -> block instanceof AbstractBannerBlock))
            .map(AbstractBlock::getLootTableKey)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toSet());

    public static void onInitialize() {
        // Modify banner loot tables, to drop with effects
        RegistryKey<LootTable> potLoot = Blocks.DECORATED_POT.getLootTableKey().orElse(null);
        BiMap<RegistryKey<LootTable>, DyeColor> sheepDyeColorFromDrops =
                HashBiMap.create(LootTables.SHEEP_DROPS_FROM_DYE_COLOR).inverse();
        BiMap<RegistryKey<LootTable>, DyeColor> sheepDyeColorFromShearing =
                HashBiMap.create(LootTables.SHEEP_SHEARING_FROM_DYE_COLOR).inverse();

        LootTableEvents.MODIFY.register((registryKey, tableBuilder, source, registries) -> {
            // Let's only modify built-in loot tables and leave data pack loot tables untouched by checking the source.
            var enchantmentRegistry = registries.getOptional(RegistryKeys.ENCHANTMENT);
            if (enchantmentRegistry.isEmpty()) return;
            var optionalEnchantment = enchantmentRegistry.get().getOptional(Enchantments.SILK_TOUCH);
            if (optionalEnchantment.isEmpty()) return;
            RegistryEntry<Enchantment> silkTouch = optionalEnchantment.get();

            if (source.isBuiltin()) {
                if (registryKey.equals(potLoot)) {
                    // Decorated pots should keep sherd glow overrides
                    LootFunction function = CopyComponentsLootFunction.blockEntity(LootContextParameters.BLOCK_ENTITY)
                            .include(ComponentRegistry.SHERD_GLOW_OVERRIDES)
                            .conditionally(toolEnchantedWith(silkTouch))
                            .build();
                    tableBuilder.apply(function);

                } else if (BANNERS_LOOT_TABLE_IDS.contains(registryKey)) {
                    // Banners should keep sherd their effects
                    LootFunction function = CopyComponentsLootFunction.blockEntity(LootContextParameters.BLOCK_ENTITY)
                            .include(ComponentRegistry.BANNER_EFFECTS)
                            .conditionally(toolEnchantedWith(silkTouch))
                            .build();
                    tableBuilder.apply(function);

                } else if (sheepDyeColorFromDrops.containsKey(registryKey)) {
                    // Dropped sheep wool should keep the glow
                    DyeColor color = sheepDyeColorFromDrops.get(registryKey);

                    tableBuilder.modifyPools(poolBuilder ->
                            poolBuilder.conditionally(sheepGlowing(false)));
                    tableBuilder.pool(LootPool.builder()
                            .with(ItemEntry.builder(BlockDyeMap.GLOWING_WOOL.get(color)))
                            .conditionally(sheepGlowing(true)));

                } else if (sheepDyeColorFromShearing.containsKey(registryKey)) {
                    // Sheared sheep wool should keep the glow
                    DyeColor color = sheepDyeColorFromShearing.get(registryKey);

                    tableBuilder.modifyPools(poolBuilder ->
                            poolBuilder.conditionally(sheepGlowing(false)));
                    tableBuilder.pool(LootPool.builder()
                            .with(ItemEntry.builder(BlockDyeMap.GLOWING_WOOL.get(color)))
                            .rolls(UniformLootNumberProvider.create(1, 3))
                            .conditionally(sheepGlowing(true)));
                }
            }
        });
    }

    public static LootCondition sheepGlowing(boolean glowing) {
        return EntityPropertiesLootCondition.builder(
                        LootContext.EntityReference.THIS,
                        EntityPredicate.Builder.create()
                                .typeSpecific(SheepPredicate.glowing(glowing))
                                .build()
                )
                .build();
    }

    public static LootCondition.Builder toolEnchantedWith(RegistryEntry<Enchantment> enchantment) {
        return MatchToolLootCondition.builder(
                ItemPredicate.Builder.create()
                        .components(ComponentsPredicate.Builder.create()
                                .partial(ENCHANTMENTS, EnchantmentsPredicate.Enchantments.enchantments(
                                        List.of(new EnchantmentPredicate(
                                                enchantment,
                                                IntRange.atLeast(1)
                                        ))
                                ))
                                .build()
                        )
        );
    }
}
