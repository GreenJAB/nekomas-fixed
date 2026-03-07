package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.registry.recipe.KilnRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public interface ModRecipeType<T extends Recipe<?>> {

    RecipeType<KilnRecipe> KILNING = register("kilning");

    static <T extends Recipe<?>> RecipeType<T> register(final String id) {
        return Registry.register(
                Registries.RECIPE_TYPE,
                Identifier.of("nekomasfixed", id),
                new RecipeType<>() {
                    @Override
                    public String toString() {
                        return "nekomasfixed:" + id;
                    }
                }
        );
    }
}