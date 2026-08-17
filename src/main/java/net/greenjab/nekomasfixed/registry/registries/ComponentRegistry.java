package net.greenjab.nekomasfixed.registry.registries;

import com.mojang.serialization.Codec;
import net.greenjab.nekomasfixed.registry.item.quiver.QuiverContents;
import net.greenjab.nekomasfixed.registry.other.*;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.alchemy.PotionContents;

import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class ComponentRegistry {
    public static void registerComponents() {
        System.out.println("register Component");
    }

    public static final DataComponentType<AnimalComponent> ANIMAL = registerComponent(
            "animal", builder -> builder.persistent(AnimalComponent.CODEC).networkSynchronized(AnimalComponent.PACKET_CODEC).cacheEncoding());
    public static final DataComponentType<TermitesComponent> TERMITES = registerComponent(
            "termites", builder -> builder.persistent(TermitesComponent.CODEC).networkSynchronized(TermitesComponent.PACKET_CODEC).cacheEncoding());

    public static final DataComponentType<Integer> CLAM_STATE = registerComponent(
            "clam_state", builder -> builder.persistent(ExtraCodecs.intRange(0, 3)).networkSynchronized(ByteBufCodecs.INT));
    public static final DataComponentType<StoredTimeComponent> STORED_TIME = registerComponent("stored_time", builder -> builder.persistent(StoredTimeComponent.CODEC).networkSynchronized(StoredTimeComponent.PACKET_CODEC).cacheEncoding());

    public static final DataComponentType<ComboComponent> COMBO_MULTIPLIER = registerComponent(
            "combo_multiplier", builder -> builder.persistent(ComboComponent.CODEC).networkSynchronized(ComboComponent.PACKET_CODEC).cacheEncoding());

    public static final DataComponentType<QuiverContents> QUIVER_CONTENTS = registerComponent("quiver_contents", (b) -> b.persistent(QuiverContents.CODEC).networkSynchronized(QuiverContents.STREAM_CODEC).cacheEncoding());


    public static final DataComponentType<TippedArrowCustomComponent> TIPPED_POTION_CONTENTS = registerComponent("tipped_potion_contents", (b) -> b.persistent(TippedArrowCustomComponent.CODEC).networkSynchronized(TippedArrowCustomComponent.STREAM_CODEC).cacheEncoding());


    private static <T> DataComponentType<T> registerComponent(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, id, builderOperator.apply(DataComponentType.builder()).build());}

}
