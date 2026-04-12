package net.greenjab.nekomasfixed.registry.block;

import net.greenjab.nekomasfixed.registry.block.entity.AbstractHollowLogBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jspecify.annotations.Nullable;

public abstract class AbstractHollowLogBlock extends PillarBlock implements BlockEntityProvider{
    private static final VoxelShape RAYCAST_SHAPE = Block.createCuboidShape(2, 0, 2, 14, 16, 14);
    private static final VoxelShape OUTLINE_X = Block.createCuboidShape(0, 2, 2, 16, 14, 14);
    private static final VoxelShape OUTLINE_Z = Block.createCuboidShape(2, 2, 0, 14, 14, 16);
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
    public ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        System.out.println("Hollow log has been clicked!!!");
        if(!world.isClient() && stack.isOf(Items.WATER_BUCKET) && world.getBlockEntity(pos) instanceof AbstractHollowLogBlockEntity blockEntity){
            if (state.get(PillarBlock.AXIS).isVertical()){
                player.setStackInHand(Hand.MAIN_HAND, Items.BUCKET.getDefaultStack());
                world.setBlockState(pos, state.with(HAS_WATER, true), Block.NOTIFY_ALL);
                player.dropStack((ServerWorld) world, blockEntity.getStoredBlock().getPickStack(world, pos, true));
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
    }

//    @Override
//    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
//        if(!world.isClient() && world.getBlockState(pos.down()).isAir() && world.getBlockEntity(pos) instanceof AbstractHollowLogBlockEntity blockEntity){
//            return blockEntity.getCachedState().with(HAS_WATER, false);
//        }
//        return state;
//    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(HAS_WATER);
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
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
       return null;
    }
}
