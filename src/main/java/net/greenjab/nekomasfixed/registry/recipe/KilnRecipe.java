package net.greenjab.nekomasfixed.registry.recipe;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.registry.registries.RecipeRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import org.jspecify.annotations.NonNull;


public class KilnRecipe extends AbstractCookingRecipe {
    public static final MapCodec<KilnRecipe> MAP_CODEC = cookingMapCodec(KilnRecipe::new, 100);
    public static final StreamCodec<RegistryFriendlyByteBuf, KilnRecipe> STREAM_CODEC = cookingStreamCodec(KilnRecipe::new);
    public static final RecipeSerializer<KilnRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    public KilnRecipe(
            final Recipe.CommonInfo commonInfo,
            final AbstractCookingRecipe.CookingBookInfo bookInfo,
            final Ingredient ingredient,
            final ItemStackTemplate result,
            final float experience,
            final int cookingTime
    ) {
        super(commonInfo, bookInfo, ingredient, result, experience, cookingTime);
    }

    protected @NonNull Item furnaceIcon() {
        return ItemRegistry.KILN;
    }

    public @NonNull RecipeSerializer<KilnRecipe> getSerializer() {
        return SERIALIZER;
    }

    public @NonNull RecipeType<KilnRecipe> getType() {
        return RecipeRegistry.KILN;
    }

    public @NonNull RecipeBookCategory recipeBookCategory() {
        return switch (this.category()) {
            case BLOCKS -> RecipeRegistry.KILNING_BLOCK;
            case FOOD, MISC -> RecipeRegistry.KILNING_MISC;
        };
    }
}
