package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
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

public class BoababFruitBlock extends Block implements Fertilizable {
    public static final MapCodec<BoababFruitBlock> CODEC = createCodec(BoababFruitBlock::new);
    private static final VoxelShape RAYCAST_SHAPE_AGE0 = Block.createCuboidShape(5, 0, 5, 11, 9, 11);
    private static final VoxelShape OUTLINE_SHAPE_AGE0 = Block.createCuboidShape(5, 0, 5, 11, 9, 11);
    private static final VoxelShape RAYCAST_SHAPE_AGE1 = Block.createCuboidShape(6, 6, 6, 10, 10, 10);
    private static final VoxelShape OUTLINE_SHAPE_AGE1 = Block.createCuboidShape(6, 6, 6, 10, 16, 10);
    public static final int MAX_AGE = 1;
    public static final IntProperty AGE = IntProperty.of("age", 0, 1);

    public BoababFruitBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.getDefaultState().get(AGE, 1) == 0 ? OUTLINE_SHAPE_AGE0 : OUTLINE_SHAPE_AGE1;
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return this.getDefaultState().get(AGE, 1) == 0 ? RAYCAST_SHAPE_AGE0 : RAYCAST_SHAPE_AGE1;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return !world.getBlockState(pos.up()).isAir() ;
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
        if(this.getDefaultState().get(AGE) < 2){
            world.setBlockState(pos, state.with(AGE, state.get(AGE) + 1), 2);
        }
    }
}
