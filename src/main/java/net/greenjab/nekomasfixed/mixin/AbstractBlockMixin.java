package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.entity.AbstractHollowLogBlockEntity;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.greenjab.nekomasfixed.registry.block.AbstractHollowLogBlock.HAS_WATER;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {
    @Inject(method = "onUseWithItem", at= @At("HEAD"), cancellable = true)
    private void customOnUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if(!world.isClient()){
            if(stack.isOf(ItemRegistry.BOABAB_SEEDS) && state.isIn(BlockTags.LEAVES)){
                BlockPos below = pos.down();
                if (world.getBlockState(below).isAir() || world.getBlockState(below).isIn(BlockTags.REPLACEABLE)) {
                    world.setBlockState(below, BlockRegistry.BOABAB_FRUIT.getDefaultState());
                    stack.decrementUnlessCreative(1, player);
                }

                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}
