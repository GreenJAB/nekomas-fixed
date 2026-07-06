package net.greenjab.nekomasfixed.util;

import net.minecraft.world.level.block.state.properties.BooleanProperty;

public interface MessyBedAccessor {
    BooleanProperty IS_MESSY = BooleanProperty.create("is_messy");
}
