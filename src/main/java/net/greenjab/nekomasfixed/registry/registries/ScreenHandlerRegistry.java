package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.screen.FletchingMenu;
import net.greenjab.nekomasfixed.screen.KilnMenu;
import net.greenjab.nekomasfixed.screen.PyrotechnicsMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.CraftingTableBlock;

public class ScreenHandlerRegistry {

    public static final MenuType<KilnMenu> KILN =
            Registry.register(
                    BuiltInRegistries.MENU,
                    NekomasFixed.id("kiln"),
                    new MenuType<>(KilnMenu::new, FeatureFlags.VANILLA_SET)
            );

    public static final MenuType<PyrotechnicsMenu> PYROTECHNICS =
            Registry.register(
                    BuiltInRegistries.MENU,
                    NekomasFixed.id("pyrotechnics"),
                    new MenuType<>(PyrotechnicsMenu::new, FeatureFlags.VANILLA_SET)
            );


    public static final MenuType<FletchingMenu> FLETCHING =
            Registry.register(
                    BuiltInRegistries.MENU,
                    NekomasFixed.id("fletching"),
                    new MenuType<>(FletchingMenu::new, FeatureFlags.VANILLA_SET)
            );


    public static void registerScreenHandlers() {
        NekomasFixed.LOGGER.info("Registering screen handlers for " + "nekomasfixed");
    }


}