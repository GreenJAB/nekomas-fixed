package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.entity.StackedCakeBlockEntity;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StackedCakeBlock extends AbstractCandleBlock implements EntityBlock {
    public static final IntegerProperty SLICES = IntegerProperty.create("slices", 1, 21);
    public static final BooleanProperty CANDLE = BooleanProperty.create("candle");
    public static final MapCodec<StackedCakeBlock> CODEC = simpleCodec(StackedCakeBlock::new);
    private static final Map<Integer, VoxelShape[]> SHAPES_BY_BITES_AND_LAYER = new HashMap<>();
    private static final Map<Integer, VoxelShape> CANDLE_SHAPES = new HashMap<>();

    public StackedCakeBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(SLICES, 7).setValue(CANDLE, false).setValue(LIT, false));
    }

    @Override
    protected @NonNull Iterable<Vec3> getParticleOffsets(BlockState state) {
        int height = ((state.getValue(SLICES)-1)/7)+1;
        return List.of((new Vec3(8.0F, 8f + 8f * height - ((2 * (height -1)) - (height >= 3 ? -2 : 0)), 8.0F)).scale(0.0625F));
    }

    @Override
    protected boolean canSurvive(@NonNull BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }

    @Override
    protected boolean canBeReplaced(@NonNull BlockState state, @NonNull BlockPlaceContext context) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SLICES, CANDLE, LIT);
    }

    @Override
    public @NonNull MapCodec<StackedCakeBlock> codec(){return CODEC;}

    static {
        for (int height = 0; height < 3; height++) {
            double scale = 1-0.2* height;
            double yMinT = 0;
            if (height == 1) yMinT = 8;
            if (height == 2) yMinT = 8 + 8*(1-0.2);
            final double yMin = yMinT;
            final double yMax = yMin + 8 * scale;
            SHAPES_BY_BITES_AND_LAYER.put(height, Block.boxes(6, slices -> Block.box(8+(7 - (slices+1) * 2)*scale, yMin, 8-7*scale, 8+7*scale, yMax, 8+7*scale)));
            CANDLE_SHAPES.put(height, Block.box(7, yMax, 7, 9, yMax+6, 9));
        }
    }

    @Override
    public void setPlacedBy(@NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState state, @Nullable LivingEntity placer, @NonNull ItemStack itemStack) {
        super.setPlacedBy(level, pos, state, placer, itemStack);
    }

    protected InteractionResult tryEat(Level level, BlockPos pos, BlockState state, Player player) {
        if (!player.canEat(false)) {
            return InteractionResult.PASS;
        } else {
            if (state.getValue(CANDLE)) {
                if (level.getBlockEntity(pos) instanceof StackedCakeBlockEntity blockEntity) {
                    popResource(level, pos.above(), blockEntity.CANDLE_STATE.getBlock().asItem().getDefaultInstance());
                    blockEntity.CANDLE_STATE = Blocks.AIR.defaultBlockState();
                    blockEntity.setChanged();
                }
                state = state.setValue(CANDLE, false).setValue(LIT, false);
            }

            player.awardStat(Stats.EAT_CAKE_SLICE);
            player.getFoodData().eat(2, 0.1F);
            level.playSound(null, player, SoundEvents.GENERIC_EAT.value(), SoundSource.PLAYERS, 1.0F, 1.0F);

            if(level.getBlockEntity(pos) instanceof StackedCakeBlockEntity blockEntity){
                int totalSlices = state.getValue(SLICES)-1;
                int height = totalSlices /7;
                BlockState currentState = state;
                if (height ==1) currentState = blockEntity.LAYER_2_STATE;
                if (height ==2) currentState = blockEntity.LAYER_3_STATE;

                if(!currentState.hasProperty(StackedCakeBlock.SLICES)) return InteractionResult.FAIL;
                level.gameEvent(player, GameEvent.EAT, pos);
                if (totalSlices %7==0) {
                    if (height ==1) blockEntity.LAYER_2_STATE=Blocks.AIR.defaultBlockState();
                    else if (height ==2) blockEntity.LAYER_3_STATE=Blocks.AIR.defaultBlockState();
                    if (totalSlices ==0) level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                    else level.setBlock(pos, state.setValue(SLICES, totalSlices), 3);
                } else {
                    if (height ==1) blockEntity.LAYER_2_STATE=currentState.setValue(SLICES, totalSlices %7);
                    else if (height ==2) blockEntity.LAYER_3_STATE=currentState.setValue(SLICES, totalSlices %7);
                    level.setBlock(pos, state.setValue(SLICES, totalSlices), 3);
                }
                blockEntity.setChanged();
            }
            return InteractionResult.SUCCESS;
        }
    }

    @Override
    protected @NonNull VoxelShape getShape(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        int height = (state.getValue(SLICES)-1)/7;
        int slice = (state.getValue(SLICES)-1)%7;
        if (state.getValue(CANDLE)){
            if (height ==0) return Shapes.or(SHAPES_BY_BITES_AND_LAYER.get(0)[slice], CANDLE_SHAPES.get(0));
            else if (height ==1) return Shapes.or(SHAPES_BY_BITES_AND_LAYER.get(0)[6], SHAPES_BY_BITES_AND_LAYER.get(1)[slice], CANDLE_SHAPES.get(1));
            else if (height ==2) return Shapes.or(SHAPES_BY_BITES_AND_LAYER.get(0)[6], SHAPES_BY_BITES_AND_LAYER.get(1)[6], SHAPES_BY_BITES_AND_LAYER.get(2)[slice], CANDLE_SHAPES.get(2));
        } else {
            if (height == 0) return SHAPES_BY_BITES_AND_LAYER.get(0)[slice];
            else if (height == 1) return Shapes.or(SHAPES_BY_BITES_AND_LAYER.get(0)[6], SHAPES_BY_BITES_AND_LAYER.get(1)[slice]);
            else if (height == 2) return Shapes.or(SHAPES_BY_BITES_AND_LAYER.get(0)[6], SHAPES_BY_BITES_AND_LAYER.get(1)[6], SHAPES_BY_BITES_AND_LAYER.get(2)[slice]);
        }
        return SHAPES_BY_BITES_AND_LAYER.get(0)[slice];
    }

    public void addCakeLayer(ItemStack stack, StackedCakeBlockEntity entity, BlockState state){
        int height = (state.getValue(SLICES)-1)/7;
        if (stack.getItem() instanceof BlockItem blockItem) {
            if (height ==0) entity.LAYER_2_STATE=blockItem.getBlock().defaultBlockState();
            else entity.LAYER_3_STATE=blockItem.getBlock().defaultBlockState();
            entity.setChanged();
        }
    }

    @Override
    protected @NonNull BlockState updateShape(@NonNull BlockState state, @NonNull LevelReader level, @NonNull ScheduledTickAccess tickView, @NonNull BlockPos pos, @NonNull Direction direction, @NonNull BlockPos neighborPos, @NonNull BlockState neighborState, @NonNull RandomSource random) {
        return direction == Direction.DOWN && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, level, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected @NonNull InteractionResult useItemOn(@NonNull ItemStack stack, @NonNull BlockState state, Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hit) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;
        else if (level.getBlockEntity(pos) instanceof StackedCakeBlockEntity stackedCakeBlockEntity){
            if (state.getValue(SLICES) == 7 || state.getValue(SLICES) == 14 || state.getValue(SLICES) == 21) {
                if (player.getMainHandItem().is(ModTags.STACKED_CAKES) && state.getValue(SLICES) != 21) {
                    this.addCakeLayer(stack, stackedCakeBlockEntity, state);
                    level.setBlockAndUpdate(pos, level.getBlockState(pos).setValue(SLICES, state.getValue(SLICES)+7));
                    level.playSound(null, player, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.PLAYERS, 1.0F, 1.0F);
                    stack.consume(1, player);
                    return InteractionResult.SUCCESS;
                } else if (player.getMainHandItem().is(ItemTags.CANDLES)) {
                    if (!state.getValue(CANDLE)) {
                        if (stack.getItem() instanceof BlockItem blockItem) {
                            BlockState candleState = blockItem.getBlock().defaultBlockState();
                            stackedCakeBlockEntity.CANDLE_STATE = candleState.setValue(CandleBlock.LIT, false);
                            level.setBlockAndUpdate(pos, level.getBlockState(pos).setValue(CANDLE, true).setValue(CandleBlock.LIT, false));
                            level.playSound(null, player, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.PLAYERS, 1.0F, 1.0F);
                            stack.consume(1, player);
                            return InteractionResult.SUCCESS;
                        }
                    }
                } else if (stack.is(Items.FLINT_AND_STEEL)) {
                    if (state.getValue(CANDLE)) {
                        BlockState candleState = stackedCakeBlockEntity.CANDLE_STATE;
                        if (!candleState.getValue(CandleBlock.LIT)) {
                            stackedCakeBlockEntity.CANDLE_STATE = candleState.setValue(CandleBlock.LIT, true);
                            stackedCakeBlockEntity.setChanged();
                            level.setBlockAndUpdate(pos, level.getBlockState(pos).setValue(LIT, true));
                            stack.hurtAndBreak(1, player, hand);
                            level.playSound(null, player, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
            return tryEat(level, pos, state, player);
        }
        return InteractionResult.PASS;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
        return new StackedCakeBlockEntity(pos, state);
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Direction direction) {
        return state.getValue(SLICES);
    }

    @Override
    protected boolean hasAnalogOutputSignal(@NonNull BlockState state) {
        return true;
    }
}
