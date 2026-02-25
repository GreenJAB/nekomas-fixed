package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    private int offHandSwingDelay = 0; // ticks to delay off-hand swing

    @Redirect(
            method = "doAttack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;swingHand(Lnet/minecraft/util/Hand;)V"
            )
    )
    private void redirectSwing(ClientPlayerEntity player, Hand hand) {
        if (player.getMainHandStack().isIn(OtherRegistry.SICKLES)
                && player.getOffHandStack().isIn(OtherRegistry.SICKLES)) {

            player.swingHand(Hand.MAIN_HAND);
            offHandSwingDelay = 4;
            return;
        }

        player.swingHand(hand);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (offHandSwingDelay > 0) {
            offHandSwingDelay--;
            if (offHandSwingDelay == 0 && MinecraftClient.getInstance().player != null) {
                MinecraftClient.getInstance().player.swingHand(Hand.OFF_HAND);
            }
        }
    }
}

