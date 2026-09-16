package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {

    // Turtle Flippers: on-ground (not in water) the player gets Dolphin's Grace.
    // Applied at tick RETURN (after the effect countdown), matching vanilla's
    // turtleHelmetTick, so the per-tick re-add is an idempotent re-pin (no HUD flicker).
    @Inject(method = "tick", at = @At("RETURN"))
    private void flipperDolphinsGrace(CallbackInfo ci) {
        Player PE = (Player) (Object) this;
        if (PE.onGround() && !PE.isInWater()) {
            if (PE.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.TURTLE_BOOTS)) {
                PE.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 200, 0, false, false, true));
            }
        }
    }

    // Turtle Knee Pads: mining speed is not reduced underwater (restores the
    // onGround branch of getDestroySpeed while eye-in-water wearing the leggings).
    @ModifyExpressionValue(method = "getDestroySpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;onGround()Z"))
    private boolean turtleLeggingsMining(boolean original) {
        Player PE = (Player) (Object) this;
        if (PE.isEyeInFluid(FluidTags.WATER)) {
            if (PE.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.TURTLE_LEGGINGS)) {
                return true;
            }
        }
        return original;
    }

    // feather deals knockback instead of damage
    @WrapOperation(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private boolean preventFeatherDamage(Entity target, DamageSource source, float damage, Operation<Boolean> original) {
        Player PE = (Player) (Object) this;

        if (PE.getMainHandItem().is(Items.FEATHER)) {
            if (target instanceof LivingEntity livingTarget) {
                livingTarget.knockback(
                        0.4,
                        Mth.sin(PE.getYRot() * ((float) Math.PI / 180F)),
                        (-Mth.cos(PE.getYRot() * ((float) Math.PI / 180F)))
                );
            }
            return true;
        }

        return original.call(target, source, damage);
    }
}