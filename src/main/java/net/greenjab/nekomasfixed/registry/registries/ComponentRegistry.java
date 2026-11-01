package net.greenjab.nekomasfixed.registry.registries;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.item.v1.ComponentTooltipAppenderRegistry;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.other.*;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.dynamic.Codecs;
import java.util.List;
import java.util.function.UnaryOperator;

public class ComponentRegistry {
    public static void registerComponents() {
        System.out.println("register Component");
        ComponentTooltipAppenderRegistry.addAfter(DataComponentTypes.BANNER_PATTERNS, BANNER_EFFECTS);
    }

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

    public static final ComponentType<List<Boolean>> SHERD_GLOW_OVERRIDES = register(
            "sherd_glow_overrides", builder -> builder
                    .codec(Codec.list(Codec.BOOL))
                    .packetCodec(PacketCodec.of(
                            (value, buf) -> value.forEach(buf::writeBoolean),
                            buf -> List.of(buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean())
                    ))
                    .cache());

    public static final ComponentType<BannerEffects> BANNER_EFFECTS = register(
            "banner_effects", builder -> builder
                    .codec(BannerEffects.CODEC)
                    .packetCodec(BannerEffects.PACKET_CODEC)
                    .cache());

    public static final ComponentType<List<EchoingLayer>> ECHOING_LAYERS = register(
            "echoing_layers", builder -> builder
                    .codec(EchoingLayer.CODEC.listOf(1, NekomasFixed.ECHOING_LAYER_LIMIT))
                    .packetCodec(EchoingLayer.PACKET_CODEC.collect(PacketCodecs.toList()))
                    .cache());


    public static final List<Boolean> DEFAULT_SHERD_GLOW_OVERRIDES = List.of(false, false, false, false);

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, NekomasFixed.id(id), builderOperator.apply(ComponentType.builder()).build());}

    private static <T> ComponentType<T> registerComponent(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, id, builderOperator.apply(ComponentType.builder()).build());}
}
