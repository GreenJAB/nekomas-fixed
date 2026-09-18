package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

/**
 * Generates the colour-suite item tags. Each block's item (via BuiltInRegistries.ITEM id)
 * is added to the matching item tag.
 */
public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        // Hollow-log family: item tag mirroring the block tag (hollow logs + their planks).
        Block[] hollowLogs = {
                BlockRegistry.HOLLOW_OAK_LOG, BlockRegistry.HOLLOW_SPRUCE_LOG,
                BlockRegistry.HOLLOW_BIRCH_LOG, BlockRegistry.HOLLOW_JUNGLE_LOG,
                BlockRegistry.HOLLOW_ACACIA_LOG, BlockRegistry.HOLLOW_DARK_OAK_LOG,
                BlockRegistry.HOLLOW_MANGROVE_LOG, BlockRegistry.HOLLOW_CHERRY_LOG,
                BlockRegistry.HOLLOW_BAMBOO_BLOCK, BlockRegistry.HOLLOW_CRIMSON_STEM,
                BlockRegistry.HOLLOW_WARPED_STEM, BlockRegistry.HOLLOW_BAOBAB_LOG
        };
        for (Block b : hollowLogs) {
            getOrCreateTagBuilder(ModTags.HOLLOW_LOGS_ITEM).add(BuiltInRegistries.ITEM.getKey(b.asItem()));
        }
        // Stripped hollow-log family (item-tag mirror of the block tag).
        Block[] strippedHollowLogs = {
                BlockRegistry.HOLLOW_STRIPPED_OAK_LOG, BlockRegistry.HOLLOW_STRIPPED_SPRUCE_LOG,
                BlockRegistry.HOLLOW_STRIPPED_BIRCH_LOG, BlockRegistry.HOLLOW_STRIPPED_JUNGLE_LOG,
                BlockRegistry.HOLLOW_STRIPPED_ACACIA_LOG, BlockRegistry.HOLLOW_STRIPPED_DARK_OAK_LOG,
                BlockRegistry.HOLLOW_STRIPPED_MANGROVE_LOG, BlockRegistry.HOLLOW_STRIPPED_CHERRY_LOG,
                BlockRegistry.HOLLOW_STRIPPED_BAMBOO_BLOCK, BlockRegistry.HOLLOW_STRIPPED_CRIMSON_STEM,
                BlockRegistry.HOLLOW_STRIPPED_WARPED_STEM, BlockRegistry.HOLLOW_STRIPPED_BAOBAB_LOG
        };
        for (Block b : strippedHollowLogs) {
            getOrCreateTagBuilder(ModTags.STRIPPED_HOLLOW_LOGS_ITEM).add(BuiltInRegistries.ITEM.getKey(b.asItem()));
        }
        getOrCreateTagBuilder(ModTags.BAOBAB_LOGS_ITEM)
                .add(BuiltInRegistries.ITEM.getKey(ItemRegistry.BAOBAB_LOG))
                .add(BuiltInRegistries.ITEM.getKey(ItemRegistry.BAOBAB_WOOD))
                .add(BuiltInRegistries.ITEM.getKey(ItemRegistry.STRIPPED_BAOBAB_LOG))
                .add(BuiltInRegistries.ITEM.getKey(ItemRegistry.STRIPPED_BAOBAB_WOOD));
        BlockDyeMap.BRICKS.values().forEach(block -> getOrCreateTagBuilder(ModTags.BRICKS_ITEM).add(BuiltInRegistries.ITEM.getKey(block.asItem())));
        BlockDyeMap.BRICK_SLAB.values().forEach(block -> getOrCreateTagBuilder(ModTags.BRICK_SLABS_ITEM).add(BuiltInRegistries.ITEM.getKey(block.asItem())));
        BlockDyeMap.BRICK_STAIRS.values().forEach(block -> getOrCreateTagBuilder(ModTags.BRICK_STAIRS_ITEM).add(BuiltInRegistries.ITEM.getKey(block.asItem())));
        BlockDyeMap.BRICK_WALL.values().forEach(block -> getOrCreateTagBuilder(ModTags.BRICK_WALLS_ITEM).add(BuiltInRegistries.ITEM.getKey(block.asItem())));
        BlockDyeMap.SPOTTED_WOOL.values().forEach(block -> getOrCreateTagBuilder(ModTags.SPOTTED_WOOL_ITEM).add(BuiltInRegistries.ITEM.getKey(block.asItem())));
        BlockDyeMap.SPOTTED_CARPET.values().forEach(block -> getOrCreateTagBuilder(ModTags.SPOTTED_CARPET_ITEM).add(BuiltInRegistries.ITEM.getKey(block.asItem())));
        BlockDyeMap.FROGLIGHT.values().forEach(block -> getOrCreateTagBuilder(ModTags.FROGLIGHTS_ITEM).add(BuiltInRegistries.ITEM.getKey(block.asItem())));
        // Candles: vanilla #minecraft:candles item tag override (replace: false semantics).
        BlockDyeMap.CANDLE.values().forEach(block -> getOrCreateTagBuilder(ItemTags.CANDLES).add(BuiltInRegistries.ITEM.getKey(block.asItem())));
        // Beds: vanilla #minecraft:beds item tag override (replace: false semantics).
        BlockDyeMap.BED.values().forEach(block -> getOrCreateTagBuilder(ItemTags.BEDS).add(BuiltInRegistries.ITEM.getKey(block.asItem())));
        // Terracotta: vanilla #minecraft:terracotta item override (replace: false) so the
        // ancient items join the vanilla family (concrete/powder have no vanilla item tag).
        BlockDyeMap.TERRACOTTA.values().forEach(block -> getOrCreateTagBuilder(ItemTags.TERRACOTTA).add(BuiltInRegistries.ITEM.getKey(block.asItem())));
        // Shulker boxes: append to the vanilla #minecraft:shulker_boxes item tag (replace: false).
        // (No ItemTags.SHULKER_BOXES constant in 1.21.1, so reference by name.)
        TagKey<Item> shulkerTag = TagKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("shulker_boxes"));
        BlockDyeMap.SHULKER_BOX.values().forEach(block -> getOrCreateTagBuilder(shulkerTag).add(BuiltInRegistries.ITEM.getKey(block.asItem())));
        // The redstone striker can take Unbreaking via the vanilla durability-enchantable tag.
        getOrCreateTagBuilder(net.minecraft.tags.ItemTags.DURABILITY_ENCHANTABLE)
                .add(BuiltInRegistries.ITEM.getKey(ItemRegistry.REDSTONE_STRIKER));
        // Wool + carpets: vanilla #minecraft:wool / #minecraft:wool_carpets item tag overrides
        // (replace: false). The block tag holds the spotted-wool reference; the item tag lists
        // just the four ancient wools (mirrors the committed hand-written item overrides).
        var woolItems = getOrCreateTagBuilder(ItemTags.WOOL);
        BlockDyeMap.WOOL.values().forEach(block -> woolItems.add(BuiltInRegistries.ITEM.getKey(block.asItem())));
        var carpetItems = getOrCreateTagBuilder(ItemTags.WOOL_CARPETS);
        BlockDyeMap.CARPET.values().forEach(block -> carpetItems.add(BuiltInRegistries.ITEM.getKey(block.asItem())));
        // Baobab wood set: vanilla item tag overrides (replace: false). Logs join via the
        // #nekomasfixed:baobab_logs item-tag reference.
        getOrCreateTagBuilder(ItemTags.PLANKS).add(BuiltInRegistries.ITEM.getKey(BlockRegistry.BAOBAB_PLANKS.asItem()));
        getOrCreateTagBuilder(ItemTags.SIGNS).add(BuiltInRegistries.ITEM.getKey(BlockRegistry.BAOBAB_SIGN.asItem()));
        getOrCreateTagBuilder(ItemTags.HANGING_SIGNS).add(BuiltInRegistries.ITEM.getKey(BlockRegistry.BAOBAB_HANGING_SIGN.asItem()));
        getOrCreateTagBuilder(ItemTags.WOODEN_BUTTONS).add(BuiltInRegistries.ITEM.getKey(BlockRegistry.BAOBAB_BUTTON.asItem()));
        getOrCreateTagBuilder(ItemTags.WOODEN_DOORS).add(BuiltInRegistries.ITEM.getKey(BlockRegistry.BAOBAB_DOOR.asItem()));
        getOrCreateTagBuilder(ItemTags.WOODEN_FENCES).add(BuiltInRegistries.ITEM.getKey(BlockRegistry.BAOBAB_FENCE.asItem()));
        getOrCreateTagBuilder(ItemTags.WOODEN_PRESSURE_PLATES).add(BuiltInRegistries.ITEM.getKey(BlockRegistry.BAOBAB_PRESSURE_PLATE.asItem()));
        getOrCreateTagBuilder(ItemTags.WOODEN_SLABS).add(BuiltInRegistries.ITEM.getKey(BlockRegistry.BAOBAB_SLAB.asItem()));
        getOrCreateTagBuilder(ItemTags.WOODEN_STAIRS).add(BuiltInRegistries.ITEM.getKey(BlockRegistry.BAOBAB_STAIRS.asItem()));
        getOrCreateTagBuilder(ItemTags.WOODEN_TRAPDOORS).add(BuiltInRegistries.ITEM.getKey(BlockRegistry.BAOBAB_TRAPDOOR.asItem()));
        getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN).addTag(ModTags.BAOBAB_LOGS_ITEM);
        // Mod-namespace item tags (kept datagen-owned like the rest of the suite).
        getOrCreateTagBuilder(ModTags.CLAMS)
                .add(BuiltInRegistries.ITEM.getKey(ItemRegistry.CLAM))
                .add(BuiltInRegistries.ITEM.getKey(ItemRegistry.CLAM_BLUE))
                .add(BuiltInRegistries.ITEM.getKey(ItemRegistry.CLAM_PINK))
                .add(BuiltInRegistries.ITEM.getKey(ItemRegistry.CLAM_PURPLE));
        getOrCreateTagBuilder(ModTags.STACKED_CAKES)
                .add(BuiltInRegistries.ITEM.getKey(ItemRegistry.SWEETBERRY_CAKE))
                .add(BuiltInRegistries.ITEM.getKey(ItemRegistry.PAN_CAKE))
                .add(BuiltInRegistries.ITEM.getKey(ItemRegistry.GLOWBERRY_CAKE))
                .add(BuiltInRegistries.ITEM.getKey(ItemRegistry.APPLE_CAKE))
                .add(BuiltInRegistries.ITEM.getKey(ItemRegistry.VANILLA_CAKE))
                .add(BuiltInRegistries.ITEM.getKey(ItemRegistry.COOKIE_CAKE))
                .add(BuiltInRegistries.ITEM.getKey(ItemRegistry.CHOCOLATE_CAKE))
                .add(BuiltInRegistries.ITEM.getKey(ItemRegistry.BEETROOT_CAKE));
        // Turtle armour: vanilla armour-slot tags so the pieces equip in the right slots.
        getOrCreateTagBuilder(ItemTags.CHEST_ARMOR).add(BuiltInRegistries.ITEM.getKey(ItemRegistry.TURTLE_CHESTPLATE));
        getOrCreateTagBuilder(ItemTags.LEG_ARMOR).add(BuiltInRegistries.ITEM.getKey(ItemRegistry.TURTLE_LEGGINGS));
        getOrCreateTagBuilder(ItemTags.FOOT_ARMOR).add(BuiltInRegistries.ITEM.getKey(ItemRegistry.TURTLE_BOOTS));
        // Slingshot: supported projectile set (1.21.1 has no copper/resin nuggets).
        getOrCreateTagBuilder(ModTags.SLINGSHOT_PROJECTILES)
                .add(BuiltInRegistries.ITEM.getKey(Items.GOLD_NUGGET))
                .add(BuiltInRegistries.ITEM.getKey(Items.IRON_NUGGET))
                .add(BuiltInRegistries.ITEM.getKey(Items.AMETHYST_SHARD));
        getOrCreateTagBuilder(ModTags.SLINGSHOT_ENCHANTABLE)
                .add(BuiltInRegistries.ITEM.getKey(ItemRegistry.SLINGSHOT));
        // Moobloom food: every vanilla flower (open_eyeblossom is 26.x-only and skipped).
        getOrCreateTagBuilder(ModTags.MOOBLOOM_FLOWERS)
                .add(BuiltInRegistries.ITEM.getKey(Items.DANDELION))
                .add(BuiltInRegistries.ITEM.getKey(Items.POPPY))
                .add(BuiltInRegistries.ITEM.getKey(Items.BLUE_ORCHID))
                .add(BuiltInRegistries.ITEM.getKey(Items.ALLIUM))
                .add(BuiltInRegistries.ITEM.getKey(Items.AZURE_BLUET))
                .add(BuiltInRegistries.ITEM.getKey(Items.RED_TULIP))
                .add(BuiltInRegistries.ITEM.getKey(Items.ORANGE_TULIP))
                .add(BuiltInRegistries.ITEM.getKey(Items.WHITE_TULIP))
                .add(BuiltInRegistries.ITEM.getKey(Items.PINK_TULIP))
                .add(BuiltInRegistries.ITEM.getKey(Items.OXEYE_DAISY))
                .add(BuiltInRegistries.ITEM.getKey(Items.CORNFLOWER))
                .add(BuiltInRegistries.ITEM.getKey(Items.LILY_OF_THE_VALLEY))
                .add(BuiltInRegistries.ITEM.getKey(Items.WITHER_ROSE))
                .add(BuiltInRegistries.ITEM.getKey(Items.TORCHFLOWER));
    }
}
