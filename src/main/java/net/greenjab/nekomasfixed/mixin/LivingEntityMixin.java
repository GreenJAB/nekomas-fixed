package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.item.WildfireShieldItem;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    public abstract void stopRiding();

    @ModifyVariable(method = "hurtServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isSleeping()Z"), ordinal = 0, argsOnly = true)
    private float turtleChestplateBlock(float damage, @Local(argsOnly = true) DamageSource source) {
        LivingEntity LE = (LivingEntity)(Object)this;
        if (LE.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.TURTLE_CHESTPLATE)) {
            Vec3 vec3d = source.getSourcePosition();
            double d;
            if (vec3d != null) {
                Vec3 vec3d2 = LE.calculateViewVector(0.0F, LE.getYHeadRot());
                Vec3 vec3d3 = vec3d.subtract(LE.position());
                vec3d3 = new Vec3(vec3d3.x, 0.0, vec3d3.z).normalize();
                d = Math.acos(vec3d3.dot(vec3d2));
            } else {
                d = 0;
            }

            float f = getReductionAmount(LE, damage, d);
            if (f > 0.0F && source.getDirectEntity() instanceof LivingEntity) {
                LE.getItemBySlot(EquipmentSlot.CHEST).hurtAndBreak((f == damage ? 3 : 1), LE, EquipmentSlot.CHEST);
            }
            if (damage - f <=0 ) return 0.00123f;
            return damage - f;
        }
        if (LE.getItemBySlot(EquipmentSlot.HEAD).is(Items.TURTLE_HELMET)) {
            if (source.typeHolder().is(DamageTypes.MACE_SMASH)) {
                LE.getItemBySlot(EquipmentSlot.HEAD).hurtAndBreak((int) damage, LE, EquipmentSlot.CHEST);
                return 0.00123f;
            }
        }

        return damage;
    }

    @WrapOperation(method = "travelInWater", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;getFluidFallingAdjustedMovement(DZLnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;"
    ))
    private Vec3 noFallInWaterWithTurtleBoots(LivingEntity instance, double baseGravity, boolean isFalling, Vec3 movement,
                                              Operation<Vec3> original) {
        if (instance.isUnderWater()&&instance.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.TURTLE_BOOTS)) baseGravity = 0.0;
        return original.call(instance, baseGravity, isFalling, movement);
    }

    @Inject(method = "blockUsingItem", at = @At("HEAD"))
    private void onShieldHit(ServerLevel level, LivingEntity attacker, CallbackInfo ci) {
        LivingEntity defender = (LivingEntity)(Object)this;
        ItemStack activeItem = defender.getUseItem();

        if (activeItem.getItem() instanceof WildfireShieldItem) {
            if (defender instanceof Player player) {
                if (player.getHealth() <= 6.0f) {
                    attacker.igniteForTicks(20 * 3);
                    attacker.knockback(1.0,
                            attacker.getX() + player.getX(),
                            attacker.getZ() + player.getZ());
                } else {
                    attacker.igniteForTicks(20);
                }
            }

        }
    }

    @Inject(method = "hurtServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getUseItem()Lnet/minecraft/world/item/ItemStack;"), cancellable = true)
    private void cancel0Damage(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
        if (damage ==0.00123f)cir.setReturnValue(true);
    }

    @Inject(method = "hurtServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;resolveMobResponsibleForDamage(Lnet/minecraft/world/damagesource/DamageSource;)V"))
    private void leechingEnchant(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
        if (source.getEntity() instanceof Player PE) {
            int i = NekomasFixed.enchantLevel(PE.getMainHandItem(), "leeching");
            if (i != 0) PE.heal((i * 0.0125f + 0.0125f) * damage);
        }
    }

    @Inject(method = "hurtServer", at = @At("HEAD"))
    private void dismountEnchant(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (source.getEntity() instanceof Player PE) {
            int i = NekomasFixed.enchantLevel(PE.getMainHandItem(), "dismount");
            if(source.getWeaponItem()!=null && !source.getWeaponItem().isEmpty() && i==1){
                this.stopRiding();
                entity.getPassengers().forEach(Entity::stopRiding);
            }
        }
    }

    @Unique
    public float getReductionAmount(LivingEntity LE, float damage, double angle) {
        if (angle > (float) (Math.PI / 180.0) * 90f) {
            if (LE instanceof Player player && !player.isShiftKeyDown()) return damage/2f;
            return damage;
        } else {
            return 0.0F;
        }
    }
}
