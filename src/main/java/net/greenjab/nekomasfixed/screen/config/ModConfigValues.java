package net.greenjab.nekomasfixed.screen.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfigValues {
    private static final Logger LOGGER = LoggerFactory.getLogger("nekomasfixed");

    public static final Option<Boolean> generateGeysers   = new Option<>("generateGeysers", true, true);
    public static final Option<Boolean> generateMound     = new Option<>("generateMound", true, true);

    public static final Option<Boolean> enableCopperBuff  = new Option<>("enableCopperBuff", true);
    public static final Option<Boolean> netherFoodRotting = new Option<>("netherFoodRotting", true);

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