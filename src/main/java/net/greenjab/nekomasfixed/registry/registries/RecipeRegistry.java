package net.greenjab.nekomasfixed.registry.registries;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.registry.recipe.CoralNautilusRecipe;
import net.greenjab.nekomasfixed.registry.recipe.ZombieNautilusRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.RawShapedRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RecipeRegistry {
    public static final RecipeSerializer<ZombieNautilusRecipe> ZOMBIE_NAUTILUS_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER,
            Identifier.of("nekomasfixed", "zombie_nautilus"),
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
            Identifier.of("nekomasfixed", "coral_nautilus"),
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
}