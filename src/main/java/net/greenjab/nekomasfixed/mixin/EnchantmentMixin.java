package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.entity.TargetDummyEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEffectEntry;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static net.minecraft.enchantment.Enchantment.createEnchantedDamageLootContext;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

    @ModifyExpressionValue(method="applyEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/effect/EnchantmentEffectEntry;test(Lnet/minecraft/loot/context/LootContext;)Z"))
    private static <T> boolean targetDummySmite(boolean original, @Local EnchantmentEffectEntry<T> enchantmentEffectEntry, @Local(argsOnly = true) LootContext lootContext) {
        if (lootContext.hasParameter(LootContextParameters.THIS_ENTITY)) {
            if (lootContext.get(LootContextParameters.THIS_ENTITY) instanceof TargetDummyEntity targetDummyEntity) {
                if (targetDummyEntity.getZombie()) {
                    if (enchantmentEffectEntry.requirements().isPresent()) {
                        if (lootContext.hasParameter(LootContextParameters.ENCHANTMENT_LEVEL) && lootContext.hasParameter(LootContextParameters.DAMAGE_SOURCE)) {
                            return enchantmentEffectEntry.requirements().get().test(createEnchantedDamageLootContext(lootContext.getWorld(), lootContext.get(LootContextParameters.ENCHANTMENT_LEVEL), new ZombieEntity(lootContext.getWorld()), lootContext.get(LootContextParameters.DAMAGE_SOURCE)));
                        }
                    }
                }
            }
        }
        return original;
    }
}
