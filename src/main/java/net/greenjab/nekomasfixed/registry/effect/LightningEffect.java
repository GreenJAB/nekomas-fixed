package net.greenjab.nekomasfixed.registry.effect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;


public class LightningEffect extends StatusEffect {
    public LightningEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        if(entity instanceof PlayerEntity ){
            super.onApplied(entity, amplifier);

            if (!entity.getEntityWorld().isClient()) {
                ServerWorld world = (ServerWorld) entity.getEntityWorld();

                LightningEntity lightning =(LightningEntity) EntityType.LIGHTNING_BOLT.create(world, SpawnReason.EVENT);
                if (lightning != null) {
                    lightning.refreshPositionAfterTeleport(entity.getX(), entity.getY(), entity.getZ());
                    world.spawnEntity(lightning);
                }
            }
        }
    }
}
