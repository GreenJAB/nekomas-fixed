package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.function.Supplier;

public enum BoatWoods {
    OAK(() -> Items.OAK_BOAT, () -> Items.OAK_PLANKS),
    SPRUCE(() -> Items.SPRUCE_BOAT, () -> Items.SPRUCE_PLANKS),
    BIRCH(() -> Items.BIRCH_BOAT, () -> Items.BIRCH_PLANKS),
    JUNGLE(() -> Items.JUNGLE_BOAT, () -> Items.JUNGLE_PLANKS),
    ACACIA(() -> Items.ACACIA_BOAT, () -> Items.ACACIA_PLANKS),
    DARK_OAK(() -> Items.DARK_OAK_BOAT, () -> Items.DARK_OAK_PLANKS),
    MANGROVE(() -> Items.MANGROVE_BOAT, () -> Items.MANGROVE_PLANKS),
    CHERRY(() -> Items.CHERRY_BOAT, () -> Items.CHERRY_PLANKS),
    BAMBOO(() -> Items.BAMBOO_RAFT, () -> Items.BAMBOO_PLANKS),
    BAOBAB(() -> ItemRegistry.BAOBAB_BOAT, () -> ItemRegistry.BAOBAB_PLANKS);

    public final Supplier<Item> boat;
    public final Supplier<Item> planks;

    BoatWoods(Supplier<Item> boat, Supplier<Item> planks) {
        this.boat = boat;
        this.planks = planks;
    }

    public String id() {
        return name().toLowerCase();
    }
}
