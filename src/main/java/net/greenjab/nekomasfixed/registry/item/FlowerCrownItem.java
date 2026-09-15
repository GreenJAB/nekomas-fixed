package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.registry.entity.Moobloom.MoobloomVariants;
import net.greenjab.nekomasfixed.util.FlowerCrownVariants;
import net.minecraft.world.item.Item;

public class FlowerCrownItem extends Item {

    private final FlowerCrownVariants variant;

    public FlowerCrownItem(Properties properties, FlowerCrownVariants variant) {
        super(properties);
        this.variant = variant;
    }

    public FlowerCrownVariants getVariant() {
        return this.variant;
    }
}