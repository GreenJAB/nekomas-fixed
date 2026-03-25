package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.registry.woldgen.tree.BoababTrunkPlacer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class ModTrunkPlacers {


        public static final TrunkPlacerType<BoababTrunkPlacer> BOABAB_TRUNK_PLACER =
                Registry.register(
                        Registries.TRUNK_PLACER_TYPE,
                        Identifier.of("nekomasfixed", "boabab_trunk_placer"),
                        new TrunkPlacerType<>(BoababTrunkPlacer.CODEC)
                );

        public static void register() {}

}
