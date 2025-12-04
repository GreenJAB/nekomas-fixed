package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.FluidTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isSubmergedIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
    private void turtleBootsSpeed(CallbackInfo ci) {
        PlayerEntity PE = (PlayerEntity)(Object)this;
        if (PE.isOnGround() && !PE.isTouchingWater()){
            if (PE.getEquippedStack(EquipmentSlot.FEET).isOf(ItemRegistry.TURTLE_BOOTS)) {
                PE.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 200, 0, false, false, true));
            }
        }
    }

    @ModifyExpressionValue(method = "getBlockBreakingSpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isOnGround()Z"))
    private boolean turtleLeggingsMining(boolean original) {
        PlayerEntity PE = (PlayerEntity)(Object)this;
        if (PE.isSubmergedIn(FluidTags.WATER)) {
            if (PE.getEquippedStack(EquipmentSlot.LEGS).isOf(ItemRegistry.TURTLE_LEGGINGS)) {
               return true;
            }
        }
        return original;
    }
}
