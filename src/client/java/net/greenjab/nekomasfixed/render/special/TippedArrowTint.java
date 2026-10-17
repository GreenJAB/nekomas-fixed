package net.greenjab.nekomasfixed.render.special;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.other.TippedArrowCustomComponent;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.util.ColorMerger;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public record TippedArrowTint() implements ItemTintSource {
    public static final MapCodec<TippedArrowTint> MAP_CODEC = MapCodec.unit(new TippedArrowTint());

    @Override
    public int calculate(ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
        TippedArrowCustomComponent comp = itemStack.get(ComponentRegistry.TIPPED_POTION_CONTENTS);
        return comp == null ? 0xFFFFFFFF : ColorMerger.mix(comp.getColorsForPotions());
    }

    @Override
    public @NonNull MapCodec<TippedArrowTint> type() {
        return MAP_CODEC;
    }
}