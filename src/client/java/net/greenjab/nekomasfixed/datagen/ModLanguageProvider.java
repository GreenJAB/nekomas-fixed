package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ModLanguageProvider extends FabricLanguageProvider {
    public ModLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        BlockDyeMap.GLOWING_WOOL.values().forEach(block -> addTranslations(translationBuilder, block));
        BlockDyeMap.GLOWING_CARPET.values().forEach(block -> addTranslations(translationBuilder, block));
        BlockDyeMap.GLOWING_BED.values().forEach(block -> addTranslations(translationBuilder, block));
        BlockDyeMap.GLOWING_BANNER.values().forEach(block -> addTranslations(translationBuilder, block));

        // Load an existing language file.
        try {
            Path existingFilePath = dataOutput.getModContainer().findPath("assets/nekomasfixed/lang/en_us.json").get();
            translationBuilder.add(existingFilePath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add existing language file!", e);
        }
    }

    private static void addTranslations(TranslationBuilder translationBuilder, Block block) {
        String translation = obviousTranslation(block);
//        translationBuilder.add(block, translation);
        translationBuilder.add(block.asItem(), translation);
    }

    private static String obviousTranslation(Block block) {
        String id = Registries.BLOCK.getId(block).getPath();
        return Arrays.stream(id.split("_"))
                .map(StringUtils::capitalize)
                .collect(Collectors.joining(" "));
    }
}
