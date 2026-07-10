package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.registry.entity.BigBoat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {

    @Inject(method = "isServerControlledInventory", at = @At(value = "RETURN"), cancellable = true)
    private void bigBoatNoChestNormal(CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            if (Minecraft.getInstance().player.getVehicle() instanceof BigBoat bigBoat) {
                cir.setReturnValue(bigBoat.hasChest());
            }
        }
    }
}