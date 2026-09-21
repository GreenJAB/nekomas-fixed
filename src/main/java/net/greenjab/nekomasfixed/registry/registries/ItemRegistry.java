package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.item.*;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.Block;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ItemRegistry {

    public static final Item GLOW_TORCH = register(
            BlockRegistry.GLOW_TORCH,
            (block, settings) -> new StandingAndWallBlockItem(block, BlockRegistry.GLOW_WALL_TORCH, settings, Direction.DOWN),
            new Item.Properties());
    public static final Item ENDERMAN_HEAD = register(
            BlockRegistry.ENDERMAN_HEAD,
            (block, settings) -> new StandingAndWallBlockItem(block, BlockRegistry.WALL_ENDERMAN_HEAD, settings, Direction.DOWN),
            new Item.Properties());
    public static final Item BAOBAB_LOG = register(BlockRegistry.BAOBAB_LOG);
    public static final Item BAOBAB_WOOD = register(BlockRegistry.BAOBAB_WOOD);
    public static final Item STRIPPED_BAOBAB_LOG = register(BlockRegistry.STRIPPED_BAOBAB_LOG);
    public static final Item STRIPPED_BAOBAB_WOOD = register(BlockRegistry.STRIPPED_BAOBAB_WOOD);
    public static final Item BAOBAB_PLANKS = register(BlockRegistry.BAOBAB_PLANKS);
    public static final Item BAOBAB_STAIRS = register(BlockRegistry.BAOBAB_STAIRS);
    public static final Item BAOBAB_SLAB = register(BlockRegistry.BAOBAB_SLAB);
    public static final Item BAOBAB_FENCE = register(BlockRegistry.BAOBAB_FENCE);
    public static final Item BAOBAB_FENCE_GATE = register(BlockRegistry.BAOBAB_FENCE_GATE);
    public static final Item BAOBAB_DOOR = register(BlockRegistry.BAOBAB_DOOR);
    public static final Item BAOBAB_TRAPDOOR = register(BlockRegistry.BAOBAB_TRAPDOOR);
    public static final Item BAOBAB_PRESSURE_PLATE = register(BlockRegistry.BAOBAB_PRESSURE_PLATE);
    public static final Item BAOBAB_BUTTON = register(BlockRegistry.BAOBAB_BUTTON);
    public static final Item BAOBAB_SIGN = register(
            BlockRegistry.BAOBAB_SIGN,
            (block, settings) -> new SignItem(settings, block, BlockRegistry.BAOBAB_WALL_SIGN),
            new Item.Properties().stacksTo(16));
    public static final Item BAOBAB_HANGING_SIGN = register(
            BlockRegistry.BAOBAB_HANGING_SIGN,
            (block, settings) -> new HangingSignItem(block, BlockRegistry.BAOBAB_WALL_HANGING_SIGN, settings),
            new Item.Properties().stacksTo(16));
    public static final Item BAOBAB_LEAVES = register(BlockRegistry.BAOBAB_LEAVES);
    public static final Item ROPE = register(BlockRegistry.ROPE, RopeItem::new, new Item.Properties());
    public static final Item REDSTONE_STRIKER = register(
            "redstone_striker",
            RedstoneStrikerItem::new,
            new Item.Properties().stacksTo(1).durability(64));
    public static final Item TARGET_DUMMY = register(
            "target_dummy",
            TargetDummyItem::new,
            new Item.Properties().stacksTo(1));
    public static final Item MOOBLOOM_SPAWN_EGG = registerSpawnEgg(EntityTypeRegistry.MOOBLOOM, 0xF8D038, 0x709028);
    public static final FoodProperties BAOBAB_FRUIT_FOOD = new FoodProperties.Builder().nutrition(4).saturationModifier(0.3F).build();
    public static final Item BAOBAB_FRUIT = register(
            "baobab_fruit",
            new Item.Properties().food(BAOBAB_FRUIT_FOOD)
    );
    public static final Item BAOBAB_SAPLING = register(BlockRegistry.BAOBAB_SAPLING);
    public static final Item BAOBAB_SEEDS = register(
            "baobab_seeds",
            BaobabSeedsItem::new,
            new Item.Properties());
    public static final Item BAOBAB_BOAT = register(
            "baobab_boat",
            settings -> new BaobabBoatItem(false, settings),
            new Item.Properties().stacksTo(1));
    public static final Item BAOBAB_CHEST_BOAT = register(
            "baobab_chest_boat",
            settings -> new BaobabBoatItem(true, settings),
            new Item.Properties().stacksTo(1));
    public static final Item CLEAR_ITEM_FRAME = register(
            "clear_item_frame",
            settings -> new ClearItemFrameItem(EntityTypeRegistry.CLEAR_ITEM_FRAME, settings),
            new Item.Properties());
    public static final Item PEARL = register(
            "pearl",
            new Item.Properties());
    public static final Item PEARL_BLOCK = register(BlockRegistry.PEARL_BLOCK);
    public static final Item GEYSER = register(BlockRegistry.GEYSER);
    public static final Item GLISTERING_MELON = register(BlockRegistry.GLISTERING_MELON);
    public static final Item TURTLE_CHESTPLATE = register("turtle_chestplate",
            settings -> new ArmorItem(ArmorMaterialRegistry.TURTLE_SCUTE, ArmorItem.Type.CHESTPLATE, settings),
            new Item.Properties().stacksTo(1).durability(400));
    public static final Item TURTLE_LEGGINGS = register("turtle_leggings",
            settings -> new ArmorItem(ArmorMaterialRegistry.TURTLE_SCUTE, ArmorItem.Type.LEGGINGS, settings),
            new Item.Properties().stacksTo(1).durability(375));
    public static final Item TURTLE_BOOTS = register("turtle_boots",
            settings -> new ArmorItem(ArmorMaterialRegistry.TURTLE_SCUTE, ArmorItem.Type.BOOTS, settings),
            new Item.Properties().stacksTo(1).durability(325));
    public static final Holder<Potion> LIGHTNING = register("lightning", new Potion("lightning", new MobEffectInstance(EffectRegistry.LIGHTNING, 1)));
    public static final Item SLINGSHOT = register("slingshot", SlingshotItem::new, new Item.Properties().stacksTo(1).durability(384));
    public static final Item SWEETBERRY_CAKE = register(BlockRegistry.SWEETBERRY_CAKE, new Item.Properties().stacksTo(1));
    public static final Item PAN_CAKE = register(BlockRegistry.PAN_CAKE, new Item.Properties().stacksTo(1));
    public static final Item GLOWBERRY_CAKE = register(BlockRegistry.GLOWBERRY_CAKE, new Item.Properties().stacksTo(1));
    public static final Item APPLE_CAKE = register(BlockRegistry.APPLE_CAKE, new Item.Properties().stacksTo(1));
    public static final Item VANILLA_CAKE = register(BlockRegistry.VANILLA_CAKE, new Item.Properties().stacksTo(1));
    public static final Item COOKIE_CAKE = register(BlockRegistry.COOKIE_CAKE, new Item.Properties().stacksTo(1));
    public static final Item CHOCOLATE_CAKE = register(BlockRegistry.CHOCOLATE_CAKE, new Item.Properties().stacksTo(1));
    public static final Item BEETROOT_CAKE = register(BlockRegistry.BEETROOT_CAKE, new Item.Properties().stacksTo(1));
    public static final Item CLAM = register(BlockRegistry.CLAM,
            new Item.Properties().stacksTo(1).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY));
    public static final Item CLAM_BLUE = register(BlockRegistry.CLAM_BLUE,
            new Item.Properties().stacksTo(1).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY));
    public static final Item CLAM_PINK = register(BlockRegistry.CLAM_PINK,
            new Item.Properties().stacksTo(1).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY));
    public static final Item CLAM_PURPLE = register(BlockRegistry.CLAM_PURPLE,
            new Item.Properties().stacksTo(1).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY));

    public static final Item HOLLOW_OAK_LOG = register(BlockRegistry.HOLLOW_OAK_LOG);
    public static final Item HOLLOW_SPRUCE_LOG = register(BlockRegistry.HOLLOW_SPRUCE_LOG);
    public static final Item HOLLOW_BIRCH_LOG = register(BlockRegistry.HOLLOW_BIRCH_LOG);
    public static final Item HOLLOW_JUNGLE_LOG = register(BlockRegistry.HOLLOW_JUNGLE_LOG);
    public static final Item HOLLOW_ACACIA_LOG = register(BlockRegistry.HOLLOW_ACACIA_LOG);
    public static final Item HOLLOW_DARK_OAK_LOG = register(BlockRegistry.HOLLOW_DARK_OAK_LOG);
    public static final Item HOLLOW_MANGROVE_LOG = register(BlockRegistry.HOLLOW_MANGROVE_LOG);
    public static final Item HOLLOW_CHERRY_LOG = register(BlockRegistry.HOLLOW_CHERRY_LOG);
    public static final Item HOLLOW_BAMBOO_BLOCK = register(BlockRegistry.HOLLOW_BAMBOO_BLOCK);
    public static final Item HOLLOW_CRIMSON_STEM = register(BlockRegistry.HOLLOW_CRIMSON_STEM);
    public static final Item HOLLOW_WARPED_STEM = register(BlockRegistry.HOLLOW_WARPED_STEM);
    public static final Item HOLLOW_BAOBAB_LOG = register(BlockRegistry.HOLLOW_BAOBAB_LOG);
    public static final Item HOLLOW_STRIPPED_OAK_LOG = register(BlockRegistry.HOLLOW_STRIPPED_OAK_LOG);
    public static final Item HOLLOW_STRIPPED_SPRUCE_LOG = register(BlockRegistry.HOLLOW_STRIPPED_SPRUCE_LOG);
    public static final Item HOLLOW_STRIPPED_BIRCH_LOG = register(BlockRegistry.HOLLOW_STRIPPED_BIRCH_LOG);
    public static final Item HOLLOW_STRIPPED_JUNGLE_LOG = register(BlockRegistry.HOLLOW_STRIPPED_JUNGLE_LOG);
    public static final Item HOLLOW_STRIPPED_ACACIA_LOG = register(BlockRegistry.HOLLOW_STRIPPED_ACACIA_LOG);
    public static final Item HOLLOW_STRIPPED_DARK_OAK_LOG = register(BlockRegistry.HOLLOW_STRIPPED_DARK_OAK_LOG);
    public static final Item HOLLOW_STRIPPED_MANGROVE_LOG = register(BlockRegistry.HOLLOW_STRIPPED_MANGROVE_LOG);
    public static final Item HOLLOW_STRIPPED_CHERRY_LOG = register(BlockRegistry.HOLLOW_STRIPPED_CHERRY_LOG);
    public static final Item HOLLOW_STRIPPED_BAMBOO_BLOCK = register(BlockRegistry.HOLLOW_STRIPPED_BAMBOO_BLOCK);
    public static final Item HOLLOW_STRIPPED_CRIMSON_STEM = register(BlockRegistry.HOLLOW_STRIPPED_CRIMSON_STEM);
    public static final Item HOLLOW_STRIPPED_WARPED_STEM = register(BlockRegistry.HOLLOW_STRIPPED_WARPED_STEM);
    public static final Item HOLLOW_STRIPPED_BAOBAB_LOG = register(BlockRegistry.HOLLOW_STRIPPED_BAOBAB_LOG);

    public static final Item CLEAR_FROGLIGHT = register(BlockRegistry.CLEAR_FROGLIGHT);
    public static final Item CLOUDY_FROGLIGHT = register(BlockRegistry.CLOUDY_FROGLIGHT);
    public static final Item CASCADING_FROGLIGHT = register(BlockRegistry.CASCADING_FROGLIGHT);
    public static final Item CLOUDBURST_FROGLIGHT = register(BlockRegistry.CLOUDBURST_FROGLIGHT);
    public static final Item CHAMOISEE_FROGLIGHT = register(BlockRegistry.CHAMOISEE_FROGLIGHT);
    public static final Item SANGUINE_FROGLIGHT = register(BlockRegistry.SANGUINE_FROGLIGHT);
    public static final Item VERMILION_FROGLIGHT = register(BlockRegistry.VERMILION_FROGLIGHT);
    public static final Item MANDARIN_FROGLIGHT = register(BlockRegistry.MANDARIN_FROGLIGHT);
    public static final Item LEMON_FROGLIGHT = register(BlockRegistry.LEMON_FROGLIGHT);
    public static final Item KIWI_FROGLIGHT = register(BlockRegistry.KIWI_FROGLIGHT);
    public static final Item SEAFOAM_FROGLIGHT = register(BlockRegistry.SEAFOAM_FROGLIGHT);
    public static final Item TEAL_FROGLIGHT = register(BlockRegistry.TEAL_FROGLIGHT);
    public static final Item CERULEAN_FROGLIGHT = register(BlockRegistry.CERULEAN_FROGLIGHT);
    public static final Item NAVY_FROGLIGHT = register(BlockRegistry.NAVY_FROGLIGHT);
    public static final Item LAVENDER_FROGLIGHT = register(BlockRegistry.LAVENDER_FROGLIGHT);
    public static final Item THULIAN_FROGLIGHT = register(BlockRegistry.THULIAN_FROGLIGHT);
    public static final Item SAKURA_FROGLIGHT = register(BlockRegistry.SAKURA_FROGLIGHT);

    public static final Item WHITE_SPOTTED_WOOL = register(BlockRegistry.WHITE_SPOTTED_WOOL);
    public static final Item LIGHT_GRAY_SPOTTED_WOOL = register(BlockRegistry.LIGHT_GRAY_SPOTTED_WOOL);
    public static final Item GRAY_SPOTTED_WOOL = register(BlockRegistry.GRAY_SPOTTED_WOOL);
    public static final Item BLACK_SPOTTED_WOOL = register(BlockRegistry.BLACK_SPOTTED_WOOL);
    public static final Item BROWN_SPOTTED_WOOL = register(BlockRegistry.BROWN_SPOTTED_WOOL);
    public static final Item RED_SPOTTED_WOOL = register(BlockRegistry.RED_SPOTTED_WOOL);
    public static final Item ORANGE_SPOTTED_WOOL = register(BlockRegistry.ORANGE_SPOTTED_WOOL);
    public static final Item YELLOW_SPOTTED_WOOL = register(BlockRegistry.YELLOW_SPOTTED_WOOL);
    public static final Item LIME_SPOTTED_WOOL = register(BlockRegistry.LIME_SPOTTED_WOOL);
    public static final Item GREEN_SPOTTED_WOOL = register(BlockRegistry.GREEN_SPOTTED_WOOL);
    public static final Item CYAN_SPOTTED_WOOL = register(BlockRegistry.CYAN_SPOTTED_WOOL);
    public static final Item LIGHT_BLUE_SPOTTED_WOOL = register(BlockRegistry.LIGHT_BLUE_SPOTTED_WOOL);
    public static final Item BLUE_SPOTTED_WOOL = register(BlockRegistry.BLUE_SPOTTED_WOOL);
    public static final Item PURPLE_SPOTTED_WOOL = register(BlockRegistry.PURPLE_SPOTTED_WOOL);
    public static final Item MAGENTA_SPOTTED_WOOL = register(BlockRegistry.MAGENTA_SPOTTED_WOOL);
    public static final Item PINK_SPOTTED_WOOL = register(BlockRegistry.PINK_SPOTTED_WOOL);
    public static final Item AMBER_SPOTTED_WOOL = register(BlockRegistry.AMBER_SPOTTED_WOOL);
    public static final Item AQUA_SPOTTED_WOOL = register(BlockRegistry.AQUA_SPOTTED_WOOL);
    public static final Item INDIGO_SPOTTED_WOOL = register(BlockRegistry.INDIGO_SPOTTED_WOOL);
    public static final Item MAROON_SPOTTED_WOOL = register(BlockRegistry.MAROON_SPOTTED_WOOL);
    public static final Item WHITE_SPOTTED_CARPET = register(BlockRegistry.WHITE_SPOTTED_CARPET);
    public static final Item LIGHT_GRAY_SPOTTED_CARPET = register(BlockRegistry.LIGHT_GRAY_SPOTTED_CARPET);
    public static final Item GRAY_SPOTTED_CARPET = register(BlockRegistry.GRAY_SPOTTED_CARPET);
    public static final Item BLACK_SPOTTED_CARPET = register(BlockRegistry.BLACK_SPOTTED_CARPET);
    public static final Item BROWN_SPOTTED_CARPET = register(BlockRegistry.BROWN_SPOTTED_CARPET);
    public static final Item RED_SPOTTED_CARPET = register(BlockRegistry.RED_SPOTTED_CARPET);
    public static final Item ORANGE_SPOTTED_CARPET = register(BlockRegistry.ORANGE_SPOTTED_CARPET);
    public static final Item YELLOW_SPOTTED_CARPET = register(BlockRegistry.YELLOW_SPOTTED_CARPET);
    public static final Item LIME_SPOTTED_CARPET = register(BlockRegistry.LIME_SPOTTED_CARPET);
    public static final Item GREEN_SPOTTED_CARPET = register(BlockRegistry.GREEN_SPOTTED_CARPET);
    public static final Item CYAN_SPOTTED_CARPET = register(BlockRegistry.CYAN_SPOTTED_CARPET);
    public static final Item LIGHT_BLUE_SPOTTED_CARPET = register(BlockRegistry.LIGHT_BLUE_SPOTTED_CARPET);
    public static final Item BLUE_SPOTTED_CARPET = register(BlockRegistry.BLUE_SPOTTED_CARPET);
    public static final Item PURPLE_SPOTTED_CARPET = register(BlockRegistry.PURPLE_SPOTTED_CARPET);
    public static final Item MAGENTA_SPOTTED_CARPET = register(BlockRegistry.MAGENTA_SPOTTED_CARPET);
    public static final Item PINK_SPOTTED_CARPET = register(BlockRegistry.PINK_SPOTTED_CARPET);
    public static final Item AMBER_SPOTTED_CARPET = register(BlockRegistry.AMBER_SPOTTED_CARPET);
    public static final Item AQUA_SPOTTED_CARPET = register(BlockRegistry.AQUA_SPOTTED_CARPET);
    public static final Item INDIGO_SPOTTED_CARPET = register(BlockRegistry.INDIGO_SPOTTED_CARPET);
    public static final Item MAROON_SPOTTED_CARPET = register(BlockRegistry.MAROON_SPOTTED_CARPET);
    public static final Item AMBER_WOOL = register(BlockRegistry.AMBER_WOOL);
    public static final Item AQUA_WOOL = register(BlockRegistry.AQUA_WOOL);
    public static final Item INDIGO_WOOL = register(BlockRegistry.INDIGO_WOOL);
    public static final Item MAROON_WOOL = register(BlockRegistry.MAROON_WOOL);
    public static final Item AMBER_CARPET = register(BlockRegistry.AMBER_CARPET);
    public static final Item AQUA_CARPET = register(BlockRegistry.AQUA_CARPET);
    public static final Item INDIGO_CARPET = register(BlockRegistry.INDIGO_CARPET);
    public static final Item MAROON_CARPET = register(BlockRegistry.MAROON_CARPET);

    public static final Item WHITE_BRICKS = register(BlockRegistry.WHITE_BRICKS);
    public static final Item ORANGE_BRICKS = register(BlockRegistry.ORANGE_BRICKS);
    public static final Item MAGENTA_BRICKS = register(BlockRegistry.MAGENTA_BRICKS);
    public static final Item LIGHT_BLUE_BRICKS = register(BlockRegistry.LIGHT_BLUE_BRICKS);
    public static final Item YELLOW_BRICKS = register(BlockRegistry.YELLOW_BRICKS);
    public static final Item LIME_BRICKS = register(BlockRegistry.LIME_BRICKS);
    public static final Item PINK_BRICKS = register(BlockRegistry.PINK_BRICKS);
    public static final Item GRAY_BRICKS = register(BlockRegistry.GRAY_BRICKS);
    public static final Item LIGHT_GRAY_BRICKS = register(BlockRegistry.LIGHT_GRAY_BRICKS);
    public static final Item CYAN_BRICKS = register(BlockRegistry.CYAN_BRICKS);
    public static final Item PURPLE_BRICKS = register(BlockRegistry.PURPLE_BRICKS);
    public static final Item BLUE_BRICKS = register(BlockRegistry.BLUE_BRICKS);
    public static final Item BROWN_BRICKS = register(BlockRegistry.BROWN_BRICKS);
    public static final Item GREEN_BRICKS = register(BlockRegistry.GREEN_BRICKS);
    public static final Item RED_BRICKS = register(BlockRegistry.RED_BRICKS);
    public static final Item BLACK_BRICKS = register(BlockRegistry.BLACK_BRICKS);
    public static final Item AMBER_BRICKS = register(BlockRegistry.AMBER_BRICKS);
    public static final Item AQUA_BRICKS = register(BlockRegistry.AQUA_BRICKS);
    public static final Item INDIGO_BRICKS = register(BlockRegistry.INDIGO_BRICKS);
    public static final Item MAROON_BRICKS = register(BlockRegistry.MAROON_BRICKS);
    public static final Item WHITE_BRICK_SLAB = register(BlockRegistry.WHITE_BRICK_SLAB);
    public static final Item ORANGE_BRICK_SLAB = register(BlockRegistry.ORANGE_BRICK_SLAB);
    public static final Item MAGENTA_BRICK_SLAB = register(BlockRegistry.MAGENTA_BRICK_SLAB);
    public static final Item LIGHT_BLUE_BRICK_SLAB = register(BlockRegistry.LIGHT_BLUE_BRICK_SLAB);
    public static final Item YELLOW_BRICK_SLAB = register(BlockRegistry.YELLOW_BRICK_SLAB);
    public static final Item LIME_BRICK_SLAB = register(BlockRegistry.LIME_BRICK_SLAB);
    public static final Item PINK_BRICK_SLAB = register(BlockRegistry.PINK_BRICK_SLAB);
    public static final Item GRAY_BRICK_SLAB = register(BlockRegistry.GRAY_BRICK_SLAB);
    public static final Item LIGHT_GRAY_BRICK_SLAB = register(BlockRegistry.LIGHT_GRAY_BRICK_SLAB);
    public static final Item CYAN_BRICK_SLAB = register(BlockRegistry.CYAN_BRICK_SLAB);
    public static final Item PURPLE_BRICK_SLAB = register(BlockRegistry.PURPLE_BRICK_SLAB);
    public static final Item BLUE_BRICK_SLAB = register(BlockRegistry.BLUE_BRICK_SLAB);
    public static final Item BROWN_BRICK_SLAB = register(BlockRegistry.BROWN_BRICK_SLAB);
    public static final Item GREEN_BRICK_SLAB = register(BlockRegistry.GREEN_BRICK_SLAB);
    public static final Item RED_BRICK_SLAB = register(BlockRegistry.RED_BRICK_SLAB);
    public static final Item BLACK_BRICK_SLAB = register(BlockRegistry.BLACK_BRICK_SLAB);
    public static final Item AMBER_BRICK_SLAB = register(BlockRegistry.AMBER_BRICK_SLAB);
    public static final Item AQUA_BRICK_SLAB = register(BlockRegistry.AQUA_BRICK_SLAB);
    public static final Item INDIGO_BRICK_SLAB = register(BlockRegistry.INDIGO_BRICK_SLAB);
    public static final Item MAROON_BRICK_SLAB = register(BlockRegistry.MAROON_BRICK_SLAB);
    public static final Item WHITE_BRICK_STAIRS = register(BlockRegistry.WHITE_BRICK_STAIRS);
    public static final Item ORANGE_BRICK_STAIRS = register(BlockRegistry.ORANGE_BRICK_STAIRS);
    public static final Item MAGENTA_BRICK_STAIRS = register(BlockRegistry.MAGENTA_BRICK_STAIRS);
    public static final Item LIGHT_BLUE_BRICK_STAIRS = register(BlockRegistry.LIGHT_BLUE_BRICK_STAIRS);
    public static final Item YELLOW_BRICK_STAIRS = register(BlockRegistry.YELLOW_BRICK_STAIRS);
    public static final Item LIME_BRICK_STAIRS = register(BlockRegistry.LIME_BRICK_STAIRS);
    public static final Item PINK_BRICK_STAIRS = register(BlockRegistry.PINK_BRICK_STAIRS);
    public static final Item GRAY_BRICK_STAIRS = register(BlockRegistry.GRAY_BRICK_STAIRS);
    public static final Item LIGHT_GRAY_BRICK_STAIRS = register(BlockRegistry.LIGHT_GRAY_BRICK_STAIRS);
    public static final Item CYAN_BRICK_STAIRS = register(BlockRegistry.CYAN_BRICK_STAIRS);
    public static final Item PURPLE_BRICK_STAIRS = register(BlockRegistry.PURPLE_BRICK_STAIRS);
    public static final Item BLUE_BRICK_STAIRS = register(BlockRegistry.BLUE_BRICK_STAIRS);
    public static final Item BROWN_BRICK_STAIRS = register(BlockRegistry.BROWN_BRICK_STAIRS);
    public static final Item GREEN_BRICK_STAIRS = register(BlockRegistry.GREEN_BRICK_STAIRS);
    public static final Item RED_BRICK_STAIRS = register(BlockRegistry.RED_BRICK_STAIRS);
    public static final Item BLACK_BRICK_STAIRS = register(BlockRegistry.BLACK_BRICK_STAIRS);
    public static final Item AMBER_BRICK_STAIRS = register(BlockRegistry.AMBER_BRICK_STAIRS);
    public static final Item AQUA_BRICK_STAIRS = register(BlockRegistry.AQUA_BRICK_STAIRS);
    public static final Item INDIGO_BRICK_STAIRS = register(BlockRegistry.INDIGO_BRICK_STAIRS);
    public static final Item MAROON_BRICK_STAIRS = register(BlockRegistry.MAROON_BRICK_STAIRS);
    public static final Item WHITE_BRICK_WALL = register(BlockRegistry.WHITE_BRICK_WALL);
    public static final Item ORANGE_BRICK_WALL = register(BlockRegistry.ORANGE_BRICK_WALL);
    public static final Item MAGENTA_BRICK_WALL = register(BlockRegistry.MAGENTA_BRICK_WALL);
    public static final Item LIGHT_BLUE_BRICK_WALL = register(BlockRegistry.LIGHT_BLUE_BRICK_WALL);
    public static final Item YELLOW_BRICK_WALL = register(BlockRegistry.YELLOW_BRICK_WALL);
    public static final Item LIME_BRICK_WALL = register(BlockRegistry.LIME_BRICK_WALL);
    public static final Item PINK_BRICK_WALL = register(BlockRegistry.PINK_BRICK_WALL);
    public static final Item GRAY_BRICK_WALL = register(BlockRegistry.GRAY_BRICK_WALL);
    public static final Item LIGHT_GRAY_BRICK_WALL = register(BlockRegistry.LIGHT_GRAY_BRICK_WALL);
    public static final Item CYAN_BRICK_WALL = register(BlockRegistry.CYAN_BRICK_WALL);
    public static final Item PURPLE_BRICK_WALL = register(BlockRegistry.PURPLE_BRICK_WALL);
    public static final Item BLUE_BRICK_WALL = register(BlockRegistry.BLUE_BRICK_WALL);
    public static final Item BROWN_BRICK_WALL = register(BlockRegistry.BROWN_BRICK_WALL);
    public static final Item GREEN_BRICK_WALL = register(BlockRegistry.GREEN_BRICK_WALL);
    public static final Item RED_BRICK_WALL = register(BlockRegistry.RED_BRICK_WALL);
    public static final Item BLACK_BRICK_WALL = register(BlockRegistry.BLACK_BRICK_WALL);
    public static final Item AMBER_BRICK_WALL = register(BlockRegistry.AMBER_BRICK_WALL);
    public static final Item AQUA_BRICK_WALL = register(BlockRegistry.AQUA_BRICK_WALL);
    public static final Item INDIGO_BRICK_WALL = register(BlockRegistry.INDIGO_BRICK_WALL);
    public static final Item MAROON_BRICK_WALL = register(BlockRegistry.MAROON_BRICK_WALL);

    // Ancient-dye stained glass + panes (plain block items).
    public static final Item AMBER_STAINED_GLASS = register(BlockRegistry.AMBER_STAINED_GLASS);
    public static final Item AQUA_STAINED_GLASS = register(BlockRegistry.AQUA_STAINED_GLASS);
    public static final Item INDIGO_STAINED_GLASS = register(BlockRegistry.INDIGO_STAINED_GLASS);
    public static final Item MAROON_STAINED_GLASS = register(BlockRegistry.MAROON_STAINED_GLASS);
    public static final Item AMBER_STAINED_GLASS_PANE = register(BlockRegistry.AMBER_STAINED_GLASS_PANE);
    public static final Item AQUA_STAINED_GLASS_PANE = register(BlockRegistry.AQUA_STAINED_GLASS_PANE);
    public static final Item INDIGO_STAINED_GLASS_PANE = register(BlockRegistry.INDIGO_STAINED_GLASS_PANE);
    public static final Item MAROON_STAINED_GLASS_PANE = register(BlockRegistry.MAROON_STAINED_GLASS_PANE);

    // Ancient-colour candles (plain block items).
    public static final Item AMBER_CANDLE = register(BlockRegistry.AMBER_CANDLE);
    public static final Item AQUA_CANDLE = register(BlockRegistry.AQUA_CANDLE);
    public static final Item INDIGO_CANDLE = register(BlockRegistry.INDIGO_CANDLE);
    public static final Item MAROON_CANDLE = register(BlockRegistry.MAROON_CANDLE);

    // Ancient-colour beds (BedItem, stack to 1).
    public static final Item AMBER_BED = registerBedItem(BlockRegistry.AMBER_BED);
    public static final Item AQUA_BED = registerBedItem(BlockRegistry.AQUA_BED);
    public static final Item INDIGO_BED = registerBedItem(BlockRegistry.INDIGO_BED);
    public static final Item MAROON_BED = registerBedItem(BlockRegistry.MAROON_BED);

    // Ancient-colour shulker boxes: stack to 1 with an empty container component (vanilla shulker).
    public static final Item AMBER_SHULKER_BOX = register(BlockRegistry.AMBER_SHULKER_BOX, shulkerBoxProperties());
    public static final Item AQUA_SHULKER_BOX = register(BlockRegistry.AQUA_SHULKER_BOX, shulkerBoxProperties());
    public static final Item INDIGO_SHULKER_BOX = register(BlockRegistry.INDIGO_SHULKER_BOX, shulkerBoxProperties());
    public static final Item MAROON_SHULKER_BOX = register(BlockRegistry.MAROON_SHULKER_BOX, shulkerBoxProperties());

    public static final Item AMBER_DYE = registerDye("amber_dye");
    public static final Item AQUA_DYE = registerDye("aqua_dye");
    public static final Item INDIGO_DYE = registerDye("indigo_dye");
    public static final Item MAROON_DYE = registerDye("maroon_dye");

    // Ancient-dye terracotta + concrete + concrete powder (plain block items).
    public static final Item AMBER_TERRACOTTA = register(BlockRegistry.AMBER_TERRACOTTA);
    public static final Item AQUA_TERRACOTTA = register(BlockRegistry.AQUA_TERRACOTTA);
    public static final Item INDIGO_TERRACOTTA = register(BlockRegistry.INDIGO_TERRACOTTA);
    public static final Item MAROON_TERRACOTTA = register(BlockRegistry.MAROON_TERRACOTTA);
    public static final Item AMBER_CONCRETE = register(BlockRegistry.AMBER_CONCRETE);
    public static final Item AQUA_CONCRETE = register(BlockRegistry.AQUA_CONCRETE);
    public static final Item INDIGO_CONCRETE = register(BlockRegistry.INDIGO_CONCRETE);
    public static final Item MAROON_CONCRETE = register(BlockRegistry.MAROON_CONCRETE);
    public static final Item AMBER_CONCRETE_POWDER = register(BlockRegistry.AMBER_CONCRETE_POWDER);
    public static final Item AQUA_CONCRETE_POWDER = register(BlockRegistry.AQUA_CONCRETE_POWDER);
    public static final Item INDIGO_CONCRETE_POWDER = register(BlockRegistry.INDIGO_CONCRETE_POWDER);
    public static final Item MAROON_CONCRETE_POWDER = register(BlockRegistry.MAROON_CONCRETE_POWDER);

    // Ancient-dye glazed terracotta (plain block items).
    public static final Item AMBER_GLAZED_TERRACOTTA = register(BlockRegistry.AMBER_GLAZED_TERRACOTTA);
    public static final Item AQUA_GLAZED_TERRACOTTA = register(BlockRegistry.AQUA_GLAZED_TERRACOTTA);
    public static final Item INDIGO_GLAZED_TERRACOTTA = register(BlockRegistry.INDIGO_GLAZED_TERRACOTTA);
    public static final Item MAROON_GLAZED_TERRACOTTA = register(BlockRegistry.MAROON_GLAZED_TERRACOTTA);

    private static Item register(Block block) {
        return Registry.register(BuiltInRegistries.ITEM,
                ResourceKey.create(Registries.ITEM, blockKeyOf(block)),
                new BlockItem(block, new Item.Properties()));
    }

    private static ResourceLocation blockKeyOf(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private static Item register(Block block, Item.Properties settings) {
        return Registry.register(BuiltInRegistries.ITEM,
                ResourceKey.create(Registries.ITEM, blockKeyOf(block)),
                new BlockItem(block, settings));
    }

    private static Item register(String id, Item.Properties settings) {
        return Registry.register(BuiltInRegistries.ITEM,
                ResourceKey.create(Registries.ITEM, NekomasFixed.id(id)),
                new Item(settings));
    }

    private static Holder<Potion> register(String id, Potion potion) {
        return Registry.registerForHolder(BuiltInRegistries.POTION, NekomasFixed.id(id), potion);
    }

    private static Item register(String id, Function<Item.Properties, Item> factory, Item.Properties settings) {
        return Registry.register(BuiltInRegistries.ITEM,
                ResourceKey.create(Registries.ITEM, NekomasFixed.id(id)),
                factory.apply(settings));
    }

    private static Item register(Block block, BiFunction<Block, Item.Properties, Item> factory, Item.Properties settings) {
        return Registry.register(BuiltInRegistries.ITEM,
                ResourceKey.create(Registries.ITEM, blockKeyOf(block)),
                factory.apply(block, settings));
    }

    private static Item registerDye(String id) {
        return Registry.register(BuiltInRegistries.ITEM, NekomasFixed.id(id), new ModDyeItems(new Item.Properties()));
    }

    // 1.21.1 spawn eggs are SpawnEggItem(type, base, highlight, props) — no 26.x
    // Item.Properties.spawnEgg() builder. Tints come from the two colours.
    private static Item registerSpawnEgg(EntityType<? extends Mob> type, int backgroundColor, int highlightColor) {
        ResourceLocation eggId = BuiltInRegistries.ENTITY_TYPE.getKey(type).withSuffix("_spawn_egg");
        return Registry.register(BuiltInRegistries.ITEM,
                ResourceKey.create(Registries.ITEM, eggId),
                new SpawnEggItem(type, backgroundColor, highlightColor, new Item.Properties()));
    }

    // Bed: vanilla BedItem, stack to 1 (can't stack beds).
    private static Item registerBedItem(Block block) {
        return register(block, BedItem::new, new Item.Properties().stacksTo(1));
    }

    // Shulker box: stack to 1 with an empty container component (vanilla shulker behaviour).
    private static Item.Properties shulkerBoxProperties() {
        return new Item.Properties().stacksTo(1).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
    }

    public static void registerItems() {
        NekomasFixed.LOGGER.info("Registering items");
    }
}
