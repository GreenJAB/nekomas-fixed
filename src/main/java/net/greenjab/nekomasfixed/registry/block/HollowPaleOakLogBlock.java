package net.greenjab.nekomasfixed.registry.block;

import net.greenjab.nekomasfixed.registry.block.entity.HollowPaleOakLogBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jspecify.annotations.Nullable;

public class HollowPaleOakLogBlock extends AbstractHollowLogBlock{
    public HollowPaleOakLogBlock(Settings settings) {
        super(settings);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HollowPaleOakLogBlockEntity(pos, state);
    }
}
