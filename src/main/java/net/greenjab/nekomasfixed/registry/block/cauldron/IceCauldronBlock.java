package net.greenjab.nekomasfixed.registry.block.cauldron;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class IceCauldronBlock extends AbstractCauldronBlock {
    public static final MapCodec<IceCauldronBlock> CODEC = createCodec(IceCauldronBlock::new);
    private static final VoxelShape ICE_SHAPE = Block.createColumnShape(12.0, 4.0, 15.0);
    private static final VoxelShape INSIDE_COLLISION_SHAPE = VoxelShapes.union(AbstractCauldronBlock.OUTLINE_SHAPE, ICE_SHAPE);

    @Override
    public MapCodec<IceCauldronBlock> getCodec() {
        return CODEC;
    }

    public IceCauldronBlock(AbstractBlock.Settings settings) {
        super(settings, createBehaviorMap());
    }

    private static CauldronBehavior.CauldronBehaviorMap createBehaviorMap() {
        var behaviorMap = CauldronBehavior.createMap("ice");
        var map = behaviorMap.map();


        return behaviorMap;
    }


    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.isClient()) {
            world.scheduleBlockTick(pos, this, 200);
        }
    }

    @Override
    protected double getFluidHeight(BlockState state) {
        return 0.9375;
    }

    @Override
    public boolean isFull(BlockState state) {
        return true;
    }

    @Override
    protected VoxelShape getInsideCollisionShape(BlockState state, BlockView world, BlockPos pos, Entity entity) {
        return INSIDE_COLLISION_SHAPE;
    }

    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos, Direction direction) {
        return 3;
    }
}