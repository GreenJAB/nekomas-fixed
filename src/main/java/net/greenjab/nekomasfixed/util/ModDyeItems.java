package net.greenjab.nekomasfixed.util;

import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.util.DyeColor;

import java.util.HashMap;
import java.util.Map;

public class ModDyeItems extends DyeItem {
    private static final Map<ModColors, ModDyeItems> DYES = new HashMap<>();
    private final ModColors color;

    public ModDyeItems(ModColors color, Item.Settings settings) {
        super(DyeColor.WHITE, settings);
        this.color = color;
        DYES.put(color, this);
    }

    public ModColors getModColor() {
        return color;
    }

    public static ModDyeItems byColor(ModColors color) {
        return DYES.get(color);
    }
}
