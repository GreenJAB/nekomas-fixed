package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.registry.block.entity.HollowLogBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jspecify.annotations.Nullable;

public class HollowLogBlock extends BlockWithEntity implements BlockEntityProvider, Waterloggable{
    private static final VoxelShape RAYCAST_SHAPE = Block.createCuboidShape(2, 0, 2, 14, 16, 14);
    private static final VoxelShape OUTLINE_X = Block.createCuboidShape(0, 2, 2, 16, 14, 14);
    private static final VoxelShape OUTLINE_Z = Block.createCuboidShape(2, 2, 0, 14, 14, 16);
    public static final IntProperty LIGHT_LEVEL = IntProperty.of("light_level", 0, 15);
    public static final VoxelShape OUTLINE_Y = Util.make(() -> VoxelShapes.combineAndSimplify(
            VoxelShapes.fullCube(),
            VoxelShapes.union(Block.createColumnShape(16.0F, 8.0F, 0.0F, 0.0F),
                    Block.createColumnShape(8.0F, 16.0F, 0.0F, 0.0F),
                    Block.createColumnShape(12.0F, 0.0F, 0.0F), RAYCAST_SHAPE),
            BooleanBiFunction.ONLY_FIRST));
    public static final EnumProperty<Direction.Axis> AXIS = Properties.AXIS;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public HollowLogBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(AXIS, Direction.Axis.Y));
    }

    public static final MapCodec<HollowLogBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    createSettingsCodec()
            ).apply(instance, HollowLogBlock::new)
    );
    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
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
        if (state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return changeRotation(state, rotation);
    }
    public static BlockState changeRotation(BlockState state, BlockRotation rotation) {
        return switch (rotation) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (state.get(AXIS)) {
                case X -> state.with(AXIS, Direction.Axis.Z);
                case Z -> state.with(AXIS, Direction.Axis.X);
                default -> state;
            };
            default -> state;
        };
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(AXIS, WATERLOGGED, LIGHT_LEVEL);
    }
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(AXIS, ctx.getSide().getAxis()).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction.Axis axis = state.get(AXIS);
        return switch (axis) {
            case X -> OUTLINE_X;
            case Y -> OUTLINE_Y;
            case Z -> OUTLINE_Z;
        };
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return RAYCAST_SHAPE;
    }

    /*@Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        if(state.get(WATERLOGGED) && world.getBlockState(pos.down()).isAir()){
            if(state.get(AXIS).isVertical() ){
                world.setBlockState(pos, state.with(WATERLOGGED, false));
                world.setBlockState(pos.down(), Blocks.WATER.getDefaultState());
            }
            else if(state.get(AXIS).isHorizontal()){
                world.setBlockState(pos, state.with(WATERLOGGED, false));
                world.setBlockState(pos.offset(state.get(AXIS).getPositiveDirection()), Blocks.WATER.getDefaultState());
                world.setBlockState(pos.offset(state.get(AXIS).getNegativeDirection()), Blocks.WATER.getDefaultState());
            }
        }
    }*/

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
       return new HollowLogBlockEntity(pos, state);
    }
}
