package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.entity.StackedCakeBlockEntity;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.tick.ScheduledTickView;
import org.jspecify.annotations.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StackedCakeBlock extends AbstractCandleBlock implements BlockEntityProvider {
    public static final IntProperty SLICES = IntProperty.of("slices", 1, 21);
    public static final BooleanProperty CANDLE = BooleanProperty.of("candle");
    public static final MapCodec<StackedCakeBlock> CODEC = createCodec(StackedCakeBlock::new);
    private static final Map<Integer, VoxelShape[]> SHAPES_BY_BITES_AND_LAYER = new HashMap<>();
    private static final Map<Integer, VoxelShape> CANDLE_SHAPES = new HashMap<>();

    public StackedCakeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(SLICES, 7).with(CANDLE, false).with(LIT, false));
    }

    @Override
    protected Iterable<Vec3d> getParticleOffsets(BlockState state) {
        int HEIGHT = ((state.get(SLICES)-1)/7)+1;
        return List.of((new Vec3d(8.0F, 8f + 8f * HEIGHT - ((2 * (HEIGHT-1)) - (HEIGHT >= 3 ? -2 : 0)), 8.0F)).multiply(0.0625F));
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSolid();
    }

    @Override
    protected boolean canReplace(BlockState state, ItemPlacementContext context) {
        return false;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SLICES, CANDLE, LIT);
    }

    @Override
    public MapCodec<StackedCakeBlock> getCodec(){return CODEC;}

    static {
        for (int layer = 0; layer < 3; layer++) {
            double scale = 1-0.2*layer;
            double yMinT = 0;
            if (layer == 1) yMinT = 8;
            if (layer == 2) yMinT = 8 + 8*(1-0.2);
            final double yMin = yMinT;
            final double yMax = yMin + 8 * scale;
            SHAPES_BY_BITES_AND_LAYER.put(layer, Block.createShapeArray(6, slices -> Block.createCuboidShape(8+(7 - (slices+1) * 2)*scale, yMin, 8-7*scale, 8+7*scale, yMax, 8+7*scale)));
            CANDLE_SHAPES.put(layer, Block.createCuboidShape(7, yMax, 7, 9, yMax+6, 9));
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    protected ActionResult tryEat(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.canConsume(false)) {
            return ActionResult.PASS;
        } else {
            if (state.get(CANDLE)) {
                if (world.getBlockEntity(pos) instanceof StackedCakeBlockEntity blockEntity) {
                    dropStack(world, pos.up(), blockEntity.CANDLE_STATE.getBlock().asItem().getDefaultStack());
                    blockEntity.CANDLE_STATE = Blocks.AIR.getDefaultState();
                    blockEntity.markDirty();
                }
                state = state.with(CANDLE, false).with(LIT, false);
            }

            player.incrementStat(Stats.EAT_CAKE_SLICE);
            player.getHungerManager().add(2, 0.1F);

            if(world.getBlockEntity(pos) instanceof StackedCakeBlockEntity blockEntity){
                int i = state.get(SLICES)-1;
                int h = i/7;
                BlockState currentState = state;
                if (h==1) currentState = blockEntity.LAYER_2_STATE;
                if (h==2) currentState = blockEntity.LAYER_3_STATE;

                if(!currentState.contains(StackedCakeBlock.SLICES)) return ActionResult.FAIL;
                world.emitGameEvent(player, GameEvent.EAT, pos);
                if (i%7==0) {
                    if (h==1) blockEntity.LAYER_2_STATE=Blocks.AIR.getDefaultState();
                    else if (h==2) blockEntity.LAYER_3_STATE=Blocks.AIR.getDefaultState();
                    if (i==0) world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                    else world.setBlockState(pos, state.with(SLICES,i), 3);
                } else {
                    if (h==1) blockEntity.LAYER_2_STATE=currentState.with(SLICES,i%7);
                    else if (h==2) blockEntity.LAYER_3_STATE=currentState.with(SLICES,i%7);
                    world.setBlockState(pos, state.with(SLICES,i), 3);
                }
                blockEntity.markDirty();
            }
            return ActionResult.SUCCESS;
        }
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int h = (state.get(SLICES)-1)/7;
        int s = (state.get(SLICES)-1)%7;
        if (state.get(CANDLE)){
            if (h==0) return VoxelShapes.union(SHAPES_BY_BITES_AND_LAYER.get(0)[s], CANDLE_SHAPES.get(0));
            else if (h==1) return VoxelShapes.union(SHAPES_BY_BITES_AND_LAYER.get(0)[6], SHAPES_BY_BITES_AND_LAYER.get(1)[s], CANDLE_SHAPES.get(1));
            else if (h==2) return VoxelShapes.union(SHAPES_BY_BITES_AND_LAYER.get(0)[6], SHAPES_BY_BITES_AND_LAYER.get(1)[6], SHAPES_BY_BITES_AND_LAYER.get(2)[s], CANDLE_SHAPES.get(2));
        } else {
            if (h == 0) return SHAPES_BY_BITES_AND_LAYER.get(0)[s];
            else if (h == 1) return VoxelShapes.union(SHAPES_BY_BITES_AND_LAYER.get(0)[6], SHAPES_BY_BITES_AND_LAYER.get(1)[s]);
            else if (h == 2) return VoxelShapes.union(SHAPES_BY_BITES_AND_LAYER.get(0)[6], SHAPES_BY_BITES_AND_LAYER.get(1)[6], SHAPES_BY_BITES_AND_LAYER.get(2)[s]);
        }
        return SHAPES_BY_BITES_AND_LAYER.get(0)[s];
    }

    public void addCakeLayer(ItemStack stack, StackedCakeBlockEntity entity, BlockState state){
        int h = (state.get(SLICES)-1)/7;
        if (stack.getItem() instanceof BlockItem blockItem) {
            if (h==0) entity.LAYER_2_STATE=blockItem.getBlock().getDefaultState();
            else entity.LAYER_3_STATE=blockItem.getBlock().getDefaultState();

            entity.markDirty();
        }
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient()) return ActionResult.SUCCESS;
        else if (world.getBlockEntity(pos) instanceof StackedCakeBlockEntity stackedCakeBlockEntity){
            if (state.get(SLICES) == 7 || state.get(SLICES) == 14 || state.get(SLICES) == 21) {
                if (player.getMainHandStack().isIn(ModTags.STACKED_CAKES) && state.get(SLICES) != 21) {
                    this.addCakeLayer(stack, stackedCakeBlockEntity, state);
                    world.setBlockState(pos, world.getBlockState(pos).with(SLICES, state.get(SLICES)+7));
                    player.swingHand(hand, true);
                    stack.decrementUnlessCreative(1, player);
                    return ActionResult.SUCCESS;
                } else if (player.getMainHandStack().isIn(ItemTags.CANDLES)) {
                    if (!state.get(CANDLE)) {
                        if (stack.getItem() instanceof BlockItem blockItem) {
                            BlockState candleState = blockItem.getBlock().getDefaultState();
                            stackedCakeBlockEntity.CANDLE_STATE = candleState.with(CandleBlock.LIT, false);
                            world.setBlockState(pos, world.getBlockState(pos).with(CANDLE, true).with(CandleBlock.LIT, false));
                            player.swingHand(hand, true);
                            stack.decrementUnlessCreative(1, player);
                            return ActionResult.SUCCESS;
                        }
                    }
                } else if (stack.isOf(Items.FLINT_AND_STEEL)) {
                    if (state.get(CANDLE)) {
                        BlockState candleState = stackedCakeBlockEntity.CANDLE_STATE;
                        if (!candleState.get(CandleBlock.LIT)) {
                            stackedCakeBlockEntity.CANDLE_STATE = candleState.with(CandleBlock.LIT, true);
                            stackedCakeBlockEntity.markDirty();
                            world.setBlockState(pos, world.getBlockState(pos).with(LIT, true));
                            player.swingHand(hand, true);
                            stack.damage(1, player);
                            world.playSound(null, pos, net.minecraft.sound.SoundEvents.ITEM_FLINTANDSTEEL_USE, net.minecraft.sound.SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
                            return ActionResult.SUCCESS;
                        }
                    }
                }
            }
            return tryEat(world, pos, state, player);
        }
        return ActionResult.PASS;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StackedCakeBlockEntity(pos, state);
    }

    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos, Direction direction) {
        return state.get(SLICES);
    }

    @Override
    protected boolean hasComparatorOutput(BlockState state) {
        return true;
    }
}
