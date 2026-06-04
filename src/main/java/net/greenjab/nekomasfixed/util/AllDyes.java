package net.greenjab.nekomasfixed.util;

import net.minecraft.util.StringIdentifiable;

public enum AllDyes implements StringIdentifiable {
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

    public static final StringIdentifiable.EnumCodec<AllDyes> CODEC = StringIdentifiable.createCodec(AllDyes::values);

    private final String id;

    AllDyes(final String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public String toString() {
        return this.id;
    }

    @Override
    public String asString() {
        return this.id;
    }
}
