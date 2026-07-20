package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
    public ModBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }


    @Override
    protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
        //TODO datagen tags

        //tag(ModTags.DYED_BRICKS).add(BlockRegistry.WHITE_BRICKS.properties().blockId());
        //tag(ModTags.DYED_BRICKS).add(BlockRegistry.WHITE_BRICKS.properties().blockId());
        //BlockDyeMap.BRICKS.values().forEach(b->tag(ModTags.DYED_BRICKS).add(b.properties().blockId()));
        //BlockDyeMap.BRICKS.values().forEach(b->tag(ModTags.DYED_BRICKS).add(b.properties().blockId()));
        /*valueLookupBuilder(ModTags.DYED_BRICKS)
                .add((Block) BlockDyeMap.BRICKS.values());
        valueLookupBuilder(ModTags.DYED_BRICK_SLABS)
                .add((Block) BlockDyeMap.BRICK_SLAB.values());
        valueLookupBuilder(BlockTags.SLABS)
                .add((Block) BlockDyeMap.BRICK_SLAB.values());
        valueLookupBuilder(ModTags.DYED_BRICK_STAIRS)
                .add((Block) BlockDyeMap.BRICK_STAIRS.values());
        valueLookupBuilder(BlockTags.STAIRS)
                .add((Block) BlockDyeMap.BRICK_STAIRS.values());
        valueLookupBuilder(ModTags.DYED_BRICK_WALLS)
                .add((Block) BlockDyeMap.BRICK_WALL.values());
        valueLookupBuilder(BlockTags.WALLS)
                .add((Block) BlockDyeMap.BRICK_WALL.values());

        valueLookupBuilder(ModTags.STAINED_GLASSES)
                .add((Block) BlockDyeMap.STAINED_GLASS.values());
        valueLookupBuilder(ModTags.STAINED_GLASS_PANES)
                .add((Block) BlockDyeMap.STAINED_GLASS_PANE.values());

        valueLookupBuilder(ModTags.GLAZED_TERRACOTTAS)
                .add((Block) BlockDyeMap.GLAZED_TERRACOTTA.values());

        valueLookupBuilder(ModTags.CONCRETES)
                .add((Block) BlockDyeMap.CONCRETE.values());
        valueLookupBuilder(ModTags.CONCRETE_POWDERS)
                .add((Block) BlockDyeMap.CONCRETE_POWDER.values());

        valueLookupBuilder(ModTags.SPOTTED_WOOLS)
                .add((Block) BlockDyeMap.SPOTTED_WOOL.values());
        valueLookupBuilder(ModTags.SPOTTED_CARPETS)
                .add((Block) BlockDyeMap.SPOTTED_CARPET.values());

        valueLookupBuilder(ModTags.FROGLIGHTS)
                .add((Block) BlockDyeMap.FROGLIGHT.values());


        valueLookupBuilder(ModTags.CAN_BE_DYED_WITH_BRUSH)
                .add((Block) BlockDyeMap.BRICKS.values())
                .add((Block) BlockDyeMap.BRICK_SLAB.values())
                .add((Block) BlockDyeMap.BRICK_STAIRS.values())
                .add((Block) BlockDyeMap.BRICK_WALL.values())
                .add((Block) BlockDyeMap.STAINED_GLASS.values())
                .add((Block) BlockDyeMap.STAINED_GLASS_PANE.values())
                .add((Block) BlockDyeMap.TERRACOTTA.values())
                .add((Block) BlockDyeMap.GLAZED_TERRACOTTA.values())
                .add((Block) BlockDyeMap.WOOL.values())
                .add((Block) BlockDyeMap.SPOTTED_WOOL.values())
                .add((Block) BlockDyeMap.CANDLE.values())
                .add((Block) BlockDyeMap.CARPET.values())
                .add((Block) BlockDyeMap.SPOTTED_CARPET.values())
                .add((Block) BlockDyeMap.CONCRETE.values())
                .add((Block) BlockDyeMap.CONCRETE_POWDER.values())
                .add((Block) BlockDyeMap.FROGLIGHT.values())
                .add((Block) BlockDyeMap.SHULKER_BOX.values())
                .add((Block) BlockDyeMap.BED.values())
                .add(Blocks.GLASS)
                .add(Blocks.CANDLE)
                .add(Blocks.GLASS_PANE)
                .add(Blocks.BRICKS)
                .add(Blocks.BRICK_SLAB)
                .add(Blocks.BRICK_STAIRS)
                .add(Blocks.BRICK_WALL)
                .add(Blocks.SHULKER_BOX);*/
    }

}

