package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.@NonNull WrapperLookup wrapperLookup) {
        valueLookupBuilder(ModTags.GLOWING_WOOL_ITEMS).add(BlockDyeMap.GLOWING_WOOL.values().stream().map(Block::asItem));
        valueLookupBuilder(ModTags.GLOWING_WOOL_CARPET_ITEMS).add(BlockDyeMap.GLOWING_CARPET.values().stream().map(Block::asItem));
        valueLookupBuilder(ModTags.GLOWING_BED_ITEMS).add(BlockDyeMap.GLOWING_BED.values().stream().map(Block::asItem));
        valueLookupBuilder(ModTags.GLOWING_BANNER_ITEMS).add(BlockDyeMap.GLOWING_BANNER.values().stream().map(Block::asItem));
    }
}