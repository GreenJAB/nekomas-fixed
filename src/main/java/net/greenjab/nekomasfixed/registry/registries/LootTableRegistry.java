package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;

public class LootTableRegistry {
    public static final ResourceKey<LootTable> SUPER_CHARGED_CREEPER_ENDERMAN_LOOT_TABLE = registerLoot_Table("gameplay/super_charged_creeper_enderman");
    public static final ResourceKey<LootTable> CLAM_LOOT_TABLE = registerLoot_Table("gameplay/clam");

    private static ResourceKey<LootTable> registerLoot_Table(String id) {
        return registerLootTable(ResourceKey.create(Registries.LOOT_TABLE, NekomasFixed.id(id)));
    }
    private static ResourceKey<LootTable> registerLootTable(ResourceKey<LootTable> key) {
        if (BuiltInLootTables.LOCATIONS.add(key))return key;
        else throw new IllegalArgumentException(key.identifier() + " is already a registered built-in loot table");
    }

    public static void registerLootTables() {
        System.out.println("register LootTables");
    }
}
