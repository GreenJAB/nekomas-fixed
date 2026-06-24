package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.other.*;
import net.greenjab.nekomasfixed.registry.entity.WildFire.WildFireAttackablesSensor;
import net.greenjab.nekomasfixed.registry.entity.WildFire.WildFireDebugData;
import net.minecraft.block.Block;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.debug.DebugSubscriptionType;

import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class OtherRegistry {
    public static void registerOther() {
        System.out.println("register Other");
    }

    //component
    public static final ComponentType<AnimalComponent> ANIMAL = registerComponent(
            "animal", builder -> builder.codec(AnimalComponent.CODEC).packetCodec(AnimalComponent.PACKET_CODEC).cache());
    public static final ComponentType<TermitesComponent> TERMITES = registerComponent(
            "termites", builder -> builder.codec(TermitesComponent.CODEC).packetCodec(TermitesComponent.PACKET_CODEC).cache());

    public static final ComponentType<Integer> CLAM_STATE = registerComponent(
            "clam_state", builder -> builder.codec(Codecs.rangedInt(0, 3)).packetCodec(PacketCodecs.INTEGER));
    public static final ComponentType<StoredTimeComponent> STORED_TIME = registerComponent("stored_time", builder -> builder.codec(StoredTimeComponent.CODEC).packetCodec(StoredTimeComponent.PACKET_CODEC).cache());

    public static final ComponentType<ComboComponent> COMBO_MULTIPLIER = registerComponent(
            "combo_multiplier", builder -> builder.codec(ComboComponent.CODEC).packetCodec(ComboComponent.PACKET_CODEC).cache());
    public static final ComponentType<List<ItemStack>> SOUP_INGREDIENTS = Registry.register(Registries.DATA_COMPONENT_TYPE, NekomasFixed.id("soup_ingredients"), ComponentType.<List<ItemStack>>builder().codec(ItemStack.CODEC.listOf()).packetCodec(ItemStack.PACKET_CODEC.collect(PacketCodecs.toList())).build());
    private static <T> ComponentType<T> registerComponent(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, id, builderOperator.apply(ComponentType.builder()).build());}

    //tag
    public static final TagKey<Item> CLAMTAG = TagKey.of(RegistryKeys.ITEM, NekomasFixed.id("clams"));
    public static final TagKey<Item> SPEARS = TagKey.of(RegistryKeys.ITEM, NekomasFixed.id("spears"));
    public static final TagKey<Item> SICKLES = TagKey.of(RegistryKeys.ITEM, NekomasFixed.id("sickles"));
    public static final TagKey<Block> CAN_BE_DYED_WITH_BRUSH = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("can_be_dyed_with_brush"));
    public static final TagKey<Item> STACKED_CAKES = TagKey.of(RegistryKeys.ITEM, NekomasFixed.id("stacked_cakes"));
    public static final TagKey<Block> STAINED_GLASSES = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("stained_glasses"));
    public static final TagKey<Block> STAINED_GLASS_PANES = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("stained_glass_panes"));
    public static final TagKey<Block> GLAZED_TERRACOTTAS = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("glazed_terracottas"));
    public static final TagKey<Block> CONCRETES = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("concretes"));
    public static final TagKey<Block> CONCRETE_POWDERS = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("concrete_powders"));
    public static final TagKey<Block> SPOTTED_WOOLS = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("spotted_wools"));
    public static final TagKey<Block> SPOTTED_CARPETS = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("spotted_carpets"));
    public static final TagKey<Block> FROGLIGHTS = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("froglights"));

    public static final TagKey<Block> DYED_BRICKS = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("dyed_bricks"));
    public static final TagKey<Block> DYED_BRICK_SLABS = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("dyed_brick_slabs"));
    public static final TagKey<Block> DYED_BRICK_STAIRS = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("dyed_brick_stairs"));
    public static final TagKey<Block> DYED_BRICK_WALLS = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("dyed_brick_walls"));
    public static final TagKey<Block> FOLIAGE_REQUIRES_BASE = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("foliage_requires_base"));
    public static final TagKey<Item> FOOD_ITEMS = TagKey.of(RegistryKeys.ITEM, NekomasFixed.id("food_items"));
    public static final TagKey<Item> MOOBLOOM_FLOWERS = TagKey.of(RegistryKeys.ITEM, NekomasFixed.id("moobloom_flowers"));
    public static final TagKey<Item> SLINGSHOT_PROJECTILES = TagKey.of(RegistryKeys.ITEM, NekomasFixed.id("slingshot_projectiles"));

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

    //status effect
    public static RegistryEntry<StatusEffect> LIGHTNING = registerStatusEffect("lightning", new LightningEffect(StatusEffectCategory.BENEFICIAL,0x98D982));

    private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, NekomasFixed.id(name), statusEffect);
    }

    //particle
    public static final SimpleParticleType NUMBER = registerParticle("number", true);

    private static SimpleParticleType registerParticle(String name, boolean alwaysShow) {
        return Registry.register(Registries.PARTICLE_TYPE, name, new SimpleParticleType(alwaysShow));
    }




    //data tracker
    public static final TrackedData<Boolean> IS_TROPICAL_FISH_FED =
            DataTracker.registerData(DolphinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    //sensor
    public static final SensorType<WildFireAttackablesSensor> WILD_FIRE_ATTACK_ENTITY_SENSOR = registerSensor("wild_fire_attack_entity_sensor", WildFireAttackablesSensor::new);
    private static <U extends Sensor<?>> SensorType<U> registerSensor(String id, Supplier<U> factory) {
        return Registry.register(Registries.SENSOR_TYPE, NekomasFixed.id(id), new SensorType<>(factory));
    }

    //debug
    public static final DebugSubscriptionType<WildFireDebugData> WILDFIRES = registerDebug("wild_fires", WildFireDebugData.PACKET_CODEC);
    private static <T> DebugSubscriptionType<T> registerDebug(String id, PacketCodec<? super RegistryByteBuf, T> packetCodec) {
        return Registry.register(Registries.DEBUG_SUBSCRIPTION, NekomasFixed.id(id), new DebugSubscriptionType<>(packetCodec));
    }

}
