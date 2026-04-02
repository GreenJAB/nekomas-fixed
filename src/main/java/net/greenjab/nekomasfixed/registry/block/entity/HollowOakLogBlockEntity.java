package net.greenjab.nekomasfixed.registry.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockPos;

public class HollowOakLogBlockEntity extends BlockEntity implements Clearable {
    private BlockState blockState;

    public HollowOakLogBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public BlockState getBlockState(){return this.blockState;}

    @Override
    public void clear() {
        blockState = Blocks.AIR.getDefaultState();
    }
}
