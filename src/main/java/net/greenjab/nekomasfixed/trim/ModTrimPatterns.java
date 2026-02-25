//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.greenjab.nekomasfixed.trim;


import net.minecraft.item.equipment.trim.ArmorTrimPattern;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;



public class ModTrimPatterns {
    public static final RegistryKey<ArmorTrimPattern> WILD_FIRE_TRIM = of("wild_fire_trim");


    public static void bootstrap(Registerable<ArmorTrimPattern> registry) {
        register(registry, WILD_FIRE_TRIM);

    }

    private static void register(Registerable<ArmorTrimPattern> registry,  RegistryKey<ArmorTrimPattern> key) {
        ArmorTrimPattern armorTrimPattern = new ArmorTrimPattern(getId((key)), Text.translatable(Util.createTranslationKey("trim_pattern", key.getValue())), false);
        registry.register(key, armorTrimPattern);
    }

    private static RegistryKey<ArmorTrimPattern> of(String id) {
        return RegistryKey.of(RegistryKeys.TRIM_PATTERN, Identifier.of("nekomasfixed", id));
    }

    public static Identifier getId(RegistryKey<ArmorTrimPattern> key) {
        return key.getValue();
    }
}
