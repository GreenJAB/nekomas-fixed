package net.greenjab.nekomasfixed.datagen;

import com.terraformersmc.modmenu.util.mod.Mod;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.NonNull;

import java.util.Objects;
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
        BlockDyeMap.TERRACOTTA.values().forEach(b->tag(ModTags.CAN_BECOME_POT_FACE).add(b.properties().blockId()));

        BlockDyeMap.CONCRETE.values().forEach(b->tag(ModTags.CONCRETES).add(b.properties().blockId()));
        BlockDyeMap.CONCRETE_POWDER.values().forEach(b->tag(ModTags.CONCRETE_POWDERS).add(b.properties().blockId()));

        BlockDyeMap.SPOTTED_WOOL.values().forEach(b->tag(ModTags.SPOTTED_WOOLS).add(b.properties().blockId()));
        BlockDyeMap.SPOTTED_CARPET.values().forEach(b->tag(ModTags.SPOTTED_CARPETS).add(b.properties().blockId()));

        BlockDyeMap.CONCRETE_SLABS.values().forEach(b -> {
            tag(BlockTags.CONCRETE_SLABS).add(b.properties().blockId());
            tag(BlockTags.SLABS).add(b.properties().blockId());
        });
        BlockDyeMap.CONCRETE_STAIRS.values().forEach(b -> {
            tag(BlockTags.CONCRETE_STAIRS).add(b.properties().blockId());
            tag(BlockTags.STAIRS).add(b.properties().blockId());
        });

        BlockDyeMap.WOOL_SLABS.values().forEach(b -> {
            tag(BlockTags.WOOL_SLABS).add(b.properties().blockId());
            tag(BlockTags.SLABS).add(b.properties().blockId());
        });
        BlockDyeMap.WOOL_STAIRS.values().forEach(b -> {
            tag(BlockTags.WOOL_STAIRS).add(b.properties().blockId());
            tag(BlockTags.STAIRS).add(b.properties().blockId());
        });

        BlockDyeMap.SPOTTED_WOOL_SLABS.values().forEach(b -> {
            tag(ModTags.SPOTTED_WOOL_SLABS).add(b.properties().blockId());
            tag(BlockTags.SLABS).add(b.properties().blockId());
        });
        BlockDyeMap.SPOTTED_WOOL_STAIRS.values().forEach(b -> {
            tag(ModTags.SPOTTED_WOOL_STAIRS).add(b.properties().blockId());
            tag(BlockTags.STAIRS).add(b.properties().blockId());
        });

        BlockDyeMap.FROGLIGHT.values().forEach(b->tag(ModTags.FROGLIGHTS).add(b.properties().blockId()));

        BlockDyeMap.FROGLIGHT.values().forEach(b->tag(ModTags.FROGLIGHTS).add(b.properties().blockId()));

        tag(BlockTags.CAMPFIRES)
                .add(BlockRegistry.SULFUR_CAMPFIRE.properties().blockId());

        tag(ModTags.SULFUR_BLOCKS)
                .add(Blocks.SULFUR.properties().blockId())
                .add(Blocks.SULFUR_BRICKS.properties().blockId())
                .add(Blocks.CHISELED_SULFUR.properties().blockId())
                .add(Blocks.POTENT_SULFUR.properties().blockId())
                .add(Blocks.POLISHED_SULFUR.properties().blockId());

        tag(BlockTags.BANNERS)
                .add(BlockRegistry.AMBER_BANNER.properties().blockId())
                .add(BlockRegistry.AQUA_BANNER.properties().blockId())
                .add(BlockRegistry.MAROON_BANNER.properties().blockId())
                .add(BlockRegistry.INDIGO_BANNER.properties().blockId())
                .add(BlockRegistry.AMBER_WALL_BANNER.properties().blockId())
                .add(BlockRegistry.AQUA_WALL_BANNER.properties().blockId())
                .add(BlockRegistry.MAROON_WALL_BANNER.properties().blockId())
                .add(BlockRegistry.INDIGO_WALL_BANNER.properties().blockId())
        ;



        tag(ModTags.CAN_BECOME_POT_FACE)
                .addTag(ModTags.GLAZED_TERRACOTTAS)
                .add(Blocks.BRICKS.properties().blockId());


        tag(BlockTags.LANTERNS)
                .add(BlockRegistry.SULFUR_LANTERN.properties().blockId());

        tag(BlockTags.CHAINS)
                .add(BlockRegistry.GOLD_CHAIN.properties().blockId());

        tag(ModTags.TORCHES)
                .add(Blocks.TORCH.properties().blockId())
                .add(Blocks.REDSTONE_TORCH.properties().blockId())
                .add(Blocks.REDSTONE_WALL_TORCH.properties().blockId())
                .add(Blocks.SOUL_TORCH.properties().blockId())
                .add(Blocks.SOUL_WALL_TORCH.properties().blockId())
                .add(Blocks.COPPER_TORCH.properties().blockId())
                .add(Blocks.COPPER_WALL_TORCH.properties().blockId())
                .add(BlockRegistry.GLOW_TORCH.properties().blockId())
                .add(BlockRegistry.GLOW_WALL_TORCH.properties().blockId())
                .add(BlockRegistry.SULFUR_TORCH.properties().blockId())
                .add(BlockRegistry.WALL_SULFUR_TORCH.properties().blockId());

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
                .addTag(ModTags.SPOTTED_WOOL_SLABS)
                .addTag(ModTags.SPOTTED_WOOL_STAIRS)
                .addOptionalTag(BlockTags.CANDLES)
                .addOptionalTag(BlockTags.CONCRETE)
                .addOptionalTag(BlockTags.CONCRETE_POWDERS)
                .addTag(BlockTags.CONCRETE_SLABS)
                .addTag(BlockTags.CONCRETE_STAIRS)
                .addTag(BlockTags.WOOL_SLABS)
                .addTag(BlockTags.WOOL_STAIRS)
                .addTag(ModTags.FROGLIGHTS)
                .addOptionalTag(BlockTags.SHULKER_BOXES)
                .addOptionalTag(BlockTags.BEDS)
                .add(Blocks.GLASS.properties().blockId())
                .add(Blocks.GLASS_PANE.properties().blockId())
                .add(Blocks.BRICKS.properties().blockId())
                .add(Blocks.BRICK_SLAB.properties().blockId())
                .add(Blocks.BRICK_STAIRS.properties().blockId())
                .add(Blocks.BRICK_WALL.properties().blockId());
        tag(ConventionalBlockTags.GLASS_BLOCKS)
                .add(BlockRegistry.AMBER_STAINED_GLASS.properties().blockId())
                .add(BlockRegistry.INDIGO_STAINED_GLASS.properties().blockId())
                .add(BlockRegistry.AQUA_STAINED_GLASS.properties().blockId())
                .add(BlockRegistry.MAROON_STAINED_GLASS.properties().blockId());
        tag(ConventionalBlockTags.GLASS_PANES)
                .add(BlockRegistry.AMBER_STAINED_GLASS_PANE.properties().blockId())
                .add(BlockRegistry.INDIGO_STAINED_GLASS_PANE.properties().blockId())
                .add(BlockRegistry.AQUA_STAINED_GLASS_PANE.properties().blockId())
                .add(BlockRegistry.MAROON_STAINED_GLASS_PANE.properties().blockId());
    }
}

