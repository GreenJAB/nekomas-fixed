package net.greenjab.nekomasfixed.registry.registries;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.recipe.CoralNautilusRecipe;
import net.greenjab.nekomasfixed.registry.recipe.KilnRecipe;
import net.greenjab.nekomasfixed.registry.recipe.ZombieNautilusRecipe;
import net.greenjab.nekomasfixed.util.ModRecipeBookType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class RecipeRegistry {


    public static final RecipeSerializer<KilnRecipe> KILNING_RECIPE_SERIALIZER =
            Registry.register(
                    Registries.RECIPE_SERIALIZER,
                    NekomasFixed.id("kilning"),
                    new AbstractCookingRecipe.Serializer<>(KilnRecipe::new, 100)
            );
    public static final RecipeSerializer<ZombieNautilusRecipe> ZOMBIE_NAUTILUS_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER,
            NekomasFixed.id("zombie_nautilus"),
            new RecipeSerializer<ZombieNautilusRecipe>() {

                private final MapCodec<ZombieNautilusRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                                Codec.STRING.optionalFieldOf("group", "").forGetter(ZombieNautilusRecipe::getGroup),

                                CraftingRecipeCategory.CODEC.fieldOf("category").orElse(CraftingRecipeCategory.MISC).forGetter(ZombieNautilusRecipe::getCategory),

                                RawShapedRecipe.CODEC.forGetter(ZombieNautilusRecipe::getRaw),

                                ItemStack.CODEC.fieldOf("result").forGetter(ZombieNautilusRecipe::getResultStack),
                                Codec.BOOL.optionalFieldOf("show_notification", true).forGetter(ZombieNautilusRecipe::showNotification)
                        ).apply(instance, ZombieNautilusRecipe::new)
                );

                private final PacketCodec<RegistryByteBuf, ZombieNautilusRecipe> PACKET_CODEC = PacketCodec.tuple(
                        PacketCodecs.STRING, ZombieNautilusRecipe::getGroup,
                        CraftingRecipeCategory.PACKET_CODEC, ZombieNautilusRecipe::getCategory,
                        RawShapedRecipe.PACKET_CODEC, ZombieNautilusRecipe::getRaw,
                        ItemStack.PACKET_CODEC, ZombieNautilusRecipe::getResultStack,
                        PacketCodecs.BOOLEAN, ZombieNautilusRecipe::showNotification,
                        ZombieNautilusRecipe::new
                );

                @Override
                public MapCodec<ZombieNautilusRecipe> codec() {
                    return CODEC;
                }

                @Override
                public PacketCodec<RegistryByteBuf, ZombieNautilusRecipe> packetCodec() {
                    return PACKET_CODEC;
                }
            }
    );

    public static final RecipeSerializer<CoralNautilusRecipe> CORAL_NAUTILUS_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER,
            NekomasFixed.id("coral_nautilus"),
            new RecipeSerializer<CoralNautilusRecipe>() {

                private final MapCodec<CoralNautilusRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                                Codec.STRING.optionalFieldOf("group", "").forGetter(CoralNautilusRecipe::getGroup),

                                CraftingRecipeCategory.CODEC.fieldOf("category").orElse(CraftingRecipeCategory.MISC).forGetter(CoralNautilusRecipe::getCategory),

                                RawShapedRecipe.CODEC.forGetter(CoralNautilusRecipe::getRaw),

                                ItemStack.CODEC.fieldOf("result").forGetter(CoralNautilusRecipe::getResultStack),
                                Codec.BOOL.optionalFieldOf("show_notification", true).forGetter(CoralNautilusRecipe::showNotification)
                        ).apply(instance, CoralNautilusRecipe::new)
                );

                private final PacketCodec<RegistryByteBuf, CoralNautilusRecipe> PACKET_CODEC = PacketCodec.tuple(
                        PacketCodecs.STRING, CoralNautilusRecipe::getGroup,
                        CraftingRecipeCategory.PACKET_CODEC, CoralNautilusRecipe::getCategory,
                        RawShapedRecipe.PACKET_CODEC, CoralNautilusRecipe::getRaw,
                        ItemStack.PACKET_CODEC, CoralNautilusRecipe::getResultStack,
                        PacketCodecs.BOOLEAN, CoralNautilusRecipe::showNotification,
                        CoralNautilusRecipe::new
                );

                @Override
                public MapCodec<CoralNautilusRecipe> codec() {
                    return CODEC;
                }

                @Override
                public PacketCodec<RegistryByteBuf, CoralNautilusRecipe> packetCodec() {
                    return PACKET_CODEC;
                }
            }
    );

    public static void registerRecipes() {
        System.out.println("Registering Mod Recipes");
    }


    public static final RegistryKey<RecipePropertySet> KILN_INPUT = registerRecipePropertySet("kiln_input");
    private static RegistryKey<RecipePropertySet> registerRecipePropertySet(String id) {
        return RegistryKey.of(RecipePropertySet.REGISTRY, NekomasFixed.id(id));
    }

    public static final RecipeType<KilnRecipe> KILN = registerRecipeType("kiln");

    static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String id) {
        return Registry.register(
                Registries.RECIPE_TYPE,
                NekomasFixed.id(id),
                new RecipeType<>() {
                    @Override
                    public String toString() {
                        return "nekomasfixed:" + id;
                    }
                }
        );
    }

    public static RecipeBookCategory KILNING_BLOCK = Registry.register(
            Registries.RECIPE_BOOK_CATEGORY,
            NekomasFixed.id("kilning_block"),
            new RecipeBookCategory()
    );
    public static RecipeBookCategory KILNING_MISC = Registry.register(
            Registries.RECIPE_BOOK_CATEGORY,
            NekomasFixed.id("kilning_misc"),
            new RecipeBookCategory()
    );
   public static final ModRecipeBookType KILNING = new ModRecipeBookType(RecipeRegistry.KILNING_BLOCK, RecipeRegistry.KILNING_MISC);

}