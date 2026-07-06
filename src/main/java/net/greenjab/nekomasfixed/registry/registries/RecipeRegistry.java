package net.greenjab.nekomasfixed.registry.registries;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.recipe.CoralNautilusRecipe;
import net.greenjab.nekomasfixed.registry.recipe.KilnRecipe;
import net.greenjab.nekomasfixed.registry.recipe.ZombieNautilusRecipe;
import net.greenjab.nekomasfixed.util.ModRecipeBookType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import org.jspecify.annotations.NonNull;

public class RecipeRegistry {


    public static final RecipeSerializer<KilnRecipe> KILNING_RECIPE_SERIALIZER =
            Registry.register(
                    BuiltInRegistries.RECIPE_SERIALIZER,
                    NekomasFixed.id("kilning"),
                    new AbstractCookingRecipe.Serializer<>(KilnRecipe::new, 100)
            );
    public static final RecipeSerializer<ZombieNautilusRecipe> ZOMBIE_NAUTILUS_SERIALIZER = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            NekomasFixed.id("zombie_nautilus"),
            new RecipeSerializer<ZombieNautilusRecipe>() {

                private final MapCodec<ZombieNautilusRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                                Codec.STRING.optionalFieldOf("group", "").forGetter(ZombieNautilusRecipe::group),

                                CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(ZombieNautilusRecipe::category),

                                ShapedRecipePattern.MAP_CODEC.forGetter(ZombieNautilusRecipe::getRaw),

                                ItemStack.CODEC.fieldOf("result").forGetter(ZombieNautilusRecipe::getResultStack),
                                Codec.BOOL.optionalFieldOf("show_notification", true).forGetter(ZombieNautilusRecipe::showNotification)
                        ).apply(instance, ZombieNautilusRecipe::new)
                );

                private final StreamCodec<RegistryFriendlyByteBuf, ZombieNautilusRecipe> PACKET_CODEC = StreamCodec.composite(
                        ByteBufCodecs.STRING_UTF8, ZombieNautilusRecipe::group,
                        CraftingBookCategory.STREAM_CODEC, ZombieNautilusRecipe::category,
                        ShapedRecipePattern.STREAM_CODEC, ZombieNautilusRecipe::getRaw,
                        ItemStack.STREAM_CODEC, ZombieNautilusRecipe::getResultStack,
                        ByteBufCodecs.BOOL, ZombieNautilusRecipe::showNotification,
                        ZombieNautilusRecipe::new
                );

                @Override
                public @NonNull MapCodec<ZombieNautilusRecipe> codec() {
                    return CODEC;
                }

                @Override
                public @NonNull StreamCodec<RegistryFriendlyByteBuf, ZombieNautilusRecipe> streamCodec() {
                    return PACKET_CODEC;
                }
            }
    );

    public static final RecipeSerializer<CoralNautilusRecipe> CORAL_NAUTILUS_SERIALIZER = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            NekomasFixed.id("coral_nautilus"),
            new RecipeSerializer<CoralNautilusRecipe>() {

                private final MapCodec<CoralNautilusRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                                Codec.STRING.optionalFieldOf("group", "").forGetter(CoralNautilusRecipe::group),

                                CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(CoralNautilusRecipe::category),

                                ShapedRecipePattern.MAP_CODEC.forGetter(CoralNautilusRecipe::getRaw),

                                ItemStack.CODEC.fieldOf("result").forGetter(CoralNautilusRecipe::getResultStack),
                                Codec.BOOL.optionalFieldOf("show_notification", true).forGetter(CoralNautilusRecipe::showNotification)
                        ).apply(instance, CoralNautilusRecipe::new)
                );

                private final StreamCodec<RegistryFriendlyByteBuf, CoralNautilusRecipe> PACKET_CODEC = StreamCodec.composite(
                        ByteBufCodecs.STRING_UTF8, CoralNautilusRecipe::group,
                        CraftingBookCategory.STREAM_CODEC, CoralNautilusRecipe::category,
                        ShapedRecipePattern.STREAM_CODEC, CoralNautilusRecipe::getRaw,
                        ItemStack.STREAM_CODEC, CoralNautilusRecipe::getResultStack,
                        ByteBufCodecs.BOOL, CoralNautilusRecipe::showNotification,
                        CoralNautilusRecipe::new
                );

                @Override
                public @NonNull MapCodec<CoralNautilusRecipe> codec() {
                    return CODEC;
                }

                @Override
                public @NonNull StreamCodec<RegistryFriendlyByteBuf, CoralNautilusRecipe> streamCodec() {
                    return PACKET_CODEC;
                }
            }
    );

    public static void registerRecipes() {
        System.out.println("Registering Mod Recipes");
    }


    public static final ResourceKey<RecipePropertySet> KILN_INPUT = registerRecipePropertySet("kiln_input");
    private static ResourceKey<RecipePropertySet> registerRecipePropertySet(String id) {
        return ResourceKey.create(RecipePropertySet.TYPE_KEY, NekomasFixed.id(id));
    }

    public static final RecipeType<KilnRecipe> KILN = registerRecipeType("kiln");

    static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String id) {
        return Registry.register(
                BuiltInRegistries.RECIPE_TYPE,
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
            BuiltInRegistries.RECIPE_BOOK_CATEGORY,
            NekomasFixed.id("kilning_block"),
            new RecipeBookCategory()
    );
    public static RecipeBookCategory KILNING_MISC = Registry.register(
            BuiltInRegistries.RECIPE_BOOK_CATEGORY,
            NekomasFixed.id("kilning_misc"),
            new RecipeBookCategory()
    );
   public static final ModRecipeBookType KILNING = new ModRecipeBookType(RecipeRegistry.KILNING_BLOCK, RecipeRegistry.KILNING_MISC);
}