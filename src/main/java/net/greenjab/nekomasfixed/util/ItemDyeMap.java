package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.EnumMap;

public class ItemDyeMap {
    public static final EnumMap<AllDyes, Item> DYE = new EnumMap<>(AllDyes.class);
    public static final EnumMap<AllDyes, Item> BRUSH = new EnumMap<>(AllDyes.class);

    //this class provides all the mappings for both datagen and server side logic... its used by tagProvider and DyedBRushItem

    static {
        DYE.put(AllDyes.WHITE, Items.WHITE_DYE);
        DYE.put(AllDyes.ORANGE, Items.ORANGE_DYE);
        DYE.put(AllDyes.MAGENTA, Items.MAGENTA_DYE);
        DYE.put(AllDyes.LIGHT_BLUE, Items.LIGHT_BLUE_DYE);
        DYE.put(AllDyes.YELLOW, Items.YELLOW_DYE);
        DYE.put(AllDyes.LIME, Items.LIME_DYE);
        DYE.put(AllDyes.PINK, Items.PINK_DYE);
        DYE.put(AllDyes.GRAY, Items.GRAY_DYE);
        DYE.put(AllDyes.LIGHT_GRAY, Items.LIGHT_GRAY_DYE);
        DYE.put(AllDyes.CYAN, Items.CYAN_DYE);
        DYE.put(AllDyes.PURPLE, Items.PURPLE_DYE);
        DYE.put(AllDyes.BLUE, Items.BLUE_DYE);
        DYE.put(AllDyes.BROWN, Items.BROWN_DYE);
        DYE.put(AllDyes.GREEN, Items.GREEN_DYE);
        DYE.put(AllDyes.RED, Items.RED_DYE);
        DYE.put(AllDyes.BLACK, Items.BLACK_DYE);
        DYE.put(AllDyes.AMBER, ItemRegistry.AMBER_DYE);
        DYE.put(AllDyes.AQUA, ItemRegistry.AQUA_DYE);
        DYE.put(AllDyes.INDIGO, ItemRegistry.INDIGO_DYE);
        DYE.put(AllDyes.MAROON, ItemRegistry.MAROON_DYE);
        
        BRUSH.put(AllDyes.WHITE, ItemRegistry.WHITE_DYED_BRUSH);
        BRUSH.put(AllDyes.ORANGE, ItemRegistry.ORANGE_DYED_BRUSH);
        BRUSH.put(AllDyes.MAGENTA, ItemRegistry.MAGENTA_DYED_BRUSH);
        BRUSH.put(AllDyes.LIGHT_BLUE, ItemRegistry.LIGHT_BLUE_DYED_BRUSH);
        BRUSH.put(AllDyes.YELLOW, ItemRegistry.YELLOW_DYED_BRUSH);
        BRUSH.put(AllDyes.LIME, ItemRegistry.LIME_DYED_BRUSH);
        BRUSH.put(AllDyes.PINK, ItemRegistry.PINK_DYED_BRUSH);
        BRUSH.put(AllDyes.GRAY, ItemRegistry.GRAY_DYED_BRUSH);
        BRUSH.put(AllDyes.LIGHT_GRAY, ItemRegistry.LIGHT_GRAY_DYED_BRUSH);
        BRUSH.put(AllDyes.CYAN, ItemRegistry.CYAN_DYED_BRUSH);
        BRUSH.put(AllDyes.PURPLE, ItemRegistry.PURPLE_DYED_BRUSH);
        BRUSH.put(AllDyes.BLUE, ItemRegistry.BLUE_DYED_BRUSH);
        BRUSH.put(AllDyes.BROWN, ItemRegistry.BROWN_DYED_BRUSH);
        BRUSH.put(AllDyes.GREEN, ItemRegistry.GREEN_DYED_BRUSH);
        BRUSH.put(AllDyes.RED, ItemRegistry.RED_DYED_BRUSH);
        BRUSH.put(AllDyes.BLACK, ItemRegistry.BLACK_DYED_BRUSH);
        BRUSH.put(AllDyes.AMBER, ItemRegistry.AMBER_DYED_BRUSH);
        BRUSH.put(AllDyes.AQUA, ItemRegistry.AQUA_DYED_BRUSH);
        BRUSH.put(AllDyes.INDIGO, ItemRegistry.INDIGO_DYED_BRUSH);
        BRUSH.put(AllDyes.MAROON, ItemRegistry.MAROON_DYED_BRUSH);
        
    }
}
