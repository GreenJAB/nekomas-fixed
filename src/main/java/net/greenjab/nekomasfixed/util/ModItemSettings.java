package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.registry.other.ComboComponent;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import static net.greenjab.nekomasfixed.registry.item.SickleItem.SPEED;
import static net.minecraft.world.item.Item.BASE_ATTACK_DAMAGE_ID;
import static net.minecraft.world.item.Item.BASE_ATTACK_SPEED_ID;

public class ModItemSettings {
    // Sickle stats: durability from the tier, attack attributes built by hand (the
    // item is a plain TieredItem, not a SwordItem, so it never sweeps), and the
    // combo_multiplier component that drives the stacking hit bonus.
    public static Item.Properties sickle(SickleTiers.SickleTier tier) {
        return new Item.Properties()
                .durability(tier.getUses())
                .attributes(createAttributes(tier.getAttackDamageBonus(), SPEED))
                .component(ComponentRegistry.COMBO_MULTIPLIER, new ComboComponent(tier.comboMultiplier()));
    }

    private static ItemAttributeModifiers createAttributes(float damage, float speed) {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(
                        BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(
                        BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }
}
