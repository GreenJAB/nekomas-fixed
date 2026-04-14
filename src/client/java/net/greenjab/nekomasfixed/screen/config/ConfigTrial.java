package net.greenjab.nekomasfixed.screen.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.concurrent.atomic.AtomicReference;

public class ConfigTrial {

    public static Screen createConfigScreen(Screen parentScreen) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setTitle(Text.literal("Nekomas Fixed Config"));

        ConfigCategory emptyCategory = builder.getOrCreateCategory(Text.literal("Main Features"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        AtomicReference<String> currentValue = new AtomicReference<>("Name: ");
        emptyCategory.addEntry(entryBuilder.startStrField(Text.literal("Enter your name"), currentValue.get())
                .setDefaultValue("Your name")
                .setTooltip(Text.literal("This option takes name as the user input!"))
                .setSaveConsumer(currentValue::set)
                .build());


        builder.setSavingRunnable(() -> System.out.println("Config saved! :" + currentValue));

        return builder.build();
    }
}