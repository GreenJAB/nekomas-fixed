package net.greenjab.nekomasfixed.registry.entity;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jspecify.annotations.NonNull;

public class SlownessSnowballEntity extends Snowball {

    public SlownessSnowballEntity(EntityType<? extends Snowball> entityType, Level world) {
        super(entityType, world);
    }

    public SlownessSnowballEntity(Level world, LivingEntity owner) {
        super(world, owner, new ItemStack(Items.SNOWBALL));
    }

    @Override
    protected void onHitEntity(@NonNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);

        Entity target = entityHitResult.getEntity();
        if (target instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 100, 1), this.getOwner());
            livingEntity.hurt(this.damageSources().thrown(this, this.getOwner()), 1.0f);
            livingEntity.setTicksFrozen(livingEntity.getTicksFrozen()+100);
        }
    }
}