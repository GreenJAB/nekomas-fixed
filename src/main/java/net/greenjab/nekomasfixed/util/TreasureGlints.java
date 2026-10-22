package net.greenjab.nekomasfixed.util;

import net.minecraft.util.StringRepresentable;

import static net.greenjab.nekomasfixed.util.AllDyes.*;

public enum TreasureGlints implements StringRepresentable {
    WATER("water", new AllDyes[]{LIGHT_BLUE, CYAN, BLUE}, 80, new int[]{}, null),
    FIRE("fire", new AllDyes[]{YELLOW, AMBER, ORANGE, RED}, 80, new int[]{}, null),
    ENDER("ender", new AllDyes[]{PINK, PURPLE}, 80, new int[]{}, null),
    RAINBOW("rainbow", new AllDyes[]{RED, ORANGE, AMBER, YELLOW, LIME, GREEN, CYAN, AQUA, LIGHT_BLUE, BLUE, INDIGO, PURPLE, MAGENTA, PINK, BROWN, MAROON, WHITE, LIGHT_GRAY, GRAY, BLACK}, 200, new int[]{}, null),
    FROSTFIRE("frostfire", new AllDyes[]{BLUE, LIGHT_BLUE, CYAN, AQUA, GREEN, LIME, YELLOW, ORANGE, AMBER, RED, PINK, PURPLE}, 140, new int[]{}, null),
    JUNGLE("jungle", new AllDyes[]{GREEN, LIME}, 80, new int[]{}, null),
    VOID("void", new AllDyes[]{BLACK, GRAY, LIGHT_GRAY}, 80, new int[]{}, null),
    SKY("sky", new AllDyes[]{CYAN, LIGHT_BLUE, WHITE}, 80, new int[]{}, null),
    ANCIENT("ancient", new AllDyes[]{MAROON, AMBER, INDIGO, AQUA}, 80, new int[]{}, null),
    SCULK("sculk", new AllDyes[]{CYAN}, 80, new int[]{0x0A1C23}, "textures/misc/sculk_glint.png");

    private final String id;
    private final AllDyes[] dyes;
    private final int cycleTicks;
    private final int[] extraColors;
    private final String texture;

    TreasureGlints(final String id, final AllDyes[] dyes, final int cycleTicks, int[] extraColors, String texture) {
        this.id = id;
        this.dyes = dyes;
        this.cycleTicks = cycleTicks;
        this.extraColors = extraColors;
        this.texture = texture;
    }

    @Override
    public String getSerializedName() {
        return this.id;
    }

    public AllDyes[] getDyes() {
        return dyes;
    }

    public int getCycleTicks() {
        return cycleTicks;
    }

    public int[] getExtraColors() {
        return extraColors;
    }

    public String getTexture() {
        return texture;
    }
}
