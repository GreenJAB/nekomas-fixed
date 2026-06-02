package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.item.RedstoneStrikerItem;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RedstoneView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedstoneView.class)
public interface RedstoneViewMixin {
    @Inject(method = "getEmittedRedstonePower(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getWeakRedstonePower(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)I"), cancellable = true)
    private void powerBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Integer> cir, @Local BlockState blockState) {
        if (RedstoneStrikerItem.STRUCK_WIRES.containsKey(pos) && blockState.isSolidBlock((RedstoneView)this, pos)) {
            cir.setReturnValue(15);
        }
    }

    @Inject(method = "isReceivingRedstonePower", at = @At("HEAD"), cancellable = true)
    private void powerRedstoneComponents(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (RedstoneStrikerItem.STRUCK_WIRES.containsKey(pos)) {
            cir.setReturnValue(true);
        }
    }
}
