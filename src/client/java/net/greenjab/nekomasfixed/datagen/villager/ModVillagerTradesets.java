package net.greenjab.nekomasfixed.datagen.villager;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import static net.greenjab.nekomasfixed.util.ModTradeSetKeys.*;

import java.util.Optional;

public class ModVillagerTradesets {
    public static void bootstrap(BootstrapContext<TradeSet> context) {
        register(context, PYROTECHNIST_LEVEL_1, ModTags.PYROTECHNIST_LEVEL_1);
        register(context, PYROTECHNIST_LEVEL_2, ModTags.PYROTECHNIST_LEVEL_2);
        register(context, PYROTECHNIST_LEVEL_3, ModTags.PYROTECHNIST_LEVEL_3);
        register(context, PYROTECHNIST_LEVEL_4, ModTags.PYROTECHNIST_LEVEL_4);
        register(context, PYROTECHNIST_LEVEL_5, ModTags.PYROTECHNIST_LEVEL_5);
    }

    public static Holder.Reference<TradeSet> register(final BootstrapContext<TradeSet> context,
                                                      final ResourceKey<TradeSet> resourceKey,
                                                      final TagKey<VillagerTrade> tradeTag) {
        return register(context, resourceKey, tradeTag, ContextIntProviders.exactly(2));
    }

    public static Holder.Reference<TradeSet> register(final BootstrapContext<TradeSet> context,
                                                      final ResourceKey<TradeSet> resourceKey,
                                                      final TagKey<VillagerTrade> tradeTag,
                                                      final Holder<ContextIntProvider> contextIntProvider) {
        return context.register(resourceKey, new TradeSet(
                context.lookup(Registries.VILLAGER_TRADE).getOrThrow(tradeTag),
                contextIntProvider,
                false,
                Optional.of(resourceKey.identifier().withPrefix("trade_set/"))
        ));
    }
}