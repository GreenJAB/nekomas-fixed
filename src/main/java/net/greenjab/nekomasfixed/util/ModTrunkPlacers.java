package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.worldgen.tree.BaobabTrunkPlacer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class ModTrunkPlacers {

        public static final TrunkPlacerType<BaobabTrunkPlacer> BAOBAB_TRUNK_PLACER =
                Registry.register(
                        BuiltInRegistries.TRUNK_PLACER_TYPE,
                        NekomasFixed.id( "baobab_trunk_placer"),
                        new TrunkPlacerType<>(BaobabTrunkPlacer.CODEC)
                );

        public static void register() {}

}
