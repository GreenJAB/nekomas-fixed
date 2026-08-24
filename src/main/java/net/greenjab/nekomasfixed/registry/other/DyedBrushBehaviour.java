package net.greenjab.nekomasfixed.registry.other;

import net.greenjab.nekomasfixed.registry.item.DyedBrushItem;
import net.greenjab.nekomasfixed.util.AllDyes;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.block.enums.BedPart;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jspecify.annotations.NonNull;

public class DyedBrushBehaviour extends FallibleItemDispenserBehavior {

	@Override
	protected @NonNull ItemStack dispenseSilently(BlockPointer source, ItemStack dispensed) {
		this.setSuccess(false);
		if (dispensed.getItem() instanceof DyedBrushItem brushItem) {
			AllDyes color = brushItem.getColor();
			Direction facing = source.state().get(DispenserBlock.FACING);
			BlockPos pos = source.pos().offset(facing);
			World level = source.world();
			BlockState state = level.getBlockState(pos);

			boolean used = false;

			if (state.isIn(ModTags.CAN_BE_DYED_WITH_BRUSH)) {
				if (state.isOf(Blocks.TERRACOTTA) || state.isIn(BlockTags.TERRACOTTA) && !state.isOf(getTerracotta(color))) {
					level.setBlockState(pos, getTerracotta(color).getDefaultState());
					used = true;
				} else if (state.isIn(ModTags.DYED_BRICKS) || state.isOf(Blocks.BRICKS) && !state.isOf(getBricks(color))) {
					level.setBlockState(pos, getBricks(color).getDefaultState());
					used = true;
				} else if (state.isIn(ModTags.DYED_BRICK_SLABS) || state.isOf(Blocks.BRICK_SLAB) && !state.isOf(getBrickSlabs(color))) {
					level.setBlockState(pos, getBrickSlabs(color).getDefaultState()
							.with(SlabBlock.WATERLOGGED, state.get(SlabBlock.WATERLOGGED))
							.with(SlabBlock.TYPE, state.get(SlabBlock.TYPE)));
					used = true;
				} else if (state.isIn(ModTags.DYED_BRICK_STAIRS) || state.isOf(Blocks.BRICK_STAIRS) && !state.isOf(getBrickStairs(color))) {
					level.setBlockState(pos, getBrickStairs(color).getDefaultState()
							.with(StairsBlock.WATERLOGGED, state.get(StairsBlock.WATERLOGGED))
							.with(StairsBlock.FACING, state.get(StairsBlock.FACING))
							.with(StairsBlock.HALF, state.get(StairsBlock.HALF))
							.with(StairsBlock.SHAPE, state.get(StairsBlock.SHAPE)));
					used = true;
				} else if (state.isIn(ModTags.DYED_BRICK_WALLS) || state.isOf(Blocks.BRICK_WALL) && !state.isOf(getBrickWalls(color))) {
					level.setBlockState(pos, getBrickWalls(color).getDefaultState()
							.with(WallBlock.WATERLOGGED, state.get(WallBlock.WATERLOGGED))
							.with(WallBlock.NORTH_WALL_SHAPE, state.get(WallBlock.NORTH_WALL_SHAPE))
							.with(WallBlock.EAST_WALL_SHAPE, state.get(WallBlock.EAST_WALL_SHAPE))
							.with(WallBlock.SOUTH_WALL_SHAPE, state.get(WallBlock.SOUTH_WALL_SHAPE))
							.with(WallBlock.WEST_WALL_SHAPE, state.get(WallBlock.WEST_WALL_SHAPE))
							.with(WallBlock.UP, state.get(WallBlock.UP)));
					used = true;
				} else if (state.isIn(ModTags.STAINED_GLASSES) || state.isOf(Blocks.GLASS) && !state.isOf(getStainedGlass(color))) {
					level.setBlockState(pos, getStainedGlass(color).getDefaultState());
					used = true;
				} else if (state.isIn(ModTags.STAINED_GLASS_PANES) || state.isOf(Blocks.GLASS_PANE) && !state.isOf(getStainedGlassPane(color))) {
					level.setBlockState(pos, getStainedGlassPane(color).getDefaultState()
							.with(StainedGlassPaneBlock.WATERLOGGED, state.get(StainedGlassPaneBlock.WATERLOGGED))
							.with(StainedGlassPaneBlock.EAST, state.get(StainedGlassPaneBlock.EAST))
							.with(StainedGlassPaneBlock.WEST, state.get(StainedGlassPaneBlock.WEST))
							.with(StainedGlassPaneBlock.SOUTH, state.get(StainedGlassPaneBlock.SOUTH))
							.with(StainedGlassPaneBlock.NORTH, state.get(StainedGlassPaneBlock.NORTH)));
					used = true;
				} else if (state.isIn(ModTags.GLAZED_TERRACOTTAS) && !state.isOf(getGlazedTerracotta(color))) {
					level.setBlockState(pos, getGlazedTerracotta(color).getDefaultState()
							.with(GlazedTerracottaBlock.FACING, state.get(GlazedTerracottaBlock.FACING)));
					used = true;
				} else if (state.isIn(ModTags.SPOTTED_WOOLS) && !state.isOf(getSpottedWool(color))) {
					level.setBlockState(pos, getSpottedWool(color).getDefaultState());
					used = true;
				} else if (state.isIn(BlockTags.WOOL) && !state.isIn(ModTags.SPOTTED_WOOLS) &&!state.isOf(getWool(color))) {
					level.setBlockState(pos, getWool(color).getDefaultState());
					used = true;
				} else if (state.isIn(BlockTags.CANDLES) || state.isOf(Blocks.CANDLE) && !state.isOf(getCandle(color))) {
					level.setBlockState(pos, getCandle(color).getDefaultState()
							.with(CandleBlock.CANDLES, state.get(CandleBlock.CANDLES))
							.with(CandleBlock.LIT, state.get(CandleBlock.LIT)));
					used = true;
				} else if (state.isIn(ModTags.SPOTTED_CARPETS) && !state.isOf(getSpottedCarpet(color))) {
					level.setBlockState(pos, getSpottedCarpet(color).getDefaultState());
					used = true;
				} else if (state.isIn(BlockTags.WOOL_CARPETS) && !state.isIn(ModTags.SPOTTED_CARPETS) && !state.isOf(getCarpet(color))) {
					level.setBlockState(pos, getCarpet(color).getDefaultState());
					used = true;
				} else if (state.isIn(ModTags.CONCRETES) && !state.isOf(getConcretes(color))) {
					level.setBlockState(pos, getConcretes(color).getDefaultState());
					used = true;
				} else if (state.isIn(ModTags.CONCRETE_POWDERS) && !state.isOf(getConcretePowders(color))) {
					level.setBlockState(pos, getConcretePowders(color).getDefaultState());
					used = true;
				} else if (state.isIn(ModTags.FROGLIGHTS) && !state.isOf(getFroglight(color))) {
					level.setBlockState(pos, getFroglight(color).getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS)));
					used = true;
				} else if (state.isOf(Blocks.SHULKER_BOX) || state.isIn(BlockTags.SHULKER_BOXES) && !state.isOf(getShulkerBox(color))) {
					if (level.getBlockEntity(pos) instanceof ShulkerBoxBlockEntity shulkerBoxBlockEntity) {
						level.setBlockState(pos, getShulkerBox(color).getDefaultState().with(ShulkerBoxBlock.FACING, state.get(ShulkerBoxBlock.FACING)));
						if (level.getBlockEntity(pos) instanceof ShulkerBoxBlockEntity newshulkerBoxBlockEntity) {
							for (int i = 0; i < shulkerBoxBlockEntity.size(); i++)
								newshulkerBoxBlockEntity.setStack(i, shulkerBoxBlockEntity.getStack(i));
							newshulkerBoxBlockEntity.customName = shulkerBoxBlockEntity.getCustomName();
						}
					}
					used = true;
				} else if (state.isIn(BlockTags.BEDS) && !state.isOf(getBed(color))) {
					Block bed = getBed(color);
					BedPart bedPart = state.get(BedBlock.PART);
					Direction bedDir = state.get(BedBlock.FACING);
					BlockPos otherPos = pos.offset(getDirectionTowardsOtherPart(bedPart, bedDir));
					BlockState newBed = bed.getDefaultState().with(BedBlock.FACING, bedDir);
					if (level.getBlockState(otherPos).isIn(BlockTags.BEDS)) {
						BlockPos head = bedPart == BedPart.HEAD ? pos : otherPos;
						BlockPos foot = bedPart == BedPart.HEAD ? otherPos : pos;
						level.setBlockState(head, Blocks.AIR.getDefaultState());
						level.setBlockState(foot, Blocks.AIR.getDefaultState());
						level.setBlockState(head, newBed.with(BedBlock.PART, BedPart.HEAD));
						level.setBlockState(foot, newBed.with(BedBlock.PART, BedPart.FOOT));
					} else level.setBlockState(pos, newBed.with(BedBlock.PART, bedPart));
					used = true;
				}
			}
			if (used){
				this.setSuccess(true);
				level.playSound(null, pos, SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
				if (dispensed.willBreakNextUse()) return ItemStack.EMPTY;
				else dispensed.setDamage(dispensed.getDamage()+1);
			}
		}
		return dispensed;
	}

	private static Direction getDirectionTowardsOtherPart(BedPart part, Direction direction) {
		return part == BedPart.FOOT ? direction : direction.getOpposite();
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
