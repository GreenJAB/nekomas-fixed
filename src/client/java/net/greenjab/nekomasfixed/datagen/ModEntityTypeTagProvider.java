package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.EntityTypeTags;

import java.util.concurrent.CompletableFuture;

/** Vanilla undead tags for the Derelict, matching vanilla zombies. */
public class ModEntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {
    public ModEntityTypeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(EntityTypeTags.IGNORES_POISON_AND_REGEN)
                .add(BuiltInRegistries.ENTITY_TYPE.getKey(EntityTypeRegistry.DERELICT));
        getOrCreateTagBuilder(EntityTypeTags.INVERTED_HEALING_AND_HARM)
                .add(BuiltInRegistries.ENTITY_TYPE.getKey(EntityTypeRegistry.DERELICT));
        getOrCreateTagBuilder(EntityTypeTags.SENSITIVE_TO_SMITE)
                .add(BuiltInRegistries.ENTITY_TYPE.getKey(EntityTypeRegistry.DERELICT));
        getOrCreateTagBuilder(EntityTypeTags.ZOMBIES)
                .add(BuiltInRegistries.ENTITY_TYPE.getKey(EntityTypeRegistry.DERELICT));
        getOrCreateTagBuilder(EntityTypeTags.UNDEAD)
                .add(BuiltInRegistries.ENTITY_TYPE.getKey(EntityTypeRegistry.DERELICT));
    }
}
