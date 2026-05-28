package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.item.RedstoneStrikerItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RedstoneController;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedstoneController.class)
public class RedstoneControllerMixin {
    @Inject(method = "calculateWirePowerAt", at = @At("HEAD"), cancellable = true)
    protected void calculateWirePowerAt(World world, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (RedstoneStrikerItem.STRUCK_WIRES.containsKey(pos)) {
            cir.setReturnValue(15);
        }
    }


}
