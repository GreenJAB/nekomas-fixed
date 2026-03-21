package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.enchantment.effect.DismountEnchantmentEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EnchantmentEffectRegistry {

    public static void register() {
        Registry.register(
                Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE,
                Identifier.of("nekomasfixed", "dismount"),
                DismountEnchantmentEffect.CODEC
        );

        NekomasFixed.LOGGER.info("Registered enchantment effects");
    }
}