package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static final TagKey<Item> STACKED_CAKES = TagKey.create(Registries.ITEM, NekomasFixed.id("stacked_cakes"));
    public static final TagKey<Item> CLAMS = itemTag("clams");
    public static final TagKey<Item> SLINGSHOT_PROJECTILES = itemTag("slingshot_projectiles");
    public static final TagKey<Item> SLINGSHOT_ENCHANTABLE = itemTag("enchantable/slingshot");

    // Wood-family tag: the four baobab log/wood variants that yield planks.
    public static final TagKey<Block> BAOBAB_LOGS = blockTag("baobab_logs");
    public static final TagKey<Item> BAOBAB_LOGS_ITEM = itemTag("baobab_logs");

    // Hollow-log family: all hollow log/stem/block variants (one per wood type).
    public static final TagKey<Block> HOLLOW_LOGS = blockTag("hollow_logs");
    public static final TagKey<Item> HOLLOW_LOGS_ITEM = itemTag("hollow_logs");
    // Stripped hollow logs (axe-stripped variants of the above).
    public static final TagKey<Block> STRIPPED_HOLLOW_LOGS = blockTag("stripped_hollow_logs");
    public static final TagKey<Item> STRIPPED_HOLLOW_LOGS_ITEM = itemTag("stripped_hollow_logs");
    // Moobloom food: the flowers a moobloom eats / is tempted by.
    public static final TagKey<Item> MOOBLOOM_FLOWERS = itemTag("moobloom_flowers");

    // Colour-suite tags (block + item, mirroring the committed JSON pairs).
    public static final TagKey<Block> BRICKS = blockTag("bricks");
    public static final TagKey<Block> BRICK_SLABS = blockTag("brick_slabs");
    public static final TagKey<Block> BRICK_STAIRS = blockTag("brick_stairs");
    public static final TagKey<Block> BRICK_WALLS = blockTag("brick_walls");
    public static final TagKey<Block> SPOTTED_WOOL = blockTag("spotted_wool");
    public static final TagKey<Block> SPOTTED_CARPET = blockTag("spotted_carpet");
    public static final TagKey<Block> FROGLIGHTS = blockTag("froglights");
    public static final TagKey<Block> GLAZED_TERRACOTTAS = blockTag("glazed_terracottas");

    public static final TagKey<Item> BRICKS_ITEM = itemTag("bricks");
    public static final TagKey<Item> BRICK_SLABS_ITEM = itemTag("brick_slabs");
    public static final TagKey<Item> BRICK_STAIRS_ITEM = itemTag("brick_stairs");
    public static final TagKey<Item> BRICK_WALLS_ITEM = itemTag("brick_walls");
    public static final TagKey<Item> SPOTTED_WOOL_ITEM = itemTag("spotted_wool");
    public static final TagKey<Item> SPOTTED_CARPET_ITEM = itemTag("spotted_carpet");
    public static final TagKey<Item> FROGLIGHTS_ITEM = itemTag("froglights");

    private static TagKey<Block> blockTag(String path) {
        return TagKey.create(Registries.BLOCK, NekomasFixed.id(path));
    }

    private static TagKey<Item> itemTag(String path) {
        return TagKey.create(Registries.ITEM, NekomasFixed.id(path));
    }
}