package net.greenjab.nekomasfixed.registry.registries;

import com.google.common.collect.Maps;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.entity.WildFire.WildfireAttackablesSensor;
import net.greenjab.nekomasfixed.registry.entity.WildFire.WildfireDebugData;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.debug.DebugSubscription;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.dolphin.Dolphin;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.Map;
import java.util.function.Supplier;

public class OtherRegistry {
    public static void registerOther() {
        System.out.println("register Other");
    }

    //data tracker
    public static final EntityDataAccessor<Boolean> IS_TROPICAL_FISH_FED =
            SynchedEntityData.defineId(Dolphin.class, EntityDataSerializers.BOOLEAN);

    //sensor
    public static final SensorType<WildfireAttackablesSensor> WILDFIRE_ATTACK_ENTITY_SENSOR = registerSensor("wildfire_attack_entity_sensor", WildfireAttackablesSensor::new);
    private static <U extends Sensor<?>> SensorType<U> registerSensor(String id, Supplier<U> factory) {
        return Registry.register(BuiltInRegistries.SENSOR_TYPE, NekomasFixed.id(id), new SensorType<>(factory));
    }

    static ResourceKey<EquipmentAsset> GOAT_HORN_ASSET = createId("goat_horn");
    static ResourceKey<EquipmentAsset> createId(final String name) {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.withDefaultNamespace(name));
    }

    //armor material
    static ArmorMaterial GOAT_HORN = new ArmorMaterial(
            15, makeDefense(1, 4, 5, 2, 4), 12, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0F, 0.0F, ItemTags.REPAIRS_CHAIN_ARMOR, GOAT_HORN_ASSET
    );
    static Map<ArmorType, Integer> makeDefense(final int boots, final int legs, final int chest, final int helm, final int body) {
        return Maps.newEnumMap(Map.of(ArmorType.BOOTS, boots, ArmorType.LEGGINGS, legs, ArmorType.CHESTPLATE, chest, ArmorType.HELMET, helm, ArmorType.BODY, body));
    }

    //debug
    public static final DebugSubscription<WildfireDebugData> WILDFIRES = registerDebug("wildfires", WildfireDebugData.PACKET_CODEC);
    private static <T> DebugSubscription<T> registerDebug(String id, StreamCodec<? super RegistryFriendlyByteBuf, T> packetCodec) {
        return Registry.register(BuiltInRegistries.DEBUG_SUBSCRIPTION, NekomasFixed.id(id), new DebugSubscription<>(packetCodec));
    }

}
