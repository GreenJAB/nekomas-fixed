package net.greenjab.nekomasfixed.registry.other.predicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.item.quiver.QuiverContents;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.minecraft.advancements.predicates.SingleComponentItemPredicate;
import net.minecraft.core.component.DataComponentType;
import org.jspecify.annotations.NonNull;

public record QuiverPredicate() implements SingleComponentItemPredicate<QuiverContents> {
    public static final Codec<QuiverPredicate> CODEC =
            MapCodec.unitCodec(QuiverPredicate::new);

    @Override
    public @NonNull DataComponentType<QuiverContents> componentType() {
        return ComponentRegistry.QUIVER_CONTENTS;
    }

    @Override
    public boolean matches(final QuiverContents contents) {
        return contents.items().iterator().hasNext();
    }


}
