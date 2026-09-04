package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
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
    }

    private ResourceKey<Item> getKey(Item item){
        return item.asItem().builtInRegistryHolder().key();
    }
}