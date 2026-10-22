package net.greenjab.nekomasfixed.render.tab.screens.credits;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.TextListEntry;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.render.tab.entries.ImageEntry;
import net.greenjab.nekomasfixed.render.tab.entries.ImageTextEntry;
import net.greenjab.nekomasfixed.screen.config.ModConfigValues;
import net.greenjab.nekomasfixed.screen.config.TabLiterals;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.MapColor;


public class ModCreditScreen {

    private static final Component GREEN_JAB = Component.literal("Green_Jab").withColor(TextColor.GREEN);
    private static final Component CYBERMODDER = Component.literal("CyberModder").withColor(MapColor.COLOR_ORANGE.col);
    private static final Component AKSHAJ = Component.literal("Akshaj").withColor(MapColor.COLOR_PURPLE.col);
    private static final Component TECHNICMAN = Component.literal("Technicman").withColor(MapColor.COLOR_YELLOW.col);
    private static final Component STRIKEY = Component.literal("Strikey").withColor(0x00FFFF);
    private static final Component KRISENDO = Component.literal("Krisendo").withColor(0x771c1f);
    private static final Component TEJTY = Component.literal("Tejty").withColor(0x40bcbe);

    private static final Component NEKOMA = Component.literal("Nekoma").withColor(0xFF0054);
    private static final Component VELOCYBLUE = Component.literal("VelocyBlue").withColor(0x302E76);
    private static final Component LEBENO = Component.literal("Lebeno").withColor(0x9EAEAF);

    private static final Component TOASTER = Component.literal("Wifi Toaster").withColor(TextColor.LIGHT_PURPLE);
    private static final Component SPEEDY = Component.literal("Speedy").withColor(MapColor.COLOR_LIGHT_BLUE.col);
    private static final Component DITTO = Component.literal("Ditto_EXE20").withColor(MapColor.GOLD.col);

    private static final Component ALVARO_SEBB = Component.literal("AlvaroSebb").withColor(0x83141E);
    private static final Component LIMO4317 = Component.literal("Limo4317").withColor(TextColor.YELLOW);
    private static final Component LIMURCHIK14 = Component.literal("Limurchik14").withColor(TextColor.YELLOW);
    private static final Component PARASITAGAMER_OFC = Component.literal("Parasitagamer_ofc").withColor(TextColor.RED);
    private static final Component NOJMISEK = Component.literal("Nojmisek").withColor(MapColor.TERRACOTTA_RED.col);
    private static final Component SK4RDY = Component.literal("SK4RDY").withColor(MapColor.TERRACOTTA_WHITE.col);
    private static final Component TOTO77 = Component.literal("TOTO77").withColor(TextColor.GREEN);
    private static final Component ZLOY_KOSHAK34 = Component.literal("ZloyKoshak34").withColor(MapColor.TERRACOTTA_BLACK.col);


    public static Screen create(Screen parentScreen){
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setTitle(TabLiterals.CREDITS)
                .setSavingRunnable(ModConfigValues::saveCurrentWorld)
                ;
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory category = builder.getOrCreateCategory(TabLiterals.CREDITS);

        category.addEntry(entryBuilder.startTextDescription(TabLiterals.DEVELOPERS).build());

        addCredit(category, "green_jab", GREEN_JAB);
        addCredit(category, "cybermodder", CYBERMODDER);
        addCredit(category, "akshaj", AKSHAJ);
        addCredit(category, "technicman", TECHNICMAN);
        addCredit(category, "strikey", STRIKEY);
        addCredit(category, "krisendo", KRISENDO);
        addCredit(category, "tejty", TEJTY);

        category.addEntry(entryBuilder.startTextDescription(TabLiterals.ASSET_PROVIDERS).build());

        addCredit(category, "velocyblue", VELOCYBLUE);
        addCredit(category, "lebeno", LEBENO);

        category.addEntry(entryBuilder.startTextDescription(TabLiterals.TESTERS_CREDITS).build());

        addCredit(category, "toaster", TOASTER);
        addCredit(category, "speedy", SPEEDY);
        addCredit(category, "ditto", DITTO);

        category.addEntry(entryBuilder.startTextDescription(TabLiterals.TRANSLATORS_CREDITS).build());

        addCredit(category, "akshaj", AKSHAJ);
        addCredit(category, "alvarosebb", ALVARO_SEBB);
        addCredit(category, "limo4317", LIMO4317);
        addCredit(category, "limurchik14", LIMURCHIK14);
        addCredit(category, "nojmisek", NOJMISEK);
        addCredit(category, "parasitagamer_ofc", PARASITAGAMER_OFC);
        addCredit(category, "sk4rdy", SK4RDY);
        addCredit(category, "technicman", TECHNICMAN);
        addCredit(category, "toto77", TOTO77);
        addCredit(category, "zloykoshak34", ZLOY_KOSHAK34);


        return builder.build();
    }

    public static Identifier getId(String id){
        return NekomasFixed.id("textures/gui/sprites/images/"+id+".png");
    }

    public static void addCredit(ConfigCategory category, String pfp, Component msg){
        category.addEntry(new ImageTextEntry(getId(pfp), 25, 25, msg));
    }
}
