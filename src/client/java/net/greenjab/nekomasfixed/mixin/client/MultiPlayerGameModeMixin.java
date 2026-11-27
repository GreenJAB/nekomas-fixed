package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.registry.entity.BigBoat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {
    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "isServerControlledInventory", at = @At("RETURN"), cancellable = true)
    private void bigBoatWithoutChest(CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() && minecraft.player.getVehicle() instanceof BigBoat bigBoat) {
            cir.setReturnValue(bigBoat.hasChest());
        }
    }
}
