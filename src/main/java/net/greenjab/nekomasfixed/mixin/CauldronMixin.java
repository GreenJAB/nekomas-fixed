package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.entity.SoupCauldronBlockEntity;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.component.DataComponentTypes;
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

@Mixin(AbstractCauldronBlock.class)
public class CauldronMixin {

    @Inject(method = "onUseWithItem", at = @At("HEAD"), cancellable = true)
    private void onCauldronUse(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (state.getBlock() == Blocks.WATER_CAULDRON) {
            if (state.getBlock() instanceof LeveledCauldronBlock leveledCauldronBlock && leveledCauldronBlock.isFull(state)) {
                if (world.getBlockState(pos.down()).isIn(BlockTags.FIRE) || world.getBlockState(pos.down()).isIn(BlockTags.CAMPFIRES)) {
                    if (stack.getComponents().contains(DataComponentTypes.FOOD) || stack.getComponents().contains(DataComponentTypes.POTION_CONTENTS)) {
                        world.setBlockState(pos, BlockRegistry.SOUP_CAULDRON.getDefaultState());
                        if (world.getBlockEntity(pos) instanceof SoupCauldronBlockEntity soup ) {
                            soup.addInput(stack.copyWithCount(1));
                            stack.decrementUnlessCreative(1, player);
                            cir.setReturnValue(ActionResult.SUCCESS);
                        }
                    }
                }
            }
        }
    }
}