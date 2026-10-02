package net.greenjab.nekomasfixed.datagen.villager;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.item.trading.VillagerTrade;

import java.util.concurrent.CompletableFuture;

public class ModVillagerTradeTags extends FabricTagsProvider<VillagerTrade> {
    public ModVillagerTradeTags(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, Registries.VILLAGER_TRADE, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        getOrCreateRawBuilder(ModTags.PYROTECHNIST_LEVEL_1)
                .add(TagEntry.element(ModVillagerTrades.PYROTECHNIST_1_GUNPOWDER_EMERALD.identifier()))
                .add(TagEntry.element(ModVillagerTrades.PYROTECHNIST_1_EMERALD_FIRECHARGE.identifier()))
                .add(TagEntry.element(ModVillagerTrades.PYROTECHNIST_1_GOLD_NUGGET_EMERALD.identifier()))
                .add(TagEntry.element(ModVillagerTrades.PYROTECHNIST_1_FEATHER_EMERALD.identifier()));

        getOrCreateRawBuilder(ModTags.PYROTECHNIST_LEVEL_2)
                .add(TagEntry.element(ModVillagerTrades.PYROTECHNIST_2_PAPER_EMERALD.identifier()))
                .add(TagEntry.element(ModVillagerTrades.PYROTECHNIST_2_GLOWSTONE_DUST_EMERALD.identifier()))
                .add(TagEntry.element(ModVillagerTrades.PYROTECHNIST_2_DIAMOND_EMERALD.identifier()))
                .add(TagEntry.element(ModVillagerTrades.PYROTECHNIST_2_EMERALD_FLINT_AND_STEEL.identifier()))
                .add(TagEntry.element(ModVillagerTrades.PYROTECHNIST_2_EMERALD_REDSTONE_STRIKER.identifier()));

        getOrCreateRawBuilder(ModTags.PYROTECHNIST_LEVEL_3)
                .add(TagEntry.element(ModVillagerTrades.PYROTECHNIST_3_EMERALD_FIREWORK_ROCKET.identifier()))
                .add(TagEntry.element(ModVillagerTrades.PYROTECHNIST_3_EMERALD_FIREWORK_STAR.identifier()))
                .add(TagEntry.element(ModVillagerTrades.PYROTECHNIST_3_EMERALD_CREEPER_BANNER_PATTERN.identifier()))
                .add(TagEntry.element(ModVillagerTrades.PYROTECHNIST_3_EMERALD_RANDOM_DYE.identifier()));

        getOrCreateRawBuilder(ModTags.PYROTECHNIST_LEVEL_4)
                .add(TagEntry.element(ModVillagerTrades.PYROTECHNIST_4_EMERALD_REDSTONE_TORCH.identifier()))
                .add(TagEntry.element(ModVillagerTrades.PYROTECHNIST_4_COAL_BLOCK_EMERALD.identifier()))
                .add(TagEntry.element(ModVillagerTrades.PYROTECHNIST_4_BLAZE_ROD_EMERALD.identifier()))
                .add(TagEntry.element(ModVillagerTrades.PYROTECHNIST_4_EMERALD_CROSSBOW.identifier()));

        getOrCreateRawBuilder(ModTags.PYROTECHNIST_LEVEL_5)
                .add(TagEntry.element(ModVillagerTrades.PYROTECHNIST_5_EMERALD_TNT.identifier()))
                .add(TagEntry.element(ModVillagerTrades.PYROTECHNIST_5_EMERALD_TNT_MINECART.identifier()))
                .add(TagEntry.element(ModVillagerTrades.PYROTECHNIST_5_LOADED_CROSSBOW.identifier()))
                .add(TagEntry.element(ModVillagerTrades.PYROTECHNIST_5_EMERALD_WIND_CHARGE.identifier()));
    }
}