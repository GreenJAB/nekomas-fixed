package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.other.*;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.dynamic.Codecs;
import java.util.List;
import java.util.function.UnaryOperator;

public class ComponentRegistry {
    public static void registerComponents() {
        System.out.println("register Component");
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
    private static <T> ComponentType<T> registerComponent(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, id, builderOperator.apply(ComponentType.builder()).build());}
}
