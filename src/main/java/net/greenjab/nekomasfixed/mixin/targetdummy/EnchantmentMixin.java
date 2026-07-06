package net.greenjab.nekomasfixed.mixin.targetdummy;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.entity.TargetDummyEntity;
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

    @ModifyExpressionValue(method="applyEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/ConditionalEffect;matches(Lnet/minecraft/world/level/storage/loot/LootContext;)Z"))
    private static <T> boolean targetDummySmite(boolean original, @Local ConditionalEffect<T> enchantmentEffectEntry, @Local(argsOnly = true) LootContext lootContext) {
        if (lootContext.hasParameter(LootContextParams.THIS_ENTITY)) {
            if (lootContext.getOptionalParameter(LootContextParams.THIS_ENTITY) instanceof TargetDummyEntity targetDummyEntity) {
                if (targetDummyEntity.isZombie()) {
                    if (enchantmentEffectEntry.requirements().isPresent()) {
                        if (lootContext.hasParameter(LootContextParams.ENCHANTMENT_LEVEL) && lootContext.hasParameter(LootContextParams.DAMAGE_SOURCE)) {
                            return enchantmentEffectEntry.requirements().get().test(damageContext(lootContext.getLevel(), lootContext.getOptionalParameter(LootContextParams.ENCHANTMENT_LEVEL), new Zombie(lootContext.getLevel()), lootContext.getOptionalParameter(LootContextParams.DAMAGE_SOURCE)));
                        }
                    }
                }
            }
        }
        return original;
    }
}
