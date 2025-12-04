package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.other.AnimalComponent;
import net.greenjab.nekomasfixed.registry.other.ClamFeature;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class OtherRegistry {

    //component
    public static final ComponentType<AnimalComponent> ANIMAL = DataComponentTypes.register(
            "animal", builder -> builder.codec(AnimalComponent.CODEC).packetCodec(AnimalComponent.PACKET_CODEC).cache()
    );

    //loottable
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

    //feature
    public static final Feature<CountConfig> CLAM_FEATURE = registerFeature("clam", new ClamFeature(CountConfig.CODEC));
    private static <C extends FeatureConfig, F extends Feature<C>> F registerFeature(String name, F feature) {
        return Registry.register(Registries.FEATURE, NekomasFixed.id(name), feature);
    }


    public static void registerOther() {
        System.out.println("register Other");
    }
}
