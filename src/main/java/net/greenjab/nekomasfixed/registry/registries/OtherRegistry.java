package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.registry.other.AnimalComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;

public class OtherRegistry {

    public static final ComponentType<AnimalComponent> ANIMAL = DataComponentTypes.register(
            "animal", builder -> builder.codec(AnimalComponent.CODEC).packetCodec(AnimalComponent.PACKET_CODEC).cache()
    );

    public static void registerOther() {
        System.out.println("register Other");
    }
}
