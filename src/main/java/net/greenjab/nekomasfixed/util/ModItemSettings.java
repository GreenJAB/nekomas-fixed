package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.other.ComboComponent;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Weapon;

import static net.minecraft.world.item.Item.BASE_ATTACK_DAMAGE_ID;
import static net.minecraft.world.item.Item.BASE_ATTACK_SPEED_ID;

public class ModItemSettings {

    public static Item.Properties sickle(ToolMaterial material, float speed) {
        float realDamage = 0.0f;
        if(material.equals(ToolMaterial.WOOD)){realDamage = 1f;}
        else if(material.equals(ToolMaterial.STONE)){realDamage = 1.5f;}
        else if(material.equals(ToolMaterial.COPPER)){realDamage = 1.15f;}
        else if(material.equals(ToolMaterial.IRON)){realDamage = 2f;}
        else if(material.equals(ToolMaterial.GOLD)){realDamage = 3f;}
        else if(material.equals(ToolMaterial.DIAMOND)){realDamage = 4.5f;}
        else if(material.equals(ToolMaterial.NETHERITE)){realDamage = 5f;}
        return new Item.Properties()
                .durability(material.durability())
                .enchantable(15)
                .repairable(Items.IRON_INGOT)
                .component(DataComponents.ATTRIBUTE_MODIFIERS, createAttributes(realDamage, speed))
                .component(DataComponents.WEAPON, new Weapon(1))
                .component(DataComponents.MINIMUM_ATTACK_CHARGE, 1.0F)
                .component(ComponentRegistry.COMBO_MULTIPLIER, new ComboComponent((int) (10-material.attackDamageBonus())));
    }

    private static ItemAttributeModifiers createAttributes(float damage, float speed) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                BASE_ATTACK_DAMAGE_ID,
                                damage,
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                BASE_ATTACK_DAMAGE_ID,
                                damage,
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.OFFHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(
                                BASE_ATTACK_SPEED_ID,
                                speed,
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(
                                BASE_ATTACK_SPEED_ID,
                                speed,
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.OFFHAND
                )
                .build();
    }

    public static Item.Properties anchor(float damage, float speed) {
        return new Item.Properties()
                .durability(2500)
                .enchantable(15)
                .repairable(Items.PRISMARINE_SHARD)
                .component(DataComponents.ATTRIBUTE_MODIFIERS, createAnchorAttributes(damage, speed))
                .component(DataComponents.WEAPON, new Weapon(1))
                .component(DataComponents.MINIMUM_ATTACK_CHARGE, 1.0F);
    }

    private static ItemAttributeModifiers createAnchorAttributes(float damage, float speed) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ENTITY_INTERACTION_RANGE,
                        new AttributeModifier(NekomasFixed.id( "anchor_reach"), 1.5, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .build();
    }
}
