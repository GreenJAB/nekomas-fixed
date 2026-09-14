package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

/**
 * Generates blockstates + models for the colour-suite blocks.
 * Phase 1 covers the 20-colour spotted wool (cube_all) and spotted carpet
 * (carpet parent), reproduced from the same source fields used to hand-generate
 * the committed JSONs so the diff against the settled output is byte-identical.
 */
public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    private static Block[] spottedWoolBlocks() {
        return new Block[]{
                BlockRegistry.WHITE_SPOTTED_WOOL,
                BlockRegistry.LIGHT_GRAY_SPOTTED_WOOL,
                BlockRegistry.GRAY_SPOTTED_WOOL,
                BlockRegistry.BLACK_SPOTTED_WOOL,
                BlockRegistry.BROWN_SPOTTED_WOOL,
                BlockRegistry.RED_SPOTTED_WOOL,
                BlockRegistry.ORANGE_SPOTTED_WOOL,
                BlockRegistry.YELLOW_SPOTTED_WOOL,
                BlockRegistry.LIME_SPOTTED_WOOL,
                BlockRegistry.GREEN_SPOTTED_WOOL,
                BlockRegistry.CYAN_SPOTTED_WOOL,
                BlockRegistry.LIGHT_BLUE_SPOTTED_WOOL,
                BlockRegistry.BLUE_SPOTTED_WOOL,
                BlockRegistry.PURPLE_SPOTTED_WOOL,
                BlockRegistry.MAGENTA_SPOTTED_WOOL,
                BlockRegistry.PINK_SPOTTED_WOOL,
                BlockRegistry.AMBER_SPOTTED_WOOL,
                BlockRegistry.AQUA_SPOTTED_WOOL,
                BlockRegistry.INDIGO_SPOTTED_WOOL,
                BlockRegistry.MAROON_SPOTTED_WOOL,
        };
    }

    private static Block[] spottedCarpetBlocks() {
        return new Block[]{
                BlockRegistry.WHITE_SPOTTED_CARPET,
                BlockRegistry.LIGHT_GRAY_SPOTTED_CARPET,
                BlockRegistry.GRAY_SPOTTED_CARPET,
                BlockRegistry.BLACK_SPOTTED_CARPET,
                BlockRegistry.BROWN_SPOTTED_CARPET,
                BlockRegistry.RED_SPOTTED_CARPET,
                BlockRegistry.ORANGE_SPOTTED_CARPET,
                BlockRegistry.YELLOW_SPOTTED_CARPET,
                BlockRegistry.LIME_SPOTTED_CARPET,
                BlockRegistry.GREEN_SPOTTED_CARPET,
                BlockRegistry.CYAN_SPOTTED_CARPET,
                BlockRegistry.LIGHT_BLUE_SPOTTED_CARPET,
                BlockRegistry.BLUE_SPOTTED_CARPET,
                BlockRegistry.PURPLE_SPOTTED_CARPET,
                BlockRegistry.MAGENTA_SPOTTED_CARPET,
                BlockRegistry.PINK_SPOTTED_CARPET,
                BlockRegistry.AMBER_SPOTTED_CARPET,
                BlockRegistry.AQUA_SPOTTED_CARPET,
                BlockRegistry.INDIGO_SPOTTED_CARPET,
                BlockRegistry.MAROON_SPOTTED_CARPET,
        };
    }

    private static Block[][] brickFamilies() {
        return new Block[][]{
                {BlockRegistry.WHITE_BRICKS, BlockRegistry.WHITE_BRICK_SLAB, BlockRegistry.WHITE_BRICK_STAIRS, BlockRegistry.WHITE_BRICK_WALL},
                {BlockRegistry.ORANGE_BRICKS, BlockRegistry.ORANGE_BRICK_SLAB, BlockRegistry.ORANGE_BRICK_STAIRS, BlockRegistry.ORANGE_BRICK_WALL},
                {BlockRegistry.MAGENTA_BRICKS, BlockRegistry.MAGENTA_BRICK_SLAB, BlockRegistry.MAGENTA_BRICK_STAIRS, BlockRegistry.MAGENTA_BRICK_WALL},
                {BlockRegistry.LIGHT_BLUE_BRICKS, BlockRegistry.LIGHT_BLUE_BRICK_SLAB, BlockRegistry.LIGHT_BLUE_BRICK_STAIRS, BlockRegistry.LIGHT_BLUE_BRICK_WALL},
                {BlockRegistry.YELLOW_BRICKS, BlockRegistry.YELLOW_BRICK_SLAB, BlockRegistry.YELLOW_BRICK_STAIRS, BlockRegistry.YELLOW_BRICK_WALL},
                {BlockRegistry.LIME_BRICKS, BlockRegistry.LIME_BRICK_SLAB, BlockRegistry.LIME_BRICK_STAIRS, BlockRegistry.LIME_BRICK_WALL},
                {BlockRegistry.PINK_BRICKS, BlockRegistry.PINK_BRICK_SLAB, BlockRegistry.PINK_BRICK_STAIRS, BlockRegistry.PINK_BRICK_WALL},
                {BlockRegistry.GRAY_BRICKS, BlockRegistry.GRAY_BRICK_SLAB, BlockRegistry.GRAY_BRICK_STAIRS, BlockRegistry.GRAY_BRICK_WALL},
                {BlockRegistry.LIGHT_GRAY_BRICKS, BlockRegistry.LIGHT_GRAY_BRICK_SLAB, BlockRegistry.LIGHT_GRAY_BRICK_STAIRS, BlockRegistry.LIGHT_GRAY_BRICK_WALL},
                {BlockRegistry.CYAN_BRICKS, BlockRegistry.CYAN_BRICK_SLAB, BlockRegistry.CYAN_BRICK_STAIRS, BlockRegistry.CYAN_BRICK_WALL},
                {BlockRegistry.PURPLE_BRICKS, BlockRegistry.PURPLE_BRICK_SLAB, BlockRegistry.PURPLE_BRICK_STAIRS, BlockRegistry.PURPLE_BRICK_WALL},
                {BlockRegistry.BLUE_BRICKS, BlockRegistry.BLUE_BRICK_SLAB, BlockRegistry.BLUE_BRICK_STAIRS, BlockRegistry.BLUE_BRICK_WALL},
                {BlockRegistry.BROWN_BRICKS, BlockRegistry.BROWN_BRICK_SLAB, BlockRegistry.BROWN_BRICK_STAIRS, BlockRegistry.BROWN_BRICK_WALL},
                {BlockRegistry.GREEN_BRICKS, BlockRegistry.GREEN_BRICK_SLAB, BlockRegistry.GREEN_BRICK_STAIRS, BlockRegistry.GREEN_BRICK_WALL},
                {BlockRegistry.RED_BRICKS, BlockRegistry.RED_BRICK_SLAB, BlockRegistry.RED_BRICK_STAIRS, BlockRegistry.RED_BRICK_WALL},
                {BlockRegistry.BLACK_BRICKS, BlockRegistry.BLACK_BRICK_SLAB, BlockRegistry.BLACK_BRICK_STAIRS, BlockRegistry.BLACK_BRICK_WALL},
                {BlockRegistry.AMBER_BRICKS, BlockRegistry.AMBER_BRICK_SLAB, BlockRegistry.AMBER_BRICK_STAIRS, BlockRegistry.AMBER_BRICK_WALL},
                {BlockRegistry.AQUA_BRICKS, BlockRegistry.AQUA_BRICK_SLAB, BlockRegistry.AQUA_BRICK_STAIRS, BlockRegistry.AQUA_BRICK_WALL},
                {BlockRegistry.INDIGO_BRICKS, BlockRegistry.INDIGO_BRICK_SLAB, BlockRegistry.INDIGO_BRICK_STAIRS, BlockRegistry.INDIGO_BRICK_WALL},
                {BlockRegistry.MAROON_BRICKS, BlockRegistry.MAROON_BRICK_SLAB, BlockRegistry.MAROON_BRICK_STAIRS, BlockRegistry.MAROON_BRICK_WALL},
        };
    }

    private static ResourceLocation blockModelLoc(Block block) {
        return ModelLocationUtils.getModelLocation(block);
    }

    // cube_all for the wool + a carpet model that reuses the wool texture (carpets
    // have no texture of their own, so the default CARPET mapping is overridden).
    private static void woolAndCarpet(BlockModelGenerators generator, Block wool, Block carpet) {
        generator.createTrivialCube(wool);
        generator.createTrivialBlock(carpet,
                TexturedModel.CARPET.updateTexture(mapping ->
                        mapping.put(TextureSlot.WOOL, TextureMapping.wool(wool).get(TextureSlot.WOOL))));
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {
        Block[] wools = spottedWoolBlocks();
        Block[] carpets = spottedCarpetBlocks();
        for (int i = 0; i < wools.length; i++) {
            woolAndCarpet(generator, wools[i], carpets[i]);
        }

        // Ancient-dye plain wool + carpet (no separate {colour}_carpet.png, so the
        // carpet reuses the wool texture like the spotted set).
        Block[] plainWools = {
                BlockRegistry.AMBER_WOOL, BlockRegistry.AQUA_WOOL,
                BlockRegistry.INDIGO_WOOL, BlockRegistry.MAROON_WOOL
        };
        Block[] plainCarpets = {
                BlockRegistry.AMBER_CARPET, BlockRegistry.AQUA_CARPET,
                BlockRegistry.INDIGO_CARPET, BlockRegistry.MAROON_CARPET
        };
        for (int i = 0; i < plainWools.length; i++) {
            woolAndCarpet(generator, plainWools[i], plainCarpets[i]);
        }

        // Ancient-colour stained glass blocks: plain cube using the shared
        // {colour}_stained_glass texture. (Panes are bespoke multipart models,
        // hand-written in the resources folder.)
        for (Block glass : new Block[]{
                BlockRegistry.AMBER_STAINED_GLASS, BlockRegistry.AQUA_STAINED_GLASS,
                BlockRegistry.INDIGO_STAINED_GLASS, BlockRegistry.MAROON_STAINED_GLASS}) {
            generator.createTrivialCube(glass);
        }

        // Ancient-dye terracotta, concrete, concrete powder: plain trivial cubes
        // (textures are the {colour}_terracotta / _concrete / _concrete_powder PNGs).
        for (Block block : BlockDyeMap.TERRACOTTA.values()) generator.createTrivialCube(block);
        for (Block block : BlockDyeMap.CONCRETE.values()) generator.createTrivialCube(block);
        for (Block block : BlockDyeMap.CONCRETE_POWDER.values()) generator.createTrivialCube(block);
        // Glazed terracotta: directional blockstate + block model are hand-written (facing
        // variants + template_glazed_terracotta); only the item needs wiring. delegateItemModel
        // emits models/item/{colour}_glazed_terracotta.json -> parent block/{id} without
        // touching the blockstate. main's 26.x items/*.json format doesn't apply here.
        for (Block block : BlockDyeMap.GLAZED_TERRACOTTA.values())
            generator.delegateItemModel(block, blockModelLoc(block));

        // Colour brick families (20 colours x base/slab/stairs/wall = the 80-block set):
        // the vanilla family chain emits the cube base + slab/stairs/wall blockstates+models
        // from the {colour}_bricks texture. Base item is delegated (slab/stairs/wall items
        // are delegated internally by the family provider).
        for (Block[] family : brickFamilies()) {
            generator.family(family[0]).slab(family[1]).stairs(family[2]).wall(family[3]);
            generator.delegateItemModel(family[0], ModelLocationUtils.getModelLocation(family[0]));
        }

        // Baobab wood family: base planks cube + the vanilla-template shapes (slab, stairs,
        // fence, fence-gate, button, pressure plate) via the family chain. Door/trapdoor/signs
        // (custom textures / block-entity attach) and the log/wood pillar set stay hand-written.
        generator.family(BlockRegistry.BAOBAB_PLANKS)
                .slab(BlockRegistry.BAOBAB_SLAB)
                .stairs(BlockRegistry.BAOBAB_STAIRS)
                .fence(BlockRegistry.BAOBAB_FENCE)
                .fenceGate(BlockRegistry.BAOBAB_FENCE_GATE)
                .button(BlockRegistry.BAOBAB_BUTTON)
                .pressurePlate(BlockRegistry.BAOBAB_PRESSURE_PLATE);
        generator.delegateItemModel(BlockRegistry.BAOBAB_PLANKS,
                ModelLocationUtils.getModelLocation(BlockRegistry.BAOBAB_PLANKS));
    }

    @Override
    public void generateItemModels(ItemModelGenerators generator) {
        // Block items parent to their block model automatically (handled by the
        // ModelProviderMixin filter); nothing extra needed for these blocks.
    }
}