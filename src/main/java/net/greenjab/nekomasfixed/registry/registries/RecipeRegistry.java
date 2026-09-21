package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.recipe.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class RecipeRegistry {

    public static final RecipeSerializer<RecolourRecipe> RECOLOUR = register("recolour", new RecolourRecipe.Serializer());
    public static final SmithingEchoingFadeRecipe.Serializer SMITHING_ECHOING_FADE_RECIPE = register("smithing_echoing_fade", new SmithingEchoingFadeRecipe.Serializer());
    public static final SmithingEchoingPigmentRecipe.Serializer SMITHING_ECHOING_PIGMENT_RECIPE = register("smithing_echoing_pigment", new SmithingEchoingPigmentRecipe.Serializer());
    public static final SmithingEchoingTwinkleRecipe.Serializer SMITHING_ECHOING_TWINKLE_RECIPE = register("smithing_echoing_twinkle", new SmithingEchoingTwinkleRecipe.Serializer());
    public static final SmithingGlowingRecipe.Serializer SMITHING_GLOWING_RECIPE = register("smithing_glowing", new SmithingGlowingRecipe.Serializer());

    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String name, S serializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, NekomasFixed.id(name), serializer);
    }

    public static void registerRecipes() {
        NekomasFixed.LOGGER.info("Registering recipes");
    }
}