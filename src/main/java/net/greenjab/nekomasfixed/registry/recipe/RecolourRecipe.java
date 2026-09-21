package net.greenjab.nekomasfixed.registry.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.registry.registries.RecipeRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class RecolourRecipe implements CraftingRecipe {
    final String group;
    final CraftingBookCategory category;
    final Ingredient input;
    final Ingredient material;
    final ItemStack result;

    public RecolourRecipe(String group, CraftingBookCategory category, Ingredient input, Ingredient material, ItemStack result) {
        this.group = group;
        this.category = category;
        this.input = input;
        this.material = material;
        this.result = result;
    }

    @Override
    public @NonNull RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.RECOLOUR;
    }

    @Override
    public @NonNull String getGroup() {
        return this.group;
    }

    @Override
    public @NonNull CraftingBookCategory category() {
        return this.category;
    }

    @Override
    public @NonNull ItemStack getResultItem(HolderLookup.@NonNull Provider registries) {
        return this.result;
    }

    @Override
    public @NonNull NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, this.input, this.material);
    }

    @Override
    public boolean matches(CraftingInput input, @NonNull Level level) {
        // Vanilla TransmuteRecipe is strict about the grid: exactly one input
        // + one material, and any other populated slot invalidates the recipe.
        if (input.ingredientCount() != 2) {
            return false;
        }
        ItemStack matchedInput = null;
        int materialCount = 0;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) {
                continue;
            }
            if (this.input.test(stack)) {
                if (matchedInput != null) {
                    return false;
                }
                matchedInput = stack;
            } else if (this.material.test(stack)) {
                materialCount++;
            } else {
                return false;
            }
        }

        return matchedInput != null && materialCount == 1
                && !ItemStack.isSameItemSameComponents(matchedInput, this.result);
    }

    @Override
    public @NonNull ItemStack assemble(@NonNull CraftingInput input, HolderLookup.@NonNull Provider registries) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    public static class Serializer implements RecipeSerializer<RecolourRecipe> {
        public static final StreamCodec<RegistryFriendlyByteBuf, RecolourRecipe> STREAM_CODEC =
                StreamCodec.of(RecolourRecipe.Serializer::toNetwork, RecolourRecipe.Serializer::fromNetwork);
        private static final MapCodec<RecolourRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                com.mojang.serialization.Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                                CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(recipe -> recipe.category),
                                Ingredient.CODEC_NONEMPTY.fieldOf("input").forGetter(recipe -> recipe.input),
                                Ingredient.CODEC_NONEMPTY.fieldOf("material").forGetter(recipe -> recipe.material),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
                        )
                        .apply(instance, RecolourRecipe::new)
        );

        private static RecolourRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            CraftingBookCategory category = buffer.readEnum(CraftingBookCategory.class);
            Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient material = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
            return new RecolourRecipe(group, category, input, material, result);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, RecolourRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeEnum(recipe.category);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.material);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
        }

        @Override
        public @NonNull MapCodec<RecolourRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NonNull StreamCodec<RegistryFriendlyByteBuf, RecolourRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}