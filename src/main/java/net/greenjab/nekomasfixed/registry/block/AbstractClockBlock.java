package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.entity.ClockBlockEntity;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
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
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.block.OrientationHelper;
import net.minecraft.world.block.WireOrientation;
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
			clockBlockEntity.markDirty();
			if (state.isOf(BlockRegistry.CLOCK)){
				if (stack.isOf(Items.BELL)) {
					if (!clockBlockEntity.hasBell()) {
						clockBlockEntity.setBell(true);
						stack.decrement(1);
					}
					return ActionResult.SUCCESS;
				}
				if (stack.isOf(Items.SHEARS)) {
					if (clockBlockEntity.hasBell()) {
						clockBlockEntity.setBell(false);
						stack.damage(1, player);
						ItemEntity itemEntity = new ItemEntity(world, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5 , Items.BELL.getDefaultStack());
						itemEntity.setToDefaultPickupDelay();
						world.spawnEntity(itemEntity);
					}
					return ActionResult.SUCCESS;
				}
				if (clockBlockEntity.hasBell()) {
					int timer = clockBlockEntity.getTimer();
					if (timer < 0) timer = 0;
					timer += player.isSneaking() ? 1200 : 100;
					if (timer > 12000) timer = 12000;
					clockBlockEntity.setTimer(timer);
					return ActionResult.SUCCESS;
				} else {
					clockBlockEntity.setShowsTime(!clockBlockEntity.getShowsTime());
				}
			} else {
				clockBlockEntity.setShowsTime(!clockBlockEntity.getShowsTime());
			}
			return ActionResult.PASS;
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

	@Override
	protected int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
		return state.get(POWERED) ? 15 : 0;
	}

	@Override
	protected int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
		return direction == Direction.UP ? state.getWeakRedstonePower(world, pos, direction) : 0;
	}

	@Override
	protected boolean emitsRedstonePower(BlockState state) {
		return true;
	}

	public void setPower(World world, BlockPos pos,BlockState state,boolean power) {
		state = state.with(AbstractClockBlock.POWERED, power);
		world.setBlockState(pos, state, Block.NOTIFY_ALL);
		updateNeighbors(state, world, pos);
	}
	public void updateNeighbors(BlockState state, World world, BlockPos pos) {
		Direction direction = Direction.DOWN;
		WireOrientation wireOrientation = OrientationHelper.getEmissionOrientation(
				world, direction, Direction.UP
		);
		world.updateNeighborsAlways(pos, this, wireOrientation);
		world.updateNeighborsAlways(pos.offset(direction), this, wireOrientation);
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (state.get(POWERED)) {
			addParticle(state, world, pos, random);
			addParticle(state, world, pos, random);
			addParticle(state, world, pos, random);
		}
	}

	public void addParticle(BlockState state, World world, BlockPos pos, Random random) {
		double d = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.4;
		double e = pos.getY() + 0.4 + (random.nextDouble() - 0.5) * 0.2;
		double f = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.4;
		world.addParticleClient(DustParticleEffect.DEFAULT, d, e, f, 0.0, 0.0, 0.0);
	}

	@Override
	protected boolean hasComparatorOutput(BlockState state) {
		return true;
	}

	@Override
	protected int getComparatorOutput(BlockState state, World world, BlockPos pos, Direction direction) {
		return (int)(((world.getTimeOfDay()+5000)%12000)/1000)+1;
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
