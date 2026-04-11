package net.greenjab.nekomasfixed.registry.block;

import net.greenjab.nekomasfixed.registry.block.entity.HollowDarkOakLogBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jspecify.annotations.Nullable;

public class HollowDarkOakLogBlock extends AbstractHollowLogBlock{
    private HollowDarkOakLogBlockEntity blockEntity;
    public HollowDarkOakLogBlock(Settings settings) {
        super(settings);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        this.blockEntity = new HollowDarkOakLogBlockEntity(pos, state);
        return this.blockEntity;
    }
}
