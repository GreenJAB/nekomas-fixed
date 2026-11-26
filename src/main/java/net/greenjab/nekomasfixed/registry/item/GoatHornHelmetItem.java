package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.util.GoatHornHelmetTiers;
import net.minecraft.world.item.ArmorItem;

public class GoatHornHelmetItem extends ArmorItem {
    public final GoatHornHelmetTiers tier;

    public GoatHornHelmetItem(GoatHornHelmetTiers tier, Properties properties) {
        super(tier.material, Type.HELMET, properties);
        this.tier = tier;
    }
}
