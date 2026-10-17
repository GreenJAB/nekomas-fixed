package net.greenjab.nekomasfixed.render.tab.screens;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.greenjab.nekomasfixed.screen.config.ModConfigValues;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.Scanner;

public class NetherConfigScreen {

    public static Screen create(Screen parentScreen){
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setTitle(Component.literal("Nekomas Fixed Config"))
                .setSavingRunnable(ModConfigValues::saveCurrentWorld)
                ;
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory netherCategory = builder.getOrCreateCategory(Component.literal("Nether Features"));
        netherCategory.addEntry(entryBuilder.startTextDescription(Component.literal("=== Nether Improvements ===").withColor(0xBA2720)).build());
        netherCategory.addEntry(entryBuilder.startBooleanToggle(Component.literal("Do Food Rotting"),
                        ModConfigValues.netherFoodRotting.get()).setDefaultValue(true).setTooltip(Component.literal(
                        "All food items except for the golden ones rot in nether over time"))
                .setSaveConsumer(ModConfigValues.netherFoodRotting::set)
                .build());

        return builder.build();
    }
}
