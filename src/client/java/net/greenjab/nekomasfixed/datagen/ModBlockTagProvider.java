package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
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

        BlockDyeMap.BRICKS.values().forEach(b->tag(ModTags.DYED_BRICKS).add(getKey(b)));
        BlockDyeMap.BRICK_SLAB.values().forEach(b->tag(ModTags.DYED_BRICK_SLABS).add(getKey(b)));
        BlockDyeMap.BRICK_STAIRS.values().forEach(b->tag(ModTags.DYED_BRICK_STAIRS).add(getKey(b)));
        BlockDyeMap.BRICK_WALL.values().forEach(b->tag(ModTags.DYED_BRICK_WALLS).add(getKey(b)));

        BlockDyeMap.STAINED_GLASS.values().forEach(b->tag(ModTags.STAINED_GLASSES).add(getKey(b)));
        BlockDyeMap.STAINED_GLASS_PANE.values().forEach(b->tag(ModTags.STAINED_GLASS_PANES).add(getKey(b)));

        BlockDyeMap.GLAZED_TERRACOTTA.values().forEach(b->tag(ModTags.GLAZED_TERRACOTTAS).add(getKey(b)));

        BlockDyeMap.CONCRETE.values().forEach(b->tag(ModTags.CONCRETES).add(getKey(b)));
        BlockDyeMap.CONCRETE_POWDER.values().forEach(b->tag(ModTags.CONCRETE_POWDERS).add(getKey(b)));

        BlockDyeMap.SPOTTED_WOOL.values().forEach(b->tag(ModTags.SPOTTED_WOOLS).add(getKey(b)));
        BlockDyeMap.SPOTTED_CARPET.values().forEach(b->tag(ModTags.SPOTTED_CARPETS).add(getKey(b)));

        BlockDyeMap.FROGLIGHT.values().forEach(b->tag(ModTags.FROGLIGHTS).add(getKey(b)));

        BlockDyeMap.FROGLIGHT.values().forEach(b->tag(ModTags.FROGLIGHTS).add(getKey(b)));


        tag(ModTags.CAN_BE_DYED_WITH_BRUSH)
                .addTag(ModTags.DYED_BRICKS)
                .addTag(ModTags.DYED_BRICK_SLABS)
                .addTag(ModTags.DYED_BRICK_STAIRS)
                .addTag(ModTags.DYED_BRICK_WALLS)
                .addTag(ModTags.STAINED_GLASSES)
                .addTag(ModTags.STAINED_GLASS_PANES)
                .addOptionalTag(BlockTags.TERRACOTTA)
                .addTag(ModTags.GLAZED_TERRACOTTAS)
                .addOptionalTag(BlockTags.WOOL)
                .addOptionalTag(BlockTags.WOOL_CARPETS)
                .addOptionalTag(BlockTags.CANDLES)
                .addOptionalTag(BlockTags.CONCRETE)
                .addOptionalTag(BlockTags.CONCRETE_POWDERS)
                .addTag(ModTags.FROGLIGHTS)
                .addOptionalTag(BlockTags.SHULKER_BOXES)
                .addOptionalTag(BlockTags.BEDS)
                .add(getKey(Blocks.GLASS))
                .add(getKey(Blocks.GLASS_PANE))
                .add(getKey(Blocks.BRICKS))
                .add(getKey(Blocks.BRICK_SLAB))
                .add(getKey(Blocks.BRICK_STAIRS))
                .add(getKey(Blocks.BRICK_WALL));


        tag(ModTags.HOLLOW_LOGS)
                .add(getKey(BlockRegistry.HOLLOW_BAMBOO_BLOCK))
                .add(getKey(BlockRegistry.HOLLOW_ACACIA_LOG))
                .add(getKey(BlockRegistry.HOLLOW_BIRCH_LOG))
                .add(getKey(BlockRegistry.HOLLOW_CHERRY_LOG))
                .add(getKey(BlockRegistry.HOLLOW_WARPED_STEM))
                .add(getKey(BlockRegistry.HOLLOW_CRIMSON_STEM))
                .add(getKey(BlockRegistry.HOLLOW_DARK_OAK_LOG))
                .add(getKey(BlockRegistry.HOLLOW_JUNGLE_LOG))
                .add(getKey(BlockRegistry.HOLLOW_MANGROVE_LOG))
                .add(getKey(BlockRegistry.HOLLOW_PALE_OAK_LOG))
                .add(getKey(BlockRegistry.HOLLOW_SPRUCE_LOG))
                .add(getKey(BlockRegistry.HOLLOW_OAK_LOG))
                .add(getKey(BlockRegistry.HOLLOW_BAOBAB_LOG));

        tag(ModTags.STRIPPED_HOLLOW_LOGS)
                .add(getKey(BlockRegistry.HOLLOW_STRIPPED_BAMBOO_BLOCK))
                .add(getKey(BlockRegistry.HOLLOW_STRIPPED_ACACIA_LOG))
                .add(getKey(BlockRegistry.HOLLOW_STRIPPED_BIRCH_LOG))
                .add(getKey(BlockRegistry.HOLLOW_STRIPPED_CHERRY_LOG))
                .add(getKey(BlockRegistry.HOLLOW_STRIPPED_WARPED_STEM))
                .add(getKey(BlockRegistry.HOLLOW_STRIPPED_CRIMSON_STEM))
                .add(getKey(BlockRegistry.HOLLOW_STRIPPED_DARK_OAK_LOG))
                .add(getKey(BlockRegistry.HOLLOW_STRIPPED_JUNGLE_LOG))
                .add(getKey(BlockRegistry.HOLLOW_STRIPPED_MANGROVE_LOG))
                .add(getKey(BlockRegistry.HOLLOW_STRIPPED_PALE_OAK_LOG))
                .add(getKey(BlockRegistry.HOLLOW_STRIPPED_SPRUCE_LOG))
                .add(getKey(BlockRegistry.HOLLOW_STRIPPED_OAK_LOG))
                .add(getKey(BlockRegistry.HOLLOW_STRIPPED_BAOBAB_LOG));

        tag(BlockTags.LOGS)
                .addTag(ModTags.HOLLOW_LOGS)
                .addTag(ModTags.STRIPPED_HOLLOW_LOGS);


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

    private static ResourceKey<Block> getKey(Block block){
        return block.properties().blockId();
    }

}

