package net.greenjab.nekomasfixed.datagen.villager;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.util.LoadedCrossbowModifier;
import net.greenjab.nekomasfixed.util.RandomFireworkModifier;
import net.minecraft.client.color.item.Dye;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import net.greenjab.nekomasfixed.util.RandomDyeModifier;

public class ModVillagerTrades {
    public static final ResourceKey<VillagerTrade> PYROTECHNIST_1_GUNPOWDER_EMERALD = createKey("pyrotechnist/1/gunpowder_emerald");
    public static final ResourceKey<VillagerTrade> PYROTECHNIST_1_EMERALD_FIRECHARGE = createKey("pyrotechnist/1/emerald_firecharge");
    public static final ResourceKey<VillagerTrade> PYROTECHNIST_1_GOLD_NUGGET_EMERALD = createKey("pyrotechnist/1/gold_nugget_emerald");
    public static final ResourceKey<VillagerTrade> PYROTECHNIST_1_FEATHER_EMERALD = createKey("pyrotechnist/1/feather_emerald");

    public static final ResourceKey<VillagerTrade> PYROTECHNIST_2_PAPER_EMERALD = createKey("pyrotechnist/2/paper_emerald");
    public static final ResourceKey<VillagerTrade> PYROTECHNIST_2_GLOWSTONE_DUST_EMERALD = createKey("pyrotechnist/2/glowstone_dust_emerald");
    public static final ResourceKey<VillagerTrade> PYROTECHNIST_2_DIAMOND_EMERALD = createKey("pyrotechnist/2/diamond_emerald");
    public static final ResourceKey<VillagerTrade> PYROTECHNIST_2_EMERALD_FLINT_AND_STEEL = createKey("pyrotechnist/2/emerald_flint_and_steel");
    public static final ResourceKey<VillagerTrade> PYROTECHNIST_2_EMERALD_REDSTONE_STRIKER = createKey("pyrotechnist/2/emerald_redstone_striker");

    public static final ResourceKey<VillagerTrade> PYROTECHNIST_3_EMERALD_FIREWORK_ROCKET = createKey("pyrotechnist/3/emerald_firework_rocket");
    public static final ResourceKey<VillagerTrade> PYROTECHNIST_3_EMERALD_FIREWORK_STAR = createKey("pyrotechnist/3/emerald_firework_star");
    public static final ResourceKey<VillagerTrade> PYROTECHNIST_3_EMERALD_CREEPER_BANNER_PATTERN = createKey("pyrotechnist/3/emerald_creeper_banner_pattern");
    public static final ResourceKey<VillagerTrade> PYROTECHNIST_3_EMERALD_RANDOM_DYE = createKey("pyrotechnist/3/emerald_random_dye");

    public static final ResourceKey<VillagerTrade> PYROTECHNIST_4_EMERALD_REDSTONE_TORCH = createKey("pyrotechnist/4/emerald_redstone_torch");
    public static final ResourceKey<VillagerTrade> PYROTECHNIST_4_COAL_BLOCK_EMERALD = createKey("pyrotechnist/4/coal_block_emerald");
    public static final ResourceKey<VillagerTrade> PYROTECHNIST_4_BLAZE_ROD_EMERALD = createKey("pyrotechnist/4/blaze_rod_emerald");
    public static final ResourceKey<VillagerTrade> PYROTECHNIST_4_EMERALD_CROSSBOW = createKey("pyrotechnist/4/emerald_crossbow");

    public static final ResourceKey<VillagerTrade> PYROTECHNIST_5_EMERALD_TNT = createKey("pyrotechnist/5/emerald_tnt");
    public static final ResourceKey<VillagerTrade> PYROTECHNIST_5_EMERALD_TNT_MINECART = createKey("pyrotechnist/5/emerald_tnt_minecart");
    public static final ResourceKey<VillagerTrade> PYROTECHNIST_5_LOADED_CROSSBOW = createKey("pyrotechnist/5/loaded_crossbow");
    public static final ResourceKey<VillagerTrade> PYROTECHNIST_5_EMERALD_WIND_CHARGE = createKey("pyrotechnist/5/emerald_wind_charge");

