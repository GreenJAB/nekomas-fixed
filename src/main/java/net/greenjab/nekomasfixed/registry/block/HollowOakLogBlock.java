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
    private static final VoxelShape RAYCAST_SHAPE = Block.createColumnShape(12.0F, 4.0F, 16.0F);
    public static final VoxelShape OUTLINE_SHAPE = Util.make(() -> VoxelShapes.combineAndSimplify(
            VoxelShapes.fullCube(),
            VoxelShapes.union(Block.createColumnShape(16.0F, 8.0F, 0.0F, 0.0F),
                    Block.createColumnShape(8.0F, 16.0F, 0.0F, 0.0F),
                    Block.createColumnShape(12.0F, 0.0F, 0.0F), RAYCAST_SHAPE),
            BooleanBiFunction.ONLY_FIRST));
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
