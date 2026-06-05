package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.greenjab.nekomasfixed.util.AllDyes;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.minecraft.block.*;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.block.enums.BedPart;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;


public class DyedBrushItem extends Item {
    AllDyes color;

    public DyedBrushItem(AllDyes color, Settings settings) {
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
                } else if(state.isIn(OtherRegistry.SPOTTED_WOOLS) && !state.isOf(getSpottedWool(color))){
                    world.setBlockState(pos, getSpottedWool(color).getDefaultState());
                    used = true;
                    this.afterUse(context);
                } else if(state.isIn(BlockTags.CANDLES) || state.isOf(Blocks.CANDLE) && !state.isOf(getCandle(color))){
                    world.setBlockState(pos, getCandle(color).getDefaultState()
                            .with(CandleBlock.CANDLES, state.get(CandleBlock.CANDLES))
                            .with(CandleBlock.LIT, state.get(CandleBlock.LIT))
                    );
                    used = true;
                    this.afterUse(context);
                } else if(state.isIn(BlockTags.WOOL_CARPETS) && !state.isOf(getCarpet(color))){
                    world.setBlockState(pos, getCarpet(color).getDefaultState());
                    used = true;
                    this.afterUse(context);
                } else if(state.isIn(OtherRegistry.SPOTTED_CARPETS) && !state.isOf(getSpottedCarpet(color))){
                    world.setBlockState(pos, getSpottedCarpet(color).getDefaultState());
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
                } else if(state.isIn(OtherRegistry.FROGLIGHTS) && !state.isOf(getFroglight(color))){
                    world.setBlockState(pos, getFroglight(color).getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS)));
                    used = true;
                    this.afterUse(context);
                } else if(state.isOf(Blocks.SHULKER_BOX) || state.isIn(BlockTags.SHULKER_BOXES) && !state.isOf(getShulkerBox(color))){
                    if (world.getBlockEntity(pos) instanceof ShulkerBoxBlockEntity shulkerBoxBlockEntity) {
                       world.setBlockState(pos, getShulkerBox(color).getDefaultState().with(ShulkerBoxBlock.FACING, state.get(ShulkerBoxBlock.FACING)));
                        if (world.getBlockEntity(pos) instanceof ShulkerBoxBlockEntity newshulkerBoxBlockEntity) {
                            for (int i = 0;i<shulkerBoxBlockEntity.size();i++)
                                newshulkerBoxBlockEntity.setStack(i, shulkerBoxBlockEntity.getStack(i));
                            newshulkerBoxBlockEntity.customName = shulkerBoxBlockEntity.getCustomName();
                        }
                    }
                    used = true;
                    this.afterUse(context);
                } else if(state.isIn(BlockTags.BEDS) && !state.isOf(getBed(color))){
                    Block bed = getBed(color);
                    BedPart bedPart = state.get(BedBlock.PART);
                    Direction bedDir = state.get(BedBlock.FACING);
                    BlockPos otherPos = pos.offset(getDirectionTowardsOtherPart(bedPart, bedDir));
                    BlockState newBed = bed.getDefaultState().with(BedBlock.FACING, bedDir);
                    if (world.getBlockState(otherPos).isIn(BlockTags.BEDS)) {
                        BlockPos head = bedPart == BedPart.HEAD?pos:otherPos;
                        BlockPos foot = bedPart == BedPart.HEAD?otherPos:pos;
                        world.setBlockState(head, Blocks.AIR.getDefaultState());
                        world.setBlockState(foot, Blocks.AIR.getDefaultState());
                        world.setBlockState(head, newBed.with(BedBlock.PART, BedPart.HEAD));
                        world.setBlockState(foot, newBed.with(BedBlock.PART, BedPart.FOOT));
                    } else {
                        world.setBlockState(pos, newBed.with(BedBlock.PART, bedPart));
                    }
                    used = true;
                    this.afterUse(context);
                }
            }

            return used ? ActionResult.SUCCESS : ActionResult.FAIL;
    }

    private static Direction getDirectionTowardsOtherPart(BedPart part, Direction direction) {
        return part == BedPart.FOOT ? direction : direction.getOpposite();
    }

    private void afterUse( ItemUsageContext context){
        PlayerEntity player = context.getPlayer();
        if (player != null) {
            context.getStack().damage(1, player);
            context.getWorld().playSound(null, context.getBlockPos(), SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    private static Block getStainedGlass(AllDyes color) { return BlockDyeMap.STAINED_GLASS.get(color); }
    private static Block getStainedGlassPane(AllDyes color) { return BlockDyeMap.STAINED_GLASS_PANE.get(color); }
    private static Block getTerracotta(AllDyes color) { return BlockDyeMap.TERRACOTTA.get(color); }
    private static Block getGlazedTerracotta(AllDyes color) { return BlockDyeMap.GLAZED_TERRACOTTA.get(color); }
    private static Block getCandle(AllDyes color) { return BlockDyeMap.CANDLE.get(color); }
    private static Block getBricks(AllDyes color) { return BlockDyeMap.BRICKS.get(color); }
    private static Block getBrickSlabs(AllDyes color) { return BlockDyeMap.BRICK_SLAB.get(color); }
    private static Block getBrickStairs(AllDyes color) { return BlockDyeMap.BRICK_STAIRS.get(color); }
    private static Block getBrickWalls(AllDyes color) { return BlockDyeMap.BRICK_WALL.get(color); }
    private static Block getConcretes(AllDyes color) { return BlockDyeMap.CONCRETE.get(color); }
    private static Block getConcretePowders(AllDyes color) { return BlockDyeMap.CONCRETE_POWDER.get(color); }
    private static Block getWool(AllDyes color) { return BlockDyeMap.WOOL.get(color); }
    private static Block getCarpet(AllDyes color) {return BlockDyeMap.CARPET.get(color); }
    private static Block getSpottedWool(AllDyes color) { return BlockDyeMap.SPOTTED_WOOL.get(color); }
    private static Block getSpottedCarpet(AllDyes color) {return BlockDyeMap.SPOTTED_CARPET.get(color); }
    private static Block getFroglight(AllDyes color) {return BlockDyeMap.FROGLIGHT.get(color); }
    private static Block getShulkerBox(AllDyes color) {return BlockDyeMap.SHULKER_BOX.get(color); }
    private static Block getBed(AllDyes color) {return BlockDyeMap.BED.get(color); }

}
