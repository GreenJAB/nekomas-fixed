package net.greenjab.nekomasfixed.registry.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.util.EchoingLayer;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import org.jetbrains.annotations.NotNull;

import static net.greenjab.nekomasfixed.registry.registries.RecipeRegistry.SMITHING_GLOWING_RECIPE;

public class SmithingGlowingRecipe extends SmithingEchoingRecipe {

    public SmithingGlowingRecipe(Ingredient template, Ingredient base, Ingredient addition) {
        super(template, base, addition);
    }

    public @NotNull ItemStack assemble(SmithingRecipeInput smithingRecipeInput, HolderLookup.@NotNull Provider wrapperLookup) {
        ItemStack itemStack = smithingRecipeInput.base();
        if (isBaseIngredient(itemStack)) {
            List<EchoingLayer> echoingLayers = new ArrayList<>();
            echoingLayers.add(new EchoingLayer(
                    Optional.of(true),
                    Optional.empty(),
                    Optional.empty()
            ));
            ItemStack newStack = itemStack.copyWithCount(1);
            newStack.set(ComponentRegistry.ECHOING_LAYERS, echoingLayers);
            return newStack;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return SMITHING_GLOWING_RECIPE;
    }

    public static class Serializer implements RecipeSerializer<SmithingGlowingRecipe> {
        private static final MapCodec<SmithingGlowingRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
                instance.group(
                        Ingredient.CODEC
                                .fieldOf("template")
                                .forGetter(SmithingEchoingRecipe::getTemplate),
                        Ingredient.CODEC
                                .fieldOf("base")
                                .forGetter(SmithingEchoingRecipe::getBase),
                        Ingredient.CODEC
                                .fieldOf("addition")
                                .forGetter(SmithingEchoingRecipe::getAddition)
                ).apply(instance, SmithingGlowingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, SmithingGlowingRecipe> PACKET_CODEC =
                StreamCodec.of(
                        SmithingGlowingRecipe.Serializer::write,
                        SmithingGlowingRecipe.Serializer::read
                );

        public Serializer() {
        }

        public @NotNull MapCodec<SmithingGlowingRecipe> codec() {
            return CODEC;
        }

        public @NotNull StreamCodec<RegistryFriendlyByteBuf, SmithingGlowingRecipe> streamCodec() {
            return PACKET_CODEC;
        }

        private static SmithingGlowingRecipe read(RegistryFriendlyByteBuf buf) {
            Ingredient template = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            Ingredient base = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            Ingredient addition = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            return new SmithingGlowingRecipe(template, base, addition);
        }

        private static void write(RegistryFriendlyByteBuf buf, SmithingGlowingRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.getTemplate());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.getBase());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.getAddition());
        }
    }
}

