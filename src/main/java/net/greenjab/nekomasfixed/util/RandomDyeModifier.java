package net.greenjab.nekomasfixed.util;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;

import java.util.List;

public class RandomDyeModifier implements LootItemFunction {
    public static final MapCodec<RandomDyeModifier> CODEC = MapCodec.unit(RandomDyeModifier::new);

    @Override
    public MapCodec<? extends LootItemFunction> codec() {
        return CODEC;
    }

    @Override
    public ItemStack apply(ItemStack stack, LootContext context) {
        List<DyeItem> allDyes = BuiltInRegistries.ITEM.stream()
                .filter(DyeItem.class::isInstance)
                .map(DyeItem.class::cast)
                .toList();

        if (!allDyes.isEmpty()) {
            RandomSource random = context.getRandom();
            DyeItem chosen = allDyes.get(random.nextInt(allDyes.size()));
            return stack.transmuteCopy(chosen, stack.getCount());
        }

        return stack;
    }

    public static LootItemFunction.Builder builder() {
        return RandomDyeModifier::new;
    }
}