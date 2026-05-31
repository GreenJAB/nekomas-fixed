package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.greenjab.nekomasfixed.util.DyedBrushableBlocksMappings;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }


    @Override
    protected void configure(RegistryWrapper.@NonNull WrapperLookup wrapperLookup) {
        valueLookupBuilder(OtherRegistry.DYED_BRICKS)
                .add(DyedBrushableBlocksMappings.BRICKS.values());

        valueLookupBuilder(OtherRegistry.STAINED_GLASSES)
                .add(DyedBrushableBlocksMappings.STAINED_GLASS.values());

        valueLookupBuilder(OtherRegistry.STAINED_GLASS_PANES)
                .add(DyedBrushableBlocksMappings.STAINED_GLASS_PANES.values());

        valueLookupBuilder(OtherRegistry.GLAZED_TERRACOTTAS)
                .add(DyedBrushableBlocksMappings.GLAZED_TERRACOTTA.values());

        valueLookupBuilder(OtherRegistry.CONCRETE_POWDERS)
                .add(DyedBrushableBlocksMappings.CONCRETE_POWDER.values());

        valueLookupBuilder(OtherRegistry.CONCRETES)
                .add(DyedBrushableBlocksMappings.CONCRETE.values());


        valueLookupBuilder(OtherRegistry.CAN_BE_DYED_WITH_BRUSH)
                .add(DyedBrushableBlocksMappings.BRICKS.values())
                .add(DyedBrushableBlocksMappings.STAINED_GLASS.values())
                .add(DyedBrushableBlocksMappings.STAINED_GLASS_PANES.values())
                .add(DyedBrushableBlocksMappings.TERRACOTTA.values())
                .add(DyedBrushableBlocksMappings.GLAZED_TERRACOTTA.values())
                .add(DyedBrushableBlocksMappings.WOOL.values())
                .add(DyedBrushableBlocksMappings.CANDLES.values())
                .add(DyedBrushableBlocksMappings.CARPETS.values())
                .add(DyedBrushableBlocksMappings.CONCRETE.values())
                .add(DyedBrushableBlocksMappings.CONCRETE_POWDER.values())
                .add(Blocks.GLASS)
                .add(Blocks.CANDLE)
                .add(Blocks.GLASS_PANE);


    }

}

