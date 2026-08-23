package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
    public ModBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }


    @Override
    protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
        BlockDyeMap.BRICKS.values().forEach(b->tag(ModTags.DYED_BRICKS).add(b.properties().blockId()));
        BlockDyeMap.BRICK_SLAB.values().forEach(b->{
            tag(ModTags.DYED_BRICK_SLABS).add(b.properties().blockId());
            tag(BlockTags.SLABS).add(b.properties().blockId());
        });
        BlockDyeMap.BRICK_STAIRS.values().forEach(b->{
            tag(ModTags.DYED_BRICK_STAIRS).add(b.properties().blockId());
            tag(BlockTags.STAIRS).add(b.properties().blockId());
        });
        BlockDyeMap.BRICK_WALL.values().forEach(b->{
            tag(ModTags.DYED_BRICK_WALLS).add(b.properties().blockId());
            tag(BlockTags.WALLS).add(b.properties().blockId());
        });

        BlockDyeMap.STAINED_GLASS.values().forEach(b->tag(ModTags.STAINED_GLASSES).add(b.properties().blockId()));
        BlockDyeMap.STAINED_GLASS_PANE.values().forEach(b->tag(ModTags.STAINED_GLASS_PANES).add(b.properties().blockId()));

        BlockDyeMap.GLAZED_TERRACOTTA.values().forEach(b->tag(ModTags.GLAZED_TERRACOTTAS).add(b.properties().blockId()));

        BlockDyeMap.CONCRETE.values().forEach(b->tag(ModTags.CONCRETES).add(b.properties().blockId()));
        BlockDyeMap.CONCRETE_POWDER.values().forEach(b->tag(ModTags.CONCRETE_POWDERS).add(b.properties().blockId()));

        BlockDyeMap.SPOTTED_WOOL.values().forEach(b->tag(ModTags.SPOTTED_WOOLS).add(b.properties().blockId()));
        BlockDyeMap.SPOTTED_CARPET.values().forEach(b->tag(ModTags.SPOTTED_CARPETS).add(b.properties().blockId()));

        BlockDyeMap.FROGLIGHT.values().forEach(b->tag(ModTags.FROGLIGHTS).add(b.properties().blockId()));

        BlockDyeMap.FROGLIGHT.values().forEach(b->tag(ModTags.FROGLIGHTS).add(b.properties().blockId()));

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
                .add(Blocks.GLASS.properties().blockId())
                .add(Blocks.GLASS_PANE.properties().blockId())
                .add(Blocks.BRICKS.properties().blockId())
                .add(Blocks.BRICK_SLAB.properties().blockId())
                .add(Blocks.BRICK_STAIRS.properties().blockId())
                .add(Blocks.BRICK_WALL.properties().blockId());
    }
}

