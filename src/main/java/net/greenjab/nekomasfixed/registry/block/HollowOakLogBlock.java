package net.greenjab.nekomasfixed.registry.block;

import net.greenjab.nekomasfixed.registry.block.entity.AbstractHollowLogBlockEntity;
import net.greenjab.nekomasfixed.registry.block.entity.HollowOakLogBlockEntity;
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

public class HollowOakLogBlock extends AbstractHollowLogBlock{
    public HollowOakLogBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        System.out.println("Hollow log has been clicked!!!");
        if(!world.isClient() && stack.isOf(Items.WATER_BUCKET) && world.getBlockEntity(pos) instanceof AbstractHollowLogBlockEntity blockEntity){
            if (state.get(PillarBlock.AXIS).isVertical()){
                player.setStackInHand(Hand.MAIN_HAND, Items.BUCKET.getDefaultStack());
                world.setBlockState(pos, state.with(HAS_WATER, true), Block.NOTIFY_ALL);
                player.dropStack((ServerWorld) world, blockEntity.getStoredBlock().getPickStack(world, pos, true));
                return ActionResult.SUCCESS;
            }else{
                return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
            }
        }else {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HollowOakLogBlockEntity(pos, state);
    }
}
