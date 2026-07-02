package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class LootTableRegistry {
    public static final RegistryKey<LootTable> SUPER_CHARGED_CREEPER_ENDERMAN_LOOT_TABLE = registerLoot_Table("gameplay/super_charged_creeper_enderman");
    public static final RegistryKey<LootTable> CLAM_LOOT_TABLE = registerLoot_Table("gameplay/clam");

    private static RegistryKey<LootTable> registerLoot_Table(String id) {
        return registerLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, NekomasFixed.id(id)));
    }
    private static RegistryKey<LootTable> registerLootTable(RegistryKey<LootTable> key) {
        if (LootTables.LOOT_TABLES.add(key)) {
            return key;
        } else {
            throw new IllegalArgumentException(key.getValue() + " is already a registered built-in loot table");
        }
    }

    public static void registerLootTables() {
        System.out.println("register LootTables");
    }
}
