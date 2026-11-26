package net.greenjab.nekomasfixed.util;

import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.ints.IntList;
import net.greenjab.nekomasfixed.registry.registries.LootFunctionRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

import java.util.List;

public class RandomFireworkModifier implements LootItemFunction {
    public static final MapCodec<RandomFireworkModifier> CODEC = MapCodec.unit(RandomFireworkModifier::new);

    @Override
    public LootItemFunctionType<RandomFireworkModifier> getType() {
        return LootFunctionRegistry.RANDOM_FIREWORK;
    }

    @Override
    public ItemStack apply(ItemStack stack, LootContext context) {
        return randomize(stack, context.getRandom());
    }

    public static ItemStack randomize(ItemStack stack, RandomSource random) {
        FireworkExplosion.Shape shape = FireworkExplosion.Shape.values()[random.nextInt(FireworkExplosion.Shape.values().length)];
        FireworkExplosion explosion = new FireworkExplosion(shape, IntList.of(random.nextInt(0xFFFFFF)), IntList.of(), random.nextBoolean(), random.nextBoolean());

        if (stack.is(Items.FIREWORK_STAR)) {
            stack.set(DataComponents.FIREWORK_EXPLOSION, explosion);
        } else if (stack.is(Items.FIREWORK_ROCKET)) {
            stack.set(DataComponents.FIREWORKS, new Fireworks(1 + random.nextInt(3), List.of(explosion)));
        }
        return stack;
    }

    public static LootItemFunction.Builder builder() {
        return RandomFireworkModifier::new;
    }
}
