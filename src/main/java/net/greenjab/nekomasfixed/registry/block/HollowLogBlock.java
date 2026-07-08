package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.mixin.accessor.FlowerPotBlockAccessor;
import net.greenjab.nekomasfixed.registry.block.entity.HollowLogBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class HollowLogBlock extends BaseEntityBlock implements EntityBlock, SimpleWaterloggedBlock{
    public static final IntegerProperty LIGHT_LEVEL = IntegerProperty.create("light_level", 0, 15);
    private static final Map<Direction.Axis, VoxelShape> SHAPES_BY_AXIS = Shapes.rotateAllAxis(
            Shapes.or(
                    Block.column(16.0, 0.0, 2.0),
                    Block.column(16.0, 14.0, 16.0),
                    Block.box(0.0, 0.0, 0.0, 2.0, 16.0, 16.0),
                    Block.box(14.0, 0.0, 0.0, 16.0, 16.0, 16.0)
            )
    );
    private static final Map<Direction.Axis, VoxelShape> SHAPES_BY_AXIS_FILLED = Shapes.rotateAllAxis(
            Shapes.or(
                    Block.column(16.0, 0.0, 2.0),
                    Block.column(16.0, 14.0, 16.0),
                    Block.box(0.0, 0.0, 0.0, 2.0, 16.0, 16.0),
                    Block.box(14.0, 0.0, 0.0, 16.0, 16.0, 16.0),
                    Block.column(12.0, 2.0, 14.0)
            )
    );
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    public static final BooleanProperty SOLID_INSIDE = BooleanProperty.create("filled");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public HollowLogBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(SOLID_INSIDE, false).setValue(AXIS, Direction.Axis.Y));
    }

    public static final MapCodec<HollowLogBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    propertiesCodec()
            ).apply(instance, HollowLogBlock::new)
    );
    @Override
    protected @NonNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected @NonNull BlockState updateShape(
            BlockState state,
            @NonNull LevelReader world,
            @NonNull ScheduledTickAccess tickView,
            @NonNull BlockPos pos,
            @NonNull Direction direction,
            @NonNull BlockPos neighborPos,
            @NonNull BlockState neighborState,
            @NonNull RandomSource random
    ) {
        if (state.getValue(WATERLOGGED)) {
            tickView.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected @NonNull BlockState rotate(@NonNull BlockState state, @NonNull Rotation rotation) {
        return changeRotation(state, rotation);
    }
    public static BlockState changeRotation(BlockState state, Rotation rotation) {
        return switch (rotation) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (state.getValue(AXIS)) {
                case X -> state.setValue(AXIS, Direction.Axis.Z);
                case Z -> state.setValue(AXIS, Direction.Axis.X);
                default -> state;
            };
            default -> state;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NonNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AXIS, WATERLOGGED, LIGHT_LEVEL, SOLID_INSIDE);
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        return this.defaultBlockState().setValue(AXIS, ctx.getClickedFace().getAxis()).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    protected @NonNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean placeLiquid(LevelAccessor world, @NonNull BlockPos pos, @NonNull BlockState state, @NonNull FluidState fluidState) {
        if (world.getBlockEntity(pos) instanceof HollowLogBlockEntity logBE) {
            if (!(logBE.getStoredBlock()==Blocks.AIR.defaultBlockState()||logBE.getStoredStack().getItemName().getString().toLowerCase().contains("glass"))) return false;
        }
        return SimpleWaterloggedBlock.super.placeLiquid(world, pos, state, fluidState);
    }

    @Override
    public boolean canPlaceLiquid(@Nullable LivingEntity filler, BlockGetter world, @NonNull BlockPos pos, @NonNull BlockState state, @NonNull Fluid fluid) {
        if (world.getBlockEntity(pos) instanceof HollowLogBlockEntity logBE) {
            if (!(logBE.getStoredBlock()==Blocks.AIR.defaultBlockState()||logBE.getStoredStack().getItemName().getString().toLowerCase().contains("glass"))) return false;
        }
        return SimpleWaterloggedBlock.super.canPlaceLiquid(filler, world, pos, state, fluid);
    }

    @Override
    public @NonNull VoxelShape getShape(BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return state.getValue(SOLID_INSIDE)? SHAPES_BY_AXIS_FILLED.get(state.getValue(AXIS)):SHAPES_BY_AXIS.get(state.getValue(AXIS));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
       return new HollowLogBlockEntity(pos, state);
    }

    protected @NonNull InteractionResult useItemOn(@NonNull ItemStack stack, @NonNull BlockState state, @NonNull Level world, @NonNull BlockPos pos, @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hit) {
        if (world instanceof ServerLevel serverWorld) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof HollowLogBlockEntity logBE) {
                if (stack.getItem() instanceof BlockItem blockItem) {
                    if (blockItem.getBlock().defaultBlockState().is(BlockTags.FLOWERS) && logBE.getStoredBlock().is(BlockTags.FLOWER_POTS)) {
                        Block plant = blockItem.getBlock();
                        Block potted = FlowerPotBlockAccessor.getContentToPotted().get(plant);
                        if (potted != null) {
                            logBE.setStoredBlock(stack.copyWithCount(1), potted.defaultBlockState());
                            stack.consume(1, player);
                            return InteractionResult.SUCCESS;
                        }
                    }
                    if (HollowLogBlockEntity.canStoreBlock(logBE, blockItem, state.getValue(AXIS)== Direction.Axis.Y)) {
                        logBE.setStoredBlock(stack.copyWithCount(1), blockItem.getBlock().defaultBlockState());
                        stack.consume(1, player);
                        world.sendBlockUpdated(pos, state, state, 3);
                        if (state.getValue(AXIS)== Direction.Axis.Y)
                            state = state.setValue(SOLID_INSIDE, true);
                        if (!stack.getItemName().getString().toLowerCase().contains("glass"))
                            state = state.setValue(WATERLOGGED, false);
                        if (blockItem.getBlock().defaultBlockState().getLightEmission() > 0)
                            state = state.setValue(LIGHT_LEVEL, blockItem.getBlock().defaultBlockState().getLightEmission());
                        world.setBlockAndUpdate(pos, state);

                        return InteractionResult.SUCCESS;
                    }
                } else if (stack.getItem() instanceof Item) {
                    if (stack.is(Items.SHEARS)) {
                        if (logBE.getStoredBlock()!=Blocks.AIR.defaultBlockState())
                            stack.hurtAndBreak(1, player, hand);
                        if (logBE.getStoredBlock().is(BlockTags.FLOWER_POTS)&&!logBE.getStoredStack().is(Items.FLOWER_POT)) popResource(serverWorld, pos, Items.FLOWER_POT.getDefaultInstance());
                        popResource(serverWorld, pos, logBE.getStoredStack());
                        logBE.setStoredBlock(ItemStack.EMPTY, Blocks.AIR.defaultBlockState());
                        world.setBlockAndUpdate(pos, state.setValue(LIGHT_LEVEL, 0).setValue(SOLID_INSIDE, false));
                        world.sendBlockUpdated(pos, state, state, 3);
                        return InteractionResult.SUCCESS;
                    }
                    /*if (stack.isIn(ItemTags.HOES) && logBE.getStoredBlock().isIn(BlockTags.DIRT)) {
                        stack.damage(1, player, hand);
                        logBE.setStoredBlock(Items.DIRT.getDefaultStack(), Blocks.FARMLAND.getDefaultState());
                        world.updateListeners(pos, state, state, 2);
                        return ActionResult.SUCCESS;
                    }*/
                }
                logBE.setChanged();
            }
        } else {
            if (stack.is(Items.BUCKET) || stack.is(Items.WATER_BUCKET))
               return super.useItemOn(stack, state, world, pos, player, hand, hit);
            return InteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, world, pos, player, hand, hit);
    }

    @Override
    protected @NonNull List<ItemStack> getDrops(@NonNull BlockState state, LootParams.Builder builder) {
        BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        List<ItemStack> list = super.getDrops(state, builder);
        if (blockEntity instanceof HollowLogBlockEntity hollowLogBlockEntity) {
            if (hollowLogBlockEntity.getStoredBlock().is(BlockTags.FLOWER_POTS) && !hollowLogBlockEntity.getStoredBlock().is(Blocks.FLOWER_POT))
                list.add(Items.FLOWER_POT.getDefaultInstance());
        }
        return list;
    }
}
