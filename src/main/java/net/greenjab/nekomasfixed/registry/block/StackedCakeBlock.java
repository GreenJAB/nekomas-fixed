package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.entity.StackedCakeBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
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
import java.util.Map;

public class StackedCakeBlock extends Block implements BlockEntityProvider {
    public static final IntProperty BITES;
    public static final MapCodec<StackedCakeBlock> CODEC = createCodec(StackedCakeBlock::new);
    private static final Map<Integer, VoxelShape[]> SHAPES_BY_BITES_AND_LAYER = new HashMap<>();

    public StackedCakeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(BITES, 0));
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSolid();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BITES);
    }

    @Override
    public MapCodec<StackedCakeBlock> getCodec(){return CODEC;}

    static {
        BITES = Properties.BITES;

        for (int layer = 0; layer < 4; layer++) {
            final int yMin = layer * 8;
            final int yMax = yMin + 8;

            SHAPES_BY_BITES_AND_LAYER.put(layer, Block.createShapeArray(6, bites -> Block.createCuboidShape(1 + bites * 2, yMin, 1, 15, yMax, 15)));
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
                }
            }

            return ActionResult.SUCCESS;
        }
    }

    private boolean isFullyStacked(StackedCakeBlockEntity blockEntity){
        return blockEntity.STACKED_CAKES.size() == 3;
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
       return SHAPES_BY_BITES_AND_LAYER.get(0)[state.get(BITES)];
    }

    public void addCakeLayer(World world, BlockPos pos, StackedCakeBlockEntity entity, BlockState state){
        if(!this.isFullyStacked(entity) ){
            if(entity.STACKED_CAKES.size() == 2 ){
                if(world.getBlockState(pos.up()).isSolid()) return;
                entity.STACKED_CAKES.push(Blocks.CAKE.getDefaultState().with(CakeBlock.BITES, state.get(BITES)));
                entity.markDirty();
                return;
            }else{
                entity.STACKED_CAKES.push(Blocks.CAKE.getDefaultState().with(CakeBlock.BITES, state.get(BITES)));
                entity.markDirty();
            }
        }
    }

    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient()  && !player.getMainHandStack().isEmpty() && world.getBlockEntity(pos) instanceof StackedCakeBlockEntity stackedCakeBlockEntity && player.getMainHandStack().isOf(Items.CAKE)){
            this.addCakeLayer(world, pos, stackedCakeBlockEntity, state);
            player.swingHand(hand, true);
            if(world != null) {
                world.updateListeners(pos, stackedCakeBlockEntity.getCachedState(), stackedCakeBlockEntity.getCachedState(), 3);
            }
            return ActionResult.SUCCESS;
        }
        else if (world.isClient() && player.getMainHandStack().isEmpty()) {
            if (tryEat(world, pos, state, player).isAccepted()) {
                return ActionResult.SUCCESS;
            }

            if (player.getStackInHand(Hand.MAIN_HAND).isEmpty()) {
                return ActionResult.CONSUME;
            }
        }

        return player.getMainHandStack().isEmpty() ? tryEat(world, pos, state, player) : ActionResult.FAIL;

    }


    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StackedCakeBlockEntity(pos, state);
    }
}
