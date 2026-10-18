package net.greenjab.nekomasfixed.render.tab.screens;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.greenjab.nekomasfixed.screen.config.ModConfigValues;
import net.greenjab.nekomasfixed.screen.config.TabLiterals;
import net.minecraft.client.gui.screens.Screen;

public class EntityConfigScreen {
    public static Screen create(Screen parentScreen){
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setTitle(TabLiterals.ENTITY_TITLE)
                .setSavingRunnable(ModConfigValues::saveCurrentWorld)
                ;
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory category = builder.getOrCreateCategory(TabLiterals.ENTITY_TITLE);

        category.addEntry(entryBuilder.startTextDescription(TabLiterals.VILLAGERS).build());

        category.addEntry(entryBuilder.startBooleanToggle(TabLiterals.ENABLE_MESSY_BEDS,
                        ModConfigValues.enableMessyBeds.get()).setDefaultValue(true)
                .setTooltip(TabLiterals.ENABLE_MESSY_BEDS_DESC)
                .setSaveConsumer(ModConfigValues.enableMessyBeds::set)
                .build());

        category.addEntry(entryBuilder.startTextDescription(TabLiterals.SPAWN).build());

        category.addEntry(entryBuilder.startBooleanToggle(TabLiterals.SPAWN_SUS_SPIDERS,
                        ModConfigValues.spawnSusSpider.get()).setDefaultValue(true)
                .setTooltip(TabLiterals.SPAWN_SUS_SPIDERS_DESC)
                .setSaveConsumer(ModConfigValues.spawnSusSpider::set)
                .build());

        return builder.build();
    }
}
