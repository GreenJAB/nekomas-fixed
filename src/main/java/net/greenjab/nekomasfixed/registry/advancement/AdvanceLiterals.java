package net.greenjab.nekomasfixed.registry.advancement;


import net.minecraft.network.chat.Component;

public class AdvanceLiterals {
    public static Component title(String name){
        return Component.translatable("advancements.nekomasfixed."+name+".title");
    }

    public static Component desc(String name){
        return Component.translatable("advancements.nekomasfixed."+name+".description");
    }
}
