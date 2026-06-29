package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.worldgen.tree.BaobabTrunkPlacer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class ModTrunkPlacers {

        public static final TrunkPlacerType<BaobabTrunkPlacer> BAOBAB_TRUNK_PLACER =
                Registry.register(
                        Registries.TRUNK_PLACER_TYPE,
                        NekomasFixed.id( "baobab_trunk_placer"),
                        new TrunkPlacerType<>(BaobabTrunkPlacer.CODEC)
                );

        public static void register() {}

}
