package net.greenjab.nekomasfixed.registry.registries;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.EntityTypePredicate;
import net.minecraft.registry.RegistryKeys;

public class LootTableAdditions {

    public static void registerLootTableAdds() {
        System.out.println("register LootTableAdds");

        LootTableEvents.MODIFY.register((key, tableBuilder, source, holder) -> {
            if (key == LootTables.ROOT_CHARGED_CREEPER) {
                LootCondition.Builder predicate = EntityPropertiesLootCondition.builder(
                        LootContext.EntityReference.THIS, EntityPredicate.Builder.create().type(EntityTypePredicate.create(holder.getOrThrow(RegistryKeys.ENTITY_TYPE), EntityType.ENDERMAN)));
                LootPool.Builder poolBuilder = LootPool.builder().with(LootTableEntry.builder(LootTableRegistry.SUPER_CHARGED_CREEPER_ENDERMAN_LOOT_TABLE).conditionally(predicate));
                tableBuilder.pool(poolBuilder.build());
                //tableBuilder.modifyPools(builder -> builder.//builder
                //        .with(LootTableEntry.builder(LootTableRegistry.SUPER_CHARGED_CREEPER_ENDERMAN_LOOT_TABLE).conditionally(predicate)));
            } else if (key == LootTables.SHIPWRECK_TREASURE_CHEST) {
                tableBuilder.pool(LootPool.builder().with(ItemEntry.builder(ItemRegistry.BOAT_UPGRADE_TEMPLATE)).with(ItemEntry.builder(Items.AIR)).build());
            } else if (key == LootTables.STRONGHOLD_LIBRARY_CHEST) {
                tableBuilder.pool(LootPool.builder().with(ItemEntry.builder(Items.BOOK).apply(EnchantRandomlyLootFunction.builder(holder).option(holder.getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(EnchantmentRegistry.LEECHING)))).with(ItemEntry.builder(Items.AIR)).build());
            }
        });
    }
}
