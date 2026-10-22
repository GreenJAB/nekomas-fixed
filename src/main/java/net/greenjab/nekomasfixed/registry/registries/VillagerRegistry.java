package net.greenjab.nekomasfixed.registry.registries;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PoiHelper;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.util.ModTradeSetKeys;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.level.block.Block;

public class VillagerRegistry {
    public static final ResourceKey<PoiType> PYROTECHNIST_POI_KEY = registerPoiKey("pyrotechnist_poi");
    public static final PoiType PYROTECHNIST_POI = registerPOI("pyrotechnist_poi", BlockRegistry.PYROTECHNICS_TABLE);

    public static final ResourceKey<VillagerProfession> PYROTECHNIST_KEY =
            ResourceKey.create(Registries.VILLAGER_PROFESSION, Identifier.fromNamespaceAndPath(NekomasFixed.NAMESPACE, "pyrotechnist"));
    public static final VillagerProfession PYROTECHNIST = registerVillagerProfession("pyrotechnist", new VillagerProfession(
            Component.literal("Pyrotechnist"), holder -> holder.is(PYROTECHNIST_POI_KEY), holder -> holder.is(PYROTECHNIST_POI_KEY),
            ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_WEAPONSMITH, Int2ObjectMap.ofEntries(
                    Int2ObjectMap.entry(1, ModTradeSetKeys.PYROTECHNIST_LEVEL_1),
                    Int2ObjectMap.entry(2, ModTradeSetKeys.PYROTECHNIST_LEVEL_2),
                    Int2ObjectMap.entry(3, ModTradeSetKeys.PYROTECHNIST_LEVEL_3),
                    Int2ObjectMap.entry(4, ModTradeSetKeys.PYROTECHNIST_LEVEL_4),
                    Int2ObjectMap.entry(5, ModTradeSetKeys.PYROTECHNIST_LEVEL_5))));

    private static VillagerProfession registerVillagerProfession(String name, VillagerProfession profession) {
        return Registry.register(BuiltInRegistries.VILLAGER_PROFESSION, Identifier.fromNamespaceAndPath(NekomasFixed.NAMESPACE, name), profession);
    }

    private static PoiType registerPOI(String name, Block block) {
        return PoiHelper.register(Identifier.fromNamespaceAndPath(NekomasFixed.NAMESPACE, name),
                1, 1, block);
    }

    private static ResourceKey<PoiType> registerPoiKey(String name) {
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, Identifier.fromNamespaceAndPath(NekomasFixed.NAMESPACE, name));
    }

    public static void registerVillagers() {
        NekomasFixed.LOGGER.info("Registering Villagers and POIs for Nekoma's Fixed");
        LootFunctionRegistry.registerLootFunctions();
    }
}
