package net.greenjab.nekomasfixed.screen.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.greenjab.nekomasfixed.util.options.EntitySpawnOption;
import net.greenjab.nekomasfixed.util.options.Option;
import net.greenjab.nekomasfixed.util.options.RecipeOption;
import net.greenjab.nekomasfixed.util.options.WorldGenOption;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfigValues {
    private static final Logger LOGGER = LoggerFactory.getLogger("nekomasfixed");

    public static final WorldGenOption<Boolean> generateGeysers = new WorldGenOption<>("generateGeysers", true);
    public static final WorldGenOption<Boolean> generateMound = new WorldGenOption<>("generateMound", true);

    public static final EntitySpawnOption spawnSusSpider =
            new EntitySpawnOption("spawnSusSpider", () -> EntityTypeRegistry.SUSPICIOUS_SPIDER, true);

    public static final EntitySpawnOption spawnMoobloom =
            new EntitySpawnOption("spawnMoobloom", () -> EntityTypeRegistry.MOOBLOOM, true);

    public static final EntitySpawnOption spawnTermite =
            new EntitySpawnOption("spawnTermite", () -> EntityTypeRegistry.TERMITE, true);

    public static final EntitySpawnOption spawnRime =
            new EntitySpawnOption("spawnRime", () -> EntityTypeRegistry.RIME, true);

    public static final EntitySpawnOption spawnDerelict =
            new EntitySpawnOption("spawnDerelict", () -> EntityTypeRegistry.DERELICT, true);

    public static final EntitySpawnOption spawnDrenched =
            new EntitySpawnOption("spawnDrenched", () -> EntityTypeRegistry.DRENCHED, true);

    public static final EntitySpawnOption spawnWildfire =
            new EntitySpawnOption("spawnWildfire", () -> EntityTypeRegistry.WILDFIRE, true);

    public static final Option<Boolean> enableMessyBeds = new Option<>("enableMessyBeds", true);
    public static final Option<Boolean> enableStackedCakes = new Option<>("enableStackedCakes", true);
    public static final Option<Integer> cakeSize = new Option<>("cakeSize", 3);

    public static final Option<Boolean> enableCopperBuff = new Option<>("enableCopperBuff", true);

    public static final Option<Boolean> netherFoodRotting = new Option<>("netherFoodRotting", true);
    public static final Option<Boolean> magmaBlockNeedsSilkTouch = new Option<>("magmaBlockNeedsSilkTouch", true);

    public static final Option<Boolean> endermanHeadActivates = new Option<>("endermanHeadActivates", true);

    public static final RecipeOption dyedBrush =
            new RecipeOption("dyed_brush", path -> path.endsWith("_dyed_brush"), true);

    public static boolean isRecipeEnabled(Identifier recipeId) {
        for (RecipeOption o : RecipeOption.ALL_RECIPES) {
            if (o.controls(recipeId)) return o.get();
        }
        return true;
    }
    public static boolean applyToNextWorld = false;
    private static Path currentWorldDir = null;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_NAME = "nekomasfixed.json";

    public static boolean isWorldLoaded() {
        return currentWorldDir != null;
    }

    public static void resetToDefaults() {
        for (Option<?> o : Option.ALL) o.reset();
        applyToNextWorld = false;
    }

    public static void onServerStarting(Path worldDir) {
        currentWorldDir = worldDir;
        Path file = worldDir.resolve(FILE_NAME);

        if (Files.exists(file)) {
            try {
                JsonObject json = JsonParser.parseString(Files.readString(file)).getAsJsonObject();
                for (Option<?> o : Option.ALL) o.load(json, GSON);
            } catch (Exception e) {
                LOGGER.error("Failed to read {}", file, e);
            }
        } else {
            if (!applyToNextWorld) {
                resetToDefaults();
            }
            saveCurrentWorld();
            applyToNextWorld = false;
        }
    }

    public static void onServerStopped() {
        currentWorldDir = null;
        resetToDefaults();
    }

    public static void saveCurrentWorld() {
        if (currentWorldDir == null) return;
        JsonObject json = new JsonObject();
        for (Option<?> o : Option.ALL) o.save(json, GSON);
        try {
            Files.writeString(currentWorldDir.resolve(FILE_NAME), GSON.toJson(json));
        } catch (IOException e) {
            LOGGER.error("Failed to write config", e);
        }
    }
}