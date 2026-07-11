package net.greenjab.nekomasfixed.screen.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.material.MapColor;

public class ConfigTrial {

    public static Screen createConfigScreen(Screen parentScreen) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setTitle(Component.literal("Nekomas Fixed Config"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory netherCategory = builder.getOrCreateCategory(Component.literal("Nether Features"));
        netherCategory.addEntry(entryBuilder.startTextDescription(Component.literal("=== Nether Improvements ===").withColor(0xBA2720)).build());
        netherCategory.addEntry(entryBuilder.startBooleanToggle(Component.literal("Do Food Rotting"),
                ModConfigValues.netherFoodRotting).setDefaultValue(true).setTooltip(Component.literal(
                        "All food items except for the golden ones rot in nether over time"))
                .setSaveConsumer(val -> ModConfigValues.netherFoodRotting = val).build());

        ConfigCategory worldCategory = builder.getOrCreateCategory(Component.literal("World Features"));
        worldCategory.addEntry(entryBuilder.startTextDescription(Component.literal("=== World Improvements ===").withColor(MapColor.COLOR_LIGHT_BLUE.col)).build());
        worldCategory.addEntry(entryBuilder.startBooleanToggle(Component.literal("Enable Copper Buffs"),
                ModConfigValues.enableCopperBuff).setDefaultValue(true).setTooltip(Component.literal(
                        "Lightning striking a player with full copper gear would give the player speed "))
                .setSaveConsumer(val -> ModConfigValues.enableCopperBuff = val).build());

        builder.setSavingRunnable(ModConfigValues::saveConfigs);

        return builder.build();
    }
}