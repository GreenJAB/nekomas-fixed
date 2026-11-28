package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.util.CrownTiers;
import net.minecraft.world.item.ArmorItem;

public class CrownItem extends ArmorItem {
    public final CrownTiers tier;

    public CrownItem(CrownTiers tier, Properties properties) {
        super(tier.material, Type.HELMET, properties);
        this.tier = tier;
    }
}
