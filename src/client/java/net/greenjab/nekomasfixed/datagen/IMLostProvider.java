package net.greenjab.nekomasfixed.datagen;

import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.List;
import java.util.Set;

public class IMLostProvider extends LootTableProvider {
    public IMLostProvider(Set<ResourceKey<LootTable>> requiredTables, List<SubProviderEntry> subProviders) {
        super(requiredTables, subProviders);
    }
}
