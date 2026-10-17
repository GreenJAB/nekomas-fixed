package net.greenjab.nekomasfixed.render.tab.screens;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.greenjab.nekomasfixed.screen.config.ModConfigValues;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.material.MapColor;

public class OtherConfigScreen {
    public static Screen create(Screen parentScreen) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setTitle(Component.literal("Nekomas Fixed Config"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory worldCategory = builder.getOrCreateCategory(Component.literal("World Features"));
        worldCategory.addEntry(entryBuilder.startTextDescription(Component.literal("=== World Improvements ===").withColor(MapColor.COLOR_LIGHT_BLUE.col)).build());
        worldCategory.addEntry(entryBuilder.startBooleanToggle(Component.literal("Enable Copper Buffs"),
                        ModConfigValues.enableCopperBuff.get()).setDefaultValue(true).setTooltip(Component.literal(
                        "Lightning striking a player with full copper gear would give the player speed "))
                .setSaveConsumer(ModConfigValues.enableCopperBuff::set).build());



        return builder.build();
    }
}
