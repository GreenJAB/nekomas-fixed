package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.entity.StackedCakeBlockEntity;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.greenjab.nekomasfixed.util.CakeRegistry;
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
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.tick.ScheduledTickView;
import org.jspecify.annotations.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StackedCakeBlock extends AbstractCandleBlock implements BlockEntityProvider {
    public static final IntProperty BITES;
    public static final BooleanProperty LIT;
    public static final IntProperty LIGHT_LEVEL = IntProperty.of("light_level", 0, 15);
    public static final IntProperty HEIGHT = IntProperty.of("height", 1, 3);
    public static final MapCodec<StackedCakeBlock> CODEC = createCodec(StackedCakeBlock::new);
    private static final Map<Integer, VoxelShape[]> SHAPES_BY_BITES_AND_LAYER = new HashMap<>();
    public static final Map<Item, BlockState> ITEM_TO_CANDLE_FOR_STACKED_CAKES = new HashMap<>();

    public StackedCakeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(HEIGHT, 1).with(BITES, 0).with(LIT, false).with(LIGHT_LEVEL, 0));
    }

    @Override
    protected Iterable<Vec3d> getParticleOffsets(BlockState state) {
        return List.of((new Vec3d(8.0F, 8f + 8f * state.get(HEIGHT) - ((2 * (state.get(HEIGHT)-1)) - (state.get(HEIGHT) >= 3 ? -2 : 0)), 8.0F)).multiply(0.0625F));
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
        builder.add(BITES, LIGHT_LEVEL, LIT, HEIGHT);
    }

    @Override
    public MapCodec<StackedCakeBlock> getCodec(){return CODEC;}

    static {
        ITEM_TO_CANDLE_FOR_STACKED_CAKES.put(Items.CANDLE, Blocks.CANDLE.getDefaultState());

        ITEM_TO_CANDLE_FOR_STACKED_CAKES.put(Items.WHITE_CANDLE, Blocks.WHITE_CANDLE.getDefaultState());
        ITEM_TO_CANDLE_FOR_STACKED_CAKES.put(Items.ORANGE_CANDLE, Blocks.ORANGE_CANDLE.getDefaultState());
        ITEM_TO_CANDLE_FOR_STACKED_CAKES.put(Items.MAGENTA_CANDLE, Blocks.MAGENTA_CANDLE.getDefaultState());
        ITEM_TO_CANDLE_FOR_STACKED_CAKES.put(Items.LIGHT_BLUE_CANDLE, Blocks.LIGHT_BLUE_CANDLE.getDefaultState());
        ITEM_TO_CANDLE_FOR_STACKED_CAKES.put(Items.YELLOW_CANDLE, Blocks.YELLOW_CANDLE.getDefaultState());
        ITEM_TO_CANDLE_FOR_STACKED_CAKES.put(Items.LIME_CANDLE, Blocks.LIME_CANDLE.getDefaultState());
        ITEM_TO_CANDLE_FOR_STACKED_CAKES.put(Items.PINK_CANDLE, Blocks.PINK_CANDLE.getDefaultState());
        ITEM_TO_CANDLE_FOR_STACKED_CAKES.put(Items.GRAY_CANDLE, Blocks.GRAY_CANDLE.getDefaultState());
        ITEM_TO_CANDLE_FOR_STACKED_CAKES.put(Items.LIGHT_GRAY_CANDLE, Blocks.LIGHT_GRAY_CANDLE.getDefaultState());
        ITEM_TO_CANDLE_FOR_STACKED_CAKES.put(Items.CYAN_CANDLE, Blocks.CYAN_CANDLE.getDefaultState());
        ITEM_TO_CANDLE_FOR_STACKED_CAKES.put(Items.PURPLE_CANDLE, Blocks.PURPLE_CANDLE.getDefaultState());
        ITEM_TO_CANDLE_FOR_STACKED_CAKES.put(Items.BLUE_CANDLE, Blocks.BLUE_CANDLE.getDefaultState());
        ITEM_TO_CANDLE_FOR_STACKED_CAKES.put(Items.BROWN_CANDLE, Blocks.BROWN_CANDLE.getDefaultState());
        ITEM_TO_CANDLE_FOR_STACKED_CAKES.put(Items.GREEN_CANDLE, Blocks.GREEN_CANDLE.getDefaultState());
        ITEM_TO_CANDLE_FOR_STACKED_CAKES.put(Items.RED_CANDLE, Blocks.RED_CANDLE.getDefaultState());
        ITEM_TO_CANDLE_FOR_STACKED_CAKES.put(Items.BLACK_CANDLE, Blocks.BLACK_CANDLE.getDefaultState());
        BITES = Properties.BITES;
        LIT = Properties.LIT;

        for (int layer = 0; layer < 4; layer++) {
            final int yMin = layer * 8;
            final int yMax = yMin + 8;

            SHAPES_BY_BITES_AND_LAYER.put(layer, Block.createShapeArray(6, bites -> Block.createCuboidShape(1 + bites * 2, yMin, 1, 15, yMax, 15)));
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);

        if (!world.isClient()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);

            if (blockEntity instanceof StackedCakeBlockEntity stackedCake) {
                stackedCake.STACKED_CAKES.add(state);
            }
        }
    }

    protected static ActionResult tryEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.canConsume(false)) {
            return ActionResult.PASS;
        } else {
            player.incrementStat(Stats.EAT_CAKE_SLICE);
            player.getHungerManager().add(2, 0.1F);

            if(world.getBlockEntity(pos) instanceof StackedCakeBlockEntity blockEntity){
                BlockState currentState = blockEntity.STACKED_CAKES.peek();

                if(!currentState.contains(CakeBlock.BITES)) return ActionResult.FAIL;

                int i = currentState.get(BITES);
                world.emitGameEvent(player, GameEvent.EAT, pos);

                //this is just an example text
                if (i < 6) {
                    if(blockEntity.STACKED_CAKES.size() <= 1){
                        blockEntity.STACKED_CAKES.set(blockEntity.STACKED_CAKES.size()-1, currentState.with(CakeBlock.BITES, i+1));
                        world.setBlockState(pos, state.with(BITES, i + 1), 3);
                        return ActionResult.SUCCESS;
                    }
                    blockEntity.STACKED_CAKES.set(blockEntity.STACKED_CAKES.size()-1, currentState.with(CakeBlock.BITES, i+1));
                } else {
                    if(blockEntity.STACKED_CAKES.size() <= 1){
                        world.removeBlock(pos, false);
                        return ActionResult.SUCCESS;
                    }
                    blockEntity.STACKED_CAKES.pop();
                    world.setBlockState(pos, state.with(HEIGHT, state.get(HEIGHT)-1), 3);
                }
            }

            return ActionResult.SUCCESS;
        }
    }

    private boolean isFullyStacked(StackedCakeBlockEntity blockEntity){
        return blockEntity.STACKED_CAKES.size() == 3;
    }

    private void removeCandle(World world, BlockPos pos){
        if(world.getBlockEntity(pos) instanceof StackedCakeBlockEntity blockEntity){
            dropStack(world, pos.up(), blockEntity.CANDLE_STATE.get().getBlock().asItem().getDefaultStack());

            blockEntity.CANDLE_STATE = Optional.ofNullable(Blocks.AIR.getDefaultState());
            world.setBlockState(pos, world.getBlockState(pos).with(LIGHT_LEVEL, 0).with(LIT, false));

            blockEntity.markDirty();
            world.updateListeners(pos, blockEntity.getCachedState(), blockEntity.getCachedState(), 3);
        }
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
       return SHAPES_BY_BITES_AND_LAYER.get(0)[state.get(BITES)];
    }

    public void addCakeLayer(ItemStack stack, World world, BlockPos pos, StackedCakeBlockEntity entity, BlockState state){
        if(!this.isFullyStacked(entity) ){
            if(entity.STACKED_CAKES.size() == 2 ){
                if(world.getBlockState(pos.up()).isSolid()) return;
                entity.STACKED_CAKES.push(CakeRegistry.getDefaultState(stack).with(CakeBlock.BITES, state.get(BITES)));
               this.incrementHeight(state, world, pos);
                entity.markDirty();
            }else{
                entity.STACKED_CAKES.push(CakeRegistry.getDefaultState(stack).with(CakeBlock.BITES, state.get(BITES)));
                this.incrementHeight(state, world, pos);
                entity.markDirty();
            }
        }
    }

    private void incrementHeight( BlockState state, World world, BlockPos pos){
        if(state.get(HEIGHT) <= 3){
            world.setBlockState(pos, state.with(HEIGHT, state.get(HEIGHT)+1));
        }
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if(world.getBlockEntity(pos) instanceof StackedCakeBlockEntity entity && world.getBlockState(pos.up()).isSolidBlock(world, pos)){
            entity.STACKED_CAKES.pop();
        }
        return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    private boolean isEaten(StackedCakeBlockEntity entity){
        return entity.STACKED_CAKES.peek().contains(CakeBlock.BITES) && entity.STACKED_CAKES.peek().get(CakeBlock.BITES) > 0;
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        if(!world.isClient()  && !player.getMainHandStack().isEmpty() && world.getBlockEntity(pos) instanceof StackedCakeBlockEntity stackedCakeBlockEntity){
            if(player.getMainHandStack().isIn(OtherRegistry.STACKED_CAKES) && !this.isEaten(stackedCakeBlockEntity)){

                this.addCakeLayer(stack, world, pos, stackedCakeBlockEntity, state);
                player.swingHand(hand, true);
                stack.decrementUnlessCreative(1, player);

                world.updateListeners(pos, stackedCakeBlockEntity.getCachedState(), stackedCakeBlockEntity.getCachedState(), 3);
                return ActionResult.SUCCESS;
            }

            else if(player.getMainHandStack().isIn(ItemTags.CANDLES) ){
                if (stack.getItem() instanceof BlockItem blockItem) {
                    BlockState candleState = blockItem.getBlock().getDefaultState();

                    stackedCakeBlockEntity.CANDLE_STATE = Optional.of(candleState.with(CandleBlock.LIT, false));
                    stackedCakeBlockEntity.markDirty();

                    world.updateListeners(pos, stackedCakeBlockEntity.getCachedState(), stackedCakeBlockEntity.getCachedState(), 3);
                    return ActionResult.SUCCESS;
                }
            }

            else if (stack.isOf(Items.FLINT_AND_STEEL)) {
                if (stackedCakeBlockEntity.CANDLE_STATE.isPresent()) {
                    BlockState candleState = stackedCakeBlockEntity.CANDLE_STATE.get();
                    if (candleState.contains(CandleBlock.LIT) && !candleState.isAir() && !candleState.get(CandleBlock.LIT)) {

                        stackedCakeBlockEntity.CANDLE_STATE = Optional.of(candleState.with(CandleBlock.LIT, true));


                        stackedCakeBlockEntity.markDirty();
                        world.setBlockState(pos, world.getBlockState(pos).with(LIGHT_LEVEL, 3).with(LIT, true));
                        world.updateListeners(pos, state, state, Block.NOTIFY_ALL);

                        stack.damage(1, player);
                        world.playSound(null, pos, net.minecraft.sound.SoundEvents.ITEM_FLINTANDSTEEL_USE, net.minecraft.sound.SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);

                        return ActionResult.SUCCESS;
                    }
                }
            }
        }

        else if (world.isClient() && player.getMainHandStack().isEmpty()) {
            if (tryEat(world, pos, state, player).isAccepted()) {
                return ActionResult.SUCCESS;
            }

            if (player.getStackInHand(Hand.MAIN_HAND).isEmpty()) {
                return ActionResult.CONSUME;
            }
        }else if(!world.isClient() && player.getMainHandStack().isEmpty()){
            removeCandle(world, pos);
        }

        return player.getMainHandStack().isEmpty() ? tryEat(world, pos, state, player) : ActionResult.FAIL;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StackedCakeBlockEntity(pos, state);
    }
}
