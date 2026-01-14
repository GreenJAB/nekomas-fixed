package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.entity.ClockBlockEntity;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jspecify.annotations.Nullable;

public abstract class AbstractClockBlock extends BlockWithEntity {
	public static final BooleanProperty POWERED = Properties.POWERED;

	@Override
	public abstract MapCodec<? extends AbstractClockBlock> getCodec();

	public AbstractClockBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(POWERED, false));
	}



	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(POWERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
	}



	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(POWERED);
	}


	@Override
	protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.getBlockEntity(pos) instanceof ClockBlockEntity clockBlockEntity && !hand.equals(Hand.OFF_HAND)) {
			int timer = clockBlockEntity.getTimer();
			if (timer <0) timer = 0;
			timer += player.isSneaking()?1200:10;//100
			if (timer > 12000) timer = 12000;
			clockBlockEntity.setTimer(timer);
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;

	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new ClockBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return validateTicker(type, BlockEntityTypeRegistry.CLOCK_BLOCK_ENTITY, world.isClient()?ClockBlockEntity::clientTick:ClockBlockEntity::tick);
	}

	/*@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return world.isClient() ? validateTicker(type, BlockEntityTypeRegistry.CLOCK_BLOCK_ENTITY, ClockBlockEntity::clientTick) : null;
	}*/

	/*@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return validateTicker(world, type, BlockEntityTypeRegistry.CLOCK_BLOCK_ENTITY);
	}

	@Nullable
	protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(
			World world, BlockEntityType<T> givenType, BlockEntityType<? extends ClockBlockEntity> expectedType
	) {
		return world instanceof ServerWorld serverWorld
				? validateTicker(
				givenType, expectedType, (worldx, pos, state, blockEntity) -> ClockBlockEntity.tick(serverWorld, pos, state, blockEntity)
		)
				: null;
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

	@Override
	protected BlockState getStateForNeighborUpdate(
			BlockState state,
			WorldView world,
			ScheduledTickView tickView,
			BlockPos pos,
			Direction direction,
			BlockPos neighborPos,
			BlockState neighborState,
			Random random
	) {
		return direction == Direction.DOWN && !this.canPlaceAt(state, world, pos)
				? Blocks.AIR.getDefaultState()
				: super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
	}

	@Override
	protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return sideCoversSmallSquare(world, pos.down(), Direction.UP);
	}
}
