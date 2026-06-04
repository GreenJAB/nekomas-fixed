package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jspecify.annotations.Nullable;

public class BaobabFruitBlock extends Block implements Fertilizable {
    public static final MapCodec<BaobabFruitBlock> CODEC = createCodec(BaobabFruitBlock::new);
    private static final VoxelShape SHAPE_AGE_0 = Block.createCuboidShape(7, 6, 7, 9, 9, 9);
    private static final VoxelShape SHAPE_AGE_1 = Block.createCuboidShape(5, 0, 5, 11, 9, 11);
    public static final IntProperty AGE = IntProperty.of("age", 0, 1);

    public BaobabFruitBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(AGE) == 0 ? SHAPE_AGE_0 : SHAPE_AGE_1;
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < 1;
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.random.nextInt(5) == 0) {
            int rope = 0;
            for (; rope < 8; rope++) {
                if (!world.getBlockState(pos.up(rope + 1)).isOf(BlockRegistry.ROPE)) break;
            }
            if (world.getBlockState(pos.up(rope + 1)).isIn(BlockTags.LEAVES)) {
                if (world.getBlockState(pos.down()).isIn(BlockTags.REPLACEABLE)) {
                    if (rope > 3) {
                        if (world.random.nextInt(9 - rope) == 0)
                            world.setBlockState(pos, state.with(AGE, 1), Block.NOTIFY_LISTENERS);
                        else {
                            world.setBlockState(pos, BlockRegistry.ROPE.getDefaultState().with(RopeBlock.ATTACHED, true), Block.NOTIFY_LISTENERS);
                            world.setBlockState(pos.down(), state.with(AGE, 0), Block.NOTIFY_LISTENERS);
                        }
                    } else {
                        world.setBlockState(pos, BlockRegistry.ROPE.getDefaultState().with(RopeBlock.ATTACHED, true), Block.NOTIFY_LISTENERS);
                        world.setBlockState(pos.down(), state.with(AGE, 0), Block.NOTIFY_LISTENERS);
                    }
                } else world.setBlockState(pos, state.with(AGE, 1), Block.NOTIFY_LISTENERS);
            }
        }
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.up()).isOf(BlockRegistry.ROPE) || world.getBlockState(pos.up()).isIn(BlockTags.LEAVES) ;
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = this.getDefaultState();
        WorldView worldView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        if(canPlaceAt(blockState, worldView, blockPos)) {
            return this.getDefaultState().with(AGE, 1);
        }

        return null;
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return state.get(AGE) < 2;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if(this.getDefaultState().get(AGE) < 1){
            world.setBlockState(pos, state.with(AGE, 1), 2);
        }
    }
}
