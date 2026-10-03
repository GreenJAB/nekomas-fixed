package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public final class CushionMap {

    public static final Map<String, Item> BY_COLOR = new HashMap<>();
    public static final Map<Item, String> COLOR_OF = new HashMap<>();

    static {
        put("amber",  ItemRegistry.AMBER_CUSHION);
        put("aqua",   ItemRegistry.AQUA_CUSHION);
        put("indigo", ItemRegistry.INDIGO_CUSHION);
        put("maroon", ItemRegistry.MAROON_CUSHION);
    }

    private CushionMap() {}

    private static void put(String color, Item item) {
        BY_COLOR.put(color, item);
        COLOR_OF.put(item, color);
    }
}
