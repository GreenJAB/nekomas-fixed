package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.enums.GoatHornTorchType;
import net.greenjab.nekomasfixed.registry.block.enums.GoatHornType;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
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

import static net.minecraft.util.math.Direction.NORTH;

public class GoatHornBlock extends HorizontalFacingBlock implements Waterloggable {
    public static final MapCodec<GoatHornBlock> CODEC = createCodec(GoatHornBlock::new);
    public static final Property<Boolean> WATERLOGGED = Properties.WATERLOGGED;
    public static final IntProperty LIGHT_LEVEL = IntProperty.of("light_level", 0, 15);
    public static final EnumProperty<GoatHornTorchType> HAS_TORCH = EnumProperty.of("torch", GoatHornTorchType.class);
    public static final EnumProperty<GoatHornType> HORN = EnumProperty.of("horn", GoatHornType.class);
    private static final VoxelShape NORTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(7, 3, 0, 9, 5, 7),
            Block.createCuboidShape(6.5, 4, 4, 9.5, 6, 8),
            Block.createCuboidShape(6, 5, 5, 10, 10, 9)
    );
    private static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(7, 3, 9, 9, 5, 16),
            Block.createCuboidShape(6.5, 4, 8, 9.5, 6, 12),
            Block.createCuboidShape(6, 5, 7, 10, 10, 11)
    );
    private static final VoxelShape EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(9, 3, 7, 16, 5, 9),
            Block.createCuboidShape(8, 4, 6.5, 12, 6, 9.5),
            Block.createCuboidShape(7, 5, 6, 11, 10, 10)
    );
    private static final VoxelShape WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 3, 7, 7, 5, 9),
            Block.createCuboidShape(4, 4, 6.5, 8, 6, 9.5),
            Block.createCuboidShape(5, 5, 6, 9, 10, 10)
    );

    public GoatHornBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, NORTH)
                .with(WATERLOGGED, false)
                .with(HORN, GoatHornType.CALL)
                .with(LIGHT_LEVEL, 0)
                .with(HAS_TORCH, GoatHornTorchType.NONE)
        );
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getOutlineShape(state, world, pos, context);
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return getOutlineShape(state, world, pos, ShapeContext.absent());
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient()){
            if(stack.isOf(Items.TORCH)){
                BlockState newState = state
                        .with(HAS_TORCH, GoatHornTorchType.TORCH)
                        .with(LIGHT_LEVEL, 15);
                world.setBlockState(pos, newState);
                player.getMainHandStack().decrementUnlessCreative(1, player);
            }
            else if(stack.isOf(Items.COPPER_TORCH)){
                BlockState newState = state
                        .with(HAS_TORCH, GoatHornTorchType.COPPER_TORCH)
                        .with(LIGHT_LEVEL, 13);
                world.setBlockState(pos, newState);
                player.getMainHandStack().decrementUnlessCreative(1, player);
            }
            else if(stack.isOf(Items.SOUL_TORCH)){
                BlockState newState = state
                        .with(HAS_TORCH, GoatHornTorchType.SOULFIRE_TORCH)
                        .with(LIGHT_LEVEL, 10);
                world.setBlockState(pos, newState);
                player.getMainHandStack().decrementUnlessCreative(1, player);
            }
            else if(stack.isOf(Items.REDSTONE_TORCH)){
                BlockState newState = state
                        .with(HAS_TORCH, GoatHornTorchType.REDSTONE_TORCH)
                        .with(LIGHT_LEVEL, 10);
                world.setBlockState(pos, newState);
                player.getMainHandStack().decrementUnlessCreative(1, player);
            }
            else if(stack.isOf(ItemRegistry.GLOW_TORCH)){
                {
                    if(state.get(WATERLOGGED)){
                        BlockState newState = state
                                .with(HAS_TORCH, GoatHornTorchType.GLOW_TORCH)
                                .with(LIGHT_LEVEL, 15);
                        world.setBlockState(pos, newState);
                        player.getMainHandStack().decrementUnlessCreative(1, player);
                    }
                    else{
                        BlockState newState = state
                                .with(HAS_TORCH, GoatHornTorchType.GLOW_TORCH_OFF)
                                .with(LIGHT_LEVEL, 0);
                        world.setBlockState(pos, newState);
                        player.getMainHandStack().decrementUnlessCreative(1, player);
                    }
                }
            }
            //different states of oxidation states are remaining
        }
        return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, HORN, LIGHT_LEVEL, HAS_TORCH);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        if (ctx.getWorld().getBlockState(ctx.getBlockPos().down()).isAir() || ctx.getWorld().getBlockState(ctx.getBlockPos().up()).isAir()) return null;
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (!state.canPlaceAt(world, pos)) tickView.scheduleBlockTick(pos, this, 1);
        return state;
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction facing = state.get(FACING);
        BlockPos supportPos = pos.offset(facing);

        return world.getBlockState(supportPos).isSideSolidFullSquare(world, supportPos, facing.getOpposite());
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        }
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }
}
