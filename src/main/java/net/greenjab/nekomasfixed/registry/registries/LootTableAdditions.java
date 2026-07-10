package net.greenjab.nekomasfixed.registry.registries;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.EntityTypePredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;

public class LootTableAdditions {

    public static void registerLootTableAdds() {
        System.out.println("register LootTableAdds");

        LootTableEvents.MODIFY.register((key, tableBuilder, _, holder) -> {
            if (key == BuiltInLootTables.CHARGED_CREEPER) {
                LootItemCondition.Builder predicate = LootItemEntityPropertyCondition.hasProperties(
                        LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(holder.lookupOrThrow(Registries.ENTITY_TYPE), EntityType.ENDERMAN)));
                LootPool.Builder poolBuilder = LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableRegistry.SUPER_CHARGED_CREEPER_ENDERMAN_LOOT_TABLE).when(predicate));
                tableBuilder.pool(poolBuilder.build());
            } else if (key == BuiltInLootTables.SHIPWRECK_TREASURE) {
                tableBuilder.pool(LootPool.lootPool().add(LootItem.lootTableItem(ItemRegistry.BOAT_UPGRADE_TEMPLATE)).add(LootItem.lootTableItem(Items.AIR)).build());
            } else if (key == BuiltInLootTables.STRONGHOLD_LIBRARY) {
                tableBuilder.pool(LootPool.lootPool().add(LootItem.lootTableItem(Items.BOOK).apply(EnchantRandomlyFunction.randomApplicableEnchantment(holder).withEnchantment(holder.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(EnchantmentRegistry.LEECHING)))).add(LootItem.lootTableItem(Items.AIR)).build());
            }
        });
    }
}
