package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.registry.entity.BigBoatEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    @Inject(method = "hasRidingInventory", at = @At(value = "RETURN"), cancellable = true)
    private void bigBoatNoChestNormal(CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            if (MinecraftClient.getInstance().player.getVehicle() instanceof BigBoatEntity bigBoatEntity) {
                cir.setReturnValue(bigBoatEntity.hasChest());
            }
        }
    }
}