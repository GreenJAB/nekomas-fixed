package net.greenjab.nekomasfixed.util;

import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;

import java.util.List;

public class LoadedCrossbowModifier implements LootItemFunction {
    public static final MapCodec<LoadedCrossbowModifier> CODEC = MapCodec.unit(LoadedCrossbowModifier::new);

    @Override
    public MapCodec<? extends LootItemFunction> codec() {
        return CODEC;
    }

    @Override
    public ItemStack apply(ItemStack stack, LootContext context) {
        RandomSource random = context.getRandom();

        FireworkExplosion.Shape shape = FireworkExplosion.Shape.values()[random.nextInt(FireworkExplosion.Shape.values().length)];
        IntList colors = IntArrayList.of(random.nextInt(0xFFFFFF), random.nextInt(0xFFFFFF));
        FireworkExplosion explosion = new FireworkExplosion(shape, colors, IntList.of(), true, true);

        ItemStack rocket = new ItemStack(Items.FIREWORK_ROCKET, 1);
        rocket.set(DataComponents.FIREWORKS, new Fireworks(3, List.of(explosion)));
        stack.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.of(ItemStackTemplate.fromNonEmptyStack(rocket)));

        HolderLookup.RegistryLookup<Enchantment> enchantLookup = context.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        ResourceKey<Enchantment> enchantKey = random.nextBoolean() ? Enchantments.QUICK_CHARGE : Enchantments.MULTISHOT;

        enchantLookup.get(enchantKey).ifPresent(holder -> {
            ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(
                    stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
            );
            mutable.set(holder, enchantKey.equals(Enchantments.QUICK_CHARGE) ? 2 : 1);
            stack.set(DataComponents.ENCHANTMENTS, mutable.toImmutable());
        });

        return stack;
    }

    public static LootItemFunction.Builder builder() {
        return LoadedCrossbowModifier::new;
    }
}