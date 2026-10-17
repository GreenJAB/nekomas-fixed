package net.greenjab.nekomasfixed.render.tab.screens;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.greenjab.nekomasfixed.render.tab.TabLiterals;
import net.greenjab.nekomasfixed.screen.config.ModConfigValues;
import net.minecraft.client.gui.screens.Screen;

public class WorldGenConfigScreen {

    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(TabLiterals.WORLD_MENU_TITLE);
        ConfigEntryBuilder eb = builder.entryBuilder();

        ConfigCategory cat = builder.getOrCreateCategory(TabLiterals.WORLD_MENU_TITLE);

        cat.addEntry(eb.startTextDescription(TabLiterals.TERRAIN).build());

        cat.addEntry(eb.startBooleanToggle(TabLiterals.GENERATE_GEYSERS, ModConfigValues.generateGeysers.get())
                .setDefaultValue(false)
                .setTooltip(TabLiterals.GENERATE_GEYSERS_DESC)
                .setSaveConsumer(ModConfigValues.generateGeysers::set)
                .build());

        cat.addEntry(eb.startBooleanToggle(TabLiterals.GENERATE_TERMITE_MOUND, ModConfigValues.generateMound.get())
                .setDefaultValue(false)
                .setTooltip(TabLiterals.GENERATE_TERMITE_MOUND_DESC)
                .setSaveConsumer(ModConfigValues.generateMound::set)
                .build());


        return builder.build();
    }
}