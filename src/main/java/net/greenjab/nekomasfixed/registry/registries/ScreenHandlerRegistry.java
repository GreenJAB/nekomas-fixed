package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.screen.KilnScreenHandler;
import net.greenjab.nekomasfixed.screen.PyrotechnicsTableScreenHandler;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class ScreenHandlerRegistry {

    public static final MenuType<KilnScreenHandler> KILN_SCREEN_HANDLER =
            Registry.register(
                    BuiltInRegistries.MENU,
                    NekomasFixed.id("kiln"),
                    new MenuType<>(KilnScreenHandler::new, FeatureFlags.VANILLA_SET)
            );

    public static final MenuType<PyrotechnicsTableScreenHandler> PYROTECHNICS_TABLE_HANDLER =
            Registry.register(
                    BuiltInRegistries.MENU,
                    NekomasFixed.id("pyrotechnics_table"),
                    new MenuType<>(PyrotechnicsTableScreenHandler::new, FeatureFlags.VANILLA_SET)
            );

    public static void registerScreenHandlers() {
        NekomasFixed.LOGGER.info("Registering screen handlers for " + "nekomasfixed");
    }
}