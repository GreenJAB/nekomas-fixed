package net.greenjab.nekomasfixed.util;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorMaterials;

public enum GoatHornHelmetTiers {
    IRON(ArmorMaterials.IRON),
    GOLDEN(ArmorMaterials.GOLD),
    COPPER(ArmorMaterials.COPPER),
    CHAINMAIL(ArmorMaterials.CHAINMAIL),
    DIAMOND(ArmorMaterials.DIAMOND),
    NETHERITE(ArmorMaterials.NETHERITE),
    TURTLE(ArmorMaterials.TURTLE_SCUTE);

    private final ArmorMaterial material;
    private final Identifier texture;

    GoatHornHelmetTiers(ArmorMaterial material) {
        this.material = material;
        this.texture = Identifier.fromNamespaceAndPath("nekomasfixed",
                "textures/entity/equipment/humanoid/goat_horn_helm/" + this.name().toLowerCase());
    }

    public ArmorMaterial getMaterial() { return this.material; }
    public Identifier getTexture() { return this.texture; }
}