package net.greenjab.nekomasfixed.world.tree;

import net.greenjab.nekomasfixed.world.ModConfiguredFeatures;
import net.minecraft.block.SaplingGenerator;

import java.util.Optional;

public class ModSaplingGenerators {
    public static final SaplingGenerator DRIFTWOOD = new SaplingGenerator("nekomasfixed" + ":driftwood",
            Optional.empty(), Optional.of(ModConfiguredFeatures.BOABAB_KEY), Optional.empty());
}
