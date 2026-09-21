package net.greenjab.nekomasfixed.registry.registries;

import com.mojang.serialization.Codec;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.util.BannerEffects;
import net.greenjab.nekomasfixed.util.EchoingLayer;
import net.greenjab.nekomasfixed.registry.other.StoredTimeComponent;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;

import java.util.List;
import java.util.function.UnaryOperator;

public class ComponentRegistry {

    public static final DataComponentType<Integer> CLAM_STATE = register(
            "clam_state", builder -> builder
                    .persistent(ExtraCodecs.intRange(0, 3))
                    .networkSynchronized(ByteBufCodecs.INT));

    public static final DataComponentType<List<Boolean>> SHERD_GLOW_OVERRIDES = register(
            "sherd_glow_overrides", builder -> builder
                    .persistent(Codec.list(Codec.BOOL))
                    .networkSynchronized(StreamCodec.ofMember(
                            (value, buf) -> value.forEach(buf::writeBoolean),
                            buf -> List.of(buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean())
                    ))
                    .cacheEncoding());
    public static final DataComponentType<BannerEffects> BANNER_EFFECTS = register(
            "banner_effects", builder -> builder
                    .persistent(BannerEffects.CODEC)
                    .networkSynchronized(BannerEffects.PACKET_CODEC)
                    .cacheEncoding());
    public static final DataComponentType<List<EchoingLayer>> ECHOING_LAYERS = register(
            "echoing_layers", builder -> builder
                    .persistent(EchoingLayer.CODEC.listOf(1, NekomasFixed.ECHOING_LAYER_LIMIT))
                    .networkSynchronized(EchoingLayer.PACKET_CODEC.apply(ByteBufCodecs.list()))
                    .cacheEncoding());
    public static final List<Boolean> DEFAULT_SHERD_GLOW_OVERRIDES = List.of(false, false, false, false);

    private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, NekomasFixed.id(id), builderOperator.apply(DataComponentType.builder()).build());
    }

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
