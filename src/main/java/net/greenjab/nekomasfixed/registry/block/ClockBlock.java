package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.registry.block.entity.ClockBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationPropertyHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class ClockBlock extends BlockWithEntity implements Waterloggable {
	public static final MapCodec<ClockBlock> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
				createSettingsCodec()
			).apply(instance, ClockBlock::new)
	);
	public static final int MAX_ROTATION_INDEX = RotationPropertyHelper.getMax();
	private static final int MAX_ROTATIONS = MAX_ROTATION_INDEX + 1;
	public static final IntProperty ROTATION = Properties.ROTATION;
	private static final VoxelShape SHAPE = Block.createColumnShape(8.0, 0.0, 8.0);
	public static final BooleanProperty POWERED = Properties.POWERED;

	@Override
	public MapCodec<? extends ClockBlock> getCodec() {
		return CODEC;
	}

	public ClockBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(ROTATION, 0));
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return  SHAPE;
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx).with(ROTATION, RotationPropertyHelper.fromYaw(ctx.getPlayerYaw()));
	}

	@Override
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(ROTATION, rotation.rotate(state.get(ROTATION), MAX_ROTATIONS));
	}

	@Override
	protected BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.with(ROTATION, mirror.mirror(state.get(ROTATION), MAX_ROTATIONS));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(ROTATION);
	}


	@Override
	protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		return ActionResult.SUCCESS;
	}

	private void playSound(WorldAccess world, BlockPos pos, SoundEvent sound) {
		world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
	}


	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new ClockBlockEntity(pos, state);
	}

	/*@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return world.isClient() ? validateTicker(type, BlockEntityTypeRegistry.CLOCK_BLOCK_ENTITY, ClockBlockEntity::clientTick) : null;
	}*/

	@Override
	protected boolean hasComparatorOutput(BlockState state) {
		return true;
	}

	@Override
	protected int getComparatorOutput(BlockState state, World world, BlockPos pos, Direction direction) {
		return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
	}

	@Override
	public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof ClockBlockEntity) {
			if (!world.isClient() && player.shouldSkipBlockDrops()) {
				ItemStack itemStack = Items.CLOCK.getDefaultStack();
				itemStack.applyComponentsFrom(blockEntity.createComponentMap());
				ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, itemStack);
				itemEntity.setToDefaultPickupDelay();
				world.spawnEntity(itemEntity);
			}
		}

		return super.onBreak(world, pos, state, player);
	}


	@Override
	protected boolean canPathfindThrough(BlockState state, NavigationType type) {
		return false;
	}
}
