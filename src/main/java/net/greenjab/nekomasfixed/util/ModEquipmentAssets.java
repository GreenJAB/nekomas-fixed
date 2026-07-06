package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import java.util.Map;

public class ModEquipmentAssets {

    public static final ResourceKey<EquipmentAsset> AMBER_HARNESS =
            ResourceKey.create(EquipmentAssets.ROOT_ID,
                    NekomasFixed.id("amber_harness"));

    public static final ResourceKey<EquipmentAsset> AQUA_HARNESS =
            ResourceKey.create(EquipmentAssets.ROOT_ID,
                    NekomasFixed.id("aqua_harness"));

    public static final ResourceKey<EquipmentAsset> INDIGO_HARNESS =
            ResourceKey.create(EquipmentAssets.ROOT_ID,
                    NekomasFixed.id("indigo_harness"));

    public static final ResourceKey<EquipmentAsset> MAROON_HARNESS =
            ResourceKey.create(EquipmentAssets.ROOT_ID,
                    NekomasFixed.id("maroon_harness"));

    public static final Map<ModColors, ResourceKey<EquipmentAsset>> HARNESS_FROM_MOD_COLOR = Map.of(
            ModColors.AMBER, AMBER_HARNESS,
            ModColors.AQUA, AQUA_HARNESS,
            ModColors.INDIGO, INDIGO_HARNESS,
            ModColors.MAROON, MAROON_HARNESS
    );
}