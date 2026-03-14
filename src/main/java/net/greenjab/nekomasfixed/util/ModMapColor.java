package net.greenjab.nekomasfixed.util;

import com.google.common.base.Preconditions;
import net.minecraft.block.MapColor;
import net.minecraft.util.math.ColorHelper;
import org.jspecify.annotations.Nullable;

public class ModMapColor  {
    private static final @Nullable ModMapColor[] COLORS = new ModMapColor[5];
    public static final ModMapColor CLEAR = new ModMapColor(0, 0);
    public static final ModMapColor AMBER = new ModMapColor(1, 14724875);
    public static final ModMapColor AQUA = new ModMapColor(2, 10931911);
    public static final ModMapColor CRIMSON = new ModMapColor(3, -12239729);
    public static final ModMapColor INDIGO = new ModMapColor(4, -5886704);
    public final int color;
    public final int id;

    private ModMapColor(int id, int color){
        this.id = id;
        this.color = color;
    }

    public int getRenderColor(MapColor.Brightness brightness) {
        return this == CLEAR ? 0 : ColorHelper.scaleRgb(ColorHelper.fullAlpha(this.color), brightness.brightness);
    }

    public static @Nullable ModMapColor get(int id) {
        Preconditions.checkPositionIndex(id, COLORS.length, "material id");
        return getUnchecked(id);
    }

    private static @Nullable ModMapColor getUnchecked(int id) {
        ModMapColor mapColor = COLORS[id];
        return mapColor != null ? mapColor : CLEAR;
    }

    public byte getRenderColorByte(MapColor.Brightness brightness) {
        return (byte)(this.id << 2 | brightness.id & 3);
    }
}
