package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.registry.block.entity.NautilusBlockEntity;
import net.greenjab.nekomasfixed.registry.block.enums.NautilusBlockType;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class NautilusBlock extends BaseEntityBlock {
	public static final MapCodec<NautilusBlock> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					NautilusBlockType.CODEC.fieldOf("nautilus_block_type").forGetter(NautilusBlock::getNautilusBlockType),
					propertiesCodec()
			).apply(instance, NautilusBlock::new)
	);
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;
	private final NautilusBlockType nautilusBlockType;

	@Override
	public @NonNull MapCodec<NautilusBlock> codec() {
		return CODEC;
	}

	public NautilusBlock(NautilusBlockType nautilusBlockType, Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(OCCUPIED, false).setValue(FACING, Direction.NORTH));
		this.nautilusBlockType = nautilusBlockType;
	}

	@Override
	protected boolean hasAnalogOutputSignal(@NonNull BlockState state) {
		return true;
	}

	@Override
	protected int getAnalogOutputSignal(BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Direction direction) {
		return state.getValue(OCCUPIED)?15:0;
	}

	@Override
	public void playerDestroy(@NonNull Level level, @NonNull Player player, @NonNull BlockPos pos, @NonNull BlockState state, @Nullable BlockEntity blockEntity, @NonNull ItemStack tool) {
		super.playerDestroy(level, player, pos, state, blockEntity, tool);
	}

	@Override
	protected @NonNull InteractionResult useItemOn(@NonNull ItemStack stack, @NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hit) {
		boolean occupied = hasAnimal(level, pos);
		if (level instanceof ServerLevel serverLevel) {
			if (occupied) {
				if (level.getBlockEntity(pos) instanceof NautilusBlockEntity nautilusBlockEntity) {
					List<Entity > list = nautilusBlockEntity.tryReleaseAnimal(state);
					if (!list.isEmpty()) {
						level.setBlockAndUpdate(pos, state.setValue(NautilusBlock.OCCUPIED, false));
						if (stack.is(Items.LEAD)) {
							if (list.getFirst() instanceof Leashable leashable) {
								leashable.setLeashedTo(player, true);
								stack.shrink(1);
							}
						}
					}
				}
			} else {
				List<Entity> list = serverLevel.getEntities(player, player.getBoundingBox().inflate(10));
				for (Entity entity : list) {
					if (!player.isSecondaryUseActive()
							&& entity instanceof Leashable leashable
							&& leashable.canBeLeashed()
							&& entity.isAlive()
							&& entity.distanceToSqr(Vec3.atCenterOf(pos))<10) {
						List<Leashable> list2 = Leashable.leashableInArea(entity, leashablex -> leashablex.getLeashHolder() == player);
						for (Leashable entity2 : list2) {
							if (entity2 instanceof Animal animalEntity) {
								if (level.getBlockEntity(pos) instanceof NautilusBlockEntity nautilusBlockEntity) {
									if (animalEntity.getBoundingBox().getXsize()<1 &&
										animalEntity.getBoundingBox().getYsize()<1.5f) {
										nautilusBlockEntity.tryEnterNautilus(animalEntity);
										level.setBlockAndUpdate(pos, state.setValue(NautilusBlock.OCCUPIED, true));
										return InteractionResult.SUCCESS;
									}
								}
							}
						}
					}
				}
			}
		} else {
			return InteractionResult.SUCCESS;
		}

		return super.useItemOn(stack, state, level, pos, player, hand, hit);
	}

	private boolean hasAnimal(Level level, BlockPos pos) {
		return level.getBlockEntity(pos) instanceof NautilusBlockEntity NautilusBlockEntity && NautilusBlockEntity.hasAnimal();
	}


	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(OCCUPIED, FACING);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
		return new NautilusBlockEntity(pos, state);
	}

	@Override
	public @NonNull BlockState playerWillDestroy(@NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState state, @NonNull Player player) {
		if (level instanceof ServerLevel serverLevel
			&& player.preventsBlockDrops()
			&& serverLevel.getGameRules().get(GameRules.BLOCK_DROPS)
			&& level.getBlockEntity(pos) instanceof NautilusBlockEntity NautilusBlockEntity) {
			boolean occupied = state.getValue(OCCUPIED);
			boolean bl = NautilusBlockEntity.hasAnimal();
			if (bl || occupied) {
				ItemStack itemStack = getItemStack(this.getNautilusBlockType());
				itemStack.applyComponents(NautilusBlockEntity.collectComponents());
				itemStack.set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(OCCUPIED, occupied));
				ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), itemStack);
				itemEntity.setDefaultPickUpDelay();
				level.addFreshEntity(itemEntity);
			}
		}

		return super.playerWillDestroy(level, pos, state, player);
	}
	
	public static ItemStack getItemStack(@Nullable NautilusBlockType nautilusBlockType) {
		return new ItemStack(get(nautilusBlockType));
	}

	public static Block get(@Nullable NautilusBlockType nautilusBlockType) {
		if (nautilusBlockType == null) {
			return BlockRegistry.NAUTILUS_BLOCK;
		} else {
			return switch (nautilusBlockType) {
				case REGULAR -> BlockRegistry.NAUTILUS_BLOCK;
				case ZOMBIE -> BlockRegistry.ZOMBIE_NAUTILUS_BLOCK;
				case CORAL -> BlockRegistry.CORAL_NAUTILUS_BLOCK;
			};
		}
	}

	@Override
	protected @NonNull ItemStack getCloneItemStack(@NonNull LevelReader level, @NonNull BlockPos pos, @NonNull BlockState state, boolean includeData) {
		ItemStack itemStack = super.getCloneItemStack(level, pos, state, includeData);
		if (includeData) {
			itemStack.set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(OCCUPIED, state.getValue(OCCUPIED)));
		}

		return itemStack;
	}

	@Override
	public @NonNull BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	public @NonNull BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	public NautilusBlockType getNautilusBlockType() {
		return this.nautilusBlockType;
	}
}
