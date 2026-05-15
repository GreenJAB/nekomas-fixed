package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.mixin.accessor.FlowerPotBlockAccessor;
import net.greenjab.nekomasfixed.registry.block.entity.HollowLogBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jspecify.annotations.Nullable;

import java.util.Map;

public class HollowLogBlock extends BlockWithEntity implements BlockEntityProvider, Waterloggable{
    public static final IntProperty LIGHT_LEVEL = IntProperty.of("light_level", 0, 15);
    private static final Map<Direction.Axis, VoxelShape> SHAPES_BY_AXIS = VoxelShapes.createAxisShapeMap(
            VoxelShapes.union(
                    Block.createColumnShape(16.0, 0.0, 2.0),
                    Block.createColumnShape(16.0, 14.0, 16.0),
                    Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 16.0, 16.0),
                    Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 16.0, 16.0)
            )
    );
    private static final Map<Direction.Axis, VoxelShape> SHAPES_BY_AXIS_FILLED = VoxelShapes.createAxisShapeMap(
            VoxelShapes.union(
                    Block.createColumnShape(16.0, 0.0, 2.0),
                    Block.createColumnShape(16.0, 14.0, 16.0),
                    Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 16.0, 16.0),
                    Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 16.0, 16.0),
                    Block.createColumnShape(12.0, 2.0, 14.0)
            )
    );
    public static final EnumProperty<Direction.Axis> AXIS = Properties.AXIS;
    public static final BooleanProperty SOLID_INSIDE = BooleanProperty.of("filled");
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public HollowLogBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(SOLID_INSIDE, false).with(AXIS, Direction.Axis.Y));
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
        builder.add(AXIS, WATERLOGGED, LIGHT_LEVEL, SOLID_INSIDE);
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
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (world.getBlockEntity(pos) instanceof HollowLogBlockEntity logBE) {
            if (!(logBE.getStoredBlock()==Blocks.AIR.getDefaultState()||logBE.getStoredStack().getItem().getName().getString().toLowerCase().contains("glass"))) return false;
        }
        return Waterloggable.super.tryFillWithFluid(world, pos, state, fluidState);
    }

    @Override
    public boolean canFillWithFluid(@Nullable LivingEntity filler, BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        if (world.getBlockEntity(pos) instanceof HollowLogBlockEntity logBE) {
            if (!(logBE.getStoredBlock()==Blocks.AIR.getDefaultState()||logBE.getStoredStack().getItem().getName().getString().toLowerCase().contains("glass"))) return false;
        }
        return Waterloggable.super.canFillWithFluid(filler, world, pos, state, fluid);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(SOLID_INSIDE)? SHAPES_BY_AXIS_FILLED.get(state.get(AXIS)):SHAPES_BY_AXIS.get(state.get(AXIS));
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
       return new HollowLogBlockEntity(pos, state);
    }

    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world instanceof ServerWorld serverWorld) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof HollowLogBlockEntity logBE) {
                if (stack.getItem() instanceof BlockItem blockItem) {
                    if (blockItem.getBlock().getDefaultState().isIn(BlockTags.FLOWERS) && logBE.getStoredBlock().isIn(BlockTags.FLOWER_POTS)) {
                        Block plant = blockItem.getBlock();
                        Block potted = FlowerPotBlockAccessor.getContentToPotted().get(plant);
                        if (potted != null) {
                            logBE.setStoredBlock(stack.copyWithCount(1), potted.getDefaultState());
                            stack.decrementUnlessCreative(1, player);
                            return ActionResult.SUCCESS;
                        }
                    }
                    if (HollowLogBlockEntity.canStoreBlock(logBE, blockItem, state.get(AXIS)== Direction.Axis.Y)) {
                        logBE.setStoredBlock(stack.copyWithCount(1), blockItem.getBlock().getDefaultState());
                        stack.decrementUnlessCreative(1, player);
                        world.updateListeners(pos, state, state, 3);
                        if (state.get(AXIS)== Direction.Axis.Y)
                            state = state.with(SOLID_INSIDE, true);
                        if (!stack.getItem().getName().getString().toLowerCase().contains("glass"))
                            state = state.with(WATERLOGGED, false);
                        if (blockItem.getBlock().getDefaultState().getLuminance() > 0)
                            state = state.with(LIGHT_LEVEL, blockItem.getBlock().getDefaultState().getLuminance());
                        world.setBlockState(pos, state);

                        return ActionResult.SUCCESS;
                    }
                } else if (stack.getItem() instanceof Item) {
                    if (stack.isOf(Items.SHEARS)) {
                        if (logBE.getStoredBlock()!=Blocks.AIR.getDefaultState())
                            stack.damage(1, player, hand);
                        if (logBE.getStoredBlock().isIn(BlockTags.FLOWER_POTS)&&!logBE.getStoredStack().isOf(Items.FLOWER_POT)) dropStack(serverWorld, pos, Items.FLOWER_POT.getDefaultStack());
                        dropStack(serverWorld, pos, logBE.getStoredStack());
                        logBE.setStoredBlock(ItemStack.EMPTY, Blocks.AIR.getDefaultState());
                        world.setBlockState(pos, state.with(LIGHT_LEVEL, 0).with(SOLID_INSIDE, false));
                        world.updateListeners(pos, state, state, 3);
                        return ActionResult.SUCCESS;
                    }
                    /*if (stack.isIn(ItemTags.HOES) && logBE.getStoredBlock().isIn(BlockTags.DIRT)) {
                        stack.damage(1, player, hand);
                        logBE.setStoredBlock(Items.DIRT.getDefaultStack(), Blocks.FARMLAND.getDefaultState());
                        world.updateListeners(pos, state, state, 2);
                        return ActionResult.SUCCESS;
                    }*/
                }
                logBE.markDirty();
            }
        } else {
            if (stack.isOf(Items.BUCKET) || stack.isOf(Items.WATER_BUCKET))
               return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
            return ActionResult.SUCCESS;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }
}
