package net.greenjab.nekomasfixed.registry.block;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.ParticleUtil;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class MelonBlock extends Block {
	boolean glistering;
	public static final BooleanProperty CORNER_1 = BooleanProperty.of("corner_1");
	public static final BooleanProperty CORNER_2 = BooleanProperty.of("corner_2");
	public static final BooleanProperty CORNER_3 = BooleanProperty.of("corner_3");
	public static final BooleanProperty CORNER_4 = BooleanProperty.of("corner_4");
	public static final BooleanProperty CORNER_5 = BooleanProperty.of("corner_5");
	public static final BooleanProperty CORNER_6 = BooleanProperty.of("corner_6");
	public static final BooleanProperty CORNER_7 = BooleanProperty.of("corner_7");
	public static final BooleanProperty CORNER_8 = BooleanProperty.of("corner_8");
	public static final VoxelShape[] CORNER_SHAPES = Util.make(new VoxelShape[8], /* method_50861 */ cornerShapes -> {
		cornerShapes[0] = VoxelShapes.cuboid(0.0, 0.0, 0.0, 0.5, 0.5, 0.5);
		cornerShapes[1] = VoxelShapes.cuboid(0.5, 0.0, 0.0, 1.0, 0.5, 0.5);
		cornerShapes[2] = VoxelShapes.cuboid(0.0, 0.0, 0.5, 0.5, 0.5, 1.0);
		cornerShapes[3] = VoxelShapes.cuboid(0.5, 0.0, 0.5, 1.0, 0.5, 1.0);
		cornerShapes[4] = VoxelShapes.cuboid(0.0, 0.5, 0.0, 0.5, 1.0, 0.5);
		cornerShapes[5] = VoxelShapes.cuboid(0.5, 0.5, 0.0, 1.0, 1.0, 0.5);
		cornerShapes[6] = VoxelShapes.cuboid(0.0, 0.5, 0.5, 0.5, 1.0, 1.0);
		cornerShapes[7] = VoxelShapes.cuboid(0.5, 0.5, 0.5, 1.0, 1.0, 1.0);
	});
	public static final BooleanProperty[] CORNERS = {CORNER_1, CORNER_2, CORNER_3, CORNER_4, CORNER_5, CORNER_6, CORNER_7, CORNER_8};
	public static final VoxelShape[] SHAPES = Util.make(new VoxelShape[256], /* method_50858 */ voxelShapes -> {
		for (int i = 0; i < voxelShapes.length; i++) {
			VoxelShape voxelShape = VoxelShapes.empty();

			for (int j = 0; j < 8; j++) {
				if (oldHasCorner(i, j)) {
					voxelShape = VoxelShapes.union(voxelShape, CORNER_SHAPES[j]);
				}
			}

			voxelShapes[i] = voxelShape.simplify();
		}
	});

	private static boolean oldHasCorner(int flags, int corner) {
		return (flags & createFlag(corner)) != 0;
	}

	public MelonBlock(boolean glistering, Settings settings) {
		super(settings);
		this.glistering = glistering;
		this.setDefaultState(this.stateManager.getDefaultState().with(CORNER_1, true).with(CORNER_2, true).with(CORNER_3, true).with(CORNER_4, true).with(CORNER_5, true).with(CORNER_6, true).with(CORNER_7, true).with(CORNER_8, true));
	}

	private static int toInt(BlockState state){
		int Int = 0;
		for (int i = 0;i<8;i++){
			if (state.get(CORNERS[i])) Int+= (int) Math.pow(2, i);
		}
		return Int;
	}

	private static int slices(BlockState state){
		int Int = 0;
		for (int i = 0;i<8;i++){
			if (state.get(CORNERS[i])) Int++;
		}
		return Int;
	}

	private static boolean hasCorner(BlockState state, int corner) {
		return state.get(CORNERS[corner]);
	}

	private static int createFlag(int corner) {
		return 1 << corner;
	}


	private static boolean isFull(BlockState state) {
		return slices(state)==8;
	}

	@Override
	public ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!player.getStackInHand(hand).isEmpty()) {
			return ActionResult.FAIL;
		} else if (!player.getHungerManager().isNotFull() && !player.isCreative()) {
			return ActionResult.FAIL;
		} else {
			Vec3d vec3d = hit.getPos().subtract(pos.getX(), pos.getY(), pos.getZ());
			int i = getClosestSlice(state, vec3d);
			if (i == -1) {
				return ActionResult.FAIL;
			} else {
				if (slices(state)==1) {
					world.removeBlock(pos, false);
					world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
				} else {
					world.setBlockState(pos, state.with(CORNERS[i], false));
				}

				if (!world.isClient()) {
					//world.syncWorldEvent(2010, pos, i);
					player.getHungerManager().add(1, 0.1F);
					if (glistering) player.heal(0.5f);
					world.emitGameEvent(player, GameEvent.EAT, pos);
				}

				return ActionResult.SUCCESS;
			}
		}
	}

	private static int getClosestSlice(BlockState state, Vec3d pos) {
		double d = Double.MAX_VALUE;
		int j = -1;

		for (int k = 0; k < CORNER_SHAPES.length; k++) {
			if (hasCorner(state, k)) {
				VoxelShape voxelShape = CORNER_SHAPES[k];
				Optional<Vec3d> optional = voxelShape.getClosestPointTo(pos);
				if (optional.isPresent()) {
					double e = (optional.get()).squaredDistanceTo(pos);
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
	protected List<ItemStack> getDroppedStacks(BlockState state, LootWorldContext.Builder builder) {
		System.out.println(state);
		if (this.lootTableKey.isEmpty()) {
			return Collections.emptyList();
		} else {
			LootWorldContext lootWorldContext = builder.add(LootContextParameters.BLOCK_STATE, state).build(LootContextTypes.BLOCK);
			ServerWorld serverWorld = lootWorldContext.getWorld();
			LootTable lootTable = serverWorld.getServer().getReloadableRegistries().getLootTable(this.lootTableKey.get());
			List<ItemStack> stacks = lootTable.generateLoot(lootWorldContext);
			int slices = (int) IntStream.range(0, 8).filter(j -> hasCorner(state, j)).count();
			ArrayList<ItemStack> newstacks = new ArrayList<>(List.of());
			if (glistering) {
				stacks.forEach(stack -> {
					if (stack.isOf(Items.MELON_SLICE) || (stack.isOf(Items.MELON) && slices != 8))
						newstacks.add(Items.MELON_SLICE.getDefaultStack().copyWithCount(slices));
					else newstacks.add(stack);
				});
			} else {
				stacks.forEach(stack -> {
					if (stack.isOf(Items.GLISTERING_MELON_SLICE) || (stack.isOf(BlockRegistry.GLISTERING_MELON.asItem()) && slices != 8))
						newstacks.add(Items.GLISTERING_MELON_SLICE.getDefaultStack().copyWithCount(slices));
					else newstacks.add(stack);
				});
			}
			return newstacks;
		}
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPES[toInt(state)];
	}

	@Override
	public boolean hasSidedTransparency(BlockState state) {
		return true;
	}

	@Override
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
		return isFull(state) ? 0.2F : 1.0F;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		for (int i = 0;i<8;i++){
			builder.add(CORNERS[i]);
		}
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (!glistering)return;
		if (world.random.nextInt(2)!=0) return;
		ParticleUtil.spawnParticles(world, pos, ParticleTypes.END_ROD, UniformIntProvider.create(1, 1), Direction.random(world.random), () -> new Vec3d(0, 0, 0), 0.55);
		//world.addParticleClient(ParticleTypes.END_ROD, d, e, f, 0.0, 0.0, 0.0);
	}
}
