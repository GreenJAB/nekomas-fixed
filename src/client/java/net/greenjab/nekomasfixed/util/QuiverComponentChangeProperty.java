package net.greenjab.nekomasfixed.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.item.quiver.QuiverContents;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.util.QuiverListState;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record QuiverComponentChangeProperty() implements SelectItemModelProperty<QuiverListState> {
    public static final QuiverComponentChangeProperty INSTANCE = new QuiverComponentChangeProperty();
    public static final MapCodec<QuiverComponentChangeProperty> CODEC = MapCodec.unit(INSTANCE);
    public static SelectItemModelProperty.Type<QuiverComponentChangeProperty, QuiverListState> TYPE;

    private static final Codec<QuiverListState> VALUE_CODEC =
            Codec.STRING.xmap(
                    s -> QuiverListState.valueOf(s.toUpperCase(java.util.Locale.ROOT)),
                    QuiverListState::name
            );

    private boolean checkForValidity(QuiverContents contents){
        if(contents!=null || !contents.isEmpty()){
            return contents.items().isEmpty();
        }
        return true;
    }

    @Nullable
    @Override
    public QuiverListState get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity owner, int seed, ItemDisplayContext displayContext) {
        var content = stack.get(ComponentRegistry.QUIVER_CONTENTS);

        return checkForValidity(content) ? QuiverListState.EMPTY : QuiverListState.NOT_EMPTY;
    }

    @Override
    public Codec<QuiverListState> valueCodec() {
        return VALUE_CODEC;
    }

    @Override
    public SelectItemModelProperty.Type<QuiverComponentChangeProperty, QuiverListState> type() {
        return TYPE;
    }
}