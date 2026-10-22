package net.greenjab.nekomasfixed.render.tab.screens;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.greenjab.nekomasfixed.screen.config.ModConfigValues;
import net.greenjab.nekomasfixed.screen.config.TabLiterals;
import net.minecraft.client.gui.screens.Screen;

public class ItemConfigScreen {
    public static Screen create(Screen parentScreen){
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setTitle(TabLiterals.ITEM_TITLE)
                .setSavingRunnable(ModConfigValues::saveCurrentWorld)
                ;
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory category = builder.getOrCreateCategory(TabLiterals.ITEM_TITLE);

        category.addEntry(entryBuilder.startTextDescription(TabLiterals.RECIPE).build());

        category.addEntry(entryBuilder.startBooleanToggle(TabLiterals.ENABLE_DYED_BRUSHES,
                        ModConfigValues.dyedBrush.get()).setDefaultValue(true).setTooltip(TabLiterals.ENABLE_DYED_BRUSHES_DESC)
                .setSaveConsumer(ModConfigValues.dyedBrush::set).build());

        return builder.build();
    }
}
