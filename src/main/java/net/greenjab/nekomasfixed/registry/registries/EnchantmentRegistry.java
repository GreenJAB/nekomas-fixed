package net.greenjab.nekomasfixed.registry.registries;

import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

public class EnchantmentRegistry {
    public static final RegistryKey<Enchantment> LEECHING =
            RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of("nekomasfixed", "leeching"));

    public static void bootstrap(Registerable<Enchantment> registerable) {
        var items = registerable.getRegistryLookup(RegistryKeys.ITEM);

        registerable.register(
                LEECHING,
                Enchantment.builder(
                        Enchantment.definition(
                                items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                5,
                                1,
                                Enchantment.leveledCost(1, 10),
                                Enchantment.leveledCost(1, 15),
                                1,
                                AttributeModifierSlot.MAINHAND
                        )
                ).build(LEECHING.getValue())
        );
    }
}