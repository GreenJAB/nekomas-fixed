package net.greenjab.nekomasfixed.util;

import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModEquipmentAssetKeys {
    public static final RegistryKey<EquipmentAsset> NETHERITE_CROWN =
            RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of("nekomasfixed", "netherite_crown"));

    public static void initialize() {}
}