package net.greenjab.nekomasfixed.registry.registries;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.util.LoadedCrossbowModifier;
import net.greenjab.nekomasfixed.util.RandomFireworkModifier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.greenjab.nekomasfixed.util.RandomDyeModifier;

public class LootFunctionRegistry {
    public static final MapCodec<RandomFireworkModifier> RANDOM_FIREWORK = register(
            "random_firework",
            RandomFireworkModifier.CODEC
    );

    public static final MapCodec<RandomDyeModifier> RANDOM_DYE = register(
            "random_dye",
            RandomDyeModifier.CODEC
    );

    public static final MapCodec<LoadedCrossbowModifier> LOADED_CROSSBOW = register(
            "loaded_crossbow",
            LoadedCrossbowModifier.CODEC
    );

    private static <T extends LootItemFunction> MapCodec<T> register(String name, MapCodec<T> codec) {
        return Registry.register(
                BuiltInRegistries.LOOT_FUNCTION_TYPE,
                Identifier.fromNamespaceAndPath(NekomasFixed.NAMESPACE, name),
                codec
        );
    }

    public static void registerLootFunctions() {
        NekomasFixed.LOGGER.info("Registering Loot Functions for Nekoma's Fixed");
    }
}