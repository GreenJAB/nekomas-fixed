package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.block.entity.ClamBlockEntity;
import net.greenjab.nekomasfixed.registry.block.enums.ClamType;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.block.WireOrientation;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class ClamBlock extends BlockWithEntity implements Waterloggable {
	public static final MapCodec<ClamBlock> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
				ClamType.CODEC.fieldOf("clam_type").forGetter(ClamBlock::getClamType),
				createSettingsCodec()
			).apply(instance, ClamBlock::new)
	);
	public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	public static final BooleanProperty OPEN = Properties.OPEN;
	public static final BooleanProperty POWERED = Properties.POWERED;
	public static final Map<Direction, VoxelShape> SHAPES_BY_DIRECTION;

	public static final Identifier CONTENTS_DYNAMIC_DROP_ID = NekomasFixed.id("clam_contents");
	private final ClamType clamType;

	@Override
	public MapCodec<? extends ClamBlock> getCodec() {
		return CODEC;
	}

	public ClamBlock(ClamType clamType, Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false).with(OPEN, false).with(POWERED, false));
		this.clamType = clamType;
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
		if ((Boolean)state.get(WATERLOGGED)) {
			tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
		return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
	}

	private void tryLaunch(BlockState state, World world, BlockPos pos) {
		boolean wasPowered = state.get(POWERED);
		boolean isPowered = world.isReceivingRedstonePower(pos);
		if (wasPowered != isPowered) {
			if (isPowered && !state.get(OPEN)) {
				List<Entity> entities = world.getOtherEntities(null, new Box(pos));
				for (Entity entity : entities) {
					if (entity instanceof LivingEntity || entity instanceof ItemEntity) {
						float power = world.getReceivedRedstonePower(pos);
						power = (float) (Math.sqrt(power) / 4.0f);
						float dirx = -state.get(ClamBlock.FACING).getOffsetX();
						float dirz = -state.get(ClamBlock.FACING).getOffsetZ();
						if (entity instanceof ItemEntity) {
							dirx*=0.5f;
							dirz*=0.5f;
						}
						entity.setVelocity(power * dirx, power, power * dirz);
						entity.velocityDirty = true;
						entity.velocityModified = true;
					}
				}
			}
			if (!isPowered) {
				//world.scheduleBlockTick(pos, this, 4);
				//world.setBlockState(pos, state.with(OPEN, false), Block.NOTIFY_LISTENERS);
				world.setBlockState(pos, state.with(POWERED, false).with(OPEN, false), Block.NOTIFY_LISTENERS);
			} else {
				world.setBlockState(pos, state.with(POWERED, true).with(OPEN, true), Block.NOTIFY_LISTENERS);
			}
		}
	}

	/*@Override
	protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (state.get(POWERED)) {
			world.setBlockState(pos, state.with(POWERED, false), Block.NOTIFY_LISTENERS);
			tryLaunch(state, world, pos);
		}
	}*/

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		if (!world.isClient()) {
			tryLaunch(state, world, pos);
		}
	}

	@Override
	protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
		if (!world.isClient()) {
			tryLaunch(state, world, pos);
		}
	}

	@Override
	protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		if (!oldState.isOf(state.getBlock())) {
			if (!world.isClient() && world.getBlockEntity(pos) == null) {
				tryLaunch(state, world, pos);
			}
		}
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPES_BY_DIRECTION.get((state.get(FACING)));
	}


	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		Direction direction = ctx.getHorizontalPlayerFacing().getOpposite();
		FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());

		return this.getDefaultState().with(FACING, direction).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER).with(OPEN, false).with(POWERED, false);
	}

	@Override
	protected FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}


	@Override
	protected void onStateReplaced(BlockState state, ServerWorld world, BlockPos pos, boolean moved) {
		ItemScatterer.onStateReplaced(state, world, pos);
	}

	@Override
	protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.getBlockEntity(pos) instanceof ClamBlockEntity clamBlockEntity && !hand.equals(Hand.OFF_HAND)) {
			if (world.isClient()) {
				if (!state.get(OPEN)&&state.get(WATERLOGGED)) {
					world.addParticleClient(ParticleTypes.BUBBLE, pos.getX()+0.5, pos.getY()+0.3, pos.getZ()+0.5, 0.0, 1.0, 0.0);
					world.addParticleClient(ParticleTypes.BUBBLE, pos.getX()+0.4, pos.getY()+0.3, pos.getZ()+0.4, 0.0, 1.0, 0.0);
					world.addParticleClient(ParticleTypes.BUBBLE, pos.getX()+0.6, pos.getY()+0.3, pos.getZ()+0.6, 0.0, 1.0, 0.0);
				}
				return ActionResult.SUCCESS;
			} else {
				if (!(Boolean)state.get(OPEN) || player.isSneaking()) {
					BlockState blockState = state.cycle(OPEN);
					world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
					world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockState));
					if (state.get(OPEN)&&state.get(WATERLOGGED)) {
						//((ServerWorld)world).spawnParticles(ParticleTypes.BUBBLE, true, true, pos.getX(), pos.getY(), pos.getZ(), 5, 0.0, 0.0, 0.0, 0.0);
					}
					return ActionResult.SUCCESS;
				}
				PlayerInventory playerInventory = player.getInventory();
					boolean bl = swapSingleStack(stack, player, clamBlockEntity, playerInventory);
					if (bl) {
						this.playSound(world, pos, stack.isEmpty() ? SoundEvents.BLOCK_SHELF_TAKE_ITEM : SoundEvents.BLOCK_SHELF_SINGLE_SWAP);
					} else {
						if (stack.isEmpty()) {
							BlockState blockState = state.cycle(OPEN);
							world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
							world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockState));
							return ActionResult.SUCCESS;
						}

						this.playSound(world, pos, SoundEvents.BLOCK_SHELF_PLACE_ITEM);
					}
					return ActionResult.SUCCESS.withNewHandStack(stack);
			}
		} else {
			return ActionResult.PASS;
		}
	}
	private static boolean swapSingleStack(ItemStack stack, PlayerEntity player, ClamBlockEntity clamBlockEntity, PlayerInventory playerInventory) {
		if (stack.isIn(ItemTags.SHULKER_BOXES)) return false;
		ItemStack itemStack = clamBlockEntity.swapStackNoMarkDirty(0, stack);
		ItemStack itemStack2 = player.isInCreativeMode() && itemStack.isEmpty() ? stack.copy() : itemStack;
		playerInventory.setStack(playerInventory.getSelectedSlot(), itemStack2);
		playerInventory.markDirty();
		clamBlockEntity.markDirty(GameEvent.ITEM_INTERACT_FINISH);
		return !itemStack.isEmpty();
	}
	private void playSound(WorldAccess world, BlockPos pos, SoundEvent sound) {
		world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (!world.isClient()) {
			BlockState blockState = state.cycle(OPEN);
			world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
			world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockState));
		}
		return ActionResult.SUCCESS;
	}

	public static PropertyRetriever<ClamBlockEntity, Float2FloatFunction> getAnimationProgressRetriever(LidOpenable progress) {
		return new PropertyRetriever<ClamBlockEntity, Float2FloatFunction>() {

			public Float2FloatFunction getFrom(ClamBlockEntity ClamBlockEntity) {
				return ClamBlockEntity::getAnimationProgress;
			}

			public Float2FloatFunction getFallback() {
				return progress::getAnimationProgress;
			}
		};
	}

	public interface PropertyRetriever<S, T> {
		T getFrom(S single);
		T getFallback();
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new ClamBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return world.isClient() ? validateTicker(type, BlockEntityTypeRegistry.Clam_Block_Entity, ClamBlockEntity::clientTick) : null;
	}

	@Override
	protected boolean hasRandomTicks(BlockState state) {
		return state.get(WATERLOGGED);
	}

	@Override
	protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof ClamBlockEntity clamBlockEntity) {
			ItemStack item = clamBlockEntity.getHeldStacks().get(0);
			BlockState below = world.getBlockState(pos.down());
			if (below.isOf(Blocks.SAND) || below.isOf(Blocks.GRAVEL) || below.isOf(Blocks.DIRT)) {
				if (state.get(OPEN)) {
					if (item.isEmpty()) {
						clamBlockEntity.setHeldStack(below.getBlock().asItem().getDefaultStack());
					} else {
						if (item.isOf(below.getBlock().asItem())) {
							clamBlockEntity.setHeldStack(item.copyWithCount(Math.min(item.getCount() + 1, item.getMaxCount())));
						}
					}
					if (!state.get(POWERED) && random.nextInt(Math.max(64 - item.getCount(),1)) < 4) {
						BlockState blockState = state.cycle(OPEN);
						world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
					}
				} else {
					if (item.isOf(Items.SAND) || item.isOf(Items.GRAVEL) || item.isOf(Items.DIRT)) {
						clamBlockEntity.setHeldStack(item.copyWithCount(item.getCount() - 1));
						if (random.nextInt(16) == 0) {
							Identifier lootTableId = NekomasFixed.id("gameplay/clam");
							LootTable lootTable = world.getServer()
									.getReloadableRegistries()
									.getLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, lootTableId));

							LootWorldContext lootContextParameterSet = (new LootWorldContext.Builder(world)).add(LootContextParameters.ORIGIN, pos.toCenterPos()).add(LootContextParameters.TOOL, null).add(LootContextParameters.THIS_ENTITY, null).luck(getLuck(this.getClamType())).build(LootContextTypes.FISHING);

							ObjectArrayList<ItemStack> loots = lootTable.generateLoot(lootContextParameterSet);
							if (!loots.isEmpty()) {
								ItemStack itemStack = clamBlockEntity.getHeldStacks().get(0);
								ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, itemStack);
								itemEntity.setToDefaultPickupDelay();
								world.spawnEntity(itemEntity);

								clamBlockEntity.setHeldStack(loots.get(0));
							}
						}
					}
					if (!state.get(POWERED) && random.nextInt(item.getCount() + 1) < 4) {
						BlockState blockState = state.cycle(OPEN);
						world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
					}
				}
			}
		}
	}

	@Override
	protected boolean hasComparatorOutput(BlockState state) {
		return true;
	}

	@Override
	protected int getComparatorOutput(BlockState state, World world, BlockPos pos, Direction direction) {
		return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
	}

	@Override
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED, OPEN, POWERED);
	}

	@Override
	public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof ClamBlockEntity clamBlockEntity) {
			if (!world.isClient() && player.shouldSkipBlockDrops() && !clamBlockEntity.isEmpty()) {
				ItemStack itemStack = getItemStack(this.getClamType());
				itemStack.applyComponentsFrom(blockEntity.createComponentMap());
				ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, itemStack);
				itemEntity.setToDefaultPickupDelay();
				world.spawnEntity(itemEntity);
			}else {
				clamBlockEntity.generateLoot(player);
			}
		}

		return super.onBreak(world, pos, state, player);
	}

	@Override
	protected List<ItemStack> getDroppedStacks(BlockState state, LootWorldContext.Builder builder) {
		BlockEntity blockEntity = builder.getOptional(LootContextParameters.BLOCK_ENTITY);
		if (blockEntity instanceof ClamBlockEntity clamBlockEntity) {
			builder = builder.addDynamicDrop(CONTENTS_DYNAMIC_DROP_ID, lootConsumer -> {
				for (int i = 0; i < clamBlockEntity.size(); i++) {
					lootConsumer.accept(clamBlockEntity.getStack(i));
				}
			});
		}


		return super.getDroppedStacks(state, builder);
	}

	public static ItemStack getItemStack(@Nullable ClamType clamType) {
		return new ItemStack(get(clamType));
	}

	public static Block get(@Nullable ClamType clamType) {
		if (clamType == null) {
			return BlockRegistry.CLAM;
		} else {
			return switch (clamType) {
                case REGULAR -> BlockRegistry.CLAM;
                case BLUE -> BlockRegistry.CLAM_BLUE;
				case PINK -> BlockRegistry.CLAM_PINK;
				case PURPLE -> BlockRegistry.CLAM_PURPLE;
			};
		}
	}

	public static int getLuck(@Nullable ClamType clamType) {
		if (clamType == null) {
			return 0;
		} else {
			return switch (clamType) {
				case REGULAR -> 0;
				case BLUE -> 1;
				case PINK -> 2;
				case PURPLE -> 3;
			};
		}
	}

	@Override
	protected boolean canPathfindThrough(BlockState state, NavigationType type) {
		return false;
	}

	public ClamType getClamType() {
		return this.clamType;
	}

	static {
		SHAPES_BY_DIRECTION = VoxelShapes.createHorizontalFacingShapeMap(Block.createCuboidShape(1.0, 0, 0, 15.0, 4.0, 15.0));
	}
}
