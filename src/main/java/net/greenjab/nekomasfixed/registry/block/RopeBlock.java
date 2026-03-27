package net.greenjab.nekomasfixed.registry.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;


public class RopeBlock extends  Block  {
    public static final BooleanProperty IS_CONNECTED = BooleanProperty.of("is_connected");
    public static final BooleanProperty WATERLOGGED = BooleanProperty.of("waterlogged");

    public RopeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(IS_CONNECTED, false).with(WATERLOGGED, false));
    }
    
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(IS_CONNECTED, WATERLOGGED);
    }
    
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        boolean connected = world.getBlockState(pos.up()).isOf(this);
        if(world.getBlockState(pos.up()).isOf(Blocks.AIR)){
            return Blocks.AIR.getDefaultState();
        }
        return this.getDefaultState().with(IS_CONNECTED, connected);
    }

    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
            if (this.canPlaceAt(pos, world)) {
                tickView.scheduleBlockTick(pos, this, 1);
            }return this.getDefaultState();
    }

    protected boolean canPlaceAt(BlockPos pos, WorldView world){
        BlockState stateAbove = world.getBlockState(pos.up());
        return !stateAbove.isOf(Blocks.AIR);
    }

    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (this.canPlaceAt(pos, world)) {
            world.breakBlock(pos, true);
        }
    }

}
