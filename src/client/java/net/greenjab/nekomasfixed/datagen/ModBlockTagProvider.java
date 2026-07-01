package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }


    @Override
    protected void configure(RegistryWrapper.@NonNull WrapperLookup wrapperLookup) {
        valueLookupBuilder(ModTags.DYED_BRICKS)
                .add(BlockDyeMap.BRICKS.values());
        valueLookupBuilder(ModTags.DYED_BRICK_SLABS)
                .add(BlockDyeMap.BRICK_SLAB.values());
        valueLookupBuilder(BlockTags.SLABS)
                .add(BlockDyeMap.BRICK_SLAB.values());
        valueLookupBuilder(ModTags.DYED_BRICK_STAIRS)
                .add(BlockDyeMap.BRICK_STAIRS.values());
        valueLookupBuilder(BlockTags.STAIRS)
                .add(BlockDyeMap.BRICK_STAIRS.values());
        valueLookupBuilder(ModTags.DYED_BRICK_WALLS)
                .add(BlockDyeMap.BRICK_WALL.values());
        valueLookupBuilder(BlockTags.WALLS)
                .add(BlockDyeMap.BRICK_WALL.values());

        valueLookupBuilder(ModTags.STAINED_GLASSES)
                .add(BlockDyeMap.STAINED_GLASS.values());
        valueLookupBuilder(ModTags.STAINED_GLASS_PANES)
                .add(BlockDyeMap.STAINED_GLASS_PANE.values());

        valueLookupBuilder(ModTags.GLAZED_TERRACOTTAS)
                .add(BlockDyeMap.GLAZED_TERRACOTTA.values());

        valueLookupBuilder(ModTags.CONCRETES)
                .add(BlockDyeMap.CONCRETE.values());
        valueLookupBuilder(ModTags.CONCRETE_POWDERS)
                .add(BlockDyeMap.CONCRETE_POWDER.values());

        valueLookupBuilder(ModTags.SPOTTED_WOOLS)
                .add(BlockDyeMap.SPOTTED_WOOL.values());
        valueLookupBuilder(ModTags.SPOTTED_CARPETS)
                .add(BlockDyeMap.SPOTTED_CARPET.values());

        valueLookupBuilder(ModTags.FROGLIGHTS)
                .add(BlockDyeMap.FROGLIGHT.values());


        valueLookupBuilder(ModTags.CAN_BE_DYED_WITH_BRUSH)
                .add(BlockDyeMap.BRICKS.values())
                .add(BlockDyeMap.BRICK_SLAB.values())
                .add(BlockDyeMap.BRICK_STAIRS.values())
                .add(BlockDyeMap.BRICK_WALL.values())
                .add(BlockDyeMap.STAINED_GLASS.values())
                .add(BlockDyeMap.STAINED_GLASS_PANE.values())
                .add(BlockDyeMap.TERRACOTTA.values())
                .add(BlockDyeMap.GLAZED_TERRACOTTA.values())
                .add(BlockDyeMap.WOOL.values())
                .add(BlockDyeMap.SPOTTED_WOOL.values())
                .add(BlockDyeMap.CANDLE.values())
                .add(BlockDyeMap.CARPET.values())
                .add(BlockDyeMap.SPOTTED_CARPET.values())
                .add(BlockDyeMap.CONCRETE.values())
                .add(BlockDyeMap.CONCRETE_POWDER.values())
                .add(BlockDyeMap.FROGLIGHT.values())
                .add(BlockDyeMap.SHULKER_BOX.values())
                .add(BlockDyeMap.BED.values())
                .add(Blocks.GLASS)
                .add(Blocks.CANDLE)
                .add(Blocks.GLASS_PANE)
                .add(Blocks.BRICKS)
                .add(Blocks.BRICK_SLAB)
                .add(Blocks.BRICK_STAIRS)
                .add(Blocks.BRICK_WALL)
                .add(Blocks.SHULKER_BOX);
    }

}

