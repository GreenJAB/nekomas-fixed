package net.greenjab.nekomasfixed.registry.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class CustomTippedArrowRecipe extends CustomRecipe {
    public static final MapCodec<CustomTippedArrowRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Ingredient.CODEC.listOf().fieldOf("potions")
                            .forGetter(recipe -> recipe.potions),
                    ItemStackTemplate.CODEC.fieldOf("result")
                            .forGetter(recipe -> recipe.result)
            ).apply(instance, CustomTippedArrowRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, CustomTippedArrowRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
                    recipe -> recipe.potions,
                    ItemStackTemplate.STREAM_CODEC,
                    recipe -> recipe.result,
                    CustomTippedArrowRecipe::new
            );

    public static final RecipeSerializer<CustomTippedArrowRecipe> SERIALIZER =
            new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final List<Ingredient> potions;
    private final ItemStackTemplate result;

    public CustomTippedArrowRecipe(final List<Ingredient> potions, final ItemStackTemplate result) {
        this.potions = potions;
        this.result = result;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        return null;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return SERIALIZER;
    }
}