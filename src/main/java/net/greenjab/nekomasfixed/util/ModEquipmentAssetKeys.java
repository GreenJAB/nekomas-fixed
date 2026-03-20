package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.registry.RegistryKey;

public class ModEquipmentAssetKeys {
    public static final RegistryKey<EquipmentAsset> NETHERITE_CROWN =
            RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, NekomasFixed.id("netherite_crown"));

    public static final RegistryKey<EquipmentAsset> COPPER_CROWN =
            RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, NekomasFixed.id("copper_crown"));

    public static final RegistryKey<EquipmentAsset> IRON_CROWN =
            RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, NekomasFixed.id("iron_crown"));

    public static final RegistryKey<EquipmentAsset> GOLDEN_CROWN =
            RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, NekomasFixed.id("golden_crown"));

    public static final RegistryKey<EquipmentAsset> DIAMOND_CROWN =
            RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, NekomasFixed.id("diamond_crown"));

    public static void initialize() {}
}