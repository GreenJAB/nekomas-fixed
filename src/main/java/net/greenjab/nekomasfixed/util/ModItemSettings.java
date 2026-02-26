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

import static net.minecraft.item.Item.BASE_ATTACK_DAMAGE_MODIFIER_ID;
import static net.minecraft.item.Item.BASE_ATTACK_SPEED_MODIFIER_ID;

public class ModItemSettings {

    public static Item.Settings sickle(ToolMaterial material, float speed) {
        float realDamage = 0.0f;
        if(material.equals(ToolMaterial.WOOD)){realDamage = 1f;}
        else if(material.equals(ToolMaterial.STONE)){realDamage = 1.5f;}
        else if(material.equals(ToolMaterial.COPPER)){realDamage = 1.15f;}
        else if(material.equals(ToolMaterial.IRON)){realDamage = 2f;}
        else if(material.equals(ToolMaterial.GOLD)){realDamage = 3f;}
        else if(material.equals(ToolMaterial.DIAMOND)){realDamage = 4.5f;}
        else if(material.equals(ToolMaterial.NETHERITE)){realDamage = 5f;} // the damage is one less because one is added somewhere idk where
        return new Item.Settings()
                .maxDamage(material.durability())
                .enchantable(15)
                .repairable(Items.IRON_INGOT)
                .component(DataComponentTypes.ATTRIBUTE_MODIFIERS, createAttributes(realDamage, speed))
                .component(DataComponentTypes.WEAPON, new WeaponComponent(1)) //NOTE: This tells that it is a weapon
                .component(DataComponentTypes.MINIMUM_ATTACK_CHARGE, 1.0F);

    }

    private static AttributeModifiersComponent createAttributes(float damage, float speed) {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.ATTACK_DAMAGE,
                        new EntityAttributeModifier(
                                BASE_ATTACK_DAMAGE_MODIFIER_ID,
                                damage,
                                EntityAttributeModifier.Operation.ADD_VALUE
                        ),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.ATTACK_SPEED,
                        new EntityAttributeModifier(
                                BASE_ATTACK_SPEED_MODIFIER_ID,
                                speed,
                                EntityAttributeModifier.Operation.ADD_VALUE
                        ),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }
}
