package net.greenjab.nekomasfixed.registry.registries;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ItemGroupRegistry {

    public static final CreativeModeTab NEKOMASFIXED = FabricCreativeModeTab.builder().title(Component.translatable("itemgroup.nekomasfixed"))
            .icon( () -> new ItemStack(ItemRegistry.CLAM))
            .displayItems(
                    (_, entries) -> {
                        entries.accept(ItemRegistry.CLAM);
                        entries.accept(ItemRegistry.CLAM_BLUE);
                        entries.accept(ItemRegistry.CLAM_PINK);
                        entries.accept(ItemRegistry.CLAM_PURPLE);
                        entries.accept(ItemRegistry.PEARL);
                        entries.accept(ItemRegistry.PEARL_BLOCK);

                        entries.accept(ItemRegistry.NAUTILUS_BLOCK);
                        entries.accept(ItemRegistry.ZOMBIE_NAUTILUS_BLOCK);
                        entries.accept(ItemRegistry.CORAL_NAUTILUS_BLOCK);
                        entries.accept(ItemRegistry.GLISTERING_MELON);
                        entries.accept(ItemRegistry.GEYSER);
                        entries.accept(ItemRegistry.KILN);
                        entries.accept(ItemRegistry.PYROTECHNICS_TABLE);
                        entries.accept(ItemRegistry.ENDERMAN_HEAD);
                        entries.accept(ItemRegistry.REDSTONE_STRIKER);
                        entries.accept(ItemRegistry.GLOW_TORCH);
                        entries.accept(ItemRegistry.TARGET_DUMMY);
                        entries.accept(ItemRegistry.TURTLE_CHESTPLATE);
                        entries.accept(ItemRegistry.TURTLE_LEGGINGS);
                        entries.accept(ItemRegistry.TURTLE_BOOTS);

                        entries.accept(ItemRegistry.MOOBLOOM_SPAWN_EGG);
                        entries.accept(ItemRegistry.DRENCHED_SPAWN_EGG);
                        entries.accept(ItemRegistry.RIME_SPAWN_EGG);
                        entries.accept(ItemRegistry.DERELICT_SPAWN_EGG);
                        entries.accept(ItemRegistry.ANCHOR);
                        entries.accept(ItemRegistry.SUSPICIOUS_SPIDER_SPAWN_EGG);
                        entries.accept(ItemRegistry.WILDFIRE_SPAWN_EGG);
                        entries.accept(ItemRegistry.NETHER_HEART);
                        entries.accept(ItemRegistry.WILDFIRE_TRIDENT);
                        entries.accept(ItemRegistry.WILDFIRE_SHIELD);
                        entries.accept(ItemRegistry.JEWEL_ARMOR_TRIM_SMITHING_TEMPLATE);

                        entries.accept(ItemRegistry.CROWN_SMITHING_TEMPLATE);
                        entries.accept(ItemRegistry.COPPER_CROWN);
                        entries.accept(ItemRegistry.IRON_CROWN);
                        entries.accept(ItemRegistry.GOLDEN_CROWN);
                        entries.accept(ItemRegistry.DIAMOND_CROWN);
                        entries.accept(ItemRegistry.NETHERITE_CROWN);

                        entries.accept(ItemRegistry.SLINGSHOT);
                        entries.accept(ItemRegistry.WOODEN_SICKLE);
                        entries.accept(ItemRegistry.STONE_SICKLE);
                        entries.accept(ItemRegistry.COPPER_SICKLE);
                        entries.accept(ItemRegistry.IRON_SICKLE);
                        entries.accept(ItemRegistry.GOLDEN_SICKLE);
                        entries.accept(ItemRegistry.DIAMOND_SICKLE);
                        entries.accept(ItemRegistry.NETHERITE_SICKLE);

                        entries.accept(ItemRegistry.SWEETBERRY_CAKE);
                        entries.accept(ItemRegistry.PAN_CAKE);
                        entries.accept(ItemRegistry.GLOWBERRY_CAKE);
                        entries.accept(ItemRegistry.APPLE_CAKE);
                        entries.accept(ItemRegistry.VANILLA_CAKE);
                        entries.accept(ItemRegistry.COOKIE_CAKE);
                        entries.accept(ItemRegistry.CHOCOLATE_CAKE);
                        entries.accept(ItemRegistry.BEETROOT_CAKE);

                        entries.accept(BlockRegistry.BAOBAB_LOG);
                        entries.accept(BlockRegistry.BAOBAB_WOOD);
                        entries.accept(BlockRegistry.STRIPPED_BAOBAB_LOG);
                        entries.accept(BlockRegistry.STRIPPED_BAOBAB_WOOD);
                        entries.accept(BlockRegistry.BAOBAB_PLANKS);
                        entries.accept(BlockRegistry.BAOBAB_STAIRS);
                        entries.accept(BlockRegistry.BAOBAB_SLAB);
                        entries.accept(BlockRegistry.BAOBAB_FENCE);
                        entries.accept(BlockRegistry.BAOBAB_FENCE_GATE);
                        entries.accept(BlockRegistry.BAOBAB_DOOR);
                        entries.accept(BlockRegistry.BAOBAB_TRAPDOOR);
                        entries.accept(BlockRegistry.BAOBAB_PRESSURE_PLATE);
                        entries.accept(BlockRegistry.BAOBAB_BUTTON);
                        entries.accept(BlockRegistry.BAOBAB_LEAVES);
                        entries.accept(BlockRegistry.BAOBAB_SAPLING);
                        entries.accept(ItemRegistry.BAOBAB_SEEDS);
                        entries.accept(ItemRegistry.BAOBAB_FRUIT);
                        entries.accept(ItemRegistry.ROPE);
                        entries.accept(ItemRegistry.BAOBAB_SHELF);
                        entries.accept(ItemRegistry.BAOBAB_SIGN);
                        entries.accept(ItemRegistry.BAOBAB_HANGING_SIGN);
                        entries.accept(ItemRegistry.BAOBAB_BOAT);
                        entries.accept(ItemRegistry.BAOBAB_CHEST_BOAT);

                        entries.accept(ItemRegistry.TERMITE_SPAWN_EGG);
                        entries.accept(ItemRegistry.TERMITE_HIVE);
                        entries.accept(ItemRegistry.TERMITE_BLOCK);
                        entries.accept(ItemRegistry.HOLLOW_OAK_LOG);
                        entries.accept(ItemRegistry.HOLLOW_SPRUCE_LOG);
                        entries.accept(ItemRegistry.HOLLOW_BIRCH_LOG);
                        entries.accept(ItemRegistry.HOLLOW_JUNGLE_LOG);
                        entries.accept(ItemRegistry.HOLLOW_ACACIA_LOG);
                        entries.accept(ItemRegistry.HOLLOW_DARK_OAK_LOG);
                        entries.accept(ItemRegistry.HOLLOW_MANGROVE_LOG);
                        entries.accept(ItemRegistry.HOLLOW_CHERRY_LOG);
                        entries.accept(ItemRegistry.HOLLOW_PALE_OAK_LOG);
                        entries.accept(ItemRegistry.HOLLOW_BAMBOO_BLOCK);
                        entries.accept(ItemRegistry.HOLLOW_CRIMSON_STEM);
                        entries.accept(ItemRegistry.HOLLOW_WARPED_STEM);
                        entries.accept(ItemRegistry.HOLLOW_BAOBAB_LOG);

                        entries.accept(ItemRegistry.BOAT_UPGRADE_TEMPLATE);
                        entries.accept(ItemRegistry.BIG_OAK_BOAT);
                        entries.accept(ItemRegistry.BIG_SPRUCE_BOAT);
                        entries.accept(ItemRegistry.BIG_BIRCH_BOAT);
                        entries.accept(ItemRegistry.BIG_JUNGLE_BOAT);
                        entries.accept(ItemRegistry.BIG_ACACIA_BOAT);
                        entries.accept(ItemRegistry.BIG_DARK_OAK_BOAT);
                        entries.accept(ItemRegistry.BIG_MANGROVE_BOAT);
                        entries.accept(ItemRegistry.BIG_CHERRY_BOAT);
                        entries.accept(ItemRegistry.BIG_PALE_OAK_BOAT);
                        entries.accept(ItemRegistry.BIG_BAMBOO_BOAT);
                        entries.accept(ItemRegistry.BIG_BAOBAB_BOAT);

                        entries.accept(ItemRegistry.HUGE_OAK_BOAT);
                        entries.accept(ItemRegistry.HUGE_SPRUCE_BOAT);
                        entries.accept(ItemRegistry.HUGE_BIRCH_BOAT);
                        entries.accept(ItemRegistry.HUGE_JUNGLE_BOAT);
                        entries.accept(ItemRegistry.HUGE_ACACIA_BOAT);
                        entries.accept(ItemRegistry.HUGE_DARK_OAK_BOAT);
                        entries.accept(ItemRegistry.HUGE_MANGROVE_BOAT);
                        entries.accept(ItemRegistry.HUGE_CHERRY_BOAT);
                        entries.accept(ItemRegistry.HUGE_PALE_OAK_BOAT);
                        entries.accept(ItemRegistry.HUGE_BAMBOO_BOAT);
                        entries.accept(ItemRegistry.HUGE_BAOBAB_BOAT);

                        entries.accept(ItemRegistry.SULFUR_LANTERN);
                        entries.accept(ItemRegistry.GOLD_CHAIN);


                    }).build();

    public static final CreativeModeTab NEKOMASFIXEDCOLOURS = FabricCreativeModeTab.builder().title(Component.translatable("itemgroup.nekomasfixedcolours"))
            .icon( () -> new ItemStack(ItemRegistry.AMBER_DYE))
            .displayItems(
                    (_, entries) -> {

                        entries.accept(ItemRegistry.AMBER_WOOL);
                        entries.accept(ItemRegistry.AQUA_WOOL);
                        entries.accept(ItemRegistry.INDIGO_WOOL);
                        entries.accept(ItemRegistry.MAROON_WOOL);
                        entries.accept(ItemRegistry.AMBER_CARPET);
                        entries.accept(ItemRegistry.AQUA_CARPET);
                        entries.accept(ItemRegistry.INDIGO_CARPET);
                        entries.accept(ItemRegistry.MAROON_CARPET);

                        entries.accept(ItemRegistry.AMBER_TERRACOTTA);
                        entries.accept(ItemRegistry.AQUA_TERRACOTTA);
                        entries.accept(ItemRegistry.INDIGO_TERRACOTTA);
                        entries.accept(ItemRegistry.MAROON_TERRACOTTA);

                        entries.accept(ItemRegistry.AMBER_CONCRETE);
                        entries.accept(ItemRegistry.AQUA_CONCRETE);
                        entries.accept(ItemRegistry.INDIGO_CONCRETE);
                        entries.accept(ItemRegistry.MAROON_CONCRETE);
                        entries.accept(ItemRegistry.AMBER_CONCRETE_POWDER);
                        entries.accept(ItemRegistry.AQUA_CONCRETE_POWDER);
                        entries.accept(ItemRegistry.INDIGO_CONCRETE_POWDER);
                        entries.accept(ItemRegistry.MAROON_CONCRETE_POWDER);

                        entries.accept(ItemRegistry.AMBER_GLAZED_TERRACOTTA);
                        entries.accept(ItemRegistry.AQUA_GLAZED_TERRACOTTA);
                        entries.accept(ItemRegistry.INDIGO_GLAZED_TERRACOTTA);
                        entries.accept(ItemRegistry.MAROON_GLAZED_TERRACOTTA);

                        entries.accept(ItemRegistry.AMBER_STAINED_GLASS);
                        entries.accept(ItemRegistry.AQUA_STAINED_GLASS);
                        entries.accept(ItemRegistry.INDIGO_STAINED_GLASS);
                        entries.accept(ItemRegistry.MAROON_STAINED_GLASS);
                        entries.accept(ItemRegistry.AMBER_STAINED_GLASS_PANE);
                        entries.accept(ItemRegistry.AQUA_STAINED_GLASSS_PANE);
                        entries.accept(ItemRegistry.INDIGO_STAINED_GLASSS_PANE);
                        entries.accept(ItemRegistry.MAROON_STAINED_GLASSS_PANE);

                        entries.accept(ItemRegistry.AMBER_SHULKER_BOX);
                        entries.accept(ItemRegistry.AQUA_SHULKER_BOX);
                        entries.accept(ItemRegistry.INDIGO_SHULKER_BOX);
                        entries.accept(ItemRegistry.MAROON_SHULKER_BOX);

                        entries.accept(ItemRegistry.AMBER_BED);
                        entries.accept(ItemRegistry.AQUA_BED);
                        entries.accept(ItemRegistry.INDIGO_BED);
                        entries.accept(ItemRegistry.MAROON_BED);

                        entries.accept(ItemRegistry.AMBER_CANDLE);
                        entries.accept(ItemRegistry.AQUA_CANDLE);
                        entries.accept(ItemRegistry.INDIGO_CANDLE);
                        entries.accept(ItemRegistry.MAROON_CANDLE);

                        entries.accept(ItemRegistry.AMBER_BUNDLE);
                        entries.accept(ItemRegistry.AQUA_BUNDLE);
                        entries.accept(ItemRegistry.INDIGO_BUNDLE);
                        entries.accept(ItemRegistry.MAROON_BUNDLE);

                        entries.accept(ItemRegistry.AMBER_HARNESS);
                        entries.accept(ItemRegistry.AQUA_HARNESS);
                        entries.accept(ItemRegistry.INDIGO_HARNESS);
                        entries.accept(ItemRegistry.MAROON_HARNESS);

                        entries.accept(ItemRegistry.AMBER_DYE);
                        entries.accept(ItemRegistry.AQUA_DYE);
                        entries.accept(ItemRegistry.INDIGO_DYE);
                        entries.accept(ItemRegistry.MAROON_DYE);


                        entries.accept(ItemRegistry.WHITE_DYED_BRUSH);
                        entries.accept(ItemRegistry.LIGHT_GRAY_DYED_BRUSH);
                        entries.accept(ItemRegistry.GRAY_DYED_BRUSH);
                        entries.accept(ItemRegistry.BLACK_DYED_BRUSH);
                        entries.accept(ItemRegistry.BROWN_DYED_BRUSH);
                        entries.accept(ItemRegistry.RED_DYED_BRUSH);
                        entries.accept(ItemRegistry.ORANGE_DYED_BRUSH);
                        entries.accept(ItemRegistry.YELLOW_DYED_BRUSH);
                        entries.accept(ItemRegistry.LIME_DYED_BRUSH);
                        entries.accept(ItemRegistry.GREEN_DYED_BRUSH);
                        entries.accept(ItemRegistry.CYAN_DYED_BRUSH);
                        entries.accept(ItemRegistry.LIGHT_BLUE_DYED_BRUSH);
                        entries.accept(ItemRegistry.BLUE_DYED_BRUSH);
                        entries.accept(ItemRegistry.PURPLE_DYED_BRUSH);
                        entries.accept(ItemRegistry.MAGENTA_DYED_BRUSH);
                        entries.accept(ItemRegistry.PINK_DYED_BRUSH);
                        entries.accept(ItemRegistry.AMBER_DYED_BRUSH);
                        entries.accept(ItemRegistry.AQUA_DYED_BRUSH);
                        entries.accept(ItemRegistry.INDIGO_DYED_BRUSH);
                        entries.accept(ItemRegistry.MAROON_DYED_BRUSH);

                        entries.accept(ItemRegistry.WHITE_BRICKS);
                        entries.accept(ItemRegistry.LIGHT_GRAY_BRICKS);
                        entries.accept(ItemRegistry.GRAY_BRICKS);
                        entries.accept(ItemRegistry.BLACK_BRICKS);
                        entries.accept(ItemRegistry.BROWN_BRICKS);
                        entries.accept(ItemRegistry.RED_BRICKS);
                        entries.accept(ItemRegistry.ORANGE_BRICKS);
                        entries.accept(ItemRegistry.YELLOW_BRICKS);
                        entries.accept(ItemRegistry.LIME_BRICKS);
                        entries.accept(ItemRegistry.GREEN_BRICKS);
                        entries.accept(ItemRegistry.CYAN_BRICKS);
                        entries.accept(ItemRegistry.LIGHT_BLUE_BRICKS);
                        entries.accept(ItemRegistry.BLUE_BRICKS);
                        entries.accept(ItemRegistry.PURPLE_BRICKS);
                        entries.accept(ItemRegistry.MAGENTA_BRICKS);
                        entries.accept(ItemRegistry.PINK_BRICKS);
                        entries.accept(ItemRegistry.AMBER_BRICKS);
                        entries.accept(ItemRegistry.AQUA_BRICKS);
                        entries.accept(ItemRegistry.INDIGO_BRICKS);
                        entries.accept(ItemRegistry.MAROON_BRICKS);

                        entries.accept(ItemRegistry.WHITE_BRICK_SLAB);
                        entries.accept(ItemRegistry.LIGHT_GRAY_BRICK_SLAB);
                        entries.accept(ItemRegistry.GRAY_BRICK_SLAB);
                        entries.accept(ItemRegistry.BLACK_BRICK_SLAB);
                        entries.accept(ItemRegistry.BROWN_BRICK_SLAB);
                        entries.accept(ItemRegistry.RED_BRICK_SLAB);
                        entries.accept(ItemRegistry.ORANGE_BRICK_SLAB);
                        entries.accept(ItemRegistry.YELLOW_BRICK_SLAB);
                        entries.accept(ItemRegistry.LIME_BRICK_SLAB);
                        entries.accept(ItemRegistry.GREEN_BRICK_SLAB);
                        entries.accept(ItemRegistry.CYAN_BRICK_SLAB);
                        entries.accept(ItemRegistry.LIGHT_BLUE_BRICK_SLAB);
                        entries.accept(ItemRegistry.BLUE_BRICK_SLAB);
                        entries.accept(ItemRegistry.PURPLE_BRICK_SLAB);
                        entries.accept(ItemRegistry.MAGENTA_BRICK_SLAB);
                        entries.accept(ItemRegistry.PINK_BRICK_SLAB);
                        entries.accept(ItemRegistry.AMBER_BRICK_SLAB);
                        entries.accept(ItemRegistry.AQUA_BRICK_SLAB);
                        entries.accept(ItemRegistry.INDIGO_BRICK_SLAB);
                        entries.accept(ItemRegistry.MAROON_BRICK_SLAB);

                        entries.accept(ItemRegistry.WHITE_BRICK_STAIRS);
                        entries.accept(ItemRegistry.LIGHT_GRAY_BRICK_STAIRS);
                        entries.accept(ItemRegistry.GRAY_BRICK_STAIRS);
                        entries.accept(ItemRegistry.BLACK_BRICK_STAIRS);
                        entries.accept(ItemRegistry.BROWN_BRICK_STAIRS);
                        entries.accept(ItemRegistry.RED_BRICK_STAIRS);
                        entries.accept(ItemRegistry.ORANGE_BRICK_STAIRS);
                        entries.accept(ItemRegistry.YELLOW_BRICK_STAIRS);
                        entries.accept(ItemRegistry.LIME_BRICK_STAIRS);
                        entries.accept(ItemRegistry.GREEN_BRICK_STAIRS);
                        entries.accept(ItemRegistry.CYAN_BRICK_STAIRS);
                        entries.accept(ItemRegistry.LIGHT_BLUE_BRICK_STAIRS);
                        entries.accept(ItemRegistry.BLUE_BRICK_STAIRS);
                        entries.accept(ItemRegistry.PURPLE_BRICK_STAIRS);
                        entries.accept(ItemRegistry.MAGENTA_BRICK_STAIRS);
                        entries.accept(ItemRegistry.PINK_BRICK_STAIRS);
                        entries.accept(ItemRegistry.AMBER_BRICK_STAIRS);
                        entries.accept(ItemRegistry.AQUA_BRICK_STAIRS);
                        entries.accept(ItemRegistry.INDIGO_BRICK_STAIRS);
                        entries.accept(ItemRegistry.MAROON_BRICK_STAIRS);

                        entries.accept(ItemRegistry.WHITE_BRICK_WALL);
                        entries.accept(ItemRegistry.LIGHT_GRAY_BRICK_WALL);
                        entries.accept(ItemRegistry.GRAY_BRICK_WALL);
                        entries.accept(ItemRegistry.BLACK_BRICK_WALL);
                        entries.accept(ItemRegistry.BROWN_BRICK_WALL);
                        entries.accept(ItemRegistry.RED_BRICK_WALL);
                        entries.accept(ItemRegistry.ORANGE_BRICK_WALL);
                        entries.accept(ItemRegistry.YELLOW_BRICK_WALL);
                        entries.accept(ItemRegistry.LIME_BRICK_WALL);
                        entries.accept(ItemRegistry.GREEN_BRICK_WALL);
                        entries.accept(ItemRegistry.CYAN_BRICK_WALL);
                        entries.accept(ItemRegistry.LIGHT_BLUE_BRICK_WALL);
                        entries.accept(ItemRegistry.BLUE_BRICK_WALL);
                        entries.accept(ItemRegistry.PURPLE_BRICK_WALL);
                        entries.accept(ItemRegistry.MAGENTA_BRICK_WALL);
                        entries.accept(ItemRegistry.PINK_BRICK_WALL);
                        entries.accept(ItemRegistry.AMBER_BRICK_WALL);
                        entries.accept(ItemRegistry.AQUA_BRICK_WALL);
                        entries.accept(ItemRegistry.INDIGO_BRICK_WALL);
                        entries.accept(ItemRegistry.MAROON_BRICK_WALL);
                        

                        entries.accept(ItemRegistry.CLEAR_FROGLIGHT);
                        entries.accept(ItemRegistry.CLOUDY_FROGLIGHT);
                        entries.accept(ItemRegistry.CASCADING_FROGLIGHT);
                        entries.accept(ItemRegistry.CLOUDBURST_FROGLIGHT);
                        entries.accept(ItemRegistry.CHAMOISEE_FROGLIGHT);
                        entries.accept(ItemRegistry.SANGUINE_FROGLIGHT);
                        entries.accept(ItemRegistry.VERMILION_FROGLIGHT);
                        entries.accept(ItemRegistry.MANDARIN_FROGLIGHT);
                        entries.accept(ItemRegistry.LEMON_FROGLIGHT);
                        entries.accept(ItemRegistry.KIWI_FROGLIGHT);
                        entries.accept(ItemRegistry.SEAFOAM_FROGLIGHT);
                        entries.accept(ItemRegistry.TEAL_FROGLIGHT);
                        entries.accept(ItemRegistry.CERULEAN_FROGLIGHT);
                        entries.accept(ItemRegistry.NAVY_FROGLIGHT);
                        entries.accept(ItemRegistry.LAVENDER_FROGLIGHT);
                        entries.accept(ItemRegistry.THULIAN_FROGLIGHT);
                        entries.accept(ItemRegistry.SAKURA_FROGLIGHT);

                        entries.accept(ItemRegistry.WHITE_SPOTTED_WOOL);
                        entries.accept(ItemRegistry.LIGHT_GRAY_SPOTTED_WOOL);
                        entries.accept(ItemRegistry.GRAY_SPOTTED_WOOL);
                        entries.accept(ItemRegistry.BLACK_SPOTTED_WOOL);
                        entries.accept(ItemRegistry.BROWN_SPOTTED_WOOL);
                        entries.accept(ItemRegistry.RED_SPOTTED_WOOL);
                        entries.accept(ItemRegistry.ORANGE_SPOTTED_WOOL);
                        entries.accept(ItemRegistry.YELLOW_SPOTTED_WOOL);
                        entries.accept(ItemRegistry.LIME_SPOTTED_WOOL);
                        entries.accept(ItemRegistry.GREEN_SPOTTED_WOOL);
                        entries.accept(ItemRegistry.CYAN_SPOTTED_WOOL);
                        entries.accept(ItemRegistry.LIGHT_BLUE_SPOTTED_WOOL);
                        entries.accept(ItemRegistry.BLUE_SPOTTED_WOOL);
                        entries.accept(ItemRegistry.PURPLE_SPOTTED_WOOL);
                        entries.accept(ItemRegistry.MAGENTA_SPOTTED_WOOL);
                        entries.accept(ItemRegistry.PINK_SPOTTED_WOOL);
                        entries.accept(ItemRegistry.AMBER_SPOTTED_WOOL);
                        entries.accept(ItemRegistry.AQUA_SPOTTED_WOOL);
                        entries.accept(ItemRegistry.INDIGO_SPOTTED_WOOL);
                        entries.accept(ItemRegistry.MAROON_SPOTTED_WOOL);

                        entries.accept(ItemRegistry.WHITE_SPOTTED_CARPET);
                        entries.accept(ItemRegistry.LIGHT_GRAY_SPOTTED_CARPET);
                        entries.accept(ItemRegistry.GRAY_SPOTTED_CARPET);
                        entries.accept(ItemRegistry.BLACK_SPOTTED_CARPET);
                        entries.accept(ItemRegistry.BROWN_SPOTTED_CARPET);
                        entries.accept(ItemRegistry.RED_SPOTTED_CARPET);
                        entries.accept(ItemRegistry.ORANGE_SPOTTED_CARPET);
                        entries.accept(ItemRegistry.YELLOW_SPOTTED_CARPET);
                        entries.accept(ItemRegistry.LIME_SPOTTED_CARPET);
                        entries.accept(ItemRegistry.GREEN_SPOTTED_CARPET);
                        entries.accept(ItemRegistry.CYAN_SPOTTED_CARPET);
                        entries.accept(ItemRegistry.LIGHT_BLUE_SPOTTED_CARPET);
                        entries.accept(ItemRegistry.BLUE_SPOTTED_CARPET);
                        entries.accept(ItemRegistry.PURPLE_SPOTTED_CARPET);
                        entries.accept(ItemRegistry.MAGENTA_SPOTTED_CARPET);
                        entries.accept(ItemRegistry.PINK_SPOTTED_CARPET);
                        entries.accept(ItemRegistry.AMBER_SPOTTED_CARPET);
                        entries.accept(ItemRegistry.AQUA_SPOTTED_CARPET);
                        entries.accept(ItemRegistry.INDIGO_SPOTTED_CARPET);
                        entries.accept(ItemRegistry.MAROON_SPOTTED_CARPET);
                    }).build();

    public static void registerItemGroup() {
        System.out.println("register ItemGroup");
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, "nekomasfixed", NEKOMASFIXED);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, "nekomasfixedcolours", NEKOMASFIXEDCOLOURS);
    }
}
