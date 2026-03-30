package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.registry.RegistryKey;

import java.util.Map;

public class ModEquipmentAssets {

    public static final RegistryKey<EquipmentAsset> AMBER_HARNESS =
            RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY,
                    NekomasFixed.id("amber_harness"));

    public static final RegistryKey<EquipmentAsset> AQUA_HARNESS =
            RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY,
                    NekomasFixed.id("aqua_harness"));

    public static final RegistryKey<EquipmentAsset> INDIGO_HARNESS =
            RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY,
                    NekomasFixed.id("indigo_harness"));

    public static final RegistryKey<EquipmentAsset> MAROON_HARNESS =
            RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY,
                    NekomasFixed.id("maroon_harness"));

    public static final Map<ModColors, RegistryKey<EquipmentAsset>> HARNESS_FROM_MOD_COLOR = Map.of(
            ModColors.AMBER, AMBER_HARNESS,
            ModColors.AQUA, AQUA_HARNESS,
            ModColors.INDIGO, INDIGO_HARNESS,
            ModColors.MAROON, MAROON_HARNESS
    );
}