package net.greenjab.nekomasfixed.util;

import net.minecraft.resources.Identifier;

public final class GlintContext {
    private static int color;
    private static Identifier base;
    private GlintContext() {

    }

    public static int getColor() {
        return color;
    }

    public static Identifier getBase() {
        return base;
    }

    public static void setColor(int color) {
        GlintContext.color = color;
    }

    public static void setBase(Identifier base) {
        GlintContext.base = base;
    }
}
