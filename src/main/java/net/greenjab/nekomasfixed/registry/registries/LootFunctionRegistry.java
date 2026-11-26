package net.greenjab.nekomasfixed.registry.registries;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.util.RandomDyeModifier;
import net.greenjab.nekomasfixed.util.RandomFireworkModifier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class LootFunctionRegistry {
    public static final LootItemFunctionType<RandomFireworkModifier> RANDOM_FIREWORK = register("random_firework", RandomFireworkModifier.CODEC);
    public static final LootItemFunctionType<RandomDyeModifier> RANDOM_DYE = register("random_dye", RandomDyeModifier.CODEC);

    private static <T extends LootItemFunction> LootItemFunctionType<T> register(String name, MapCodec<T> codec) {
        return Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, NekomasFixed.id(name), new LootItemFunctionType<>(codec));
    }

    public static void registerLootFunctions() {
        NekomasFixed.LOGGER.info("Registering loot functions");
    }
}
