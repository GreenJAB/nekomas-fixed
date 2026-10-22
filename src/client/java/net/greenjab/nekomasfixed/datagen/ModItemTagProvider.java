package net.greenjab.nekomasfixed.datagen;

import com.terraformersmc.modmenu.util.mod.Mod;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
    public ModItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
        tag(ModTags.CAN_BE_ENGRAVED)
                .add(getKey(Items.IRON_INGOT))
                .add(getKey(Items.GOLD_INGOT))
                .add(getKey(Items.DIAMOND))
                .add(getKey(Items.RESIN_BRICK))
                .add(getKey(Items.REDSTONE))
                .add(getKey(Items.LAPIS_LAZULI))
                .add(getKey(Items.NETHERITE_INGOT))
                .add(getKey(Items.QUARTZ))
                .add(getKey(Items.COPPER_INGOT))
                .add(getKey(Items.AMETHYST_SHARD))
                .add(getKey(Items.EMERALD))
        ;

        tag(ItemTags.LOOM_DYES)
                .add(getKey(ItemRegistry.AMBER_DYE))
                .add(getKey(ItemRegistry.AQUA_DYE))
                .add(getKey(ItemRegistry.MAROON_DYE))
                .add(getKey(ItemRegistry.INDIGO_DYE));

        tag(ItemTags.DYES)
                .add(getKey(ItemRegistry.AMBER_DYE))
                .add(getKey(ItemRegistry.AQUA_DYE))
                .add(getKey(ItemRegistry.MAROON_DYE))
                .add(getKey(ItemRegistry.INDIGO_DYE));

        tag(ModTags.POTIONS)
                .add(getKey(Items.POTION));
        tag(ModTags.FLOWER_CROWNS)
                .add(ItemRegistry.TORCHFLOWER_FLOWER_CROWN.builtInRegistryHolder().key())
                .add(ItemRegistry.BLUE_ORCHID_FLOWER_CROWN.builtInRegistryHolder().key())
                .add(ItemRegistry.WITHER_ROSE_FLOWER_CROWN.builtInRegistryHolder().key())
                .add(ItemRegistry.CORNFLOWER_FLOWER_CROWN.builtInRegistryHolder().key())
                .add(ItemRegistry.LILY_OF_THE_VALLEY_FLOWER_CROWN.builtInRegistryHolder().key())
                .add(ItemRegistry.ORANGE_TULIP_FLOWER_CROWN.builtInRegistryHolder().key())
                .add(ItemRegistry.PINK_TULIP_FLOWER_CROWN.builtInRegistryHolder().key())
                .add(ItemRegistry.ALLIUM_FLOWER_CROWN.builtInRegistryHolder().key())
                .add(ItemRegistry.RED_TULIP_FLOWER_CROWN.builtInRegistryHolder().key())
                .add(ItemRegistry.POPPY_FLOWER_CROWN.builtInRegistryHolder().key())
                .add(ItemRegistry.AZURE_BLUET_FLOWER_CROWN.builtInRegistryHolder().key())
                .add(ItemRegistry.WHITE_TULIP_FLOWER_CROWN.builtInRegistryHolder().key())
                .add(ItemRegistry.OXEYE_DAISY_FLOWER_CROWN.builtInRegistryHolder().key())
                .add(ItemRegistry.DANDELION_FLOWER_CROWN.builtInRegistryHolder().key())
                .add(ItemRegistry.OPEN_EYEBLOSSOM_FLOWER_CROWN.builtInRegistryHolder().key())
                .add(ItemRegistry.CLOSED_EYEBLOSSOM_FLOWER_CROWN.builtInRegistryHolder().key());

        tag(ModTags.GOAT_HORN_HELMETS)
                .add(ItemRegistry.GOAT_HORN_IRON_HELMET.builtInRegistryHolder().key())
                .add(ItemRegistry.GOAT_HORN_GOLDEN_HELMET.builtInRegistryHolder().key())
                .add(ItemRegistry.GOAT_HORN_COPPER_HELMET.builtInRegistryHolder().key())
                .add(ItemRegistry.GOAT_HORN_DIAMOND_HELMET.builtInRegistryHolder().key())
                .add(ItemRegistry.GOAT_HORN_NETHERITE_HELMET.builtInRegistryHolder().key())
                .add(ItemRegistry.GOAT_HORN_TURTLE_HELMET.builtInRegistryHolder().key());

        tag(ItemTags.ARROWS).add(getKey(ItemRegistry.TIPPED_ARROW_CUSTOM));
    }

    private ResourceKey<Item> getKey(Item item){
        return item.asItem().builtInRegistryHolder().key();
    }
}