package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.util.ModData;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {

    // combo weapons (sickles) build a stacking damage bonus the longer the chain goes.
    // Only fully-charged hits extend the combo, so spam-clicking can't farm it. The
    // charge must be captured at attack HEAD — Player.attack resets the ticker early,
    // so reading getAttackStrengthScale mid-method always sees 0.
    @Unique
    private float lastAttackScale = 1.0F;

    @Unique
    private static int ceilDiv(int x, int y) {
        final int q = x / y;
        if ((x ^ y) >= 0 && (q * y != x)) {
            return q + 1;
        }
        return q;
    }

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

        // dual-wielding sickles: shorter invulnerability window so both hits connect
        if (PE.getItemInHand(InteractionHand.MAIN_HAND).is(ModTags.SICKLES) && PE.getItemInHand(InteractionHand.OFF_HAND).is(ModTags.SICKLES)) {
            target.invulnerableTime = 10;
        }

        return original.call(target, source, damage);
    }

    // dual-wielding sickles: skip the vanilla entity interact so the right-click
    // falls through to the sickle's off-hand attack (interactLivingEntity)
    @WrapOperation(method = "interactOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;interact(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;"))
    private InteractionResult allowOffhandAttack(Entity instance, Player player, InteractionHand hand, Operation<InteractionResult> original) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModTags.SICKLES) && player.getItemInHand(InteractionHand.OFF_HAND).is(ModTags.SICKLES))
            return InteractionResult.PASS;
        return original.call(instance, player, hand);
    }

    @Inject(method = "attack", at = @At("HEAD"))
    private void captureAttackCharge(Entity target, CallbackInfo ci) {
        lastAttackScale = ((Player) (Object) this).getAttackStrengthScale(0.5F);
    }

    @ModifyExpressionValue(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;getAttackDamageBonus(Lnet/minecraft/world/entity/Entity;FLnet/minecraft/world/damagesource/DamageSource;)F"))
    private float comboDamage(float original, @Local ItemStack attackingItemStack, @Local(ordinal = 0) float baseDamage) {
        if (attackingItemStack.has(ComponentRegistry.COMBO_MULTIPLIER)) {
            Player player = (Player) (Object) this;
            int comboTimer = ModData.combos.getOrDefault(player.getUUID(), 0);
            int comboSec = ceilDiv(comboTimer, 30);
            int multiplier = attackingItemStack.get(ComponentRegistry.COMBO_MULTIPLIER).multiplier();

            if (!player.level().isClientSide() && lastAttackScale >= 1.0F) {
                ModData.combos.put(player.getUUID(), Math.min((comboSec + 1) * 30, 10 * 30));
            }

            return original + baseDamage * comboSec * multiplier * 0.01f;
        }
        return original;
    }

    // taking damage breaks the combo chain. 1.21.1 has no hurtServer/scalesWithDifficulty
    // anchor, so reset when the damage actually lands (Player.actuallyHurt).
    @Inject(method = "actuallyHurt", at = @At("HEAD"))
    private void cancelCombo(DamageSource source, float damage, CallbackInfo ci) {
        ModData.combos.remove(((Player) (Object) this).getUUID());
    }

    // the combo timer counts down each tick and removes itself at 0 (main's customTickLogics).
    @Inject(method = "tick", at = @At("RETURN"))
    private void tickComboDecay(CallbackInfo ci) {
        Player PE = (Player) (Object) this;
        if (ModData.combos.containsKey(PE.getUUID())) {
            int comboTimer = ModData.combos.get(PE.getUUID()) - 1;
            if (comboTimer <= 0) ModData.combos.remove(PE.getUUID());
            else ModData.combos.put(PE.getUUID(), comboTimer);
        }
    }
}