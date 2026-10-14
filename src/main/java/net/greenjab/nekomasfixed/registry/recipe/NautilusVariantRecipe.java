package net.greenjab.nekomasfixed.registry.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.registry.other.AnimalComponent;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import static net.greenjab.nekomasfixed.registry.registries.RecipeRegistry.NAUTILUS_VARIANT_RECIPE;

public record NautilusVariantRecipe(String group, Ingredient input, Ingredient material, ItemStack result) implements CraftingRecipe {

    @Override
    public boolean matches(@NotNull CraftingInput craftingInput, @NotNull Level level) {
        if (craftingInput.size() != 2) return false;
        ItemStack first = craftingInput.getItem(0);
        ItemStack second = craftingInput.getItem(1);
        return (this.input.test(first) && this.material.test(second))
                || (this.input.test(second) && this.material.test(first));
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingInput craftingInput, HolderLookup.@NotNull Provider registries) {
        ItemStack block = ItemStack.EMPTY;
        for (int i = 0; i < craftingInput.size(); i++) {
            ItemStack stack = craftingInput.getItem(i);
            if (this.input.test(stack)) {
                block = stack;
                break;
            }
        }
        ItemStack output = this.result.copy();
        if (!block.isEmpty()) {
            AnimalComponent animal = block.get(ComponentRegistry.ANIMAL);
            if (animal != null && !animal.animal().isEmpty()) {
                output.set(ComponentRegistry.ANIMAL, animal);
            }
        }
        return output;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, this.input, this.material);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider registries) {
        return this.result.copy();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return NAUTILUS_VARIANT_RECIPE;
    }

    @Override
    public @NotNull CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }

    public static class Serializer implements RecipeSerializer<NautilusVariantRecipe> {
        public static final StreamCodec<RegistryFriendlyByteBuf, NautilusVariantRecipe> PACKET_CODEC =
                StreamCodec.of(NautilusVariantRecipe.Serializer::write, NautilusVariantRecipe.Serializer::read);
        private static final MapCodec<NautilusVariantRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        com.mojang.serialization.Codec.STRING.optionalFieldOf("group", "").forGetter(NautilusVariantRecipe::group),
                        Ingredient.CODEC.fieldOf("input").forGetter(NautilusVariantRecipe::input),
                        Ingredient.CODEC.fieldOf("material").forGetter(NautilusVariantRecipe::material),
                        ItemStack.SINGLE_ITEM_CODEC.fieldOf("result").forGetter(NautilusVariantRecipe::result)
                ).apply(instance, NautilusVariantRecipe::new));

        private static NautilusVariantRecipe read(RegistryFriendlyByteBuf buf) {
            String group = buf.readUtf();
            Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            Ingredient material = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            ItemStack result = ItemStack.STREAM_CODEC.decode(buf);
            return new NautilusVariantRecipe(group, input, material, result);
        }

        private static void write(RegistryFriendlyByteBuf buf, NautilusVariantRecipe recipe) {
            buf.writeUtf(recipe.group());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.input());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.material());
            ItemStack.STREAM_CODEC.encode(buf, recipe.result());
        }

        @Override
        public @NotNull MapCodec<NautilusVariantRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, NautilusVariantRecipe> streamCodec() {
            return PACKET_CODEC;
        }
    }
}
