package net.greenjab.nekomasfixed.render.tab.screens;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.greenjab.nekomasfixed.screen.config.ModConfigValues;
import net.greenjab.nekomasfixed.screen.config.TabLiterals;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.Scanner;

public class NetherConfigScreen {

    public static Screen create(Screen parentScreen){
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setTitle(TabLiterals.NETHER_TITLE)
                .setSavingRunnable(ModConfigValues::saveCurrentWorld)
                ;
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory netherCategory = builder.getOrCreateCategory(TabLiterals.NETHER_TITLE);

        netherCategory.addEntry(entryBuilder.startTextDescription(TabLiterals.NETHER).build());

        netherCategory.addEntry(entryBuilder.startBooleanToggle(TabLiterals.DO_FOOD_ROTTING,
                        ModConfigValues.netherFoodRotting.get()).setDefaultValue(true)
                .setTooltip(TabLiterals.DO_FOOD_ROTTING_DESC)
                .setSaveConsumer(ModConfigValues.netherFoodRotting::set)
                .build());

        netherCategory.addEntry(entryBuilder.startBooleanToggle(TabLiterals.MAGMA_BLOCK_CHANGES,
                        ModConfigValues.magmaBlockNeedsSilkTouch.get()).setDefaultValue(true)
                .setTooltip(TabLiterals.MAGMA_BLOCK_CHANGES_DESC)
                .setSaveConsumer(ModConfigValues.magmaBlockNeedsSilkTouch::set)
                .build());

        return builder.build();
    }
}
