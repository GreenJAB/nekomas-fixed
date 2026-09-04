package net.greenjab.nekomasfixed.datagen;

import net.greenjab.nekomasfixed.registries.SheetRegistry;
import net.minecraft.client.data.AtlasProvider;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.sources.PalettedPermutations;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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


//            "nekomasfixed:trims/entity/pot/angler",
//                    "nekomasfixed:trims/entity/pot/archer",
//                    "nekomasfixed:trims/entity/pot/arms_up",
//                    "nekomasfixed:trims/entity/pot/burn",
//                    "nekomasfixed:trims/entity/pot/brewer",
//                    "nekomasfixed:trims/entity/pot/danger",
//                    "nekomasfixed:trims/entity/pot/explorer",
//                    "nekomasfixed:trims/entity/pot/flow",
//                    "nekomasfixed:trims/entity/pot/friend",
//                    "nekomasfixed:trims/entity/pot/guster",
//                    "nekomasfixed:trims/entity/pot/heart",
//                    "nekomasfixed:trims/entity/pot/heartbreak",
//                    "nekomasfixed:trims/entity/pot/howl",
//                    "nekomasfixed:trims/entity/pot/miner",
//                    "nekomasfixed:trims/entity/pot/mourner",
//                    "nekomasfixed:trims/entity/pot/plenty",
//                    "nekomasfixed:trims/entity/pot/prize",
//                    "nekomasfixed:trims/entity/pot/scrape",
//                    "nekomasfixed:trims/entity/pot/sheaf",
//                    "nekomasfixed:trims/entity/pot/skull",
//                    "nekomasfixed:trims/entity/pot/shelter",
//                    "nekomasfixed:trims/entity/pot/snort"

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
        return List.of(new PalettedPermutations(patternTextures(), AtlasProvider.TRIM_PALETTE_KEY, AtlasProvider.TRIM_PALETTE_VALUES));
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
