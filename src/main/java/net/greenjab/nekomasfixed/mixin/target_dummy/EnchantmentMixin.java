package net.greenjab.nekomasfixed.mixin.target_dummy;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.entity.TargetDummy;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import static net.minecraft.world.item.enchantment.Enchantment.damageContext;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

    @ModifyExpressionValue(method="applyEffects(Ljava/util/List;Lnet/minecraft/world/level/storage/loot/LootContext;Lnet/minecraft/world/item/enchantment/Enchantment$GenericAction;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/ConditionalEffect;matches(Lnet/minecraft/world/level/storage/loot/LootContext;)Z"))
    private static <T> boolean targetDummySmite(boolean original, @Local ConditionalEffect<T> conditionalEffect, @Local(argsOnly = true) LootContext filterData) {
        if (filterData.hasParameter(LootContextParams.THIS_ENTITY)) {
            if (filterData.getOptionalParameter(LootContextParams.THIS_ENTITY) instanceof TargetDummy targetDummy) {
                if (targetDummy.isZombie()) {
                    if (conditionalEffect.requirements().isPresent()) {
                        if (filterData.hasParameter(LootContextParams.ENCHANTMENT_LEVEL) && filterData.hasParameter(LootContextParams.DAMAGE_SOURCE)) {
                            return conditionalEffect.requirements().get().test(damageContext(filterData.getLevel(), filterData.getOptionalParameter(LootContextParams.ENCHANTMENT_LEVEL), new Zombie(filterData.getLevel()), filterData.getOptionalParameter(LootContextParams.DAMAGE_SOURCE)));
                        }
                    }
                }
            }
        }
        return original;
    }
}
