package net.greenjab.nekomasfixed.registry.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.util.EchoingKeyframe;
import net.greenjab.nekomasfixed.util.EchoingLayer;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

import static net.greenjab.nekomasfixed.registry.registries.RecipeRegistry.SMITHING_ECHOING_PIGMENT_RECIPE;

public class SmithingEchoingPigmentRecipe extends SmithingEchoingRecipe {

    public SmithingEchoingPigmentRecipe(Ingredient template, Ingredient base, Ingredient addition) {
        super(template, base, addition);
    }

    public @NotNull ItemStack assemble(SmithingRecipeInput smithingRecipeInput, HolderLookup.@NotNull Provider wrapperLookup) {
        ItemStack itemStack = smithingRecipeInput.base();
        if (isBaseIngredient(itemStack)) {
            Optional<Holder.Reference<TrimMaterial>> optionalMaterial = TrimMaterials.getFromIngredient(wrapperLookup, smithingRecipeInput.addition());
            if (optionalMaterial.isPresent()) {
                Holder<TrimMaterial> material = optionalMaterial.get();

                EchoingKeyframe last = EchoingKeyframe.buildLast(itemStack);
                if (last.material().equals(material)) {
                    return ItemStack.EMPTY;
                }

                List<EchoingLayer> echoingLayers = copyEchoingLayers(itemStack);
                if (echoingLayers == null) {
                    return ItemStack.EMPTY;
                }

                echoingLayers.add(new EchoingLayer(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(material)
                ));

                ItemStack newStack = itemStack.copyWithCount(1);
                newStack.set(ComponentRegistry.ECHOING_LAYERS, echoingLayers);
                return newStack;
            }
        }

        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return SMITHING_ECHOING_PIGMENT_RECIPE;
    }

    public static class Serializer implements RecipeSerializer<SmithingEchoingPigmentRecipe> {
        private static final MapCodec<SmithingEchoingPigmentRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
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
                ).apply(instance, SmithingEchoingPigmentRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, SmithingEchoingPigmentRecipe> PACKET_CODEC =
                StreamCodec.of(
                        SmithingEchoingPigmentRecipe.Serializer::write,
                        SmithingEchoingPigmentRecipe.Serializer::read
                );

        public Serializer() {
        }

        public @NotNull MapCodec<SmithingEchoingPigmentRecipe> codec() {
            return CODEC;
        }

        public @NotNull StreamCodec<RegistryFriendlyByteBuf, SmithingEchoingPigmentRecipe> streamCodec() {
            return PACKET_CODEC;
        }

        private static SmithingEchoingPigmentRecipe read(RegistryFriendlyByteBuf buf) {
            Ingredient template = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            Ingredient base = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            Ingredient addition = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            return new SmithingEchoingPigmentRecipe(template, base, addition);
        }

        private static void write(RegistryFriendlyByteBuf buf, SmithingEchoingPigmentRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.getTemplate());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.getBase());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.getAddition());
        }
    }
}

