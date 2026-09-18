package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.mixin.accessor.FlowerPotBlockAccessor;
import net.greenjab.nekomasfixed.registry.block.entity.HollowLogBlockEntity;
import net.greenjab.nekomasfixed.registry.block.enums.HollowLogType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
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

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * A hollow log that can store a single block inside (flowers, glass, torches,
 * lanterns, or any full-cube block). Stored contents are held by a
 * {@link HollowLogBlockEntity}.
 */
public class HollowLogBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

    public static final IntegerProperty LIGHT_LEVEL = IntegerProperty.create("light_level", 0, 15);
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    public static final BooleanProperty SOLID_INSIDE = BooleanProperty.create("filled");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final MapCodec<HollowLogBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    propertiesCodec()
            ).apply(instance, HollowLogBlock::new));

    // A hollow shell tube. Each shell is four walls around the bore, which runs
    // along the log's axis and is open at both ends of that axis (matching the
    // blockstate model's hollow faces). The FILLED variant adds a solid core
    // rod down the centre of the bore.
    private static final VoxelShape Y_AXIS_SHELL = Shapes.or(
            // closed: left (x=0), right (x=16), north (z=0), south (z=16); open: up/down
            Block.box(0.0, 0.0, 0.0, 2.0, 16.0, 16.0),
            Block.box(14.0, 0.0, 0.0, 16.0, 16.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 2.0),
            Block.box(0.0, 0.0, 14.0, 16.0, 16.0, 16.0)
    );
    private static final VoxelShape X_AXIS_SHELL = Shapes.or(
            // closed: bottom (y=0), top (y=16), north (z=0), south (z=16); open: east/west
            Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            Block.box(0.0, 14.0, 0.0, 16.0, 16.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 2.0),
            Block.box(0.0, 0.0, 14.0, 16.0, 16.0, 16.0)
    );
    private static final VoxelShape Z_AXIS_SHELL = Shapes.or(
            // closed: bottom (y=0), top (y=16), left (x=0), right (x=16); open: north/south
            Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            Block.box(0.0, 14.0, 0.0, 16.0, 16.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 2.0, 16.0, 16.0),
            Block.box(14.0, 0.0, 0.0, 16.0, 16.0, 16.0)
    );
    private static final VoxelShape Y_AXIS_FILLED = Shapes.or(Y_AXIS_SHELL, Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0));
    private static final VoxelShape X_AXIS_FILLED = Shapes.or(X_AXIS_SHELL, Block.box(0.0, 2.0, 2.0, 16.0, 14.0, 14.0));
    private static final VoxelShape Z_AXIS_FILLED = Shapes.or(Z_AXIS_SHELL, Block.box(2.0, 2.0, 0.0, 14.0, 14.0, 16.0));

    public static final Map<Direction.Axis, VoxelShape> SHAPES_BY_AXIS = buildAxisShapes(false);
    public static final Map<Direction.Axis, VoxelShape> SHAPES_BY_AXIS_FILLED = buildAxisShapes(true);

    public HollowLogBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(WATERLOGGED, false)
                .setValue(SOLID_INSIDE, false)
                .setValue(AXIS, Direction.Axis.Y));
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

    // Build the axis-mapped shell shapes so each hollow opening runs along the
    // log's axis (the same faces the blockstate model shows as hollow).
    private static Map<Direction.Axis, VoxelShape> buildAxisShapes(boolean filled) {
        Map<Direction.Axis, VoxelShape> map = new EnumMap<>(Direction.Axis.class);
        if (filled) {
            map.put(Direction.Axis.Y, Y_AXIS_FILLED);
            map.put(Direction.Axis.X, X_AXIS_FILLED);
            map.put(Direction.Axis.Z, Z_AXIS_FILLED);
        } else {
            map.put(Direction.Axis.Y, Y_AXIS_SHELL);
            map.put(Direction.Axis.X, X_AXIS_SHELL);
            map.put(Direction.Axis.Z, Z_AXIS_SHELL);
        }
        return map;
    }

    @Override
    public @NonNull MapCodec<? extends HollowLogBlock> codec() {
        return CODEC;
    }

    @Override
    protected @NonNull BlockState updateShape(BlockState state, @NonNull Direction direction, @NonNull BlockState neighborState,
                                              @NonNull LevelAccessor level, @NonNull BlockPos pos, @NonNull BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected @NonNull BlockState rotate(@NonNull BlockState state, @NonNull Rotation rotation) {
        return changeRotation(state, rotation);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NonNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AXIS, WATERLOGGED, LIGHT_LEVEL, SOLID_INSIDE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        return this.defaultBlockState()
                .setValue(AXIS, ctx.getClickedFace().getAxis())
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    protected @NonNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean placeLiquid(LevelAccessor level, @NonNull BlockPos pos, @NonNull BlockState state, @NonNull FluidState fluidState) {
        if (level.getBlockEntity(pos) instanceof HollowLogBlockEntity logBE) {
            if (!(logBE.getStoredBlock() == Blocks.AIR.defaultBlockState()
                    || logBE.getStoredStack().getHoverName().getString().toLowerCase().contains("glass"))) {
                return false;
            }
        }
        return SimpleWaterloggedBlock.super.placeLiquid(level, pos, state, fluidState);
    }

    @Override
    public boolean canPlaceLiquid(Player filler, BlockGetter level, @NonNull BlockPos pos, @NonNull BlockState state, @NonNull Fluid fluid) {
        if (level.getBlockEntity(pos) instanceof HollowLogBlockEntity logBE) {
            if (!(logBE.getStoredBlock() == Blocks.AIR.defaultBlockState()
                    || logBE.getStoredStack().getHoverName().getString().toLowerCase().contains("glass"))) {
                return false;
            }
        }
        return SimpleWaterloggedBlock.super.canPlaceLiquid(filler, level, pos, state, fluid);
    }

    @Override
    protected @NonNull ItemInteractionResult useItemOn(@NonNull ItemStack stack, @NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos,
                                                       @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hit) {
        // Axe-stripping: hollow -> stripped hollow. Both share the hollow-log block
        // entity type, so vanilla keeps the block entity (and stored contents)
        // across the swap; re-sync because clients recreate their copy empty on a
        // block-type change.
        Block stripped = HollowLogType.getStrippedBlock(state.getBlock());
        if (stripped != null && stack.is(ItemTags.AXES)) {
            level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F,
                    level.getRandom().nextFloat() * 0.4F + 0.8F);
            if (level instanceof ServerLevel) {
                level.setBlockAndUpdate(pos,
                        stripped.defaultBlockState()
                                .setValue(LIGHT_LEVEL, state.getValue(LIGHT_LEVEL))
                                .setValue(AXIS, state.getValue(AXIS))
                                .setValue(SOLID_INSIDE, state.getValue(SOLID_INSIDE))
                                .setValue(WATERLOGGED, state.getValue(WATERLOGGED)));
                stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                if (level.getBlockEntity(pos) instanceof HollowLogBlockEntity kept) {
                    kept.setChanged();
                }
            }
            return ItemInteractionResult.SUCCESS;
        }
        if (level instanceof ServerLevel serverLevel) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof HollowLogBlockEntity logBE) {
                if (stack.getItem() instanceof BlockItem blockItem) {
                    if (blockItem.getBlock().defaultBlockState().is(BlockTags.FLOWERS)
                            && logBE.getStoredBlock().is(BlockTags.FLOWER_POTS)) {
                        Block plant = blockItem.getBlock();
                        Block potted = FlowerPotBlockAccessor.getContentToPotted().get(plant);
                        if (potted != null) {
                            logBE.setStoredBlock(stack.copyWithCount(1), potted.defaultBlockState());
                            stack.consume(1, player);
                            return ItemInteractionResult.SUCCESS;
                        }
                    }
                    if (HollowLogBlockEntity.canStoreBlock(logBE, blockItem, state.getValue(AXIS) == Direction.Axis.Y)) {
                        logBE.setStoredBlock(stack.copyWithCount(1), blockItem.getBlock().defaultBlockState());
                        stack.consume(1, player);
                        level.sendBlockUpdated(pos, state, state, 3);
                        boolean fullBlock = blockItem.getBlock().defaultBlockState().getShape(
                                EmptyBlockGetter.INSTANCE, BlockPos.ZERO) == Shapes.block();
                        state = state.setValue(SOLID_INSIDE, fullBlock);
                        if (!stack.getHoverName().getString().toLowerCase().contains("glass")) {
                            state = state.setValue(WATERLOGGED, false);
                        }
                        if (blockItem.getBlock().defaultBlockState().getLightEmission() > 0) {
                            state = state.setValue(LIGHT_LEVEL, blockItem.getBlock().defaultBlockState().getLightEmission());
                        }
                        level.setBlockAndUpdate(pos, state);
                        return ItemInteractionResult.SUCCESS;
                    }
                } else if (stack.getItem() instanceof Item) {
                    if (stack.is(Items.SHEARS)) {
                        if (logBE.getStoredBlock() != Blocks.AIR.defaultBlockState()) {
                            stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                        }
                        if (logBE.getStoredBlock().is(BlockTags.FLOWER_POTS)
                                && !logBE.getStoredStack().is(Items.FLOWER_POT)) {
                            popResource(serverLevel, pos, Items.FLOWER_POT.getDefaultInstance());
                        }
                        popResource(serverLevel, pos, logBE.getStoredStack());
                        logBE.setStoredBlock(ItemStack.EMPTY, Blocks.AIR.defaultBlockState());
                        level.setBlockAndUpdate(pos, state.setValue(LIGHT_LEVEL, 0).setValue(SOLID_INSIDE, false));
                        level.sendBlockUpdated(pos, state, state, 3);
                        return ItemInteractionResult.SUCCESS;
                    }
                }
                logBE.setChanged();
            }
        } else {
            if (stack.is(Items.BUCKET) || stack.is(Items.WATER_BUCKET)) {
                return super.useItemOn(stack, state, level, pos, player, hand, hit);
            }
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hit);
    }

    @Override
    protected void onRemove(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, BlockState newState, boolean movedByPiston) {
        // Axe-stripping swaps to another hollow log; vanilla keeps the block entity
        // (and stored contents) there, so don't drain here or the item is duplicated
        // (dropped AND still stored). Only drain when the hollow log is actually
        // replaced by a non-hollow block.
        if (newState.getBlock() instanceof HollowLogBlock) {
            return;
        }
        if (!state.is(newState.getBlock())) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof HollowLogBlockEntity logBE) {
                // Drain the stored item so it isn't lost when the hollow log is broken.
                net.minecraft.world.Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(),
                        logBE.getStoredStack());
            }
            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    @Override
    protected @NonNull List<ItemStack> getDrops(@NonNull BlockState state, LootParams.Builder builder) {
        BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        List<ItemStack> list = super.getDrops(state, builder);
        if (blockEntity instanceof HollowLogBlockEntity hollowLogBlockEntity) {
            if (hollowLogBlockEntity.getStoredBlock().is(BlockTags.FLOWER_POTS)
                    && !hollowLogBlockEntity.getStoredBlock().is(Blocks.FLOWER_POT)) {
                list.add(Items.FLOWER_POT.getDefaultInstance());
            }
        }
        return list;
    }

    @Override
    public @NonNull VoxelShape getShape(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return state.getValue(SOLID_INSIDE)
                ? SHAPES_BY_AXIS_FILLED.get(state.getValue(AXIS))
                : SHAPES_BY_AXIS.get(state.getValue(AXIS));
    }
    // The shell is drawn by its blockstate model -- BASE_ENTITY's default render shape would
    // skip the model and render nothing (hollow logs would look see-through). The block-entity
    // renderer is still called separately to draw the stored block inside.

    @Override
    public BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
        return new HollowLogBlockEntity(pos, state);
    }

    @Override
    public @NonNull RenderShape getRenderShape(@NonNull BlockState state) {
        return RenderShape.MODEL;

    }
}