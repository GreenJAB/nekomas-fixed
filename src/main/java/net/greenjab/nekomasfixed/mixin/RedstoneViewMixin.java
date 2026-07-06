package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.item.RedstoneStrikerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SignalGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignalGetter.class)
public interface RedstoneViewMixin {
    @Inject(method = "getSignal(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getSignal(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)I"), cancellable = true)
    private void powerBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Integer> cir, @Local BlockState blockState) {
        if (RedstoneStrikerItem.STRUCK_WIRES.containsKey(new GlobalPos(((Level)this).dimension(), pos)) && blockState.isRedstoneConductor((SignalGetter)this, pos)) {
            cir.setReturnValue(15);
        }
    }

    @Inject(method = "hasNeighborSignal", at = @At("HEAD"), cancellable = true)
    private void powerRedstoneComponents(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (RedstoneStrikerItem.STRUCK_WIRES.containsKey(new GlobalPos(((Level)this).dimension(), pos))) {
            cir.setReturnValue(true);
        }
    }
}
