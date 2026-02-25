package net.greenjab.nekomasfixed.util;

public class ModColors {
    // Creating new Mod colors, new colors can be added later...
    public static final ModColors AMBER = new ModColors("amber", 0xE0AF0B);
    public static final ModColors AQUA = new ModColors("aqua", 0xA6CEC7);
    public static final ModColors INDIGO = new ModColors("indigo", 0xFF453C8F);
    public static final ModColors CRIMSON = new ModColors("crimson", 0xFFA62D10);

    private final String name; // Instance field
    private final int color;   // Instance field

    // Constructor
    public ModColors(String name, int color){
        this.name = name;
        this.color = color;
    }

    public  String getName(){
        return name;
    }

    public  int getColor(){
        return color;
    }
}
