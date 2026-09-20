package net.greenjab.nekomasfixed.datagen.villager;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.trading.VillagerTrade;

public class ModVillagerTrades {

    private static ResourceKey<VillagerTrade> createKey(String name) {
        return ResourceKey.create(Registries.VILLAGER_TRADE, Identifier.fromNamespaceAndPath(NekomasFixed.NAMESPACE, name));
    }

    private static void register(BootstrapContext<VillagerTrade> context, ResourceKey<VillagerTrade> resourceKey, VillagerTrade trade) {
        context.register(resourceKey, trade);
    }
}