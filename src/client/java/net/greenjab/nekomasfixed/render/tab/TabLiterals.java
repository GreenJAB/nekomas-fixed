package net.greenjab.nekomasfixed.render.tab;

import net.minecraft.network.chat.Component;
import oshi.util.tuples.Pair;


public class TabLiterals {

    public static final Component WORLD_MENU_TITLE = Component.translatable("config.worldgen.title");
    public static final Component TERRAIN = compSeps("terrain", 0xBA2720);
    public static final Component GO_TO_MENU = Component.translatable("config.worldgen.go_menu");

    public static final Component GENERATE_GEYSERS = compButton("gen_geyser");
    public static final Component GENERATE_GEYSERS_DESC = compDesc("gen_geyser");

    public static final Component GENERATE_TERMITE_MOUND = compButton("gen_mound");
    public static final Component GENERATE_TERMITE_MOUND_DESC = compDesc("gen_mound");

    // DO NOT USE CAPITAL LETTERS HERE, ITS LANG FILE ID!
    private static Component compButton(String text){
        return Component.translatable("config.worldgen.button."+text);
    }

    private static Component compDesc(String text){
        return Component.translatable("config.worldgen.desc."+text);
    }

    private static Component compText(String text){
        return Component.translatable("config.worldgen.text."+text);
    }

    // full name is compose separations -> compSeps
    private static Component compSeps(String text, int color){
        return Component.translatable("config.worldgen.title."+text).withColor(color);
    }





}
