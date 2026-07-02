package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.worldgen.tree.BaobabTreeDecorator;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class ModTreeDecorators {
    public static final TreeDecoratorType<BaobabTreeDecorator> BAOBAB_TREE_DECORATOR =
            Registry.register(
                    Registries.TREE_DECORATOR_TYPE,
                    NekomasFixed.id( "baobab_tree_decorator"),
                    new TreeDecoratorType<>(BaobabTreeDecorator.CODEC)
            );

    public static void register() {}
}
