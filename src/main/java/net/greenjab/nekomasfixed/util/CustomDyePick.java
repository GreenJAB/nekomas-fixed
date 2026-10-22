package net.greenjab.nekomasfixed.util;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ColorCollection;

import java.util.EnumMap;
import java.util.List;

public class CustomDyePick {

    private static List<EnumMap<AllDyes, Block>> maps;

    private static List<EnumMap<AllDyes, Block>> maps() {
        if (maps == null) {
            maps = List.of(
                    BlockDyeMap.BRICKS, BlockDyeMap.BRICK_SLAB, BlockDyeMap.BRICK_STAIRS, BlockDyeMap.BRICK_WALL,
                    BlockDyeMap.TERRACOTTA, BlockDyeMap.STAINED_GLASS, BlockDyeMap.STAINED_GLASS_PANE,
                    BlockDyeMap.WOOL, BlockDyeMap.SPOTTED_WOOL, BlockDyeMap.CARPET, BlockDyeMap.SPOTTED_CARPET,
                    BlockDyeMap.GLAZED_TERRACOTTA, BlockDyeMap.CANDLE, BlockDyeMap.CONCRETE,
                    BlockDyeMap.CONCRETE_POWDER, BlockDyeMap.FROGLIGHT, BlockDyeMap.SHULKER_BOX, BlockDyeMap.BED,
                    BlockDyeMap.SPOTTED_WOOL_SLABS, BlockDyeMap.SPOTTED_WOOL_STAIRS, BlockDyeMap.WOOL_SLABS,
                    BlockDyeMap.WOOL_STAIRS, BlockDyeMap.CONCRETE_SLABS, BlockDyeMap.CONCRETE_STAIRS
            );
        }
        return maps;
    }

    @SuppressWarnings("unchecked")
    public static <T> T pick(ColorCollection<T> collection, DyeColor color) {
        AllDyes dye = null;
        for (AllDyes d : AllDyes.values()) {
            if (d.getName().equals(color.getName())) { dye = d; break; }
        }
        if (dye == null) return null;

        T white = collection.white();
        for (EnumMap<AllDyes, Block> map : maps()) {
            Block mapWhite = map.get(AllDyes.WHITE);
            Block target = map.get(dye);
            if (mapWhite == null || target == null) continue;

            if (white == mapWhite) return (T) target;
            if (white instanceof Item && white == mapWhite.asItem()) return (T) target.asItem();
        }
        return null;
    }
}