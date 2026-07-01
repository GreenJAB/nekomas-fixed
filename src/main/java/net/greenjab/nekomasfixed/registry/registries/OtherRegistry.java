package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.entity.WildFire.WildfireAttackablesSensor;
import net.greenjab.nekomasfixed.registry.entity.WildFire.WildfireDebugData;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.*;
import net.minecraft.world.debug.DebugSubscriptionType;
import java.util.function.Supplier;

public class OtherRegistry {
    public static void registerOther() {
        System.out.println("register Other");
    }

    //data tracker
    public static final TrackedData<Boolean> IS_TROPICAL_FISH_FED =
            DataTracker.registerData(DolphinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    //sensor
    public static final SensorType<WildfireAttackablesSensor> WILDFIRE_ATTACK_ENTITY_SENSOR = registerSensor("wildfire_attack_entity_sensor", WildfireAttackablesSensor::new);
    private static <U extends Sensor<?>> SensorType<U> registerSensor(String id, Supplier<U> factory) {
        return Registry.register(Registries.SENSOR_TYPE, NekomasFixed.id(id), new SensorType<>(factory));
    }

    //debug
    public static final DebugSubscriptionType<WildfireDebugData> WILDFIRES = registerDebug("wildfires", WildfireDebugData.PACKET_CODEC);
    private static <T> DebugSubscriptionType<T> registerDebug(String id, PacketCodec<? super RegistryByteBuf, T> packetCodec) {
        return Registry.register(Registries.DEBUG_SUBSCRIPTION, NekomasFixed.id(id), new DebugSubscriptionType<>(packetCodec));
    }

}
