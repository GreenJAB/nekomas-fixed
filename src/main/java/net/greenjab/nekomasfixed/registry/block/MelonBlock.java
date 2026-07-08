package net.greenjab.nekomasfixed.registry.block;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class MelonBlock extends Block {
	boolean glistering;
	public static final BooleanProperty CORNER_1 = BooleanProperty.create("corner_1");
	public static final BooleanProperty CORNER_2 = BooleanProperty.create("corner_2");
	public static final BooleanProperty CORNER_3 = BooleanProperty.create("corner_3");
	public static final BooleanProperty CORNER_4 = BooleanProperty.create("corner_4");
	public static final BooleanProperty CORNER_5 = BooleanProperty.create("corner_5");
	public static final BooleanProperty CORNER_6 = BooleanProperty.create("corner_6");
	public static final BooleanProperty CORNER_7 = BooleanProperty.create("corner_7");
	public static final BooleanProperty CORNER_8 = BooleanProperty.create("corner_8");
	public static final VoxelShape[] CORNER_SHAPES = Util.make(new VoxelShape[8], cornerShapes -> {
		cornerShapes[0] = Shapes.box(0.0, 0.0, 0.0, 0.5, 0.5, 0.5);
		cornerShapes[1] = Shapes.box(0.5, 0.0, 0.0, 1.0, 0.5, 0.5);
		cornerShapes[2] = Shapes.box(0.0, 0.0, 0.5, 0.5, 0.5, 1.0);
		cornerShapes[3] = Shapes.box(0.5, 0.0, 0.5, 1.0, 0.5, 1.0);
		cornerShapes[4] = Shapes.box(0.0, 0.5, 0.0, 0.5, 1.0, 0.5);
		cornerShapes[5] = Shapes.box(0.5, 0.5, 0.0, 1.0, 1.0, 0.5);
		cornerShapes[6] = Shapes.box(0.0, 0.5, 0.5, 0.5, 1.0, 1.0);
		cornerShapes[7] = Shapes.box(0.5, 0.5, 0.5, 1.0, 1.0, 1.0);
	});
	public static final BooleanProperty[] CORNERS = {CORNER_1, CORNER_2, CORNER_3, CORNER_4, CORNER_5, CORNER_6, CORNER_7, CORNER_8};
	public static final VoxelShape[] SHAPES = Util.make(new VoxelShape[256], voxelShapes -> {
		for (int i = 0; i < voxelShapes.length; i++) {
			VoxelShape voxelShape = Shapes.empty();

			for (int j = 0; j < 8; j++) {
				if (oldHasCorner(i, j)) {
					voxelShape = Shapes.or(voxelShape, CORNER_SHAPES[j]);
				}
			}

			voxelShapes[i] = voxelShape.optimize();
		}
	});

	private static boolean oldHasCorner(int flags, int corner) {
		return (flags & createFlag(corner)) != 0;
	}

	public MelonBlock(boolean glistering, Properties settings) {
		super(settings);
		this.glistering = glistering;
		this.registerDefaultState(this.stateDefinition.any().setValue(CORNER_1, true).setValue(CORNER_2, true).setValue(CORNER_3, true).setValue(CORNER_4, true).setValue(CORNER_5, true).setValue(CORNER_6, true).setValue(CORNER_7, true).setValue(CORNER_8, true));
	}

	private static int toInt(BlockState state){
		int Int = 0;
		for (int i = 0;i<8;i++){
			if (state.getValue(CORNERS[i])) Int+= (int) Math.pow(2, i);
		}
		return Int;
	}

	private static int slices(BlockState state){
		int Int = 0;
		for (int i = 0;i<8;i++){
			if (state.getValue(CORNERS[i])) Int++;
		}
		return Int;
	}

	private static boolean hasCorner(BlockState state, int corner) {
		return state.getValue(CORNERS[corner]);
	}

	private static int createFlag(int corner) {
		return 1 << corner;
	}


	private static boolean isFull(BlockState state) {
		return slices(state)==8;
	}

	@Override
	public @NonNull InteractionResult useItemOn(@NonNull ItemStack stack, @NonNull BlockState state, @NonNull Level world, @NonNull BlockPos pos, Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hit) {

		if (!player.getItemInHand(hand).isEmpty()) {
			return InteractionResult.FAIL;
		} else if (!player.getFoodData().needsFood() && !player.isCreative()) {
			return InteractionResult.FAIL;
		} else {
			Vec3 vec3d = hit.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
			int i = getClosestSlice(state, vec3d);
			if (i == -1) {
				return InteractionResult.FAIL;
			} else {
				if (slices(state)==1) {
					world.removeBlock(pos, false);
					world.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
				} else {
					world.setBlockAndUpdate(pos, state.setValue(CORNERS[i], false));
				}

				if (!world.isClientSide()) {
					player.getFoodData().eat(1, 0.1F);
					if (glistering) player.heal(0.5f);
					world.gameEvent(player, GameEvent.EAT, pos);
				}

				return InteractionResult.SUCCESS;
			}
		}
	}

	private static int getClosestSlice(BlockState state, Vec3 pos) {
		double d = Double.MAX_VALUE;
		int j = -1;

		for (int k = 0; k < CORNER_SHAPES.length; k++) {
			if (hasCorner(state, k)) {
				VoxelShape voxelShape = CORNER_SHAPES[k];
				Optional<Vec3> optional = voxelShape.closestPointTo(pos);
				if (optional.isPresent()) {
					double e = (optional.get()).distanceToSqr(pos);
					if (e < d) {
						d = e;
						j = k;
					}
				}
			}
		}

		return j;
	}


	@Override
	protected @NonNull List<ItemStack> getDrops(@NonNull BlockState state, LootParams.@NonNull Builder builder) {
		if (this.drops.isEmpty()) {
			return Collections.emptyList();
		} else {
			LootParams lootWorldContext = builder.withParameter(LootContextParams.BLOCK_STATE, state).create(LootContextParamSets.BLOCK);
			ServerLevel serverWorld = lootWorldContext.getLevel();
			LootTable lootTable = serverWorld.getServer().reloadableRegistries().getLootTable(this.drops.get());
			List<ItemStack> stacks = lootTable.getRandomItems(lootWorldContext);
			int slices = (int) IntStream.range(0, 8).filter(j -> hasCorner(state, j)).count();
			ArrayList<ItemStack> newstacks = new ArrayList<>(List.of());
			if (glistering) {
				stacks.forEach(stack -> {
					if (stack.is(Items.MELON_SLICE) || (stack.is(Items.MELON) && slices != 8))
						newstacks.add(Items.MELON_SLICE.getDefaultInstance().copyWithCount(slices));
					else newstacks.add(stack);
				});
			} else {
				stacks.forEach(stack -> {
					if (stack.is(Items.GLISTERING_MELON_SLICE) || (stack.is(BlockRegistry.GLISTERING_MELON.asItem()) && slices != 8))
						newstacks.add(Items.GLISTERING_MELON_SLICE.getDefaultInstance().copyWithCount(slices));
					else newstacks.add(stack);
				});
			}
			return newstacks;
		}
	}

	@Override
	public @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull CollisionContext context) {
		return SHAPES[toInt(state)];
	}

	@Override
	public boolean useShapeForLightOcclusion(@NonNull BlockState state) {
		return true;
	}

	@Override
	public float getShadeBrightness(@NonNull BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos) {
		return isFull(state) ? 0.2F : 1.0F;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NonNull Builder<Block, BlockState> builder) {
		for (int i = 0;i<8;i++){
			builder.add(CORNERS[i]);
		}
	}

	@Override
	public void animateTick(@NonNull BlockState state, @NonNull Level world, @NonNull BlockPos pos, @NonNull RandomSource random) {
		if (!glistering)return;
		if (world.getRandom().nextInt(2)!=0) return;
		ParticleUtils.spawnParticlesOnBlockFace(world, pos, ParticleTypes.END_ROD, UniformInt.of(1, 1), Direction.getRandom(world.getRandom()), () -> new Vec3(0, 0, 0), 0.55);
	}
}
