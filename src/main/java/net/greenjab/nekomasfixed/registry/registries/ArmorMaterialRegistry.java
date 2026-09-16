package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;

// Custom armour materials. main renders the turtle set via the 1.21.2+ `equipment`
// asset system, which doesn't exist in 1.21.1; a custom ArmorMaterial lets the same
// pieces render as proper armour layers instead.
public class ArmorMaterialRegistry {

    public static final Holder<ArmorMaterial> TURTLE_SCUTE = registerTurtleScute();

    private static Holder<ArmorMaterial> registerTurtleScute() {
        EnumMap<ArmorItem.Type, Integer> defense = new EnumMap<>(ArmorItem.Type.class);
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            defense.put(type, type == ArmorItem.Type.BOOTS ? 1 : 3);
        }
        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, NekomasFixed.id("turtle_scute"),
                new ArmorMaterial(
                        defense,
                        9,
                        SoundEvents.ARMOR_EQUIP_TURTLE,
                        () -> Ingredient.of(Items.TURTLE_SCUTE),
                        List.of(new ArmorMaterial.Layer(NekomasFixed.id("turtle_scute"))),
                        0.0F, 0.0F));
    }

    public static void registerArmorMaterials() {
        NekomasFixed.LOGGER.info("Registering armour materials");
    }
}