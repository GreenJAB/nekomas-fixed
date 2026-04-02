package net.greenjab.nekomasfixed.registry.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class HollowOakLogBlock extends PillarBlock {
    private static final VoxelShape RAYCAST_SHAPE = Block.createColumnShape((double)12.0F, (double)4.0F, (double)16.0F);
    public static final VoxelShape OUTLINE_SHAPE = (VoxelShape) Util.make(() -> {
        int i = 4;
        int j = 3;
        int k = 2;
        return VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.union(Block.createColumnShape((double)16.0F, (double)8.0F, (double)0.0F, (double)3.0F), new VoxelShape[]{Block.createColumnShape((double)8.0F, (double)16.0F, (double)0.0F, (double)3.0F), Block.createColumnShape((double)12.0F, (double)0.0F, (double)3.0F), RAYCAST_SHAPE}), BooleanBiFunction.ONLY_FIRST);
    });
    public HollowOakLogBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPE;
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return RAYCAST_SHAPE;
    }
}
