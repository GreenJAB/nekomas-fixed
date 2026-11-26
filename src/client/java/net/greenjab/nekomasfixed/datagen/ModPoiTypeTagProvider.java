package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.greenjab.nekomasfixed.registry.registries.VillagerRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.world.entity.ai.village.poi.PoiType;

import java.util.concurrent.CompletableFuture;

public class ModPoiTypeTagProvider extends FabricTagProvider<PoiType> {
    public ModPoiTypeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.POINT_OF_INTEREST_TYPE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(PoiTypeTags.ACQUIRABLE_JOB_SITE)
                .add(VillagerRegistry.PYROTECHNIST_POI_KEY);
    }
}
