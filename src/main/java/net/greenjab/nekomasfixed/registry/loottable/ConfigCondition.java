package net.greenjab.nekomasfixed.registry.loottable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.util.Option;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jspecify.annotations.NonNull;

public record ConfigCondition(String option) implements LootItemCondition {
    public static final MapCodec<ConfigCondition> CODEC = RecordCodecBuilder.mapCodec(i ->
            i.group(Codec.STRING.fieldOf("option").forGetter(ConfigCondition::option))
                    .apply(i, ConfigCondition::new));


    public static void register() { }


    @Override
    public boolean test(LootContext context) {
        for (Option<?> o : Option.ALL) {
            if (o.name.equals(option) && o.get() instanceof Boolean b) return b;
        }
        return false;
    }

    @Override
    public @NonNull MapCodec<? extends LootItemCondition> codec() {
        return CODEC;
    }
}
