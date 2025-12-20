package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.entity.ZombieNautilusBlockEntity;
import net.greenjab.nekomasfixed.registry.other.AnimalComponent;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockStateComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Leashable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ZombieNautilusBlock extends BlockWithEntity {
	public static final MapCodec<ZombieNautilusBlock> CODEC = createCodec(ZombieNautilusBlock::new);
	public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
	public static final BooleanProperty OCCUPIED = Properties.OCCUPIED;

	@Override
	public MapCodec<ZombieNautilusBlock> getCodec() {
		return CODEC;
	}

	public ZombieNautilusBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(OCCUPIED, false).with(FACING, Direction.NORTH));
	}

	@Override
	protected boolean hasComparatorOutput(BlockState state) {
		return true;
	}

	@Override
	protected int getComparatorOutput(BlockState state, World world, BlockPos pos, Direction direction) {
		return state.get(OCCUPIED)?15:0;
	}

	@Override
	public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
		super.afterBreak(world, player, pos, state, blockEntity, tool);
	}

	@Override
	protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		boolean occupied = hasAnimal(world, pos);
		if (world instanceof ServerWorld serverWorld) {
			System.out.println(occupied);
			if (occupied) {
				if (world.getBlockEntity(pos) instanceof ZombieNautilusBlockEntity nautilusBlockEntity) {
					List<Entity > list = nautilusBlockEntity.tryReleaseAnimal(state);
					if (!list.isEmpty()) {
						world.setBlockState(pos, state.with(ZombieNautilusBlock.OCCUPIED, false));
						if (stack.isOf(Items.LEAD)) {
							if (list.get(0) instanceof Leashable leashable) {
								leashable.attachLeash(player, true);
								stack.decrement(1);
							}
						}
					}
				}
			} else {
				List<Entity> list = serverWorld.getOtherEntities(player, player.getBoundingBox().expand(10));
				for (Entity entity : list) {
					if (!player.shouldCancelInteraction()
							&& entity instanceof Leashable leashable
							&& leashable.canBeLeashed()
							&& entity.isAlive()
							&& !(entity instanceof LivingEntity livingEntity && livingEntity.isBaby())
							&& entity.squaredDistanceTo(pos.toCenterPos())<10) {
						List<Leashable> list2 = Leashable.collectLeashablesAround(entity, leashablex -> leashablex.getLeashHolder() == player);
						for (Leashable entity2 : list2) {
							if (entity2 instanceof AnimalEntity animalEntity) {
								if (world.getBlockEntity(pos) instanceof ZombieNautilusBlockEntity nautilusBlockEntity) {
									if (((AnimalEntity) entity2).getBoundingBox().getLengthX()<1 &&
											((AnimalEntity) entity2).getBoundingBox().getLengthY()<1.5f) {
										nautilusBlockEntity.tryEnterNautilus(animalEntity);
										world.setBlockState(pos, state.with(NautilusBlock.OCCUPIED, true));
										return ActionResult.SUCCESS;
									}
								}
							}
						}
					}
				}
			}
		} else {
			return ActionResult.SUCCESS;
		}

		return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
	}

	private boolean hasAnimal(World world, BlockPos pos) {
		return world.getBlockEntity(pos) instanceof ZombieNautilusBlockEntity ZombieNautilusBlockEntity && ZombieNautilusBlockEntity.hasAnimal();
	}


	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState state = this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
		ItemStack stack = ctx.getStack();
		AnimalComponent component = stack.get(OtherRegistry.ANIMAL);
		boolean isOccupied = component != null && !component.animal().isEmpty();
		return state.with(OCCUPIED, isOccupied);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(OCCUPIED, FACING);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new ZombieNautilusBlockEntity(pos, state);
	}

	@Override
	public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		if (world instanceof ServerWorld serverWorld
				&& player.shouldSkipBlockDrops()
				&& serverWorld.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)
				&& world.getBlockEntity(pos) instanceof ZombieNautilusBlockEntity ZombieNautilusBlockEntity) {
			boolean occupied = state.get(OCCUPIED);
			boolean bl = ZombieNautilusBlockEntity.hasAnimal();
			if (bl || occupied) {
				ItemStack itemStack = new ItemStack(this);
				itemStack.applyComponentsFrom(ZombieNautilusBlockEntity.createComponentMap());
				itemStack.set(DataComponentTypes.BLOCK_STATE, BlockStateComponent.DEFAULT.with(OCCUPIED, occupied));
				ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
				itemEntity.setToDefaultPickupDelay();
				world.spawnEntity(itemEntity);
			}
		}

		return super.onBreak(world, pos, state, player);
	}


	@Override
	protected ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean includeData) {
		ItemStack itemStack = super.getPickStack(world, pos, state, includeData);
		if (includeData) {
			itemStack.set(DataComponentTypes.BLOCK_STATE, BlockStateComponent.DEFAULT.with(OCCUPIED, state.get(OCCUPIED)));
		}

		return itemStack;
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}
}