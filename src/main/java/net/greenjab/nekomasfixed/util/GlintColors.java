package net.greenjab.nekomasfixed.util;

public final class GlintColors {
    public static final int GLINT_FLOOR = 120;
    public static final int GLINT_FLOOR_GRAY = 90;

    public static final int TOOLTIP_FLOOR = 150;
    public static final int TOOLTIP_FLOOR_GRAY = 130;

    private static final int GRAY_SPREAD = 24;

    private GlintColors() {}

    public static int lift(int color, int floor, int grayFloor) {
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;
        int peak = Math.max(r, Math.max(g, b));
        int lowest = Math.min(r, Math.min(g, b));

        int usedFloor = (peak - lowest < GRAY_SPREAD) ? grayFloor : floor;
        // Pure black has no hue to keep, so return a plain gray at the floor
        if (peak == 0) {
            return 0xFF000000 | usedFloor << 16 | usedFloor << 8 | usedFloor;
        }

        float targetPeak = usedFloor + (peak / 255f) * (255 - usedFloor);
        float scale = targetPeak / peak;

        int nr = Math.min(255, Math.round(r * scale));
        int ng = Math.min(255, Math.round(g * scale));
        int nb = Math.min(255, Math.round(b * scale));
        return 0xFF000000 | nr << 16 | ng << 8 | nb;
    }
}