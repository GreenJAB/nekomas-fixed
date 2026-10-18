package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.screen.PyrotechnicsMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class ScreenHandlerRegistry {

    public static final MenuType<PyrotechnicsMenu> PYROTECHNICS =
            Registry.register(
                    BuiltInRegistries.MENU,
                    NekomasFixed.id("pyrotechnics"),
                    new MenuType<>(PyrotechnicsMenu::new, FeatureFlags.VANILLA_SET)
            );

    public static void registerScreenHandlers() {
        NekomasFixed.LOGGER.info("Registering screen handlers for " + "nekomasfixed");
    }
}