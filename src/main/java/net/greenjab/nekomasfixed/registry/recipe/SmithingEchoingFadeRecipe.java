package net.greenjab.nekomasfixed.registry.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;

import net.greenjab.nekomasfixed.util.EchoingKeyframe;
import net.greenjab.nekomasfixed.util.EchoingLayer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

import static net.greenjab.nekomasfixed.registry.registries.RecipeRegistry.SMITHING_ECHOING_FADE_RECIPE;

public class SmithingEchoingFadeRecipe extends SmithingToggleEchoingFlagRecipe {

    public SmithingEchoingFadeRecipe(Ingredient template, Ingredient base, Ingredient addition) {
        super(template, base, addition);
    }

    @Override
    public EchoingLayer computeEchoingLayer(EchoingKeyframe keyFrame) {
        return new EchoingLayer(
                Optional.empty(),
                Optional.of(!keyFrame.hidden()),
                Optional.empty()
        );
    }
    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return SMITHING_ECHOING_FADE_RECIPE;
    }

    public static class Serializer implements RecipeSerializer<SmithingEchoingFadeRecipe> {
        private static final MapCodec<SmithingEchoingFadeRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
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
                ).apply(instance, SmithingEchoingFadeRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, SmithingEchoingFadeRecipe> PACKET_CODEC =
                StreamCodec.of(
                        SmithingEchoingFadeRecipe.Serializer::write,
                        SmithingEchoingFadeRecipe.Serializer::read
                );

        public Serializer() {
        }

        public @NotNull MapCodec<SmithingEchoingFadeRecipe> codec() {
            return CODEC;
        }

        public @NotNull StreamCodec<RegistryFriendlyByteBuf, SmithingEchoingFadeRecipe> streamCodec() {
            return PACKET_CODEC;
        }

        private static SmithingEchoingFadeRecipe read(RegistryFriendlyByteBuf buf) {
            Ingredient template = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            Ingredient base = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            Ingredient addition = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            return new SmithingEchoingFadeRecipe(template, base, addition);
        }

        private static void write(RegistryFriendlyByteBuf buf, SmithingEchoingFadeRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.getTemplate());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.getBase());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.getAddition());
        }
    }
}

