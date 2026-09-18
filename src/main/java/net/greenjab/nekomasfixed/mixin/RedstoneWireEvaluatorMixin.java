package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.item.RedstoneStrikerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.redstone.RedstoneWireEvaluator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedstoneWireEvaluator.class)
public class RedstoneWireEvaluatorMixin {
    @Inject(method = "getIncomingWireSignal", at = @At("HEAD"), cancellable = true)
    protected void powerWire(Level level, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (RedstoneStrikerItem.STRUCK_WIRES.containsKey(new GlobalPos(level.dimension(), pos))) {
            cir.setReturnValue(15);
        }
    }
}
