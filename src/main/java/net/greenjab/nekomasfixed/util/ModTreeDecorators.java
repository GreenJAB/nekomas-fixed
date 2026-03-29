package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.registry.woldgen.tree.BoababTrunkPlacer;
import net.greenjab.nekomasfixed.registry.woldgen.treedecorator.BoababTreeDecorator;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class ModTreeDecorators {
    public static final TreeDecoratorType<BoababTreeDecorator> BOABAB_TREE_DECORATOR =
            Registry.register(
                    Registries.TREE_DECORATOR_TYPE,
                    Identifier.of("nekomasfixed", "boabab_tree_decorator"),
                    new TreeDecoratorType<>(BoababTreeDecorator.CODEC)
            );

    public static void register() {}
}
