package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.greenjab.nekomasfixed.registry.registries.EnchantmentRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import java.util.concurrent.CompletableFuture;

// Vanilla loot/trade pools overridden to include the mod's treasure enchants.
public class ModEnchantmentTagProvider extends FabricTagProvider.EnchantmentTagProvider {
    public ModEnchantmentTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registriesFuture) {
        // Leeching + Shatter are treasure enchants: random-loot + villager trades only,
        // never offered by the enchanting table (so no in_enchanting_table entry, matching main's treasure:true).
        getOrCreateTagBuilder(TagKey.create(Registries.ENCHANTMENT,
                ResourceLocation.withDefaultNamespace("on_random_loot")))
                .addOptional(EnchantmentRegistry.LEECHING)
                .addOptional(EnchantmentRegistry.SHATTER);
        getOrCreateTagBuilder(TagKey.create(Registries.ENCHANTMENT,
                ResourceLocation.withDefaultNamespace("tradable")))
                .addOptional(EnchantmentRegistry.LEECHING)
                .addOptional(EnchantmentRegistry.SHATTER);
    }
}