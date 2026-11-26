package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public enum GoatHornHelmetTiers {
    IRON(ArmorMaterials.IRON, 15, Items.IRON_HELMET),
    GOLDEN(ArmorMaterials.GOLD, 7, Items.GOLDEN_HELMET),
    DIAMOND(ArmorMaterials.DIAMOND, 33, Items.DIAMOND_HELMET),
    NETHERITE(ArmorMaterials.NETHERITE, 37, Items.NETHERITE_HELMET),
    TURTLE(ArmorMaterials.TURTLE, 25, Items.TURTLE_HELMET);

    public final Holder<ArmorMaterial> material;
    public final int durability;
    public final Item helmet;
    public final ResourceLocation texture;

    GoatHornHelmetTiers(Holder<ArmorMaterial> material, int durability, Item helmet) {
        this.material = material;
        this.durability = durability;
        this.helmet = helmet;
        this.texture = NekomasFixed.id("textures/entity/equipment/humanoid/goat_horn_helm/" + name().toLowerCase() + ".png");
    }

    public String itemId() {
        return "goat_horn_" + name().toLowerCase() + "_helmet";
    }
}
