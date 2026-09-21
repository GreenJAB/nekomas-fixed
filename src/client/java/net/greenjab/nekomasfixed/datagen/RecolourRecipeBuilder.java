package net.greenjab.nekomasfixed.datagen;

import net.greenjab.nekomasfixed.registry.recipe.RecolourRecipe;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * RecipeBuilder for the mod's custom `nekomasfixed:recolour` recipe type.
 * Reproduces the 26.x colour-transmute field shape (input/material/result) as a
 * single-input+matching-dye craft, mirroring the hand-generated recipe JSONs.
 */
public class RecolourRecipeBuilder implements RecipeBuilder {
    private final Ingredient input;
    private final Ingredient material;
    private final ItemStack result;
    private final RecipeCategory category;
    private final CraftingBookCategory bookCategory;
    private final String group;
    private final Map<String, Criterion<?>> criteria = new HashMap<>();

    public RecolourRecipeBuilder(RecipeCategory category, CraftingBookCategory bookCategory,
                                 Ingredient input, Ingredient material, ItemStack result, String group) {
        this.category = category;
        this.bookCategory = bookCategory;
        this.input = input;
        this.material = material;
        this.result = result;
        this.group = group;
    }

    public static RecolourRecipeBuilder recolour(RecipeCategory category, CraftingBookCategory bookCategory,
                                                 Ingredient input, Ingredient material, ItemStack result, String group) {
        return new RecolourRecipeBuilder(category, bookCategory, input, material, result, group);
    }

    @Override
    public @NonNull RecolourRecipeBuilder unlockedBy(@NonNull String name, @NonNull Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public @NonNull RecolourRecipeBuilder group(String group) {
        return this;
    }

    @Override
    public @NonNull Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(RecipeOutput output, @NonNull ResourceLocation id) {
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);

        // material criterion (has_needed_dye) mirrors vanilla builders.
        for (Map.Entry<String, Criterion<?>> e : this.criteria.entrySet()) {
            advancement.addCriterion(e.getKey(), e.getValue());
        }

        AdvancementHolder holder = advancement.build(
                id.withPrefix("recipes/" + category.getFolderName() + "/"));

        RecolourRecipe recipe = new RecolourRecipe(this.group, this.bookCategory, this.input, this.material, this.result);
        output.accept(id, recipe, holder);
    }
}