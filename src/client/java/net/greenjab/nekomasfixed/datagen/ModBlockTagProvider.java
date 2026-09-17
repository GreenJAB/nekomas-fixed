package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

/**
 * Generates the colour-suite block tags.
 * The coloured brick walls are also appended to the vanilla #minecraft:walls tag
 * (replace: false) so they connect to vanilla walls (1.21.1 WallBlock.connectsTo).
 */
public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        // Hollow-log family: all 12 hollow log/stem/block variants.
        Block[] hollowLogs = {
                BlockRegistry.HOLLOW_OAK_LOG, BlockRegistry.HOLLOW_SPRUCE_LOG,
                BlockRegistry.HOLLOW_BIRCH_LOG, BlockRegistry.HOLLOW_JUNGLE_LOG,
                BlockRegistry.HOLLOW_ACACIA_LOG, BlockRegistry.HOLLOW_DARK_OAK_LOG,
                BlockRegistry.HOLLOW_MANGROVE_LOG, BlockRegistry.HOLLOW_CHERRY_LOG,
                BlockRegistry.HOLLOW_BAMBOO_BLOCK, BlockRegistry.HOLLOW_CRIMSON_STEM,
                BlockRegistry.HOLLOW_WARPED_STEM, BlockRegistry.HOLLOW_BAOBAB_LOG
        };
        for (Block b : hollowLogs) {
            getOrCreateTagBuilder(ModTags.HOLLOW_LOGS).add(BuiltInRegistries.BLOCK.getKey(b));
        }
        // Stripped hollow-log family (axe-stripped variants, same 12 woods).
        Block[] strippedHollowLogs = {
                BlockRegistry.HOLLOW_STRIPPED_OAK_LOG, BlockRegistry.HOLLOW_STRIPPED_SPRUCE_LOG,
                BlockRegistry.HOLLOW_STRIPPED_BIRCH_LOG, BlockRegistry.HOLLOW_STRIPPED_JUNGLE_LOG,
                BlockRegistry.HOLLOW_STRIPPED_ACACIA_LOG, BlockRegistry.HOLLOW_STRIPPED_DARK_OAK_LOG,
                BlockRegistry.HOLLOW_STRIPPED_MANGROVE_LOG, BlockRegistry.HOLLOW_STRIPPED_CHERRY_LOG,
                BlockRegistry.HOLLOW_STRIPPED_BAMBOO_BLOCK, BlockRegistry.HOLLOW_STRIPPED_CRIMSON_STEM,
                BlockRegistry.HOLLOW_STRIPPED_WARPED_STEM, BlockRegistry.HOLLOW_STRIPPED_BAOBAB_LOG
        };
        for (Block b : strippedHollowLogs) {
            getOrCreateTagBuilder(ModTags.STRIPPED_HOLLOW_LOGS).add(BuiltInRegistries.BLOCK.getKey(b));
        }
        getOrCreateTagBuilder(ModTags.BAOBAB_LOGS)
                .add(BuiltInRegistries.BLOCK.getKey(BlockRegistry.BAOBAB_LOG))
                .add(BuiltInRegistries.BLOCK.getKey(BlockRegistry.BAOBAB_WOOD))
                .add(BuiltInRegistries.BLOCK.getKey(BlockRegistry.STRIPPED_BAOBAB_LOG))
                .add(BuiltInRegistries.BLOCK.getKey(BlockRegistry.STRIPPED_BAOBAB_WOOD));
        BlockDyeMap.BRICKS.values().forEach(block -> getOrCreateTagBuilder(ModTags.BRICKS).add(BuiltInRegistries.BLOCK.getKey(block)));
        BlockDyeMap.BRICK_SLAB.values().forEach(block -> getOrCreateTagBuilder(ModTags.BRICK_SLABS).add(BuiltInRegistries.BLOCK.getKey(block)));
        BlockDyeMap.BRICK_STAIRS.values().forEach(block -> getOrCreateTagBuilder(ModTags.BRICK_STAIRS).add(BuiltInRegistries.BLOCK.getKey(block)));
        BlockDyeMap.BRICK_WALL.values().forEach(block -> {
            getOrCreateTagBuilder(ModTags.BRICK_WALLS).add(BuiltInRegistries.BLOCK.getKey(block));
            getOrCreateTagBuilder(BlockTags.WALLS).add(BuiltInRegistries.BLOCK.getKey(block));  // vanilla connectivity override
        });
        BlockDyeMap.SPOTTED_WOOL.values().forEach(block -> getOrCreateTagBuilder(ModTags.SPOTTED_WOOL).add(BuiltInRegistries.BLOCK.getKey(block)));
        BlockDyeMap.SPOTTED_CARPET.values().forEach(block -> getOrCreateTagBuilder(ModTags.SPOTTED_CARPET).add(BuiltInRegistries.BLOCK.getKey(block)));
        BlockDyeMap.FROGLIGHT.values().forEach(block -> getOrCreateTagBuilder(ModTags.FROGLIGHTS).add(BuiltInRegistries.BLOCK.getKey(block)));
        // Mod glazed-terracotta tag (mirrors main's same-named tag).
        BlockDyeMap.GLAZED_TERRACOTTA.values().forEach(block -> getOrCreateTagBuilder(ModTags.GLAZED_TERRACOTTAS).add(BuiltInRegistries.BLOCK.getKey(block)));
        // Candles: vanilla #minecraft:candles tag override so ancient candles work with
        // cake/waxing interactions as vanilla candles do (replace: false semantics).
        BlockDyeMap.CANDLE.values().forEach(block -> getOrCreateTagBuilder(BlockTags.CANDLES).add(BuiltInRegistries.BLOCK.getKey(block)));
        // Beds: vanilla #minecraft:beds block tag override (replace: false semantics).
        BlockDyeMap.BED.values().forEach(block -> getOrCreateTagBuilder(BlockTags.BEDS).add(BuiltInRegistries.BLOCK.getKey(block)));
        // Terracotta / concrete powder: vanilla tag overrides (replace: false) so ancient
        // blocks join the vanilla families (concrete has no block tag; it's not tagged).
        BlockDyeMap.TERRACOTTA.values().forEach(block -> getOrCreateTagBuilder(BlockTags.TERRACOTTA).add(BuiltInRegistries.BLOCK.getKey(block)));
        BlockDyeMap.CONCRETE_POWDER.values().forEach(block -> getOrCreateTagBuilder(BlockTags.CONCRETE_POWDER).add(BuiltInRegistries.BLOCK.getKey(block)));
        // Shulker boxes: vanilla #minecraft:shulker_boxes block tag override (replace: false semantics).
        BlockDyeMap.SHULKER_BOX.values().forEach(block -> getOrCreateTagBuilder(BlockTags.SHULKER_BOXES).add(BuiltInRegistries.BLOCK.getKey(block)));
        // Wool + carpets: vanilla #minecraft:wool / #minecraft:wool_carpets overrides (replace:
        // false). Spotted wools join via the mod #nekomasfixed:spotted_wool tag reference.
        var woolTags = getOrCreateTagBuilder(BlockTags.WOOL);
        BlockDyeMap.WOOL.values().forEach(block -> woolTags.add(BuiltInRegistries.BLOCK.getKey(block)));
        woolTags.addTag(ModTags.SPOTTED_WOOL);
        var carpetTags = getOrCreateTagBuilder(BlockTags.WOOL_CARPETS);
        BlockDyeMap.CARPET.values().forEach(block -> carpetTags.add(BuiltInRegistries.BLOCK.getKey(block)));
        carpetTags.addTag(ModTags.SPOTTED_CARPET);
        // Baobab wood set: vanilla tag overrides (replace: false) so it joins vanilla
        // wood categories. Logs enter via the #nekomasfixed:baobab_logs tag reference.
        getOrCreateTagBuilder(BlockTags.PLANKS).add(BuiltInRegistries.BLOCK.getKey(BlockRegistry.BAOBAB_PLANKS));
        getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(BuiltInRegistries.BLOCK.getKey(BlockRegistry.BAOBAB_SIGN));
        getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(BuiltInRegistries.BLOCK.getKey(BlockRegistry.BAOBAB_WALL_SIGN));
        getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS).add(BuiltInRegistries.BLOCK.getKey(BlockRegistry.BAOBAB_HANGING_SIGN));
        getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS).add(BuiltInRegistries.BLOCK.getKey(BlockRegistry.BAOBAB_WALL_HANGING_SIGN));
        getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(BuiltInRegistries.BLOCK.getKey(BlockRegistry.BAOBAB_BUTTON));
        getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(BuiltInRegistries.BLOCK.getKey(BlockRegistry.BAOBAB_DOOR));
        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(BuiltInRegistries.BLOCK.getKey(BlockRegistry.BAOBAB_FENCE));
        getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(BuiltInRegistries.BLOCK.getKey(BlockRegistry.BAOBAB_PRESSURE_PLATE));
        getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(BuiltInRegistries.BLOCK.getKey(BlockRegistry.BAOBAB_SLAB));
        getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(BuiltInRegistries.BLOCK.getKey(BlockRegistry.BAOBAB_STAIRS));
        getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(BuiltInRegistries.BLOCK.getKey(BlockRegistry.BAOBAB_TRAPDOOR));
        getOrCreateTagBuilder(BlockTags.LEAVES).add(BuiltInRegistries.BLOCK.getKey(BlockRegistry.BAOBAB_LEAVES));
        getOrCreateTagBuilder(BlockTags.CLIMBABLE).add(BuiltInRegistries.BLOCK.getKey(BlockRegistry.ROPE));
        // No BlockTags.LOGS_THAT_BURN constant in 1.21.1; reference by name.
        getOrCreateTagBuilder(TagKey.create(Registries.BLOCK, ResourceLocation.withDefaultNamespace("logs_that_burn")))
                .addTag(ModTags.BAOBAB_LOGS);
    }
}