package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MaceItem;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Combat enchantment hit effects (leeching) + turtle armour behaviour.
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    // Leeching: the attacker heals a fraction of the damage actually applied to this entity.
    @Inject(method = "actuallyHurt", at = @At("HEAD"))
    private void leechingEnchant(DamageSource source, float amount, CallbackInfo ci) {
        if (source.getEntity() instanceof Player player) {
            int level = NekomasFixed.enchantLevel(player.getMainHandItem(), "leeching");
            if (level != 0) player.heal((level * 0.0125f + 0.0125f) * amount);
        }
    }

    // Turtle Shell chestplate: reduce melee damage from the front based on facing;
    // a vanilla turtle helmet also absorbs Mace smash. A fully-blocked hit becomes a
    // tiny sentinel so the hit still registers without dealing real damage.
    @ModifyVariable(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isSleeping()Z"), ordinal = 0, argsOnly = true)
    private float turtleChestplateBlock(float damage, @Local(argsOnly = true) DamageSource source) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.TURTLE_CHESTPLATE)) {
            Vec3 vec3d = source.getSourcePosition();
            double d;
            if (vec3d != null) {
                Vec3 vec3d2 = self.calculateViewVector(0.0F, self.getYHeadRot());
                Vec3 vec3d3 = vec3d.subtract(self.position());
                vec3d3 = new Vec3(vec3d3.x, 0.0, vec3d3.z).normalize();
                d = Math.acos(vec3d3.dot(vec3d2));
            } else {
                d = 0;
            }
            float f = getReductionAmount(self, damage, d);
            if (f > 0.0F && source.getDirectEntity() instanceof LivingEntity) {
                self.getItemBySlot(EquipmentSlot.CHEST).hurtAndBreak((f == damage ? 3 : 1), self, EquipmentSlot.CHEST);
            }
            if (damage - f <= 0) return 0.00123f;
            return damage - f;
        }
        // vanilla turtle helmet absorbs a Mace smash (1.21.1 has no mace_smash damage
        // type, so detect via the mace weapon + the mace's own smash predicate instead).
        if (self.getItemBySlot(EquipmentSlot.HEAD).is(Items.TURTLE_HELMET)) {
            if (source.getDirectEntity() instanceof Player attacker
                    && attacker.getMainHandItem().is(Items.MACE)
                    && MaceItem.canSmashAttack(attacker)) {
                self.getItemBySlot(EquipmentSlot.HEAD).hurtAndBreak((int) damage, self, EquipmentSlot.CHEST);
                return 0.00123f;
            }
        }
        return damage;
    }

    // Turtle Flippers: no sinking/falling while underwater.
    @WrapOperation(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getFluidFallingAdjustedMovement(DZLnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 noFallInWaterWithTurtleBoots(LivingEntity instance, double baseGravity, boolean isFalling, Vec3 movement, Operation<Vec3> original) {
        if (instance.isUnderWater() && instance.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.TURTLE_BOOTS))
            baseGravity = 0.0;
        return original.call(instance, baseGravity, isFalling, movement);
    }

    @Unique
    private float getReductionAmount(LivingEntity self, float damage, double angle) {
        if (angle > (float) (Math.PI / 180.0) * 90f) {
            if (self instanceof Player player && !player.isShiftKeyDown()) return damage / 2f;
            return damage;
        } else {
            return 0.0F;
        }
    }
}