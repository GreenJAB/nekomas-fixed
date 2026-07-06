package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.worldgen.tree.BaobabTreeDecorator;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class ModTreeDecorators {
    public static final TreeDecoratorType<BaobabTreeDecorator> BAOBAB_TREE_DECORATOR =
            Registry.register(
                    BuiltInRegistries.TREE_DECORATOR_TYPE,
                    NekomasFixed.id( "baobab_tree_decorator"),
                    new TreeDecoratorType<>(BaobabTreeDecorator.CODEC)
            );

    public static void register() {}
}
