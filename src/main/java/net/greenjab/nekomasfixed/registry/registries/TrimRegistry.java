package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.armortrim.TrimPattern;

public class TrimRegistry {
    public static final ResourceKey<TrimPattern> JEWEL = ResourceKey.create(Registries.TRIM_PATTERN, NekomasFixed.id("jewel"));
}
