package net.greenjab.nekomasfixed.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PlayerEntity.class)
public interface PlayerEntityInterfaceMixin {

    @Invoker("getDamageAgainst")
    float invokeGetDamageAgainst(Entity target, float baseDamage, DamageSource damageSource);
}