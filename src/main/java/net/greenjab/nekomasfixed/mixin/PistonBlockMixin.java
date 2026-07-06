package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.item.RedstoneStrikerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SignalGetter;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonBaseBlock.class)
public class PistonBlockMixin {
    @Inject(method = "getNeighborSignal", at = @At("HEAD"), cancellable = true)
    protected void powerPiston(SignalGetter world, BlockPos pos, Direction pistonFace, CallbackInfoReturnable<Boolean> cir) {
        if (RedstoneStrikerItem.STRUCK_WIRES.containsKey(new GlobalPos(((Level)world).dimension(), pos))) {
            cir.setReturnValue(true);
        }
    }
}
