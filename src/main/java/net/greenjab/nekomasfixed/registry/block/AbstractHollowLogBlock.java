package net.greenjab.nekomasfixed.registry.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import org.jspecify.annotations.Nullable;

public abstract class AbstractHollowLogBlock extends PillarBlock implements BlockEntityProvider, Waterloggable{
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

    public static BooleanProperty HAS_WATER = BooleanProperty.of("has_water");

    public AbstractHollowLogBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(HAS_WATER, false));
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(HAS_WATER, LIGHT_LEVEL);
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

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        if(state.get(HAS_WATER) && world.getBlockState(pos.down()).isAir()){
            if(state.get(AXIS).isVertical() ){
                world.setBlockState(pos, state.with(HAS_WATER, false));
                world.setBlockState(pos.down(), Blocks.WATER.getDefaultState());
            }
            else if(state.get(AXIS).isHorizontal()){
                world.setBlockState(pos, state.with(HAS_WATER, false));
                world.setBlockState(pos.offset(state.get(AXIS).getPositiveDirection()), Blocks.WATER.getDefaultState());
                world.setBlockState(pos.offset(state.get(AXIS).getNegativeDirection()), Blocks.WATER.getDefaultState());
            }
        }
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
       return null;
    }
}
