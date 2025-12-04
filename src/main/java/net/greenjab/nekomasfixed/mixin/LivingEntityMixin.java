package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @ModifyVariable(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isSleeping()Z"), ordinal = 0, argsOnly = true)
    private float turtleChestplateBlock(float amount, @Local(argsOnly = true) ServerWorld world, @Local(argsOnly = true) DamageSource source) {
        LivingEntity LE = (LivingEntity)(Object)this;
        if (LE.getEquippedStack(EquipmentSlot.CHEST).isOf(ItemRegistry.TURTLE_CHESTPLATE)) {
            if (source.getSource() instanceof PersistentProjectileEntity persistentProjectileEntity && persistentProjectileEntity.getPierceLevel() > 0) {
                return 0.0F;
            } else {
                Vec3d vec3d = source.getPosition();
                double d;
                if (vec3d != null) {
                    Vec3d vec3d2 = LE.getRotationVector(0.0F, LE.getHeadYaw());
                    Vec3d vec3d3 = vec3d.subtract(LE.getEntityPos());
                    vec3d3 = new Vec3d(vec3d3.x, 0.0, vec3d3.z).normalize();
                    d = Math.acos(vec3d3.dotProduct(vec3d2));
                } else {
                    d = (float) Math.PI;
                }

                float f = getReductionAmount(LE, amount, d);
                if (f > 0.0F && source.getSource() instanceof LivingEntity) {
                    LE.getEquippedStack(EquipmentSlot.CHEST).damage((f==amount?3:1), LE, EquipmentSlot.CHEST);
                }
                return amount-f;
            }
        }
        if (LE.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.TURTLE_HELMET)) {
            if (source.getTypeRegistryEntry().matchesKey(DamageTypes.MACE_SMASH)) {
                LE.getEquippedStack(EquipmentSlot.HEAD).damage((int)amount, LE, EquipmentSlot.CHEST);
                return 0;
            }
        }

        return amount;
    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getActiveItem()Lnet/minecraft/item/ItemStack;"), cancellable = true)
    private void cancel0Damage(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (amount<=0)cir.setReturnValue(true);
    }


    @Unique
    public float getReductionAmount(LivingEntity LE, float damage, double angle) {
        if (angle > (float) (Math.PI / 180.0) * 90f) {
            if (LE instanceof PlayerEntity player && !player.isSneaking()) return damage/2f;
            return damage;
        } else {
            return 0.0F;
        }
    }

}
