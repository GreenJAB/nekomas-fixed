package net.greenjab.nekomasfixed.util;

import net.minecraft.state.property.BooleanProperty;

public interface MessyBedAccessor {
    BooleanProperty IS_MESSY = BooleanProperty.of("is_messy");
}
