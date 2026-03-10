package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.greenjab.nekomasfixed.util.ModData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
        if (ModData.combos.containsKey(PE.getUuid())){
            int comboTimer = ModData.combos.get(PE.getUuid())-1;
            if (comboTimer<=0) ModData.combos.remove(PE.getUuid());
            else ModData.combos.put(PE.getUuid(), comboTimer);
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

    @Redirect(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;sidedDamage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
            )
    )
    private boolean preventFeatherDamage(Entity target, DamageSource source, float amount) {
        PlayerEntity PE = (PlayerEntity)(Object)this;

        if (PE.getMainHandStack().isOf(Items.FEATHER)) {
            if (target instanceof LivingEntity livingTarget) {
                livingTarget.takeKnockback(
                        0.4,
                        MathHelper.sin(PE.getYaw() * ((float)Math.PI / 180F)),
                        (-MathHelper.cos(PE.getYaw() * ((float)Math.PI / 180F)))
                );
            }
            return true;
        }

        if (PE.getStackInHand(Hand.MAIN_HAND).isIn(OtherRegistry.SICKLES) && PE.getStackInHand(Hand.OFF_HAND).isIn(OtherRegistry.SICKLES)) target.timeUntilRegen = 10;

        return target.sidedDamage(source, amount);
    }

    @WrapOperation(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;interact(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"))
    private ActionResult allowOffhandAttack(Entity instance, PlayerEntity player, Hand hand, Operation<ActionResult> original) {
        if (player.getStackInHand(Hand.MAIN_HAND).isIn(OtherRegistry.SICKLES) && player.getStackInHand(Hand.OFF_HAND).isIn(OtherRegistry.SICKLES)) return ActionResult.PASS;
        return original.call(instance, player, hand);
    }

    @Inject(method = "getAttackCooldownDamageModifier", at = @At("HEAD"), cancellable = true)
    private void offHandDamage(CallbackInfoReturnable<Float> cir){
        PlayerEntity player = (PlayerEntity)(Object)this;
        if (player.getStackInHand(Hand.MAIN_HAND).isIn(OtherRegistry.SICKLES) && player.getStackInHand(Hand.OFF_HAND).isIn(OtherRegistry.SICKLES)) cir.setReturnValue(1f);
    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSource;isScaledWithDifficulty()Z"))
    private void cancelCombo(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity PE = (PlayerEntity)(Object)this;
        ModData.combos.remove(PE.getUuid());
    }

    @ModifyExpressionValue(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getBonusAttackDamage(Lnet/minecraft/entity/Entity;FLnet/minecraft/entity/damage/DamageSource;)F"))
    private float comboDamage(float original, @Local ItemStack itemStack, @Local(ordinal = 0) float baseAttackDamage){
        if (itemStack.getComponents().contains(OtherRegistry.COMBO_MULTIPLIER)) {
            PlayerEntity player = (PlayerEntity)(Object)this;
            int comboTimer = ModData.combos.getOrDefault(player.getUuid(), 0);
            int comboSec = ceilDiv(comboTimer, 30);
            int multiplier = itemStack.getComponents().get(OtherRegistry.COMBO_MULTIPLIER).multiplier();

            if (!player.getEntityWorld().isClient()) ModData.combos.put(player.getUuid(), Math.min((comboSec+1)*30, 10*30));

            return original + baseAttackDamage*comboSec*multiplier*0.01f;
        }
        return original;
    }

    @Unique
    private static int ceilDiv(int x, int y) {
        final int q = x / y;
        if ((x ^ y) >= 0 && (q * y != x)) {
            return q + 1;
        }
        return q;
    }
}
