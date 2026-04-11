package net.greenjab.nekomasfixed.registry.block;

import net.greenjab.nekomasfixed.registry.block.entity.HollowDarkOakLogBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;

public class HollowDarkOakLogBlock extends AbstractHollowLogBlock{
    private HollowDarkOakLogBlockEntity blockEntity;
    public HollowDarkOakLogBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(stack.isOf(Items.SHEARS) && !world.isClient() && !this.blockEntity.getStoredBlock().isAir()){
            player.dropStack((ServerWorld) world, this.blockEntity.getStoredBlock().getPickStack(world, pos, true));
        }
        return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        this.blockEntity = new HollowDarkOakLogBlockEntity(pos, state);
        return this.blockEntity;
    }
}
