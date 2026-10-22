package net.greenjab.nekomasfixed.render.tab.screens;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.fabric.ClothConfigModMenuDemo;
import net.greenjab.nekomasfixed.screen.config.ModConfigValues;
import net.greenjab.nekomasfixed.screen.config.TabLiterals;
import net.minecraft.client.gui.screens.Screen;

public class OtherConfigScreen {
    public static Screen create(Screen parentScreen) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setTitle(TabLiterals.OTHER_TITLE)
                .setSavingRunnable(ModConfigValues::saveCurrentWorld)
                ;

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory worldCategory = builder.getOrCreateCategory(TabLiterals.OTHER_TITLE);

        worldCategory.addEntry(entryBuilder.startTextDescription(TabLiterals.MISC_BLUE).build());

        worldCategory.addEntry(entryBuilder.startBooleanToggle(TabLiterals.COPPER_ARMOR_CHANGES,
                        ModConfigValues.enableCopperBuff.get()).setDefaultValue(true).setTooltip(TabLiterals.COPPER_ARMOR_CHANGES_DESC)
                .setSaveConsumer(ModConfigValues.enableCopperBuff::set).build());




        return builder.build();
    }
}
