package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.enums.GoatHornTorchType;
import net.greenjab.nekomasfixed.registry.block.enums.GoatHornType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static net.minecraft.core.Direction.*;

public class GoatHornBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<GoatHornBlock> CODEC = simpleCodec(GoatHornBlock::new);
    public static final Property<Boolean> WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<GoatHornTorchType> TORCH = EnumProperty.create("torch", GoatHornTorchType.class);
    public static final EnumProperty<GoatHornType> HORN = EnumProperty.create("horn", GoatHornType.class);
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    Map<Direction, VoxelShape> SHAPE = Shapes.rotateHorizontal(Shapes.or(
            Block.box(7, 3, 0, 9, 5, 7),
            Block.box(6.5, 4, 4, 9.5, 6, 8),
            Block.box(6, 5, 5, 10, 10, 9)
    ));

    public GoatHornBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, NORTH)
                .setValue(WATERLOGGED, false)
                .setValue(HORN, GoatHornType.CALL)
                .setValue(TORCH, GoatHornTorchType.NONE)
                .setValue(POWERED, false)
        );
    }

    @Override
    public @NonNull VoxelShape getShape(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return SHAPE.get(state.getValue(FACING));
    }

    @Override
    public @NonNull VoxelShape getCollisionShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return getShape(state, level, pos, context);
    }

    @Override
    public @NonNull VoxelShape getInteractionShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos) {
        return getShape(state, level, pos, CollisionContext.empty());
    }

    @Override
    protected @NonNull InteractionResult useItemOn(@NonNull ItemStack stack, @NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hit) {
        if (state.getValue(TORCH) != GoatHornTorchType.NONE) {
            if (stack.is(Items.SHEARS)) {
                level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), state.getValue(TORCH).toItem().getDefaultInstance()));
                level.setBlockAndUpdate(pos, state.setValue(TORCH, GoatHornTorchType.NONE));
                level.playSound(null, player, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
                stack.hurtAndBreak(1, player, hand);
                return InteractionResult.SUCCESS;
            }
        } else {
            GoatHornTorchType type = GoatHornTorchType.fromItem(stack.getItem(), state.getValue(WATERLOGGED));
            if (type != GoatHornTorchType.NONE) {
                level.setBlockAndUpdate(pos, state.setValue(TORCH, type));
                level.playSound(null, player, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.PLAYERS, 1.0F, 1.0F);
                stack.consume(1, player);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void animateTick(BlockState state, @NonNull Level level, BlockPos pos, @NonNull RandomSource random) {
        Direction facing = state.getValue(FACING);
        double d = pos.getX() + 0.5;
        double e = pos.getY() + 1;
        double f = pos.getZ() + 0.5;

        switch (facing){
            case SOUTH -> f+= 0.1;
            case NORTH -> f =(f-0.1) + 0.03;
            case EAST -> d += 0.1;
            case WEST -> d = (d-0.1) + 0.03;
        }

        GoatHornTorchType type = state.getValue(TORCH);
        if (type == GoatHornTorchType.NONE || type == GoatHornTorchType.GLOW_TORCH_OFF) {return;}
        level.addParticle(ParticleTypes.SMOKE, d, e, f, 0, 0, 0);
        level.addParticle(type.getParticle(), d, e, f, 0, 0, 0);
    }

    @Override
    protected @NonNull List<ItemStack> getDrops(@NonNull BlockState state, LootParams.@NonNull Builder builder) {
        if (this.drops.isEmpty()) {
            return Collections.emptyList();
        } else {
            LootParams lootContext = builder.withParameter(LootContextParams.BLOCK_STATE, state).create(LootContextParamSets.BLOCK);
            ServerLevel level = lootContext.getLevel();
            LootTable lootTable = level.getServer().reloadableRegistries().getLootTable(this.drops.get());
            List<ItemStack> drops = lootTable.getRandomItems(lootContext);

            if (state.getValue(TORCH) != GoatHornTorchType.NONE) {
                drops.add(state.getValue(TORCH).toItem().getDefaultInstance());
            }

            Holder<Instrument> entry = level.registryAccess().lookupOrThrow(Registries.INSTRUMENT).getOrThrow(state.getValue(HORN).getInstrument());
            drops.add(InstrumentItem.create(Items.GOAT_HORN, entry));

            return drops;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, HORN, TORCH, POWERED);
    }

    @Override
    protected void neighborChanged(@NonNull BlockState state, Level level, @NonNull BlockPos pos, @NonNull Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
        if (!level.isClientSide()) {
            boolean bl = state.getValue(POWERED);
            if (bl != level.hasNeighborSignal(pos)) {
                if (bl) level.scheduleTick(pos, this, 20);
                else {
                    Holder<Instrument> entry = level.registryAccess()
                            .lookupOrThrow(net.minecraft.core.registries.Registries.INSTRUMENT)
                            .getOrThrow(state.getValue(GoatHornBlock.HORN).getInstrument());
                    level.playSound(null, pos, entry.value().soundEvent().value(), SoundSource.RECORDS, 3.0F, 1.0F);
                    level.setBlock(pos, state.cycle(POWERED), Block.UPDATE_CLIENTS);
                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.NOTE, pos.getX()+0.5,pos.getY() +0.85,pos.getZ()+0.5,
                                0,0.1, 0.1, 0.1,0);
                    }
                }
            }
        }
    }

    @Override
    public @NonNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        if (ctx.getLevel().getBlockState(ctx.getClickedPos().below()).isAir() || ctx.getLevel().getBlockState(ctx.getClickedPos().above()).isAir()) return null;
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    protected @NonNull BlockState updateShape(BlockState state, @NonNull LevelReader level, @NonNull ScheduledTickAccess tickView, @NonNull BlockPos pos, @NonNull Direction direction, @NonNull BlockPos neighborPos, @NonNull BlockState neighborState, @NonNull RandomSource random) {
        if (state.getValue(WATERLOGGED)) tickView.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        if (!state.canSurvive(level, pos)) tickView.scheduleTick(pos, this, 1);
        return state;
    }

    @Override
    public boolean placeLiquid(@NonNull LevelAccessor level, @NonNull BlockPos pos, BlockState state, @NonNull FluidState fluidState) {
        if (!state.getValue(BlockStateProperties.WATERLOGGED) && fluidState.getType() == Fluids.WATER) {
            if (!level.isClientSide()) {
                if (state.getValue(TORCH)==GoatHornTorchType.GLOW_TORCH_OFF) state = state.setValue(TORCH, GoatHornTorchType.GLOW_TORCH);
                level.setBlock(pos, state.setValue(BlockStateProperties.WATERLOGGED, true), Block.UPDATE_ALL);
                level.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(level));
            }
            return true;
        } else return false;
    }

    @Override
    public @NonNull ItemStack pickupBlock(@Nullable LivingEntity drainer, @NonNull LevelAccessor level, @NonNull BlockPos pos, BlockState state) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            if (state.getValue(TORCH)==GoatHornTorchType.GLOW_TORCH) state = state.setValue(TORCH, GoatHornTorchType.GLOW_TORCH_OFF);
            level.setBlock(pos, state.setValue(BlockStateProperties.WATERLOGGED, false), Block.UPDATE_ALL);
            if (!state.canSurvive(level, pos)) level.destroyBlock(pos, true);
            return new ItemStack(Items.WATER_BUCKET);
        } else return ItemStack.EMPTY;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        BlockPos supportPos = pos.relative(facing);
        return level.getBlockState(supportPos).isFaceSturdy(level, supportPos, facing.getOpposite());
    }

    @Override
    protected void tick(BlockState state, @NonNull ServerLevel level, @NonNull BlockPos pos, @NonNull RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
        } else {
            if (state.getValue(POWERED) && !level.hasNeighborSignal(pos)) {
                level.setBlock(pos, state.cycle(POWERED), Block.UPDATE_CLIENTS);
            }
        }
    }

    @Override
    protected @NonNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }


    protected @NonNull ItemStack getCloneItemStack(@NonNull LevelReader level, @NonNull BlockPos pos, @NonNull BlockState state, boolean includeData) {
        Holder<Instrument> entry = level.registryAccess().lookupOrThrow(Registries.INSTRUMENT).getOrThrow(state.getValue(HORN).getInstrument());
        return InstrumentItem.create(Items.GOAT_HORN, entry);
    }
}
