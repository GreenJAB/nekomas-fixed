package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.screen.KilnScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;

public class ScreenHandlerRegistry {

    public static final ScreenHandlerType<KilnScreenHandler> KILN_SCREEN_HANDLER =
            Registry.register(
                    Registries.SCREEN_HANDLER,
                    NekomasFixed.id("kiln"),
                    new ScreenHandlerType<>(KilnScreenHandler::new, FeatureFlags.VANILLA_FEATURES)
            );

    public static void registerScreenHandlers() {
        NekomasFixed.LOGGER.info("Registering screen handlers for " + "nekomasfixed");
    }
}