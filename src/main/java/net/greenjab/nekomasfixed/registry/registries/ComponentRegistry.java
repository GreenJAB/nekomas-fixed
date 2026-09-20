package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.other.StoredTimeComponent;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;

public class ComponentRegistry {

    public static final DataComponentType<Integer> CLAM_STATE = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE, NekomasFixed.id("clam_state"),
            DataComponentType.<Integer>builder()
                    .persistent(ExtraCodecs.intRange(0, 3))
                    .networkSynchronized(ByteBufCodecs.INT)
                    .build());

    // Registered under minecraft: (not the mod namespace) so the clock loot table's copy_components
    // include can reference it as "minecraft:stored_time", exactly as main does.
    public static final DataComponentType<StoredTimeComponent> STORED_TIME = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.withDefaultNamespace("stored_time"),
            DataComponentType.<StoredTimeComponent>builder()
                    .persistent(StoredTimeComponent.CODEC)
                    .networkSynchronized(StoredTimeComponent.PACKET_CODEC)
                    .build());

    public static void registerComponents() {
        NekomasFixed.LOGGER.info("Registering components");
    }
}
