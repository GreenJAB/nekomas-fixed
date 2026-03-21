package net.greenjab.nekomasfixed.enchantment.effect;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.entity.TargetDummyEntity;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record DismountEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<DismountEnchantmentEffect> CODEC = MapCodec.unit(DismountEnchantmentEffect::new);

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
        LivingEntity attacker = context.owner();
        if (target instanceof LivingEntity victim) {
            if(victim.hasVehicle()){
                victim.stopRiding();
                target.stopRiding();
            }

        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}