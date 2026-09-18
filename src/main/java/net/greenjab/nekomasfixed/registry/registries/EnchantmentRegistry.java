package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentRegistry {
    public static final ResourceKey<Enchantment> LEECHING = of("leeching");
    public static final ResourceKey<Enchantment> SHATTER = of("shatter");

    public static void registerEnchantments() {
        NekomasFixed.LOGGER.info("Registering enchantments");
    }

    private static ResourceKey<Enchantment> of(String id) {
        return ResourceKey.create(Registries.ENCHANTMENT, NekomasFixed.id(id));
    }
}