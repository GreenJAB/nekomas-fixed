package net.greenjab.nekomasfixed.util;

import java.util.HashMap;
import java.util.UUID;

public class ModData {
    // Per-player combo timer in ticks, for weapons carrying the combo_multiplier component.
    public static final HashMap<UUID, Integer> combos = new HashMap<>();
}
