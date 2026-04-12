package net.greenjab.nekomasfixed.registry.block;

import net.greenjab.nekomasfixed.registry.block.entity.AbstractHollowLogBlockEntity;
import net.greenjab.nekomasfixed.registry.block.entity.HollowOakLogBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;

public class HollowOakLogBlock extends AbstractHollowLogBlock{
    public HollowOakLogBlock(Settings settings) {
        super(settings);
    }


    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.get(HAS_WATER)
                ? Fluids.WATER.getStill(false)
                : super.getFluidState(state);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HollowOakLogBlockEntity(pos, state);
    }
}
