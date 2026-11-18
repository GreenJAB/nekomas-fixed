package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.recipe.NautilusVariantRecipe;
import net.greenjab.nekomasfixed.registry.recipe.SmithingEchoingFadeRecipe;
import net.greenjab.nekomasfixed.registry.recipe.SmithingEchoingPigmentRecipe;
import net.greenjab.nekomasfixed.registry.recipe.SmithingEchoingTwinkleRecipe;
import net.greenjab.nekomasfixed.registry.recipe.SmithingGlowingRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.greenjab.nekomasfixed.registry.recipe.KilnRecipe;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeRegistry {

    public static final SmithingEchoingFadeRecipe.Serializer SMITHING_ECHOING_FADE_RECIPE = register("smithing_echoing_fade", new SmithingEchoingFadeRecipe.Serializer());
    public static final SmithingEchoingPigmentRecipe.Serializer SMITHING_ECHOING_PIGMENT_RECIPE = register("smithing_echoing_pigment", new SmithingEchoingPigmentRecipe.Serializer());
    public static final SmithingEchoingTwinkleRecipe.Serializer SMITHING_ECHOING_TWINKLE_RECIPE = register("smithing_echoing_twinkle", new SmithingEchoingTwinkleRecipe.Serializer());
    public static final SmithingGlowingRecipe.Serializer SMITHING_GLOWING_RECIPE = register("smithing_glowing", new SmithingGlowingRecipe.Serializer());
    public static final NautilusVariantRecipe.Serializer NAUTILUS_VARIANT_RECIPE = register("nautilus_variant", new NautilusVariantRecipe.Serializer());
    public static final RecipeType<KilnRecipe> KILN = Registry.register(BuiltInRegistries.RECIPE_TYPE, NekomasFixed.id("kilning"), new RecipeType<>() {
        @Override
        public String toString() {
            return "kilning";
        }
    });
    public static final RecipeSerializer<KilnRecipe> KILNING = register("kilning", new SimpleCookingSerializer<>(KilnRecipe::new, 100));

    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String name, S serializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, NekomasFixed.id(name), serializer);
    }

    public static void registerRecipes() {
        NekomasFixed.LOGGER.info("Registering recipes");
    }
}