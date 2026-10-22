package net.greenjab.nekomasfixed.datagen;

import net.greenjab.nekomasfixed.registries.SheetRegistry;
import net.minecraft.client.data.AtlasProvider;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.sources.PalettedPermutations;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.providers.EnchantmentProvider;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ModAtlasProvider extends AtlasProvider {
    public static List<ResourceKey<DecoratedPotPattern>> POT_FACE_PATTERNS = List.of(
//            DecoratedPotPatterns.BLANK,
            DecoratedPotPatterns.ANGLER,
            DecoratedPotPatterns.ARCHER,
            DecoratedPotPatterns.ARMS_UP,
            DecoratedPotPatterns.BLADE,
            DecoratedPotPatterns.BURN,
            DecoratedPotPatterns.BREWER,
            DecoratedPotPatterns.DANGER,
            DecoratedPotPatterns.EXPLORER,
            DecoratedPotPatterns.FLOW,
            DecoratedPotPatterns.FRIEND,
            DecoratedPotPatterns.GUSTER,
            DecoratedPotPatterns.HEART,
            DecoratedPotPatterns.HEARTBREAK,
            DecoratedPotPatterns.HOWL,
            DecoratedPotPatterns.MINER,
            DecoratedPotPatterns.MOURNER,
            DecoratedPotPatterns.PLENTY,
            DecoratedPotPatterns.PRIZE,
            DecoratedPotPatterns.SCRAPE,
            DecoratedPotPatterns.SHEAF,
            DecoratedPotPatterns.SKULL,
            DecoratedPotPatterns.SHELTER,
            DecoratedPotPatterns.SNORT
    );




    @Unique
    private static List<Identifier> patternTextures() {
        List<Identifier> result = new ArrayList<>(POT_FACE_PATTERNS.size());

        for (ResourceKey<DecoratedPotPattern> vanillaPattern : POT_FACE_PATTERNS) {
            Identifier assetId = vanillaPattern.identifier();
            result.add(assetId.withPath("trim/entity/pot/"+assetId.getPath()));
        }

        return result;
    }

    @Unique
    private static List<SpriteSource> sherdFaces() {
        Map<String, Identifier> trimPaletteValues = Arrays.stream(TrimMaterials.Palette.values())
                .collect(Collectors.toMap(TrimMaterials.Palette::suffix, TrimMaterials.Palette::id));

        return List.of(new PalettedPermutations(patternTextures(), AtlasProvider.TRIM_PALETTE_KEY, trimPaletteValues));
    }

    public ModAtlasProvider(PackOutput output) {
        super(output);
        System.out.println("Generating Atlas Files");

    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {

        return CompletableFuture.allOf(
                this.storeAtlas(cache, Identifier.fromNamespaceAndPath("nekomasfixed","custom_pot"), sherdFaces()),
                this.storeAtlas(cache, Identifier.fromNamespaceAndPath("nekomasfixed","terracotta_decorated_pot"), simpleMapper(SheetRegistry.TERRACOTTA_DECORATED_POT_MAPPER))
        );

    }
}
