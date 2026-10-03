package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.util.GoatHornHelmetTiers;
import net.minecraft.world.item.Item;

public class GoatHornHelmetItem extends Item {

    private final GoatHornHelmetTiers tier;

    public GoatHornHelmetItem(Properties properties, GoatHornHelmetTiers tier) {
        super(properties);
        this.tier = tier;
    }

    public GoatHornHelmetTiers getTier() {
        return this.tier;
    }
}