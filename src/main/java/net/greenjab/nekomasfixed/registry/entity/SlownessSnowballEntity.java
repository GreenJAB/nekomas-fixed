package net.greenjab.nekomasfixed.registry.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class SlownessSnowballEntity extends SnowballEntity {

    public SlownessSnowballEntity(EntityType<? extends SnowballEntity> entityType, World world) {
        super(entityType, world);
    }

    public SlownessSnowballEntity(World world, LivingEntity owner) {
        super(world, owner, new ItemStack(Items.SNOWBALL));
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        Entity target = entityHitResult.getEntity();
        if (target instanceof LivingEntity livingEntity) {
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 1), this.getOwner());
            livingEntity.serverDamage(this.getDamageSources().thrown(this, this.getOwner()), 1.0f);
            livingEntity.setFrozenTicks(livingEntity.getFrozenTicks()+100);
        }
    }
}