package net.greenjab.nekomasfixed.registry.block;

import net.greenjab.nekomasfixed.registry.block.entity.HollowOakLogBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jspecify.annotations.Nullable;

public class HollowOakLogBlock extends AbstractHollowLogBlock{
    public HollowOakLogBlock(Settings settings) {
        super(settings);
    }
    //finished all the hollow logs
    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HollowOakLogBlockEntity(pos, state);
    }
}
