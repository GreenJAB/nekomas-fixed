package net.greenjab.nekomasfixed.screen.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.block.MapColor;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.concurrent.atomic.AtomicReference;

public class ConfigTrial {

    public static Screen createConfigScreen(Screen parentScreen) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setTitle(Text.literal("Nekomas Fixed Config"));

        ConfigCategory netherCategory = builder.getOrCreateCategory(Text.literal("Nether Features"));
        ConfigCategory worldCategory = builder.getOrCreateCategory(Text.literal("World Features"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        AtomicReference<Boolean> doFoodRotting = new AtomicReference<>(true);
        AtomicReference<Boolean> enableCopperBuff = new AtomicReference<>(true);

        netherCategory.addEntry(
                entryBuilder.startTextDescription(
                        Text.literal("=== Nether Improvements ===").withColor(0xBA2720)
                ).build()
        );

        netherCategory.addEntry(entryBuilder.startBooleanToggle(Text.literal("Do Food Rotting"), doFoodRotting.get())
                .setDefaultValue(true)
                .setTooltip(Text.literal("All food items except for the golden ones rot in nether over time"))
                .setSaveConsumer(doFoodRotting::set)
                .build());

        worldCategory.addEntry(
                entryBuilder.startTextDescription(
                        Text.literal("=== World Improvements ===").withColor(MapColor.LIGHT_BLUE.color)
                ).build()
        );

        worldCategory.addEntry(entryBuilder.startBooleanToggle(Text.literal("Enable Copper Buffs"), ModConfigValues.enableCopperBuff)
                .setDefaultValue(true)
                .setTooltip(Text.literal("Lightning striking a player with full copper gear would give the player speed "))
                .setSaveConsumer((val)-> ModConfigValues.enableCopperBuff = val)
                .build());

        builder.setSavingRunnable(() -> System.out.println("Config saved! :" + enableCopperBuff));

        return builder.build();
    }
}