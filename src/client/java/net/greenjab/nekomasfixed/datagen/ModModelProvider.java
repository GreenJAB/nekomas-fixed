package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import org.jspecify.annotations.NonNull;


public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(@NonNull BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(@NonNull ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ItemRegistry.MOOBLOOM_SPAWN_EGG, Models.GENERATED);
    }

}