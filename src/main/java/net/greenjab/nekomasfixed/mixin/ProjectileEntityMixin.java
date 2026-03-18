package net.greenjab.nekomasfixed.mixin;

import it.unimi.dsi.fastutil.doubles.DoubleDoubleImmutablePair;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin {
    @Shadow
    public abstract @Nullable Entity getOwner();

    @Inject(method = "onCollision", at = @At("HEAD"), cancellable = true)
    protected void onCollision(HitResult hitResult, CallbackInfo ci) {
ProjectileEntity projectileEntity = (ProjectileEntity)(Object)this;
        World world = Objects.requireNonNull(projectileEntity.getOwner()).getEntityWorld();
Vec3d pos = hitResult.getPos();
        LightningEntity lightningEntity = (LightningEntity) EntityType.LIGHTNING_BOLT.create(world, SpawnReason.EVENT);
        if(lightningEntity!=null){
            lightningEntity.refreshPositionAfterTeleport(pos);
            world.spawnEntity(lightningEntity);
            if(projectileEntity.getOwner() instanceof PlayerEntity player){
                if(player.getBlockPos().isWithinDistance(projectileEntity.getVelocityAffectingPos(), 2.0d)){
                    if(player.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.COPPER_HELMET)&&
                            player.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.COPPER_CHESTPLATE)&&
                            player.getEquippedStack(EquipmentSlot.LEGS).isOf(Items.COPPER_LEGGINGS)&&
                            player.getEquippedStack(EquipmentSlot.FEET).isOf(Items.COPPER_BOOTS)){
                        StatusEffectInstance st = new StatusEffectInstance(StatusEffects.SPEED, 10*20, 4, false, false, false);
                        StatusEffectInstance stH = new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1*20, 2, false, false, false);
                        player.addStatusEffect(st);
                        player.addStatusEffect(stH);
                    }
                }
            }
        }
    }
}
