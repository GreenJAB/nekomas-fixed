package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Combat enchantment hit effects (leeching now; dismount later).
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
}