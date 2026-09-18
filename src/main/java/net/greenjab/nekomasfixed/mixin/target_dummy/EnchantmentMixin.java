package net.greenjab.nekomasfixed.mixin.target_dummy;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.entity.TargetDummy;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static net.minecraft.world.item.enchantment.Enchantment.damageContext;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

    // A zombie-flagged dummy should take Smite's bonus: re-run the effect's
    // requirements against a stand-in Zombie instead of the dummy's LivingEntity type.
    @ModifyExpressionValue(method = "applyEffects(Ljava/util/List;Lnet/minecraft/world/level/storage/loot/LootContext;Ljava/util/function/Consumer;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/ConditionalEffect;matches(Lnet/minecraft/world/level/storage/loot/LootContext;)Z"))
    private static <T> boolean targetDummySmite(boolean original, @Local ConditionalEffect<T> conditionalEffect,
                                                @Local(argsOnly = true) LootContext filterData) {
        if (filterData.hasParam(LootContextParams.THIS_ENTITY)) {
            if (filterData.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof TargetDummy targetDummy) {
                if (targetDummy.isZombie()) {
                    if (conditionalEffect.requirements().isPresent()) {
                        Integer enchantLevel = filterData.getParamOrNull(LootContextParams.ENCHANTMENT_LEVEL);
                        DamageSource damageSource = filterData.getParamOrNull(LootContextParams.DAMAGE_SOURCE);
                        if (enchantLevel != null && damageSource != null) {
                            return conditionalEffect.requirements().get().test(damageContext(
                                    filterData.getLevel(),
                                    enchantLevel,
                                    new Zombie(filterData.getLevel()),
                                    damageSource));
                        }
                    }
                }
            }
        }
        return original;
    }
}