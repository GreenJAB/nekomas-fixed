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

        category.addEntry(entryBuilder.startTextDescription(TabLiterals.SPAWN).build());

        //sus spider
        category.addEntry(entryBuilder.startBooleanToggle(TabLiterals.SPAWN_SUS_SPIDERS,
                        ModConfigValues.spawnSusSpider.get()).setDefaultValue(true)
                .setTooltip(TabLiterals.SPAWN_SUS_SPIDERS_DESC)
                .setSaveConsumer(ModConfigValues.spawnSusSpider::set)
                .build());

        //moobloom
        category.addEntry(entryBuilder.startBooleanToggle(TabLiterals.SPAWN_MOOBLOOMS,
                        ModConfigValues.spawnMoobloom.get()).setDefaultValue(true)
                .setTooltip(TabLiterals.SPAWN_MOOBLOOMS_DESC)
                .setSaveConsumer(ModConfigValues.spawnMoobloom::set)
                .build());


        //termites
        category.addEntry(entryBuilder.startBooleanToggle(TabLiterals.SPAWN_TERMITE,
                        ModConfigValues.spawnTermite.get()).setDefaultValue(true)
                .setTooltip(TabLiterals.SPAWN_TERMITE_DESC)
                .setSaveConsumer(ModConfigValues.spawnTermite::set)
                .build());

        //derelict
        category.addEntry(entryBuilder.startBooleanToggle(TabLiterals.SPAWN_DERELICT,
                        ModConfigValues.spawnDerelict.get()).setDefaultValue(true)
                .setTooltip(TabLiterals.SPAWN_DERELICT_DESC)
                .setSaveConsumer(ModConfigValues.spawnDerelict::set)
                .build());

        //rime
        category.addEntry(entryBuilder.startBooleanToggle(TabLiterals.SPAWN_RIME,
                        ModConfigValues.spawnRime.get()).setDefaultValue(true)
                .setTooltip(TabLiterals.SPAWN_RIME_DESC)
                .setSaveConsumer(ModConfigValues.spawnRime::set)
                .build());

        //drenched
        category.addEntry(entryBuilder.startBooleanToggle(TabLiterals.SPAWN_DRENCHED,
                        ModConfigValues.spawnDrenched.get()).setDefaultValue(true)
                .setTooltip(TabLiterals.SPAWN_DRENCHED_DESC)
                .setSaveConsumer(ModConfigValues.spawnDrenched::set)
                .build());

        //wildfire
        category.addEntry(entryBuilder.startBooleanToggle(TabLiterals.SPAWN_WILDFIRE,
                        ModConfigValues.spawnWildfire.get()).setDefaultValue(true)
                .setTooltip(TabLiterals.SPAWN_WILDFIRE_DESC)
                .setSaveConsumer(ModConfigValues.spawnWildfire::set)
                .build());

        return builder.build();
    }
}
