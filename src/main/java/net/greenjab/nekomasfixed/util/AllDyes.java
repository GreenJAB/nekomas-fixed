package net.greenjab.nekomasfixed.util;

import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

public enum AllDyes implements StringRepresentable {
    WHITE("white" ),
    ORANGE("orange"),
    MAGENTA("magenta"),
    LIGHT_BLUE("light_blue"),
    YELLOW("yellow"),
    LIME( "lime"),
    PINK("pink"),
    GRAY("gray"),
    LIGHT_GRAY( "light_gray"),
    CYAN("cyan"),
    PURPLE("purple"),
    BLUE( "blue"),
    BROWN("brown"),
    GREEN("green"),
    RED("red"),
    BLACK( "black"),
    AMBER("amber"),
    AQUA("aqua"),
    INDIGO("indigo"),
    MAROON("maroon");

    private final String id;
    AllDyes(final String id) {
        this.id = id;
    }

    @Override
    public @NonNull String getSerializedName() {
        return this.id;
    }
}
