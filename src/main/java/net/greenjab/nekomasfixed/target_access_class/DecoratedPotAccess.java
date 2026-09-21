package net.greenjab.nekomasfixed.target_access_class;

public interface DecoratedPotAccess {
    String SHERD_GLOW_OVERRIDES_KEY = "nekomasfixed.sherd_glow_overrides";
    int BACK = 0;
    int LEFT = 1;
    int RIGHT = 2;
    int FRONT = 3;
    boolean nekomasfixed$getSherdGlow(int index);
    void nekomasfixed$setSherdGlow(int index, boolean glowing);
}
