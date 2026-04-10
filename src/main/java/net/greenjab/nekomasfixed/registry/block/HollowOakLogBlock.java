package net.greenjab.nekomasfixed.registry.block;

import net.greenjab.nekomasfixed.registry.block.entity.HollowOakLogBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jspecify.annotations.Nullable;

public class HollowOakLogBlock extends AbstractHollowLogBlock{
    public HollowOakLogBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return super.getOutlineShape(state, world, pos, context);
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return super.getRaycastShape(state, world, pos);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HollowOakLogBlockEntity(pos, state);
    }


}
