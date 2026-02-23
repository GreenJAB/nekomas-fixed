package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.HoneyCauldronBlock;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractCauldronBlock.class)
public class CauldronMixin {




    @Inject(method = "onUseWithItem", at = @At("HEAD"), cancellable = true)
    private void onCauldronUse(ItemStack stack, BlockState state, World world, BlockPos pos,
                               PlayerEntity player, Hand hand, BlockHitResult hit,
                               CallbackInfoReturnable<ActionResult> cir) {
        if(stack.isEmpty()){
            int level = state.get(HoneyCauldronBlock.HONEY_LEVEL);
            if(level == HoneyCauldronBlock.MAX_LEVEL){
                player.getInventory().offerOrDrop(new ItemStack(Items.HONEY_BLOCK, 1));
            }
        }
        if (stack.getItem() == Items.HONEY_BOTTLE && state.getBlock() == Blocks.CAULDRON ) {
            if (!world.isClient()) {
                world.setBlockState(pos, BlockRegistry.HONEY_CAULDRON.getDefaultState()
                        .with(HoneyCauldronBlock.HONEY_LEVEL, 1));
                stack.decrement(1);
                player.getInventory().offerOrDrop(new ItemStack(Items.GLASS_BOTTLE));
            }
            cir.setReturnValue(ActionResult.SUCCESS);
            return;
        }

        if (state.getBlock() == BlockRegistry.HONEY_CAULDRON) {
            int level = state.get(HoneyCauldronBlock.HONEY_LEVEL);

            if (stack.getItem() == Items.GLASS_BOTTLE) {
                if (!world.isClient()) {
                    player.getInventory().offerOrDrop(new ItemStack(Items.HONEY_BOTTLE));
                    stack.decrement(1);

                    if (level > 1) {
                        world.setBlockState(pos, state.with(HoneyCauldronBlock.HONEY_LEVEL, level - 1));
                    } else {
                        world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
                    }
                }
                cir.setReturnValue(ActionResult.SUCCESS);
                return;
            }


            if (stack.getItem() == Items.HONEY_BOTTLE && level < 4) {
                if (!world.isClient()) {
                    world.setBlockState(pos, state.with(HoneyCauldronBlock.HONEY_LEVEL, level + 1));
                    stack.decrement(1);
                    player.getInventory().offerOrDrop(new ItemStack(Items.GLASS_BOTTLE));
                }
                cir.setReturnValue(ActionResult.SUCCESS);
                return;
            }
        }
    }
}