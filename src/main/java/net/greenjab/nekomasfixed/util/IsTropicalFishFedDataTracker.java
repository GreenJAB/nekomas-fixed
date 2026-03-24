package net.greenjab.nekomasfixed.util;

import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.DolphinEntity;

public abstract class IsTropicalFishFedDataTracker {
    public static final TrackedData<Boolean> IS_TROPICAL_FISH_FED =
            DataTracker.registerData(DolphinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

}
