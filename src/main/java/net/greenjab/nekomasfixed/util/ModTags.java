package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

public class ModTags {
    public static final TagKey<Biome> SPAWNS_RIME = TagKey.of(RegistryKeys.BIOME, NekomasFixed.id("spawns_rime"));
}
