package net.greenjab.nekomasfixed.util;

import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.sound.SoundEvents;

public class HarnessHelper {

    public static EquippableComponent ofHarness(ModColors color) {
        RegistryEntryLookup<EntityType<?>> registryEntryLookup =
                Registries.createEntryLookup(Registries.ENTITY_TYPE);

        return EquippableComponent.builder(EquipmentSlot.BODY)
                .equipSound(SoundEvents.ENTITY_HAPPY_GHAST_EQUIP)
                .model(ModEquipmentAssets.HARNESS_FROM_MOD_COLOR.get(color))
                .allowedEntities(registryEntryLookup.getOrThrow(EntityTypeTags.CAN_EQUIP_HARNESS))
                .equipOnInteract(true)
                .canBeSheared(true)
                .shearingSound(Registries.SOUND_EVENT.getEntry(SoundEvents.ENTITY_HAPPY_GHAST_UNEQUIP))
                .build();
    }
}