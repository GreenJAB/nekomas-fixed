package net.greenjab.nekomasfixed.enchantment.effect;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.entity.TargetDummyEntity;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record LeechingEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<LeechingEnchantmentEffect> CODEC = MapCodec.unit(LeechingEnchantmentEffect::new);

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
        LivingEntity attacker = context.owner();
        if (target instanceof LivingEntity victim) {
            float health = ((LivingEntity) target).getHealth();
            float playerHeal = 0.0f;
            if(level == 1){
                playerHeal = (float) (0.05 * health);
            }
            if(level == 2){
                playerHeal = (float) (0.08 * health);
            }
            if(level == 3){
                playerHeal = (float) (0.1 * health);
            }
            if(target instanceof TargetDummyEntity){
                playerHeal = 0.0f;
            }
            attacker.setHealth(attacker.getHealth() + playerHeal);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}