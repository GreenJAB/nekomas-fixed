package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.registry.registries.TrimRegistry;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.armortrim.TrimPattern;

import java.util.concurrent.CompletableFuture;

public class ModTrimPatternProvider extends FabricDynamicRegistryProvider {
    public ModTrimPatternProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public String getName() {
        return "nekomasfixed-trim-patterns";
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        entries.add(TrimRegistry.JEWEL, new TrimPattern(
                TrimRegistry.JEWEL.location(),
                ItemRegistry.JEWEL_ARMOR_TRIM_SMITHING_TEMPLATE.builtInRegistryHolder(),
                Component.translatable(Util.makeDescriptionId("trim_pattern", TrimRegistry.JEWEL.location())),
                false));
    }
}
