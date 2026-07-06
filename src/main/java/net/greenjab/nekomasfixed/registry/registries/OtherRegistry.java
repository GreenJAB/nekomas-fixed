package net.greenjab.nekomasfixed.registry.registries;

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
import net.minecraft.util.debug.DebugSubscription;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.dolphin.Dolphin;
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

    //debug
    public static final DebugSubscription<WildfireDebugData> WILDFIRES = registerDebug("wildfires", WildfireDebugData.PACKET_CODEC);
    private static <T> DebugSubscription<T> registerDebug(String id, StreamCodec<? super RegistryFriendlyByteBuf, T> packetCodec) {
        return Registry.register(BuiltInRegistries.DEBUG_SUBSCRIPTION, NekomasFixed.id(id), new DebugSubscription<>(packetCodec));
    }

}
