package net.greenjab.nekomasfixed.registry.registries;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.other.AnimalComponent;
import net.greenjab.nekomasfixed.registry.other.ClamFeature;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;

public class OtherRegistry {

    //component
    public static final ComponentType<AnimalComponent> ANIMAL = DataComponentTypes.register(
            "animal", builder -> builder.codec(AnimalComponent.CODEC).packetCodec(AnimalComponent.PACKET_CODEC).cache()
    );
    public static final ComponentType<Integer> CLAM_STATE = DataComponentTypes.register(
            "clam_state", builder -> builder.codec(Codecs.rangedInt(0, 3)).packetCodec(PacketCodecs.INTEGER)
    );


    //tag
    public static final TagKey<Item> CLAMTAG = TagKey.of(RegistryKeys.ITEM, NekomasFixed.id("clams"));


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
    public static RegistryKey<PlacedFeature> featureOf(String id) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, NekomasFixed.id(id));
    }
    public static void registerOther() {
        System.out.println("register Other");
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN), GenerationStep.Feature.VEGETAL_DECORATION, featureOf("clam"));

    }

    //particle
    public static final SimpleParticleType NUMBER = registerParticle("number", true);

    private static SimpleParticleType registerParticle(String name, boolean alwaysShow) {
        return Registry.register(Registries.PARTICLE_TYPE, name, new SimpleParticleType(alwaysShow));
    }
}
