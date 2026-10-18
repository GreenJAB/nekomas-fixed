package net.greenjab.nekomasfixed.screen.config;

import net.minecraft.network.chat.Component;

public class Literal {
    public static Component compText(String text, String id){
        return Component.translatable("config."+id+".button."+text);
    }

    public static Component compDesc(String text, String id){
        return Component.translatable("config."+id+".desc."+text);
    }

    // full name is compose separations -> compSeps
    public static Component compSeps(String text, int color) {
        return Component.translatable("config.separator." + text).withColor(color);
    }

    public static Component compSeps(String text) {
        return compSeps(text, 0xFFFFFF);
    }


    public static Component compNetherText(String text){
        return compText(text, "nether");
    }

    public static Component compWorldText(String text){
        return compText(text, "worldgen");
    }

    public static Component compEntityText(String text){
        return compText(text, "entity");
    }

    public static Component compEntityDesc(String text){
        return compDesc(text, "entity");
    }

    public static Component compWorldDesc(String text){
        return compDesc(text, "worldgen");
    }

    public static Component compOtherText(String text){
        return compText(text, "other");
    }

    public static Component compOtherDesc(String text){
        return compDesc(text, "other");
    }

    public static Component compNetherDesc(String text){
        return compDesc(text, "nether");
    }

    public static Component compMainTitle(String text){
        return Component.translatable("config.main_title."+text);
    }

   public static Component compGoToText(String text){
        return Component.translatable("config.go_to."+text);
   }
}
