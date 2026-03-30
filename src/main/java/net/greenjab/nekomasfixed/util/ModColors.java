package net.greenjab.nekomasfixed.util;

public record ModColors(String name, int color) {
    public static final ModColors AMBER = new ModColors("amber", 0xE0AF0B);
    public static final ModColors AQUA = new ModColors("aqua", 0xA6CEC7);
    public static final ModColors INDIGO = new ModColors("indigo", 0xFF453C8F);
    public static final ModColors MAROON = new ModColors("maroon", 0xFFA62D10);
}
