package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

public class RopeBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final MapCodec<RopeBlock> CODEC = simpleCodec(RopeBlock::new);
    private static final VoxelShape SHAPE = Block.column(14.0, 0.0, 16.0);

    public RopeBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(ATTACHED, false).setValue(WATERLOGGED, false));
    }

    @Override
    public @NonNull MapCodec<RopeBlock> codec() {
        return CODEC;
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ATTACHED, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Level world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        boolean connected = world.getBlockState(pos.above()).is(BlockRegistry.ROPE)|| world.getBlockState(pos.above()).is(BlockTags.LEAVES);
        return this.defaultBlockState()
                .setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER)
                .setValue(ATTACHED, connected);
    }

    protected @NonNull BlockState updateShape(BlockState state, @NonNull LevelReader world, @NonNull ScheduledTickAccess tickView, @NonNull BlockPos pos, @NonNull Direction direction, @NonNull BlockPos neighborPos, @NonNull BlockState neighborState, @NonNull RandomSource random) {
        if (state.getValue(WATERLOGGED)) {
            tickView.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        if (!state.canSurvive(world, pos)) tickView.scheduleTick(pos, this, 1);
        return state;
    }

    @Override
    protected boolean canSurvive(@NonNull BlockState state, LevelReader world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.above());
        return blockState.is(BlockRegistry.ROPE) || blockState.is(BlockTags.LEAVES) || blockState.isFaceSturdy(world, pos, Direction.DOWN);
    }

    @Override
    protected void tick(BlockState state, @NonNull ServerLevel world, @NonNull BlockPos pos, @NonNull RandomSource random) {
        if (!state.canSurvive(world, pos)) {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    protected @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected @NonNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

}
