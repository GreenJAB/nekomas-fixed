package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

public class ModEquipmentAssetKeys {
    public static final ResourceKey<EquipmentAsset> NETHERITE_CROWN =
            ResourceKey.create(EquipmentAssets.ROOT_ID, NekomasFixed.id("netherite_crown"));

    public static final ResourceKey<EquipmentAsset> COPPER_CROWN =
            ResourceKey.create(EquipmentAssets.ROOT_ID, NekomasFixed.id("copper_crown"));

    public static final ResourceKey<EquipmentAsset> IRON_CROWN =
            ResourceKey.create(EquipmentAssets.ROOT_ID, NekomasFixed.id("iron_crown"));

    public static final ResourceKey<EquipmentAsset> GOLDEN_CROWN =
            ResourceKey.create(EquipmentAssets.ROOT_ID, NekomasFixed.id("golden_crown"));

    public static final ResourceKey<EquipmentAsset> DIAMOND_CROWN =
            ResourceKey.create(EquipmentAssets.ROOT_ID, NekomasFixed.id("diamond_crown"));

}