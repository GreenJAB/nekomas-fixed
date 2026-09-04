package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static final TagKey<Biome> SPAWNS_RIME = TagKey.create(Registries.BIOME, NekomasFixed.id("spawns_rime"));

    public static final TagKey<Block> SULFUR_BLOCKS = TagKey.create(Registries.BLOCK, NekomasFixed.id("sulfur_blocks"));
    public static final TagKey<Block> TORCHES = TagKey.create(Registries.BLOCK, NekomasFixed.id("torches"));
    public static final TagKey<Block> CAN_BE_DYED_WITH_BRUSH = TagKey.create(Registries.BLOCK, NekomasFixed.id("can_be_dyed_with_brush"));
    public static final TagKey<Block> DYED_BRICKS = TagKey.create(Registries.BLOCK, NekomasFixed.id("dyed_bricks"));
    public static final TagKey<Block> DYED_BRICK_SLABS = TagKey.create(Registries.BLOCK, NekomasFixed.id("dyed_brick_slabs"));
    public static final TagKey<Block> DYED_BRICK_STAIRS = TagKey.create(Registries.BLOCK, NekomasFixed.id("dyed_brick_stairs"));
    public static final TagKey<Block> DYED_BRICK_WALLS = TagKey.create(Registries.BLOCK, NekomasFixed.id("dyed_brick_walls"));
    public static final TagKey<Block> STAINED_GLASSES = TagKey.create(Registries.BLOCK, NekomasFixed.id("stained_glasses"));
    public static final TagKey<Block> STAINED_GLASS_PANES = TagKey.create(Registries.BLOCK, NekomasFixed.id("stained_glass_panes"));
    public static final TagKey<Block> GLAZED_TERRACOTTAS = TagKey.create(Registries.BLOCK, NekomasFixed.id("glazed_terracottas"));
    public static final TagKey<Block> CONCRETES = TagKey.create(Registries.BLOCK, NekomasFixed.id("concretes"));
    public static final TagKey<Block> CONCRETE_POWDERS = TagKey.create(Registries.BLOCK, NekomasFixed.id("concrete_powders"));
    public static final TagKey<Block> SPOTTED_WOOLS = TagKey.create(Registries.BLOCK, NekomasFixed.id("spotted_wools"));
    public static final TagKey<Block> SPOTTED_CARPETS = TagKey.create(Registries.BLOCK, NekomasFixed.id("spotted_carpets"));
    public static final TagKey<Block> FROGLIGHTS = TagKey.create(Registries.BLOCK, NekomasFixed.id("froglights"));

    public static final TagKey<Item> BAOBAB_LOGS = TagKey.create(Registries.ITEM, NekomasFixed.id("baobab_logs"));
    public static final TagKey<Item> CAN_BE_ENGRAVED = TagKey.create(Registries.ITEM, NekomasFixed.id("can_be_engraved"));
    public static final TagKey<Item> CLAMTAG = TagKey.create(Registries.ITEM, NekomasFixed.id("clams"));
    public static final TagKey<Item> SPEARS = TagKey.create(Registries.ITEM, NekomasFixed.id("spears"));
    public static final TagKey<Item> SICKLES = TagKey.create(Registries.ITEM, NekomasFixed.id("sickles"));
    public static final TagKey<Item> STACKED_CAKES = TagKey.create(Registries.ITEM, NekomasFixed.id("stacked_cakes"));
    public static final TagKey<Item> FOOD_ITEMS = TagKey.create(Registries.ITEM, NekomasFixed.id("food_items"));
    public static final TagKey<Item> MOOBLOOM_FLOWERS = TagKey.create(Registries.ITEM, NekomasFixed.id("moobloom_flowers"));
    public static final TagKey<Item> SLINGSHOT_PROJECTILES = TagKey.create(Registries.ITEM, NekomasFixed.id("slingshot_projectiles"));
}
