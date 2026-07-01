package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class EnchantmentRegistry {
    public static void registerEnchantments() {
        System.out.println("register Enchantments");
    }

    public static final RegistryKey<Enchantment> DISMOUNT = of("dismount");
    public static final RegistryKey<Enchantment> LEECHING = of("leeching");
    public static final RegistryKey<Enchantment> SHATTER = of("shatter");

    private static RegistryKey<Enchantment> of(String id) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, NekomasFixed.id(id));
    }
}
