package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @WrapOperation(method = "doItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;interactEntityAtLocation(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/util/hit/EntityHitResult;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"))
    private ActionResult allowOffhandAttack(ClientPlayerInteractionManager instance, PlayerEntity player, Entity entity, EntityHitResult hitResult, Hand hand, Operation<ActionResult> original) {
        if (player.getStackInHand(Hand.MAIN_HAND).isIn(OtherRegistry.SICKLES) && player.getStackInHand(Hand.OFF_HAND).isIn(OtherRegistry.SICKLES)) return ActionResult.PASS;
        return original.call(instance, player, entity, hitResult, hand);
    }

}