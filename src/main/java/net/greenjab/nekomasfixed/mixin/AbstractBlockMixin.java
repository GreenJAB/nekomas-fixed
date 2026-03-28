package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.RopeBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {

    @Inject(method = "onUseWithItem", at = @At("HEAD"), cancellable = true)
    private void onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
                               PlayerEntity player, Hand hand, BlockHitResult hit,
                               CallbackInfoReturnable<ActionResult> cir) {

        if (state.isOf(BlockRegistry.ROPE) && stack.isOf(ItemRegistry.ROPE_ITEM)) {
            stack.decrementUnlessCreative(1, player);
            BlockPos.Mutable mutable = new BlockPos.Mutable(pos.getX(), pos.getY(), pos.getZ());
            while (world.getBlockState(mutable).isOf(BlockRegistry.ROPE)) {
                mutable.move(0, -1, 0);
            }

            if (world.getBlockState(mutable).isIn(BlockTags.REPLACEABLE)) {
                world.setBlockState(
                        mutable,
                        BlockRegistry.ROPE.getDefaultState().with(RopeBlock.IS_CONNECTED, true)
                );

                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}
