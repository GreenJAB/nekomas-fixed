package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.util.ModColors;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.Map;

public class ModEquipmentAssets {

    public static final RegistryKey<EquipmentAsset> AMBER_HARNESS =
            RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY,
                    Identifier.of("nekomasfixed", "amber_harness"));

    public static final RegistryKey<EquipmentAsset> AQUA_HARNESS =
            RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY,
                    Identifier.of("nekomasfixed", "aqua_harness"));

    public static final RegistryKey<EquipmentAsset> INDIGO_HARNESS =
            RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY,
                    Identifier.of("nekomasfixed", "indigo_harness"));

    public static final RegistryKey<EquipmentAsset> CRIMSON_HARNESS =
            RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY,
                    Identifier.of("nekomasfixed", "crimson_harness"));

    public static final Map<ModColors, RegistryKey<EquipmentAsset>> HARNESS_FROM_MOD_COLOR = Map.of(
            ModColors.AMBER, AMBER_HARNESS,
            ModColors.AQUA, AQUA_HARNESS,
            ModColors.INDIGO, INDIGO_HARNESS,
            ModColors.CRIMSON, CRIMSON_HARNESS
    );
}