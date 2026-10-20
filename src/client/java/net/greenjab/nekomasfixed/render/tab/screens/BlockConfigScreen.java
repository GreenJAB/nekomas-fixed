package net.greenjab.nekomasfixed.render.tab.screens;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.api.Requirement;
import me.shedaniel.clothconfig2.fabric.ClothConfigModMenuDemo;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import me.shedaniel.clothconfig2.gui.entries.IntegerSliderEntry;
import me.shedaniel.clothconfig2.impl.builders.BooleanToggleBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.greenjab.nekomasfixed.screen.config.ModConfigValues;
import net.greenjab.nekomasfixed.screen.config.TabLiterals;
import net.minecraft.client.gui.screens.CreditsAndAttributionScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.Collection;
import java.util.LinkedList;

public class BlockConfigScreen {
    public static Screen create(Screen parentScreen){
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setTitle(TabLiterals.BLOCKS_TITLE)
                .setSavingRunnable(ModConfigValues::saveCurrentWorld)
                ;
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory category = builder.getOrCreateCategory(TabLiterals.BLOCKS_TITLE);

        category.addEntry(entryBuilder.startTextDescription(TabLiterals.VILLAGERS).build());

        category.addEntry(entryBuilder.startBooleanToggle(TabLiterals.ENABLE_MESSY_BEDS,
                        ModConfigValues.enableMessyBeds.get()).setDefaultValue(true)
                .setTooltip(TabLiterals.ENABLE_MESSY_BEDS_DESC)
                .setSaveConsumer(ModConfigValues.enableMessyBeds::set)
                .build());

        category.addEntry(entryBuilder.startTextDescription(TabLiterals.INTERACTION).build());

        category.addEntry(entryBuilder.startBooleanToggle(TabLiterals.ENDERMAN_HEAD_ACTIVATES,
                        ModConfigValues.endermanHeadActivates.get()).setDefaultValue(true)
                .setTooltip(TabLiterals.ENDERMAN_HEAD_ACTIVATES_DESC)
                .setSaveConsumer(ModConfigValues.endermanHeadActivates::set)
                .build());

        SubCategoryBuilder cakeSubCategory = entryBuilder.startSubCategory(TabLiterals.CAKE).setExpanded(true);
        BooleanListEntry enableStackedCakes = entryBuilder.startBooleanToggle(TabLiterals.ENABLE_STACKED_CAKES,
                        ModConfigValues.enableStackedCakes.get()).setDefaultValue(true)
                .setTooltip(TabLiterals.ENABLE_STACKED_CAKES_DESC)
                .setSaveConsumer(ModConfigValues.enableStackedCakes::set)
                .build();
        cakeSubCategory.add(enableStackedCakes);


        Collection<IntegerSliderEntry> toggles = new LinkedList<>();
        toggles.add(entryBuilder.startIntSlider(TabLiterals.CAKE_SLIDER, ModConfigValues.cakeSize.get(), 2, 3)
                .setRequirement(Requirement.isTrue(enableStackedCakes))
                        .setTooltip(TabLiterals.CAKE_SLIDER_DESC)
                        .setSaveConsumer(ModConfigValues.cakeSize::set)
                .build());

        cakeSubCategory.addAll(toggles);
        category.addEntry(cakeSubCategory.build());

        return builder.build();
    }
}

