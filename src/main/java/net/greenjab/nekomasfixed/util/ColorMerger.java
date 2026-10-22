package net.greenjab.nekomasfixed.util;

import net.minecraft.util.ARGB;

public class ColorMerger {
    public static int mix(int... colors) {
        if (colors.length == 0) return 0xFFFFFFFF;

        int r = 0, g = 0, b = 0;
        for (int c : colors) {
            r += (c >> 16) & 0xFF;
            g += (c >> 8) & 0xFF;
            b += c & 0xFF;
        }
        int n = colors.length;
        return ARGB.color(255, r / n, g / n, b / n);
    }

}
