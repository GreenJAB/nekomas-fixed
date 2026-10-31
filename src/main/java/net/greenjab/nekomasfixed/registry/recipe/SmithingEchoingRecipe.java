package net.greenjab.nekomasfixed.registry.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.other.EchoingLayer;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.IngredientPlacement;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.recipe.input.SmithingRecipeInput;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class SmithingEchoingRecipe implements SmithingRecipe {
    final Ingredient template;
    final Ingredient base;
    final Ingredient addition;
    private @Nullable IngredientPlacement ingredientPlacement;

    public List<EchoingLayer> copyEchoingLayers(ItemStack item) {
        List<EchoingLayer> echoingLayers = item.get(ComponentRegistry.ECHOING_LAYERS);
        if (echoingLayers == null) return EchoingLayer.emptyList();
            // If the echoing layer limit reached, refuse to craft
        else if (echoingLayers.size() >= NekomasFixed.ECHOING_LAYER_LIMIT) {
            return null;
        }
        // If not, then copy layers from original item stack
        else {
            return new ArrayList<>(echoingLayers);
        }
    }

    public SmithingEchoingRecipe(Ingredient template, Ingredient base, Ingredient addition) {
        this.template = template;
        this.base = base;
        this.addition = addition;
    }

    @Override
    public Optional<Ingredient> template() {
        return Optional.of(template);
    }

    @Override
    public Ingredient base() {
        return base;
    }


    @Override
    public Optional<Ingredient> addition() {
        return Optional.of(addition);
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        if (this.ingredientPlacement == null) {
            this.ingredientPlacement = IngredientPlacement.forShapeless(List.of(this.template, this.base, this.addition));
        }

        return this.ingredientPlacement;
    }

    @Override
    public boolean matches(SmithingRecipeInput smithingRecipeInput, World world) {
        ItemStack armor = smithingRecipeInput.base();
        return Ingredient.matches(this.template(), smithingRecipeInput.template()) &&
                Ingredient.matches(this.addition(), smithingRecipeInput.addition()) &&
                base().test(armor) &&
                armor.contains(DataComponentTypes.TRIM);
    }

    @FunctionalInterface
    public interface RecipeFactory<T extends SmithingEchoingRecipe> {
        T create(Ingredient template, Ingredient base, Ingredient addition);
    }

    public static class Serializer<T extends SmithingEchoingRecipe> implements RecipeSerializer<T> {
        private final MapCodec<T> codec;
        public final PacketCodec<RegistryByteBuf, T> packetCodec;

        public Serializer(RecipeFactory<T> factory) {
            codec = RecordCodecBuilder.mapCodec(
                    (instance) -> instance.group(
                            Ingredient.CODEC.fieldOf("template").forGetter((recipe) -> recipe.template),
                            Ingredient.CODEC.fieldOf("base").forGetter((recipe) -> recipe.base),
                            Ingredient.CODEC.fieldOf("addition").forGetter((recipe) -> recipe.addition)
                    ).apply(instance, factory::create)
            );
            packetCodec = PacketCodec.tuple(
                    Ingredient.PACKET_CODEC, (recipe) -> recipe.template,
                    Ingredient.PACKET_CODEC, (recipe) -> recipe.base,
                    Ingredient.PACKET_CODEC, (recipe) -> recipe.addition,
                    factory::create
            );
        }

        public MapCodec<T> codec() {
            return codec;
        }

        public PacketCodec<RegistryByteBuf, T> packetCodec() {
            return packetCodec;
        }
    }
}
