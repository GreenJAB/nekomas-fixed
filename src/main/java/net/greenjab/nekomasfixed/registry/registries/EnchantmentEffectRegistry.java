package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.enchantment.effect.LeechingEnchantmentEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EnchantmentEffectRegistry {

    public static void register() {
        // Register the leeching effect
        Registry.register(
                Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE,
                Identifier.of("nekomasfixed", "leeching"),
                LeechingEnchantmentEffect.CODEC
        );

        NekomasFixed.LOGGER.info("Registered enchantment effects");
    }
}