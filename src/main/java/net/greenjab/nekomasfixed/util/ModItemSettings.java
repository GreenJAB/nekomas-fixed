package net.greenjab.nekomasfixed.util;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.WeaponComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.util.Identifier;

public class ModItemSettings {

    public static Item.Settings sickle(ToolMaterial material, float speed) {
        float realDamage = 0.0f;
        if(material.equals(ToolMaterial.WOOD)){realDamage = 2f;}
        else if(material.equals(ToolMaterial.STONE)){realDamage = 2.5f;}
        else if(material.equals(ToolMaterial.COPPER)){realDamage = 2.15f;}
        else if(material.equals(ToolMaterial.IRON)){realDamage = 3f;}
        else if(material.equals(ToolMaterial.GOLD)){realDamage = 2f;}
        else if(material.equals(ToolMaterial.DIAMOND)){realDamage = 3.5f;}
        else if(material.equals(ToolMaterial.NETHERITE)){realDamage = 4f;} // the damage is one less because one is added somewhere idk where
        return new Item.Settings()
                .maxDamage(material.durability())
                .enchantable(15)
                .repairable(Items.IRON_INGOT)
                .component(DataComponentTypes.ATTRIBUTE_MODIFIERS, createAttributes(realDamage, speed))
                .component(DataComponentTypes.WEAPON, new WeaponComponent(1)); //NOTE: This tells that it is a weapon
    }

    private static AttributeModifiersComponent createAttributes(float damage, float speed) {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.ATTACK_DAMAGE,
                        new EntityAttributeModifier(
                                Identifier.of("nekomasfixed", "sickle_damage"),
                                damage,
                                EntityAttributeModifier.Operation.ADD_VALUE
                        ),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.ATTACK_SPEED,
                        new EntityAttributeModifier(
                                Identifier.of("nekomasfixed", "sickle_speed"),
                                speed,
                                EntityAttributeModifier.Operation.ADD_VALUE
                        ),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }
}
