package net.greenjab.nekomasfixed.util;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.registry.registries.LootFunctionRegistry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

import java.util.ArrayList;
import java.util.List;

public class RandomDyeModifier implements LootItemFunction {
    public static final MapCodec<RandomDyeModifier> CODEC = MapCodec.unit(RandomDyeModifier::new);

    @Override
    public LootItemFunctionType<RandomDyeModifier> getType() {
        return LootFunctionRegistry.RANDOM_DYE;
    }

    @Override
    public ItemStack apply(ItemStack stack, LootContext context) {
        return randomize(stack, context.getRandom());
    }

    public static ItemStack randomize(ItemStack stack, RandomSource random) {
        List<Item> dyes = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            dyes.add(DyeItem.byColor(color));
        }
        dyes.addAll(List.of(ItemRegistry.AMBER_DYE, ItemRegistry.AQUA_DYE, ItemRegistry.INDIGO_DYE, ItemRegistry.MAROON_DYE));
        return new ItemStack(dyes.get(random.nextInt(dyes.size())), stack.getCount());
    }

    public static LootItemFunction.Builder builder() {
        return RandomDyeModifier::new;
    }
}
