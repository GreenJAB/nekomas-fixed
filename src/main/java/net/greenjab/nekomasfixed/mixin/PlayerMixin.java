package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.screen.config.ModConfigValues;
import net.greenjab.nekomasfixed.util.ModData;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(Player.class)
public class PlayerMixin {

    @Unique
    private void checkForEdibles(Player PE){
        if (PE.level().getRandom().nextInt(15*20) > 0) return;
        Random random = new Random();
        Container inventory = PE.getInventory();
        int i = random.nextInt(inventory.getContainerSize());
        ItemStack food = inventory.getItem(i);
        if (!food.isEmpty() && food.is(ModTags.FOOD_ITEMS)) {
            food.shrink(1);
            ItemStack rotten = new ItemStack(Items.ROTTEN_FLESH, 1);
            if (!PE.getInventory().add(rotten.copy())) {
                PE.drop(rotten, false);
            }
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z"))
    private void customTickLogics(CallbackInfo ci) {
        Player PE = (Player)(Object)this;

        if (PE.onGround() && !PE.isInWater()) {
            if (PE.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.TURTLE_BOOTS)) {
                PE.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 200, 0, false, false, true));
            }
        }
        if (PE.level().getBiome(PE.blockPosition()).is(BiomeTags.IS_NETHER)) {
            if (!PE.isCreative()&&!PE.isSpectator() && ModConfigValues.netherFoodRotting){
                this.checkForEdibles(PE);
            }
        }
        if (ModData.combos.containsKey(PE.getUUID())){
            int comboTimer = ModData.combos.get(PE.getUUID())-1;
            if (comboTimer<=0) ModData.combos.remove(PE.getUUID());
            else ModData.combos.put(PE.getUUID(), comboTimer);
        }
    }

    @ModifyExpressionValue(method = "getDestroySpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;onGround()Z"))
    private boolean turtleLeggingsMining(boolean original) {
        Player PE = (Player)(Object)this;
        if (PE.isEyeInFluid(FluidTags.WATER)) {
            if (PE.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.TURTLE_LEGGINGS)) {
               return true;
            }
        }
        return original;
    }

    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurtOrSimulate(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private boolean preventFeatherDamage(Entity target, DamageSource source, float damage) {
        Player PE = (Player)(Object)this;

        if (PE.getMainHandItem().is(Items.FEATHER)) {
            if (target instanceof LivingEntity livingTarget) {
                livingTarget.knockback(
                        0.4,
                        Mth.sin(PE.getYRot() * ((float)Math.PI / 180F)),
                        (-Mth.cos(PE.getYRot() * ((float)Math.PI / 180F)))
                );
            }
            return true;
        }

        if (PE.getItemInHand(InteractionHand.MAIN_HAND).is(ModTags.SICKLES) && PE.getItemInHand(InteractionHand.OFF_HAND).is(ModTags.SICKLES)) target.invulnerableTime = 10;

        return target.hurtOrSimulate(source, damage);
    }

    @WrapOperation(method = "interactOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;interact(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/InteractionResult;"))
    private InteractionResult allowOffhandAttack(Entity instance, Player player, InteractionHand hand, Vec3 location, Operation<InteractionResult> original) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModTags.SICKLES) && player.getItemInHand(InteractionHand.OFF_HAND).is(ModTags.SICKLES)) return InteractionResult.PASS;
        return original.call(instance, player, hand, location);
    }

    @Inject(method = "baseDamageScaleFactor", at = @At("HEAD"), cancellable = true)
    private void offHandDamage(CallbackInfoReturnable<Float> cir){
        Player player = (Player)(Object)this;
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModTags.SICKLES) && player.getItemInHand(InteractionHand.OFF_HAND).is(ModTags.SICKLES)) cir.setReturnValue(1f);
    }

    @Inject(method = "hurtServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/DamageSource;scalesWithDifficulty()Z"))
    private void cancelCombo(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
        Player PE = (Player)(Object)this;
        ModData.combos.remove(PE.getUUID());
    }

    @ModifyExpressionValue(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;getAttackDamageBonus(Lnet/minecraft/world/entity/Entity;FLnet/minecraft/world/damagesource/DamageSource;)F"))
    private float comboDamage(float original, @Local ItemStack attackingItemStack, @Local(ordinal = 0) float baseDamage){
        if (attackingItemStack.getComponents().has(ComponentRegistry.COMBO_MULTIPLIER)) {
            Player player = (Player)(Object)this;
            int comboTimer = ModData.combos.getOrDefault(player.getUUID(), 0);
            int comboSec = ceilDiv(comboTimer, 30);
            int multiplier = attackingItemStack.getComponents().get(ComponentRegistry.COMBO_MULTIPLIER).multiplier();

            if (!player.level().isClientSide()) ModData.combos.put(player.getUUID(), Math.min((comboSec+1)*30, 10*30));

            return original + baseDamage *comboSec*multiplier*0.01f;
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

    @ModifyVariable(method = "hurtServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;removeEntitiesOnShoulder()V"), ordinal = 0, argsOnly = true)
    private float turtleHelmetMaceBlock(float damage, @Local(argsOnly = true) DamageSource source) {
        Player PE = (Player)(Object)this;
        if (PE.getItemBySlot(EquipmentSlot.HEAD).is(Items.TURTLE_HELMET)) {
            if (source.typeHolder().is(DamageTypes.MACE_SMASH)) {
                PE.getItemBySlot(EquipmentSlot.HEAD).hurtAndBreak((int) damage, PE, EquipmentSlot.CHEST);
                return 0.00123f;
            }
        }
        return damage;
    }
}
