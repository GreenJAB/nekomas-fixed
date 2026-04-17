package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GoatHornItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {
    @Inject(method = "onUseWithItem", at= @At("HEAD"), cancellable = true)
    private void customOnUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {

        if(!world.isClient()){
            if (stack.isOf(Items.GOAT_HORN)) {
                Direction direction = hit.getSide();
                BlockPos placePos = pos.offset(direction);

                BlockState newState = BlockRegistry.GOAT_HORN.getDefaultState().with(HorizontalFacingBlock.FACING, direction.getOpposite());
                if (newState.contains(Properties.HORIZONTAL_FACING)) {
                    newState = newState.with(Properties.HORIZONTAL_FACING, direction.getOpposite());
                }
                if (world.getBlockState(placePos).isReplaceable()) {
                    if(stack.getItem() instanceof GoatHornItem goatHornItem){

                    }
                    world.setBlockState(placePos, newState);
                    player.getMainHandStack().decrementUnlessCreative(1, player);
                    cir.setReturnValue(ActionResult.SUCCESS);
                }
            }

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
