package net.greenjab.nekomasfixed.registry.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;

// 1.21.1 BlockItem derives its translation key from the block, which would name this
// item "Ghost Pepper Shrub"; main achieves "Ghost Pepper" via
// Item.Properties.useItemDescriptionPrefix() (26.x-only).
public class GhostPepperItem extends BlockItem {
    public GhostPepperItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    public @NonNull String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }
}
