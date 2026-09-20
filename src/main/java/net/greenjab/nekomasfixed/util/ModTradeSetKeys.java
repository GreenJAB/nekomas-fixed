package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.trading.TradeSet;

public class ModTradeSetKeys {

    public static final ResourceKey<TradeSet> PYROTECHNIST_LEVEL_1 = create("pyrotechnist/level_1");
    public static final ResourceKey<TradeSet> PYROTECHNIST_LEVEL_2 = create("pyrotechnist/level_2");
    public static final ResourceKey<TradeSet> PYROTECHNIST_LEVEL_3 = create("pyrotechnist/level_3");
    public static final ResourceKey<TradeSet> PYROTECHNIST_LEVEL_4 = create("pyrotechnist/level_4");

    private static ResourceKey<TradeSet> create(final String id) {
        return ResourceKey.create(Registries.TRADE_SET, Identifier.fromNamespaceAndPath(NekomasFixed.NAMESPACE, id));
    }
}
