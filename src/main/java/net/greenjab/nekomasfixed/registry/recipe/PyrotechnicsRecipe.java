package net.greenjab.nekomasfixed.registry.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.recipe.IngredientPlacement;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.book.RecipeBookCategories;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import java.util.List;
import java.util.Optional;

public class PyrotechnicsRecipe implements Recipe<RecipeInput> {

    private final Ingredient dye1;
    private final Ingredient dye2;
    private final Ingredient paper;
    private final Ingredient gunpowder;
    private final ItemStack output;
    private IngredientPlacement ingredientPlacement;

    public PyrotechnicsRecipe(Ingredient dye1, Ingredient dye2, Ingredient paper, Ingredient gunpowder, ItemStack output) {
        this.dye1 = dye1;
        this.dye2 = dye2;
        this.paper = paper;
        this.gunpowder = gunpowder;
        this.output = output;
    }

    public static final MapCodec<PyrotechnicsRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.fieldOf("dye1").forGetter(recipe -> recipe.dye1),
            Ingredient.CODEC.fieldOf("dye2").forGetter(recipe -> recipe.dye2),
            Ingredient.CODEC.fieldOf("paper").forGetter(recipe -> recipe.paper),
            Ingredient.CODEC.fieldOf("gunpowder").forGetter(recipe -> recipe.gunpowder),
            ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.output)
    ).apply(instance, PyrotechnicsRecipe::new));

    public static final PacketCodec<RegistryByteBuf, PyrotechnicsRecipe> PACKET_CODEC = PacketCodec.tuple(
            Ingredient.PACKET_CODEC, recipe -> recipe.dye1,
            Ingredient.PACKET_CODEC, recipe -> recipe.dye2,
            Ingredient.PACKET_CODEC, recipe -> recipe.paper,
            Ingredient.PACKET_CODEC, recipe -> recipe.gunpowder,
            ItemStack.PACKET_CODEC, recipe -> recipe.output,
            PyrotechnicsRecipe::new
    );

    @Override
    public boolean matches(RecipeInput input, World world) {
        if (input.size() < 4) return false;
        return this.dye1.test(input.getStackInSlot(0)) &&
                this.dye2.test(input.getStackInSlot(1)) &&
                this.paper.test(input.getStackInSlot(2)) &&
                this.gunpowder.test(input.getStackInSlot(3));
    }

    @Override
    public ItemStack craft(RecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return this.output.copy();
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        if (this.ingredientPlacement == null) {
            this.ingredientPlacement = IngredientPlacement.forMultipleSlots(List.of(
                    Optional.of(this.dye1),
                    Optional.of(this.dye2),
                    Optional.of(this.paper),
                    Optional.of(this.gunpowder)
            ));
        }
        return this.ingredientPlacement;
    }

    @Override
    public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<? extends Recipe<RecipeInput>> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<PyrotechnicsRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "pyrotechnics";
    }

    public static class Serializer implements RecipeSerializer<PyrotechnicsRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "pyrotechnics";

        @Override
        public MapCodec<PyrotechnicsRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, PyrotechnicsRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}