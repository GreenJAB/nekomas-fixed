package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.greenjab.nekomasfixed.util.DyedBrushableBlocksMappings;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class DyedBrushItem extends Item {
    DyeColor color;

    public DyedBrushItem(DyeColor color, Settings settings) {
        super(settings);
        this.color = color;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        boolean used = false;
        PlayerEntity player = context.getPlayer();
        if (player != null) {
            player.swingHand(context.getHand());
        }
            if (!world.isClient() && state.isIn(OtherRegistry.CAN_BE_DYED_WITH_BRUSH)) {

                if (state.isOf(Blocks.TERRACOTTA) || state.isIn(BlockTags.TERRACOTTA) && !state.isOf(getTerracotta(color))) {
                    world.setBlockState(pos, getTerracotta(color).getDefaultState());
                    used = true;
                    this.afterUse(context);
                } else if(state.isIn(OtherRegistry.DYED_BRICKS) || state.isOf(Blocks.BRICKS) && !state.isOf(getBricks(color))){
                    world.setBlockState(pos, getBricks(color).getDefaultState());
                    used = true;
                    this.afterUse(context);
                } else if(state.isIn(OtherRegistry.DYED_BRICK_SLABS) || state.isOf(Blocks.BRICK_SLAB) && !state.isOf(getBrickSlabs(color))){
                    world.setBlockState(pos, getBrickSlabs(color).getDefaultState()
                            .with(SlabBlock.WATERLOGGED, state.get(SlabBlock.WATERLOGGED))
                            .with(SlabBlock.TYPE, state.get(SlabBlock.TYPE)));
                    used = true;
                    this.afterUse(context);
                } else if(state.isIn(OtherRegistry.DYED_BRICK_STAIRS) || state.isOf(Blocks.BRICK_STAIRS) && !state.isOf(getBrickStairs(color))){
                    world.setBlockState(pos, getBrickStairs(color).getDefaultState()
                            .with(StairsBlock.WATERLOGGED, state.get(StairsBlock.WATERLOGGED))
                            .with(StairsBlock.FACING, state.get(StairsBlock.FACING))
                            .with(StairsBlock.HALF, state.get(StairsBlock.HALF))
                            .with(StairsBlock.SHAPE, state.get(StairsBlock.SHAPE)));
                    used = true;
                    this.afterUse(context);
                } else if(state.isIn(OtherRegistry.DYED_BRICK_WALLS) || state.isOf(Blocks.BRICK_WALL) && !state.isOf(getBrickWalls(color))){
                    world.setBlockState(pos, getBrickWalls(color).getDefaultState()
                            .with(WallBlock.WATERLOGGED, state.get(WallBlock.WATERLOGGED))
                            .with(WallBlock.NORTH_WALL_SHAPE, state.get(WallBlock.NORTH_WALL_SHAPE))
                            .with(WallBlock.EAST_WALL_SHAPE, state.get(WallBlock.EAST_WALL_SHAPE))
                            .with(WallBlock.SOUTH_WALL_SHAPE, state.get(WallBlock.SOUTH_WALL_SHAPE))
                            .with(WallBlock.WEST_WALL_SHAPE, state.get(WallBlock.WEST_WALL_SHAPE))
                            .with(WallBlock.UP, state.get(WallBlock.UP)));
                    used = true;
                    this.afterUse(context);
                } else if(state.isIn(OtherRegistry.STAINED_GLASSES) || state.isOf(Blocks.GLASS) && !state.isOf(getStainedGlass(color)) ){
                    world.setBlockState(pos, getStainedGlass(color).getDefaultState());
                    used = true;
                    this.afterUse(context);
                } else if(state.isIn(OtherRegistry.STAINED_GLASS_PANES) || state.isOf(Blocks.GLASS_PANE) && !state.isOf(getStainedGlassPane(color))){
                    world.setBlockState(pos, getStainedGlassPane(color).getDefaultState()
                            .with(StainedGlassPaneBlock.WATERLOGGED, state.get(StainedGlassPaneBlock.WATERLOGGED))
                            .with(StainedGlassPaneBlock.EAST, state.get(StainedGlassPaneBlock.EAST))
                            .with(StainedGlassPaneBlock.WEST, state.get(StainedGlassPaneBlock.WEST))
                            .with(StainedGlassPaneBlock.SOUTH, state.get(StainedGlassPaneBlock.SOUTH))
                            .with(StainedGlassPaneBlock.NORTH, state.get(StainedGlassPaneBlock.NORTH)));
                    used = true;
                    this.afterUse(context);
                } else if(state.isIn(OtherRegistry.GLAZED_TERRACOTTAS) && !state.isOf(getGlazedTerracotta(color))){
                    world.setBlockState(pos, getGlazedTerracotta(color).getDefaultState()
                            .with(GlazedTerracottaBlock.FACING, state.get(GlazedTerracottaBlock.FACING)));
                    used = true;
                    this.afterUse(context);
                } else if(state.isIn(BlockTags.WOOL) && !state.isOf(getWool(color))){
                    world.setBlockState(pos, getWool(color).getDefaultState());
                    used = true;
                    this.afterUse(context);
                } else if(state.isIn(BlockTags.CANDLES) || state.isOf(Blocks.CANDLE) && !state.isOf(getCandle(color))){
                    world.setBlockState(pos, getCandle(color).getDefaultState()
                            .with(CandleBlock.CANDLES, state.get(CandleBlock.CANDLES))
                            .with(CandleBlock.LIT, state.get(CandleBlock.LIT))
                    );
                    used = true;
                    this.afterUse(context);
                } else if(state.isIn(BlockTags.WOOL_CARPETS) && !state.isOf(getWoolCarpet(color))){
                    world.setBlockState(pos, getWoolCarpet(color).getDefaultState());
                    used = true;
                    this.afterUse(context);
                } else if(state.isIn(OtherRegistry.CONCRETES) && !state.isOf(getConcretes(color))){
                    world.setBlockState(pos, getConcretes(color).getDefaultState());
                    used = true;
                    this.afterUse(context);
                } else if(state.isIn(OtherRegistry.CONCRETE_POWDERS) && !state.isOf(getConcretePowders(color))){
                    world.setBlockState(pos, getConcretePowders(color).getDefaultState());
                    used = true;
                    this.afterUse(context);
                }
                //remaining - beds and banners
            }

            return used ? ActionResult.SUCCESS : ActionResult.FAIL;
    }

    private void afterUse( ItemUsageContext context){
        PlayerEntity player = context.getPlayer();
        if (player != null) {
            context.getStack().damage(1, player);
            context.getWorld().playSound(null, context.getBlockPos(), SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    private static Block getStainedGlass(DyeColor color) { return DyedBrushableBlocksMappings.STAINED_GLASS.get(color); }
    private static Block getStainedGlassPane(DyeColor color) { return DyedBrushableBlocksMappings.STAINED_GLASS_PANES.get(color); }
    private static Block getTerracotta(DyeColor color) { return DyedBrushableBlocksMappings.TERRACOTTA.get(color); }
    private static Block getGlazedTerracotta(DyeColor color) { return DyedBrushableBlocksMappings.GLAZED_TERRACOTTA.get(color); }
    private static Block getCandle(DyeColor color) { return DyedBrushableBlocksMappings.CANDLES.get(color); }
    private static Block getBricks(DyeColor color) { return DyedBrushableBlocksMappings.BRICKS.get(color); }
    private static Block getBrickSlabs(DyeColor color) { return DyedBrushableBlocksMappings.BRICK_SLAB.get(color); }
    private static Block getBrickStairs(DyeColor color) { return DyedBrushableBlocksMappings.BRICK_STAIRS.get(color); }
    private static Block getBrickWalls(DyeColor color) { return DyedBrushableBlocksMappings.BRICK_WALL.get(color); }
    private static Block getConcretes(DyeColor color) { return DyedBrushableBlocksMappings.CONCRETE.get(color); }
    private static Block getConcretePowders(DyeColor color) { return DyedBrushableBlocksMappings.CONCRETE_POWDER.get(color); }
    private static Block getWool(DyeColor color) { return DyedBrushableBlocksMappings.WOOL.get(color); }
    private static Block getWoolCarpet(DyeColor color) {return DyedBrushableBlocksMappings.CARPETS.get(color); }

}