    public static void bootstrap(BootstrapContext<VillagerTrade> context) {
        register(context, PYROTECHNIST_1_GUNPOWDER_EMERALD,
                VillagerTrade.builder(new TradeCost(Items.GUNPOWDER, 4),
                        new ItemStackTemplate(Items.EMERALD, 1), 10,
                        2, 0.05f).build());

        register(context, PYROTECHNIST_1_GOLD_NUGGET_EMERALD,
                VillagerTrade.builder(new TradeCost(Items.GOLD_NUGGET, 27),
                        new ItemStackTemplate(Items.EMERALD, 1), 10,
                        4, 0.05f).build());

        register(context, PYROTECHNIST_1_EMERALD_FIRECHARGE,
                VillagerTrade.builder(new TradeCost(Items.EMERALD, 1),
                        new ItemStackTemplate(Items.FIRE_CHARGE, 4), 10,
                        2, 0.05f).build());

        register(context, PYROTECHNIST_1_FEATHER_EMERALD,
                VillagerTrade.builder(new TradeCost(Items.FEATHER, 24),
                        new ItemStackTemplate(Items.EMERALD, 1), 10,
                        2, 0.05f).build());

        register(context, PYROTECHNIST_2_PAPER_EMERALD,
                VillagerTrade.builder(new TradeCost(Items.PAPER, 24),
                        new ItemStackTemplate(Items.EMERALD, 1), 16,
                        10, 0.05f).build());

        register(context, PYROTECHNIST_2_GLOWSTONE_DUST_EMERALD,
                VillagerTrade.builder(new TradeCost(Items.EMERALD, 1),
                        new ItemStackTemplate(Items.GLOWSTONE_DUST, 1), 12,
                        10, 0.05f).build());

        register(context, PYROTECHNIST_2_DIAMOND_EMERALD,
                VillagerTrade.builder(new TradeCost(Items.DIAMOND, 1),
                        new ItemStackTemplate(Items.EMERALD, 1), 12,
                        10, 0.05f).build());

        register(context, PYROTECHNIST_2_EMERALD_FLINT_AND_STEEL,
                VillagerTrade.builder(new TradeCost(Items.EMERALD, 1),
                        new ItemStackTemplate(Items.FLINT_AND_STEEL, 1), 12,
                        10, 0.2f).build());

        register(context, PYROTECHNIST_2_EMERALD_REDSTONE_STRIKER,
                VillagerTrade.builder(new TradeCost(Items.EMERALD, 1),
                        new ItemStackTemplate(ItemRegistry.REDSTONE_STRIKER, 1), 12,
                        10, 0.2f).build());

        register(context, PYROTECHNIST_3_EMERALD_FIREWORK_ROCKET,
                VillagerTrade.builder(new TradeCost(Items.EMERALD, 2),
                                new ItemStackTemplate(Items.FIREWORK_ROCKET, 1), 12,
                                10, 0.05f)
                        .addModifier(RandomFireworkModifier.builder())
                        .build());

        register(context, PYROTECHNIST_3_EMERALD_FIREWORK_STAR,
                VillagerTrade.builder(new TradeCost(Items.EMERALD, 1),
                                new ItemStackTemplate(Items.FIREWORK_STAR, 1), 12,
                                10, 0.05f)
                        .addModifier(RandomFireworkModifier.builder())
                        .build());

        register(context, PYROTECHNIST_3_EMERALD_CREEPER_BANNER_PATTERN,
                VillagerTrade.builder(new TradeCost(Items.EMERALD, 8),
                        new ItemStackTemplate(Items.CREEPER_BANNER_PATTERN, 1), 12,
                        15, 0.05f).build());

        register(context, PYROTECHNIST_3_EMERALD_RANDOM_DYE,
                VillagerTrade.builder(new TradeCost(Items.EMERALD, 1),
                                // Base template uses AQUA_DYE to establish stack size (4); RandomDyeModifier
                                // randomizes the final item across all registered vanilla and custom dyes on trade generation.
                                new ItemStackTemplate(ItemRegistry.AMBER_DYE, 4), 12,
                                10, 0.05f)
                        .addModifier(RandomDyeModifier.builder())
                        .build());

        register(context, PYROTECHNIST_4_BLAZE_ROD_EMERALD,
                VillagerTrade.builder(new TradeCost(Items.BLAZE_ROD, 3),
                        new ItemStackTemplate(Items.EMERALD, 1), 12,
                        15, 0.05f).build());

        register(context, PYROTECHNIST_4_EMERALD_CROSSBOW,
                VillagerTrade.builder(new TradeCost(Items.EMERALD, 3),
                        new ItemStackTemplate(Items.CROSSBOW, 1), 12,
                        15, 0.2f).build());

        register(context, PYROTECHNIST_4_EMERALD_REDSTONE_TORCH,
                VillagerTrade.builder(new TradeCost(Items.EMERALD, 1),
                        new ItemStackTemplate(Items.REDSTONE_TORCH, 8), 12,
                        15, 0.05f).build());

        register(context, PYROTECHNIST_4_COAL_BLOCK_EMERALD,
                VillagerTrade.builder(new TradeCost(Items.COAL_BLOCK, 2),
                        new ItemStackTemplate(Items.EMERALD, 1), 12,
                        15, 0.05f).build());

        register(context, PYROTECHNIST_5_EMERALD_TNT,
                VillagerTrade.builder(new TradeCost(Items.EMERALD, 4),
                        new ItemStackTemplate(Items.TNT, 1), 12,
                        20, 0.05f).build());

        register(context, PYROTECHNIST_5_EMERALD_TNT_MINECART,
                VillagerTrade.builder(new TradeCost(Items.EMERALD, 7),
                        new ItemStackTemplate(Items.TNT_MINECART, 1), 8,
                        20, 0.05f).build());

        register(context, PYROTECHNIST_5_LOADED_CROSSBOW,
                VillagerTrade.builder(new TradeCost(Items.EMERALD, 12),
                                new ItemStackTemplate(Items.CROSSBOW, 1), 6,
                                30, 0.2f)
                        .additionalWants(new TradeCost(Items.CROSSBOW, 1))
                        .addModifier(LoadedCrossbowModifier.builder())
                        .build());

        register(context, PYROTECHNIST_5_EMERALD_WIND_CHARGE,
                VillagerTrade.builder(new TradeCost(Items.EMERALD, 2),
                        new ItemStackTemplate(Items.WIND_CHARGE, 4), 8,
                        30, 0.05f).build());
    }

    private static ResourceKey<VillagerTrade> createKey(String name) {
        return ResourceKey.create(Registries.VILLAGER_TRADE, Identifier.fromNamespaceAndPath(NekomasFixed.NAMESPACE, name));
    }

    private static void register(BootstrapContext<VillagerTrade> context, ResourceKey<VillagerTrade> resourceKey, VillagerTrade trade) {
        context.register(resourceKey, trade);
    }
}

