package net.greenjab.nekomasfixed.util;

import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;

import java.util.List;

public class RandomFireworkModifier implements LootItemFunction {
    public static final MapCodec<RandomFireworkModifier> CODEC = MapCodec.unit(RandomFireworkModifier::new);

    @Override
    public MapCodec<? extends LootItemFunction> codec() {
        return CODEC;
    }

    @Override
    public ItemStack apply(ItemStack stack, LootContext context) {
        RandomSource random = context.getRandom();

        FireworkExplosion.Shape shape = FireworkExplosion.Shape.values()[random.nextInt(FireworkExplosion.Shape.values().length)];
        IntList colors = IntArrayList.of(random.nextInt(0xFFFFFF));
        FireworkExplosion explosion = new FireworkExplosion(shape, colors, IntList.of(), random.nextBoolean(), random.nextBoolean());

        if (stack.is(Items.FIREWORK_STAR)) {
            stack.set(DataComponents.FIREWORK_EXPLOSION, explosion);
        } else if (stack.is(Items.FIREWORK_ROCKET)) {
            int flightDuration = 1 + random.nextInt(3);
            stack.set(DataComponents.FIREWORKS, new Fireworks(flightDuration, List.of(explosion)));
        }

        return stack;
    }

    public static LootItemFunction.Builder builder() {
        return RandomFireworkModifier::new;
    }
}