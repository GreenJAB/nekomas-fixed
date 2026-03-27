package net.greenjab.nekomasfixed.registry.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;


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
        return this.getDefaultState().with(IS_CONNECTED, connected);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        for(int i = pos.getY(); i>=pos.getY()-15; --i){
            BlockPos currentPos = new BlockPos(pos.getX(), i, pos.getZ());
            if(world.getBlockState(currentPos).isOf(this)){
                world.setBlockState(currentPos, Blocks.AIR.getDefaultState(), 1);
            }
        }
    }
}
