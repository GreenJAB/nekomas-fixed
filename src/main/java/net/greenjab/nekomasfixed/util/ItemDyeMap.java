package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import java.util.EnumMap;

public class ItemDyeMap {
    public static final EnumMap<AllDyes, Item> DYE = new EnumMap<>(AllDyes.class);
    public static final EnumMap<AllDyes, Item> BRUSH = new EnumMap<>(AllDyes.class);

    static {
        DYE.put(AllDyes.WHITE, Items.WOOL.white());
        DYE.put(AllDyes.ORANGE, Items.WOOL.orange());
        DYE.put(AllDyes.MAGENTA, Items.WOOL.magenta());
        DYE.put(AllDyes.LIGHT_BLUE, Items.WOOL.lightBlue());
        DYE.put(AllDyes.YELLOW, Items.WOOL.yellow());
        DYE.put(AllDyes.LIME, Items.WOOL.lime());
        DYE.put(AllDyes.PINK, Items.WOOL.pink());
        DYE.put(AllDyes.GRAY, Items.WOOL.gray());
        DYE.put(AllDyes.LIGHT_GRAY, Items.WOOL.lightGray());
        DYE.put(AllDyes.CYAN, Items.WOOL.cyan());
        DYE.put(AllDyes.PURPLE, Items.WOOL.purple());
        DYE.put(AllDyes.BLUE, Items.WOOL.blue());
        DYE.put(AllDyes.BROWN, Items.WOOL.brown());
        DYE.put(AllDyes.GREEN, Items.WOOL.green());
        DYE.put(AllDyes.RED, Items.WOOL.red());
        DYE.put(AllDyes.BLACK, Items.WOOL.black());
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
