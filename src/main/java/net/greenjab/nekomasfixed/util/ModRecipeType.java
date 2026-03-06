//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.registry.recipe.KilnRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public interface ModRecipeType<T extends Recipe<?>> {
    RecipeType<KilnRecipe> KILNING = register("kilning");

    static <T extends Recipe<?>> RecipeType register(final String id) {
        return (RecipeType) Registry.register(
                Registries.RECIPE_TYPE,
                Identifier.ofVanilla(id),
                new RecipeType<T>() {
                    public String toString() {
                        return id;
                    }
                }
        );
    }
}
