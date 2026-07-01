package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

public class ModTags {
    public static final TagKey<Biome> SPAWNS_RIME = TagKey.of(RegistryKeys.BIOME, NekomasFixed.id("spawns_rime"));

    public static final TagKey<Block> CAN_BE_DYED_WITH_BRUSH = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("can_be_dyed_with_brush"));
    public static final TagKey<Block> DYED_BRICKS = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("dyed_bricks"));
    public static final TagKey<Block> DYED_BRICK_SLABS = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("dyed_brick_slabs"));
    public static final TagKey<Block> DYED_BRICK_STAIRS = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("dyed_brick_stairs"));
    public static final TagKey<Block> DYED_BRICK_WALLS = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("dyed_brick_walls"));
    public static final TagKey<Block> STAINED_GLASSES = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("stained_glasses"));
    public static final TagKey<Block> STAINED_GLASS_PANES = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("stained_glass_panes"));
    public static final TagKey<Block> GLAZED_TERRACOTTAS = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("glazed_terracottas"));
    public static final TagKey<Block> CONCRETES = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("concretes"));
    public static final TagKey<Block> CONCRETE_POWDERS = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("concrete_powders"));
    public static final TagKey<Block> SPOTTED_WOOLS = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("spotted_wools"));
    public static final TagKey<Block> SPOTTED_CARPETS = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("spotted_carpets"));
    public static final TagKey<Block> FROGLIGHTS = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("froglights"));
    public static final TagKey<Block> FOLIAGE_REQUIRES_BASE = TagKey.of(RegistryKeys.BLOCK, NekomasFixed.id("foliage_requires_base"));

    public static final TagKey<Item> CLAMTAG = TagKey.of(RegistryKeys.ITEM, NekomasFixed.id("clams"));
    public static final TagKey<Item> SPEARS = TagKey.of(RegistryKeys.ITEM, NekomasFixed.id("spears"));
    public static final TagKey<Item> SICKLES = TagKey.of(RegistryKeys.ITEM, NekomasFixed.id("sickles"));
    public static final TagKey<Item> STACKED_CAKES = TagKey.of(RegistryKeys.ITEM, NekomasFixed.id("stacked_cakes"));
    public static final TagKey<Item> FOOD_ITEMS = TagKey.of(RegistryKeys.ITEM, NekomasFixed.id("food_items"));
    public static final TagKey<Item> MOOBLOOM_FLOWERS = TagKey.of(RegistryKeys.ITEM, NekomasFixed.id("moobloom_flowers"));
    public static final TagKey<Item> SLINGSHOT_PROJECTILES = TagKey.of(RegistryKeys.ITEM, NekomasFixed.id("slingshot_projectiles"));
}
