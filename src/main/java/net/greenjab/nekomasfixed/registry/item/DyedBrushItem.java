package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.util.AllDyes;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.GlazedTerracottaBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import org.jspecify.annotations.NonNull;


public class DyedBrushItem extends Item {
    AllDyes color;

    public DyedBrushItem(AllDyes color, Properties settings) {
        super(settings);
        this.color = color;
    }

    @Override
    public @NonNull InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);
        boolean used = false;
        Player player = context.getPlayer();
        if (player != null) {
            player.swing(context.getHand());
        }
            if (!world.isClientSide() && state.is(ModTags.CAN_BE_DYED_WITH_BRUSH)) {

                if (state.is(Blocks.TERRACOTTA) || state.is(BlockTags.TERRACOTTA) && !state.is(getTerracotta(color))) {
                    world.setBlockAndUpdate(pos, getTerracotta(color).defaultBlockState());
                    used = true;
                    this.afterUse(context);
                } else if(state.is(ModTags.DYED_BRICKS) || state.is(Blocks.BRICKS) && !state.is(getBricks(color))){
                    world.setBlockAndUpdate(pos, getBricks(color).defaultBlockState());
                    used = true;
                    this.afterUse(context);
                } else if(state.is(ModTags.DYED_BRICK_SLABS) || state.is(Blocks.BRICK_SLAB) && !state.is(getBrickSlabs(color))){
                    world.setBlockAndUpdate(pos, getBrickSlabs(color).defaultBlockState()
                            .setValue(SlabBlock.WATERLOGGED, state.getValue(SlabBlock.WATERLOGGED))
                            .setValue(SlabBlock.TYPE, state.getValue(SlabBlock.TYPE)));
                    used = true;
                    this.afterUse(context);
                } else if(state.is(ModTags.DYED_BRICK_STAIRS) || state.is(Blocks.BRICK_STAIRS) && !state.is(getBrickStairs(color))){
                    world.setBlockAndUpdate(pos, getBrickStairs(color).defaultBlockState()
                            .setValue(StairBlock.WATERLOGGED, state.getValue(StairBlock.WATERLOGGED))
                            .setValue(StairBlock.FACING, state.getValue(StairBlock.FACING))
                            .setValue(StairBlock.HALF, state.getValue(StairBlock.HALF))
                            .setValue(StairBlock.SHAPE, state.getValue(StairBlock.SHAPE)));
                    used = true;
                    this.afterUse(context);
                } else if(state.is(ModTags.DYED_BRICK_WALLS) || state.is(Blocks.BRICK_WALL) && !state.is(getBrickWalls(color))){
                    world.setBlockAndUpdate(pos, getBrickWalls(color).defaultBlockState()
                            .setValue(WallBlock.WATERLOGGED, state.getValue(WallBlock.WATERLOGGED))
                            .setValue(WallBlock.NORTH, state.getValue(WallBlock.NORTH))
                            .setValue(WallBlock.EAST, state.getValue(WallBlock.EAST))
                            .setValue(WallBlock.SOUTH, state.getValue(WallBlock.SOUTH))
                            .setValue(WallBlock.WEST, state.getValue(WallBlock.WEST))
                            .setValue(WallBlock.UP, state.getValue(WallBlock.UP)));
                    used = true;
                    this.afterUse(context);
                } else if(state.is(ModTags.STAINED_GLASSES) || state.is(Blocks.GLASS) && !state.is(getStainedGlass(color)) ){
                    world.setBlockAndUpdate(pos, getStainedGlass(color).defaultBlockState());
                    used = true;
                    this.afterUse(context);
                } else if(state.is(ModTags.STAINED_GLASS_PANES) || state.is(Blocks.GLASS_PANE) && !state.is(getStainedGlassPane(color))){
                    world.setBlockAndUpdate(pos, getStainedGlassPane(color).defaultBlockState()
                            .setValue(StainedGlassPaneBlock.WATERLOGGED, state.getValue(StainedGlassPaneBlock.WATERLOGGED))
                            .setValue(StainedGlassPaneBlock.EAST, state.getValue(StainedGlassPaneBlock.EAST))
                            .setValue(StainedGlassPaneBlock.WEST, state.getValue(StainedGlassPaneBlock.WEST))
                            .setValue(StainedGlassPaneBlock.SOUTH, state.getValue(StainedGlassPaneBlock.SOUTH))
                            .setValue(StainedGlassPaneBlock.NORTH, state.getValue(StainedGlassPaneBlock.NORTH)));
                    used = true;
                    this.afterUse(context);
                } else if(state.is(ModTags.GLAZED_TERRACOTTAS) && !state.is(getGlazedTerracotta(color))){
                    world.setBlockAndUpdate(pos, getGlazedTerracotta(color).defaultBlockState()
                            .setValue(GlazedTerracottaBlock.FACING, state.getValue(GlazedTerracottaBlock.FACING)));
                    used = true;
                    this.afterUse(context);
                } else if(state.is(ModTags.SPOTTED_WOOLS) && !state.is(getSpottedWool(color))){
                    world.setBlockAndUpdate(pos, getSpottedWool(color).defaultBlockState());
                    used = true;
                    this.afterUse(context);
                } else if(state.is(BlockTags.WOOL) && !state.is(getWool(color))){
                    world.setBlockAndUpdate(pos, getWool(color).defaultBlockState());
                    used = true;
                    this.afterUse(context);
                } else if(state.is(BlockTags.CANDLES) || state.is(Blocks.CANDLE) && !state.is(getCandle(color))){
                    world.setBlockAndUpdate(pos, getCandle(color).defaultBlockState()
                            .setValue(CandleBlock.CANDLES, state.getValue(CandleBlock.CANDLES))
                            .setValue(CandleBlock.LIT, state.getValue(CandleBlock.LIT))
                    );
                    used = true;
                    this.afterUse(context);
                } else if(state.is(ModTags.SPOTTED_CARPETS) && !state.is(getSpottedCarpet(color))){
                    world.setBlockAndUpdate(pos, getSpottedCarpet(color).defaultBlockState());
                    used = true;
                    this.afterUse(context);
                } else if(state.is(BlockTags.WOOL_CARPETS) && !state.is(getCarpet(color))){
                    world.setBlockAndUpdate(pos, getCarpet(color).defaultBlockState());
                    used = true;
                    this.afterUse(context);
                } else if(state.is(ModTags.CONCRETES) && !state.is(getConcretes(color))){
                    world.setBlockAndUpdate(pos, getConcretes(color).defaultBlockState());
                    used = true;
                    this.afterUse(context);
                } else if(state.is(ModTags.CONCRETE_POWDERS) && !state.is(getConcretePowders(color))){
                    world.setBlockAndUpdate(pos, getConcretePowders(color).defaultBlockState());
                    used = true;
                    this.afterUse(context);
                } else if(state.is(ModTags.FROGLIGHTS) && !state.is(getFroglight(color))){
                    world.setBlockAndUpdate(pos, getFroglight(color).defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)));
                    used = true;
                    this.afterUse(context);
                } else if(state.is(Blocks.SHULKER_BOX) || state.is(BlockTags.SHULKER_BOXES) && !state.is(getShulkerBox(color))){
                    if (world.getBlockEntity(pos) instanceof ShulkerBoxBlockEntity shulkerBoxBlockEntity) {
                       world.setBlockAndUpdate(pos, getShulkerBox(color).defaultBlockState().setValue(ShulkerBoxBlock.FACING, state.getValue(ShulkerBoxBlock.FACING)));
                        if (world.getBlockEntity(pos) instanceof ShulkerBoxBlockEntity newshulkerBoxBlockEntity) {
                            for (int i = 0;i<shulkerBoxBlockEntity.getContainerSize();i++)
                                newshulkerBoxBlockEntity.setItem(i, shulkerBoxBlockEntity.getItem(i));
                            newshulkerBoxBlockEntity.name = shulkerBoxBlockEntity.getCustomName();
                        }
                    }
                    used = true;
                    this.afterUse(context);
                } else if(state.is(BlockTags.BEDS) && !state.is(getBed(color))){
                    Block bed = getBed(color);
                    BedPart bedPart = state.getValue(BedBlock.PART);
                    Direction bedDir = state.getValue(BedBlock.FACING);
                    BlockPos otherPos = pos.relative(getDirectionTowardsOtherPart(bedPart, bedDir));
                    BlockState newBed = bed.defaultBlockState().setValue(BedBlock.FACING, bedDir);
                    if (world.getBlockState(otherPos).is(BlockTags.BEDS)) {
                        BlockPos head = bedPart == BedPart.HEAD?pos:otherPos;
                        BlockPos foot = bedPart == BedPart.HEAD?otherPos:pos;
                        world.setBlockAndUpdate(head, Blocks.AIR.defaultBlockState());
                        world.setBlockAndUpdate(foot, Blocks.AIR.defaultBlockState());
                        world.setBlockAndUpdate(head, newBed.setValue(BedBlock.PART, BedPart.HEAD));
                        world.setBlockAndUpdate(foot, newBed.setValue(BedBlock.PART, BedPart.FOOT));
                    } else {
                        world.setBlockAndUpdate(pos, newBed.setValue(BedBlock.PART, bedPart));
                    }
                    used = true;
                    this.afterUse(context);
                }
            }

            return used ? InteractionResult.SUCCESS : InteractionResult.FAIL;
    }

    private static Direction getDirectionTowardsOtherPart(BedPart part, Direction direction) {
        return part == BedPart.FOOT ? direction : direction.getOpposite();
    }

    private void afterUse( UseOnContext context){
        Player player = context.getPlayer();
        if (player != null) {
            context.getItemInHand().hurtWithoutBreaking(1, player);
            context.getLevel().playSound(null, context.getClickedPos(), SoundEvents.SLIME_SQUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
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
