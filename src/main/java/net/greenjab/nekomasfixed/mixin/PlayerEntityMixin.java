package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.greenjab.nekomasfixed.registry.registries.EnchantmentRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
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
    }

    @Inject(method = "damage", at = @At("HEAD"))
    private void cancelChargeOnHit(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        if (player.isUsingItem()) {
            ItemStack activeItem = player.getActiveItem();

            boolean hasLunge = EnchantmentHelper.getLevel((RegistryEntry<Enchantment>) Enchantments.LUNGE, activeItem) > 0;

            if (hasLunge) {
                player.stopUsingItem();
                if (!player.isSilent()) {
                    player.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, 1.0F, 1.0F);
                }
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

/*
    @Unique
    private static final int CLONE_DISTANCE = 2;

    @Unique
    private int cloneTimer = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;

        ItemStack mainHand = player.getMainHandStack();

        RegistryEntry<?> cloningEnchantment = player.getRegistryManager()
                .get(RegistryKeys.ENCHANTMENT)
                .getEntry(EnchantmentRegistry.CLONING)
                .orElse(null);

        if (cloningEnchantment == null) {
            return;
        }

        int level = EnchantmentHelper.getLevel(cloningEnchantment, mainHand);


        if (level > 0 && player.isUsingItem() && player.getEntityWorld() instanceof ServerWorld serverWorld) {

            showClones(serverWorld, player);
            cloneTimer++;
        } else {
            cloneTimer = 0;
        }
    }

    @Unique
    private void showClones(ServerWorld world, PlayerEntity player) {
        Direction facing = player.getHorizontalFacing();
        Direction leftDir = facing.rotateYCounterclockwise();
        Direction rightDir = facing.rotateYClockwise();

        Vec3d playerPos = Vec3d.of(player.getBlockPos());
        for (int i = 0; i < 15; i++) {
            // Left clone
            world.spawnParticles(
                    ParticleTypes.CLOUD,
                    playerPos.x + leftDir.getOffsetX() * CLONE_DISTANCE,
                    playerPos.y + 1.0 + (world.random.nextDouble() - 0.5) * 1.8,
                    playerPos.z + leftDir.getOffsetZ() * CLONE_DISTANCE,
                    3, 0.3, 0.5, 0.3, 0.02
            );

            world.spawnParticles(
                    ParticleTypes.CLOUD,
                    playerPos.x + rightDir.getOffsetX() * CLONE_DISTANCE,
                    playerPos.y + 1.0 + (world.random.nextDouble() - 0.5) * 1.8,
                    playerPos.z + rightDir.getOffsetZ() * CLONE_DISTANCE,
                    3, 0.3, 0.5, 0.3, 0.02
            );
        }

        if (cloneTimer % 20 == 0) {
            world.playSound(
                    null,
                    player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENTITY_ILLUSIONER_MIRROR_MOVE,
                    player.getSoundCategory(),
                    0.5F, 1.0F
            );
        }
    }*/

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

        return target.sidedDamage(source, amount);
    }

    @Unique
    private static float lastFinalDamage = 0.0f;

    @Unique
    private static float lastBaseDamage = 0.0f;

    @Unique
    private static Entity lastTarget = null;

    @Inject(method = "getDamageAgainst", at = @At("HEAD"))
    private void captureBaseDamage(Entity target, float baseDamage, DamageSource damageSource, CallbackInfoReturnable<Float> cir) {
        lastBaseDamage = baseDamage;
        lastTarget = target;
    }

    @Inject(method = "getDamageAgainst", at = @At("RETURN"))
    private void captureFinalDamage(Entity target, float baseDamage, DamageSource damageSource, CallbackInfoReturnable<Float> cir) {
        lastFinalDamage = cir.getReturnValue();
        System.out.println("Base damage: " + baseDamage + " | Final damage: " + lastFinalDamage);
    }

    @Unique
    public float getLastFinalDamage() {
        return lastFinalDamage;
    }

    @Unique
    public float getLastBaseDamage() {
        return lastBaseDamage;
    }

    @Unique
    public Entity getLastTarget() {
        return lastTarget;
    }
}
