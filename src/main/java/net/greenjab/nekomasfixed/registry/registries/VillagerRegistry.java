package net.greenjab.nekomasfixed.registry.registries;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.IntList;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.util.RandomDyeModifier;
import net.greenjab.nekomasfixed.util.RandomFireworkModifier;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.List;
import java.util.Optional;

public class VillagerRegistry {
    public static final ResourceKey<PoiType> PYROTECHNIST_POI_KEY = ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, NekomasFixed.id("pyrotechnist_poi"));
    public static final PoiType PYROTECHNIST_POI = PointOfInterestHelper.register(PYROTECHNIST_POI_KEY.location(), 1, 1, BlockRegistry.PYROTECHNICS_TABLE);

    public static final VillagerProfession PYROTECHNIST = Registry.register(BuiltInRegistries.VILLAGER_PROFESSION, NekomasFixed.id("pyrotechnist"),
            new VillagerProfession("pyrotechnist", holder -> holder.is(PYROTECHNIST_POI_KEY), holder -> holder.is(PYROTECHNIST_POI_KEY),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_WEAPONSMITH));

    public static void registerVillagers() {
        NekomasFixed.LOGGER.info("Registering villagers");
        LootFunctionRegistry.registerLootFunctions();

        TradeOfferHelper.registerVillagerOffers(PYROTECHNIST, 1, trades -> {
            trades.add(trade(Items.GUNPOWDER, 4, Items.EMERALD, 1, 10, 2, 0.05F));
            trades.add(trade(Items.EMERALD, 1, Items.FIRE_CHARGE, 4, 10, 2, 0.05F));
            trades.add(trade(Items.GOLD_NUGGET, 27, Items.EMERALD, 1, 10, 4, 0.05F));
            trades.add(trade(Items.FEATHER, 24, Items.EMERALD, 1, 10, 2, 0.05F));
        });
        TradeOfferHelper.registerVillagerOffers(PYROTECHNIST, 2, trades -> {
            trades.add(trade(Items.PAPER, 24, Items.EMERALD, 1, 16, 10, 0.05F));
            trades.add(trade(Items.EMERALD, 1, Items.GLOWSTONE_DUST, 1, 12, 10, 0.05F));
            trades.add(trade(Items.DIAMOND, 1, Items.EMERALD, 1, 12, 10, 0.05F));
            trades.add(trade(Items.EMERALD, 1, Items.FLINT_AND_STEEL, 1, 12, 10, 0.2F));
            trades.add(trade(Items.EMERALD, 1, ItemRegistry.REDSTONE_STRIKER, 1, 12, 10, 0.2F));
        });
        TradeOfferHelper.registerVillagerOffers(PYROTECHNIST, 3, trades -> {
            trades.add((trader, random) -> new MerchantOffer(new ItemCost(Items.EMERALD, 2),
                    RandomFireworkModifier.randomize(new ItemStack(Items.FIREWORK_ROCKET), random), 12, 10, 0.05F));
            trades.add((trader, random) -> new MerchantOffer(new ItemCost(Items.EMERALD, 1),
                    RandomFireworkModifier.randomize(new ItemStack(Items.FIREWORK_STAR), random), 12, 10, 0.05F));
            trades.add(trade(Items.EMERALD, 8, Items.CREEPER_BANNER_PATTERN, 1, 12, 15, 0.05F));
            trades.add((trader, random) -> new MerchantOffer(new ItemCost(Items.EMERALD, 1),
                    RandomDyeModifier.randomize(new ItemStack(Items.WHITE_DYE, 4), random), 12, 10, 0.05F));
        });
        TradeOfferHelper.registerVillagerOffers(PYROTECHNIST, 4, trades -> {
            trades.add(trade(Items.EMERALD, 1, Items.REDSTONE_TORCH, 8, 12, 15, 0.05F));
            trades.add(trade(Items.COAL_BLOCK, 2, Items.EMERALD, 1, 12, 15, 0.05F));
            trades.add(trade(Items.BLAZE_ROD, 3, Items.EMERALD, 1, 12, 15, 0.05F));
            trades.add(trade(Items.EMERALD, 3, Items.CROSSBOW, 1, 12, 15, 0.2F));
        });
        TradeOfferHelper.registerVillagerOffers(PYROTECHNIST, 5, trades -> {
            trades.add(trade(Items.EMERALD, 4, Items.TNT, 1, 12, 20, 0.05F));
            trades.add(trade(Items.EMERALD, 7, Items.TNT_MINECART, 1, 8, 20, 0.05F));
            trades.add(VillagerRegistry::loadedCrossbow);
            trades.add(trade(Items.EMERALD, 2, Items.WIND_CHARGE, 4, 8, 30, 0.05F));
        });

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            Registry<StructureTemplatePool> pools = server.registryAccess().registryOrThrow(Registries.TEMPLATE_POOL);
            Registry<StructureProcessorList> processors = server.registryAccess().registryOrThrow(Registries.PROCESSOR_LIST);
            for (String biome : List.of("plains", "desert", "savanna", "snowy", "taiga")) {
                StructureTemplatePool pool = pools.get(ResourceLocation.withDefaultNamespace("village/" + biome + "/houses"));
                Holder<StructureProcessorList> processor = processors.getHolderOrThrow(biome.equals("plains") ? ProcessorLists.MOSSIFY_10_PERCENT : ProcessorLists.EMPTY);
                StructurePoolElement house = StructurePoolElement.legacy(NekomasFixed.id("village/" + biome + "/pyrotechnist_house_" + biome).toString(), processor)
                        .apply(StructureTemplatePool.Projection.RIGID);
                pool.templates.add(house);
                pool.templates.add(house);
            }
        });
    }

    private static VillagerTrades.ItemListing trade(Item cost, int costCount, Item result, int count, int maxUses, int xp, float priceMultiplier) {
        return (trader, random) -> new MerchantOffer(new ItemCost(cost, costCount), new ItemStack(result, count), maxUses, xp, priceMultiplier);
    }

    private static MerchantOffer loadedCrossbow(Entity trader, RandomSource random) {
        FireworkExplosion.Shape shape = FireworkExplosion.Shape.values()[random.nextInt(FireworkExplosion.Shape.values().length)];
        ItemStack rocket = new ItemStack(Items.FIREWORK_ROCKET);
        rocket.set(DataComponents.FIREWORKS, new Fireworks(3, List.of(new FireworkExplosion(shape,
                IntList.of(random.nextInt(0xFFFFFF), random.nextInt(0xFFFFFF)), IntList.of(), true, true))));

        ItemStack crossbow = new ItemStack(Items.CROSSBOW);
        crossbow.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.of(rocket));
        boolean quickCharge = random.nextBoolean();
        Holder<Enchantment> enchantment = trader.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT)
                .getHolderOrThrow(quickCharge ? Enchantments.QUICK_CHARGE : Enchantments.MULTISHOT);
        crossbow.enchant(enchantment, quickCharge ? 2 : 1);

        return new MerchantOffer(new ItemCost(Items.EMERALD, 12), Optional.of(new ItemCost(Items.CROSSBOW)), crossbow, 6, 30, 0.2F);
    }
}
