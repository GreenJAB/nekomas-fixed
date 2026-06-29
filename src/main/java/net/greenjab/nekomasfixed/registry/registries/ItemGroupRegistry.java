package net.greenjab.nekomasfixed.registry.registries;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class ItemGroupRegistry {

    public static final ItemGroup NEKOMASFIXED = FabricItemGroup.builder().displayName(Text.translatable("itemgroup.nekomasfixed"))
            .icon( () -> new ItemStack(ItemRegistry.CLAM))
            .entries(
                    (displayContext, entries) -> {
                        entries.add(ItemRegistry.CLAM);
                        entries.add(ItemRegistry.CLAM_BLUE);
                        entries.add(ItemRegistry.CLAM_PINK);
                        entries.add(ItemRegistry.CLAM_PURPLE);
                        entries.add(ItemRegistry.PEARL);
                        entries.add(ItemRegistry.PEARL_BLOCK);

                        entries.add(ItemRegistry.NAUTILUS_BLOCK);
                        entries.add(ItemRegistry.ZOMBIE_NAUTILUS_BLOCK);
                        entries.add(ItemRegistry.CORAL_NAUTILUS_BLOCK);
                        entries.add(ItemRegistry.GLISTERING_MELON);
                        entries.add(ItemRegistry.KILN);
                        entries.add(ItemRegistry.GEYSER);
                        entries.add(ItemRegistry.PYROTECHNICS_TABLE);
                        entries.add(ItemRegistry.ENDERMAN_HEAD);
                        entries.add(ItemRegistry.REDSTONE_STRIKER);
                        entries.add(ItemRegistry.TARGET_DUMMY);
                        entries.add(ItemRegistry.GLOW_TORCH);
                        entries.add(ItemRegistry.TURTLE_CHESTPLATE);
                        entries.add(ItemRegistry.TURTLE_LEGGINGS);
                        entries.add(ItemRegistry.TURTLE_BOOTS);

                        entries.add(ItemRegistry.MOOBLOOM_SPAWN_EGG);
                        entries.add(ItemRegistry.DERELICT_SPAWN_EGG);
                        entries.add(ItemRegistry.ANCHOR);
                        entries.add(ItemRegistry.RIME_SPAWN_EGG);
                        entries.add(ItemRegistry.DRENCHED_SPAWN_EGG);
                        entries.add(ItemRegistry.SUSPICIOUS_SPIDER_SPAWN_EGG);
                        entries.add(ItemRegistry.WILDFIRE_SPAWN_EGG);
                        entries.add(ItemRegistry.NETHER_HEART);
                        entries.add(ItemRegistry.WILDFIRE_TRIDENT);
                        entries.add(ItemRegistry.WILDFIRE_SHIELD);
                        entries.add(ItemRegistry.JEWEL_ARMOR_TRIM_SMITHING_TEMPLATE);

                        entries.add(ItemRegistry.CROWN_SMITHING_TEMPLATE);
                        entries.add(ItemRegistry.COPPER_CROWN);
                        entries.add(ItemRegistry.IRON_CROWN);
                        entries.add(ItemRegistry.GOLDEN_CROWN);
                        entries.add(ItemRegistry.DIAMOND_CROWN);
                        entries.add(ItemRegistry.NETHERITE_CROWN);


                        entries.add(ItemRegistry.SLINGSHOT);
                        entries.add(ItemRegistry.WOODEN_SICKLE);
                        entries.add(ItemRegistry.STONE_SICKLE);
                        entries.add(ItemRegistry.COPPER_SICKLE);
                        entries.add(ItemRegistry.IRON_SICKLE);
                        entries.add(ItemRegistry.GOLDEN_SICKLE);
                        entries.add(ItemRegistry.DIAMOND_SICKLE);
                        entries.add(ItemRegistry.NETHERITE_SICKLE);

                        entries.add(ItemRegistry.SWEETBERRY_CAKE);
                        entries.add(ItemRegistry.PAN_CAKE);
                        entries.add(ItemRegistry.GLOWBERRY_CAKE);
                        entries.add(ItemRegistry.APPLE_CAKE);
                        entries.add(ItemRegistry.VANILLA_CAKE);
                        entries.add(ItemRegistry.COOKIE_CAKE);
                        entries.add(ItemRegistry.CHOCOLATE_CAKE);
                        entries.add(ItemRegistry.BEETROOT_CAKE);

                        entries.add(ItemRegistry.TERMITE_SPAWN_EGG);
                        entries.add(ItemRegistry.TERMITE_HIVE);
                        entries.add(ItemRegistry.TERMITE_BLOCK);
                        entries.add(ItemRegistry.HOLLOW_OAK_LOG);
                        entries.add(ItemRegistry.HOLLOW_DARK_OAK_LOG);
                        entries.add(ItemRegistry.HOLLOW_CRIMSON_LOG);
                        entries.add(ItemRegistry.HOLLOW_ACACIA_LOG);
                        entries.add(ItemRegistry.HOLLOW_BIRCH_LOG);
                        entries.add(ItemRegistry.HOLLOW_JUNGLE_LOG);
                        entries.add(ItemRegistry.HOLLOW_SPRUCE_LOG);
                        entries.add(ItemRegistry.HOLLOW_WARPED_LOG);
                        entries.add(ItemRegistry.HOLLOW_PALE_OAK_LOG);
                        entries.add(ItemRegistry.HOLLOW_CHERRY_LOG);
                        entries.add(ItemRegistry.HOLLOW_BAOBAB_LOG);

                        entries.add(ItemRegistry.ROPE_ITEM);
                        entries.add(ItemRegistry.BAOBAB_SEEDS);
                        entries.add(ItemRegistry.BAOBAB_FRUIT);
                        entries.add(BlockRegistry.BAOBAB_LEAVES);
                        entries.add(BlockRegistry.BAOBAB_SAPLING);
                        entries.add(BlockRegistry.BAOBAB_PLANKS);
                        entries.add(BlockRegistry.BAOBAB_LOG);
                        entries.add(BlockRegistry.BAOBAB_WOOD);
                        entries.add(ItemRegistry.BAOBAB_SIGN);
                        entries.add(ItemRegistry.BAOBAB_HANGING_SIGN);
                        entries.add(BlockRegistry.BAOBAB_PRESSURE_PLATE);
                        entries.add(BlockRegistry.BAOBAB_TRAPDOOR);
                        entries.add(BlockRegistry.BAOBAB_BUTTON);
                        entries.add(BlockRegistry.BAOBAB_STAIRS);
                        entries.add(BlockRegistry.BAOBAB_SLAB);
                        entries.add(BlockRegistry.BAOBAB_FENCE_GATE);
                        entries.add(BlockRegistry.BAOBAB_FENCE);
                        entries.add(BlockRegistry.BAOBAB_DOOR);
                        entries.add(ItemRegistry.BAOBAB_BOAT);
                        entries.add(ItemRegistry.BAOBAB_CHEST_BOAT);
                        entries.add(ItemRegistry.BAOBAB_SHELF);

                        entries.add(ItemRegistry.BOAT_UPGRADE_TEMPLATE);
                        entries.add(ItemRegistry.BIG_OAK_BOAT);
                        entries.add(ItemRegistry.HUGE_OAK_BOAT);
                        entries.add(ItemRegistry.BIG_ACACIA_BOAT);
                        entries.add(ItemRegistry.HUGE_ACACIA_BOAT);
                        entries.add(ItemRegistry.BIG_BAMBOO_BOAT);
                        entries.add(ItemRegistry.HUGE_BAMBOO_BOAT);
                        entries.add(ItemRegistry.BIG_BIRCH_BOAT);
                        entries.add(ItemRegistry.HUGE_BIRCH_BOAT);
                        entries.add(ItemRegistry.BIG_CHERRY_BOAT);
                        entries.add(ItemRegistry.HUGE_CHERRY_BOAT);
                        entries.add(ItemRegistry.BIG_DARK_OAK_BOAT);
                        entries.add(ItemRegistry.HUGE_DARK_OAK_BOAT);
                        entries.add(ItemRegistry.BIG_JUNGLE_BOAT);
                        entries.add(ItemRegistry.HUGE_JUNGLE_BOAT);
                        entries.add(ItemRegistry.BIG_MANGROVE_BOAT);
                        entries.add(ItemRegistry.HUGE_MANGROVE_BOAT);
                        entries.add(ItemRegistry.BIG_PALE_OAK_BOAT);
                        entries.add(ItemRegistry.HUGE_PALE_OAK_BOAT);
                        entries.add(ItemRegistry.BIG_SPRUCE_BOAT);
                        entries.add(ItemRegistry.HUGE_SPRUCE_BOAT);
                        entries.add(ItemRegistry.BIG_BAOBAB_BOAT);
                        entries.add(ItemRegistry.HUGE_BAOBAB_BOAT);

                    }).build();

    public static final ItemGroup NEKOMASFIXEDCOLOURS = FabricItemGroup.builder().displayName(Text.translatable("itemgroup.nekomasfixedcolours"))
            .icon( () -> new ItemStack(ItemRegistry.AMBER_DYE))
            .entries(
                    (displayContext, entries) -> {

                        entries.add(ItemRegistry.AMBER_WOOL);
                        entries.add(ItemRegistry.AQUA_WOOL);
                        entries.add(ItemRegistry.INDIGO_WOOL);
                        entries.add(ItemRegistry.MAROON_WOOL);
                        entries.add(ItemRegistry.AMBER_CARPET);
                        entries.add(ItemRegistry.AQUA_CARPET);
                        entries.add(ItemRegistry.INDIGO_CARPET);
                        entries.add(ItemRegistry.MAROON_CARPET);

                        entries.add(ItemRegistry.AMBER_TERRACOTTA);
                        entries.add(ItemRegistry.AQUA_TERRACOTTA);
                        entries.add(ItemRegistry.INDIGO_TERRACOTTA);
                        entries.add(ItemRegistry.MAROON_TERRACOTTA);

                        entries.add(ItemRegistry.AMBER_CONCRETE);
                        entries.add(ItemRegistry.AQUA_CONCRETE);
                        entries.add(ItemRegistry.INDIGO_CONCRETE);
                        entries.add(ItemRegistry.MAROON_CONCRETE);
                        entries.add(ItemRegistry.AMBER_CONCRETE_POWDER);
                        entries.add(ItemRegistry.AQUA_CONCRETE_POWDER);
                        entries.add(ItemRegistry.INDIGO_CONCRETE_POWDER);
                        entries.add(ItemRegistry.MAROON_CONCRETE_POWDER);

                        entries.add(ItemRegistry.AMBER_GLAZED_TERRACOTTA);
                        entries.add(ItemRegistry.AQUA_GLAZED_TERRACOTTA);
                        entries.add(ItemRegistry.INDIGO_GLAZED_TERRACOTTA);
                        entries.add(ItemRegistry.MAROON_GLAZED_TERRACOTTA);

                        entries.add(ItemRegistry.AMBER_STAINED_GLASS);
                        entries.add(ItemRegistry.AQUA_STAINED_GLASS);
                        entries.add(ItemRegistry.INDIGO_STAINED_GLASS);
                        entries.add(ItemRegistry.MAROON_STAINED_GLASS);
                        entries.add(ItemRegistry.AMBER_STAINED_GLASS_PANE);
                        entries.add(ItemRegistry.AQUA_STAINED_GLASSS_PANE);
                        entries.add(ItemRegistry.INDIGO_STAINED_GLASSS_PANE);
                        entries.add(ItemRegistry.MAROON_STAINED_GLASSS_PANE);

                        entries.add(ItemRegistry.AMBER_SHULKER_BOX);
                        entries.add(ItemRegistry.AQUA_SHULKER_BOX);
                        entries.add(ItemRegistry.INDIGO_SHULKER_BOX);
                        entries.add(ItemRegistry.MAROON_SHULKER_BOX);

                        entries.add(ItemRegistry.AMBER_BED);
                        entries.add(ItemRegistry.AQUA_BED);
                        entries.add(ItemRegistry.INDIGO_BED);
                        entries.add(ItemRegistry.MAROON_BED);

                        entries.add(ItemRegistry.AMBER_CANDLE);
                        entries.add(ItemRegistry.AQUA_CANDLE);
                        entries.add(ItemRegistry.INDIGO_CANDLE);
                        entries.add(ItemRegistry.MAROON_CANDLE);

                        entries.add(ItemRegistry.AMBER_BUNDLE);
                        entries.add(ItemRegistry.AQUA_BUNDLE);
                        entries.add(ItemRegistry.INDIGO_BUNDLE);
                        entries.add(ItemRegistry.MAROON_BUNDLE);

                        entries.add(ItemRegistry.AMBER_HARNESS);
                        entries.add(ItemRegistry.AQUA_HARNESS);
                        entries.add(ItemRegistry.INDIGO_HARNESS);
                        entries.add(ItemRegistry.MAROON_HARNESS);

                        entries.add(ItemRegistry.AMBER_DYE);
                        entries.add(ItemRegistry.AQUA_DYE);
                        entries.add(ItemRegistry.INDIGO_DYE);
                        entries.add(ItemRegistry.MAROON_DYE);


                        entries.add(ItemRegistry.WHITE_DYED_BRUSH);
                        entries.add(ItemRegistry.LIGHT_GRAY_DYED_BRUSH);
                        entries.add(ItemRegistry.GRAY_DYED_BRUSH);
                        entries.add(ItemRegistry.BLACK_DYED_BRUSH);
                        entries.add(ItemRegistry.BROWN_DYED_BRUSH);
                        entries.add(ItemRegistry.RED_DYED_BRUSH);
                        entries.add(ItemRegistry.ORANGE_DYED_BRUSH);
                        entries.add(ItemRegistry.YELLOW_DYED_BRUSH);
                        entries.add(ItemRegistry.LIME_DYED_BRUSH);
                        entries.add(ItemRegistry.GREEN_DYED_BRUSH);
                        entries.add(ItemRegistry.CYAN_DYED_BRUSH);
                        entries.add(ItemRegistry.LIGHT_BLUE_DYED_BRUSH);
                        entries.add(ItemRegistry.BLUE_DYED_BRUSH);
                        entries.add(ItemRegistry.PURPLE_DYED_BRUSH);
                        entries.add(ItemRegistry.MAGENTA_DYED_BRUSH);
                        entries.add(ItemRegistry.PINK_DYED_BRUSH);
                        entries.add(ItemRegistry.AMBER_DYED_BRUSH);
                        entries.add(ItemRegistry.AQUA_DYED_BRUSH);
                        entries.add(ItemRegistry.INDIGO_DYED_BRUSH);
                        entries.add(ItemRegistry.MAROON_DYED_BRUSH);

                        entries.add(ItemRegistry.WHITE_BRICKS);
                        entries.add(ItemRegistry.LIGHT_GRAY_BRICKS);
                        entries.add(ItemRegistry.GRAY_BRICKS);
                        entries.add(ItemRegistry.BLACK_BRICKS);
                        entries.add(ItemRegistry.BROWN_BRICKS);
                        entries.add(ItemRegistry.RED_BRICKS);
                        entries.add(ItemRegistry.ORANGE_BRICKS);
                        entries.add(ItemRegistry.YELLOW_BRICKS);
                        entries.add(ItemRegistry.LIME_BRICKS);
                        entries.add(ItemRegistry.GREEN_BRICKS);
                        entries.add(ItemRegistry.CYAN_BRICKS);
                        entries.add(ItemRegistry.LIGHT_BLUE_BRICKS);
                        entries.add(ItemRegistry.BLUE_BRICKS);
                        entries.add(ItemRegistry.PURPLE_BRICKS);
                        entries.add(ItemRegistry.MAGENTA_BRICKS);
                        entries.add(ItemRegistry.PINK_BRICKS);
                        entries.add(ItemRegistry.AMBER_BRICKS);
                        entries.add(ItemRegistry.AQUA_BRICKS);
                        entries.add(ItemRegistry.INDIGO_BRICKS);
                        entries.add(ItemRegistry.MAROON_BRICKS);

                        entries.add(ItemRegistry.WHITE_BRICK_SLAB);
                        entries.add(ItemRegistry.LIGHT_GRAY_BRICK_SLAB);
                        entries.add(ItemRegistry.GRAY_BRICK_SLAB);
                        entries.add(ItemRegistry.BLACK_BRICK_SLAB);
                        entries.add(ItemRegistry.BROWN_BRICK_SLAB);
                        entries.add(ItemRegistry.RED_BRICK_SLAB);
                        entries.add(ItemRegistry.ORANGE_BRICK_SLAB);
                        entries.add(ItemRegistry.YELLOW_BRICK_SLAB);
                        entries.add(ItemRegistry.LIME_BRICK_SLAB);
                        entries.add(ItemRegistry.GREEN_BRICK_SLAB);
                        entries.add(ItemRegistry.CYAN_BRICK_SLAB);
                        entries.add(ItemRegistry.LIGHT_BLUE_BRICK_SLAB);
                        entries.add(ItemRegistry.BLUE_BRICK_SLAB);
                        entries.add(ItemRegistry.PURPLE_BRICK_SLAB);
                        entries.add(ItemRegistry.MAGENTA_BRICK_SLAB);
                        entries.add(ItemRegistry.PINK_BRICK_SLAB);
                        entries.add(ItemRegistry.AMBER_BRICK_SLAB);
                        entries.add(ItemRegistry.AQUA_BRICK_SLAB);
                        entries.add(ItemRegistry.INDIGO_BRICK_SLAB);
                        entries.add(ItemRegistry.MAROON_BRICK_SLAB);

                        entries.add(ItemRegistry.WHITE_BRICK_STAIRS);
                        entries.add(ItemRegistry.LIGHT_GRAY_BRICK_STAIRS);
                        entries.add(ItemRegistry.GRAY_BRICK_STAIRS);
                        entries.add(ItemRegistry.BLACK_BRICK_STAIRS);
                        entries.add(ItemRegistry.BROWN_BRICK_STAIRS);
                        entries.add(ItemRegistry.RED_BRICK_STAIRS);
                        entries.add(ItemRegistry.ORANGE_BRICK_STAIRS);
                        entries.add(ItemRegistry.YELLOW_BRICK_STAIRS);
                        entries.add(ItemRegistry.LIME_BRICK_STAIRS);
                        entries.add(ItemRegistry.GREEN_BRICK_STAIRS);
                        entries.add(ItemRegistry.CYAN_BRICK_STAIRS);
                        entries.add(ItemRegistry.LIGHT_BLUE_BRICK_STAIRS);
                        entries.add(ItemRegistry.BLUE_BRICK_STAIRS);
                        entries.add(ItemRegistry.PURPLE_BRICK_STAIRS);
                        entries.add(ItemRegistry.MAGENTA_BRICK_STAIRS);
                        entries.add(ItemRegistry.PINK_BRICK_STAIRS);
                        entries.add(ItemRegistry.AMBER_BRICK_STAIRS);
                        entries.add(ItemRegistry.AQUA_BRICK_STAIRS);
                        entries.add(ItemRegistry.INDIGO_BRICK_STAIRS);
                        entries.add(ItemRegistry.MAROON_BRICK_STAIRS);

                        entries.add(ItemRegistry.WHITE_BRICK_WALL);
                        entries.add(ItemRegistry.LIGHT_GRAY_BRICK_WALL);
                        entries.add(ItemRegistry.GRAY_BRICK_WALL);
                        entries.add(ItemRegistry.BLACK_BRICK_WALL);
                        entries.add(ItemRegistry.BROWN_BRICK_WALL);
                        entries.add(ItemRegistry.RED_BRICK_WALL);
                        entries.add(ItemRegistry.ORANGE_BRICK_WALL);
                        entries.add(ItemRegistry.YELLOW_BRICK_WALL);
                        entries.add(ItemRegistry.LIME_BRICK_WALL);
                        entries.add(ItemRegistry.GREEN_BRICK_WALL);
                        entries.add(ItemRegistry.CYAN_BRICK_WALL);
                        entries.add(ItemRegistry.LIGHT_BLUE_BRICK_WALL);
                        entries.add(ItemRegistry.BLUE_BRICK_WALL);
                        entries.add(ItemRegistry.PURPLE_BRICK_WALL);
                        entries.add(ItemRegistry.MAGENTA_BRICK_WALL);
                        entries.add(ItemRegistry.PINK_BRICK_WALL);
                        entries.add(ItemRegistry.AMBER_BRICK_WALL);
                        entries.add(ItemRegistry.AQUA_BRICK_WALL);
                        entries.add(ItemRegistry.INDIGO_BRICK_WALL);
                        entries.add(ItemRegistry.MAROON_BRICK_WALL);
                        

                        entries.add(ItemRegistry.CLEAR_FROGLIGHT);
                        entries.add(ItemRegistry.CLOUDY_FROGLIGHT);
                        entries.add(ItemRegistry.CASCADING_FROGLIGHT);
                        entries.add(ItemRegistry.CLOUDBURST_FROGLIGHT);
                        entries.add(ItemRegistry.CHAMOISEE_FROGLIGHT);
                        entries.add(ItemRegistry.SANGUINE_FROGLIGHT);
                        entries.add(ItemRegistry.VERMILION_FROGLIGHT);
                        entries.add(ItemRegistry.MANDARIN_FROGLIGHT);
                        entries.add(ItemRegistry.LEMON_FROGLIGHT);
                        entries.add(ItemRegistry.KIWI_FROGLIGHT);
                        entries.add(ItemRegistry.SEAFOAM_FROGLIGHT);
                        entries.add(ItemRegistry.TEAL_FROGLIGHT);
                        entries.add(ItemRegistry.CERULEAN_FROGLIGHT);
                        entries.add(ItemRegistry.NAVY_FROGLIGHT);
                        entries.add(ItemRegistry.LAVENDER_FROGLIGHT);
                        entries.add(ItemRegistry.THULIAN_FROGLIGHT);
                        entries.add(ItemRegistry.SAKURA_FROGLIGHT);

                        entries.add(ItemRegistry.WHITE_SPOTTED_WOOL);
                        entries.add(ItemRegistry.LIGHT_GRAY_SPOTTED_WOOL);
                        entries.add(ItemRegistry.GRAY_SPOTTED_WOOL);
                        entries.add(ItemRegistry.BLACK_SPOTTED_WOOL);
                        entries.add(ItemRegistry.BROWN_SPOTTED_WOOL);
                        entries.add(ItemRegistry.RED_SPOTTED_WOOL);
                        entries.add(ItemRegistry.ORANGE_SPOTTED_WOOL);
                        entries.add(ItemRegistry.YELLOW_SPOTTED_WOOL);
                        entries.add(ItemRegistry.LIME_SPOTTED_WOOL);
                        entries.add(ItemRegistry.GREEN_SPOTTED_WOOL);
                        entries.add(ItemRegistry.CYAN_SPOTTED_WOOL);
                        entries.add(ItemRegistry.LIGHT_BLUE_SPOTTED_WOOL);
                        entries.add(ItemRegistry.BLUE_SPOTTED_WOOL);
                        entries.add(ItemRegistry.PURPLE_SPOTTED_WOOL);
                        entries.add(ItemRegistry.MAGENTA_SPOTTED_WOOL);
                        entries.add(ItemRegistry.PINK_SPOTTED_WOOL);
                        entries.add(ItemRegistry.AMBER_SPOTTED_WOOL);
                        entries.add(ItemRegistry.AQUA_SPOTTED_WOOL);
                        entries.add(ItemRegistry.INDIGO_SPOTTED_WOOL);
                        entries.add(ItemRegistry.MAROON_SPOTTED_WOOL);

                        entries.add(ItemRegistry.WHITE_SPOTTED_CARPET);
                        entries.add(ItemRegistry.LIGHT_GRAY_SPOTTED_CARPET);
                        entries.add(ItemRegistry.GRAY_SPOTTED_CARPET);
                        entries.add(ItemRegistry.BLACK_SPOTTED_CARPET);
                        entries.add(ItemRegistry.BROWN_SPOTTED_CARPET);
                        entries.add(ItemRegistry.RED_SPOTTED_CARPET);
                        entries.add(ItemRegistry.ORANGE_SPOTTED_CARPET);
                        entries.add(ItemRegistry.YELLOW_SPOTTED_CARPET);
                        entries.add(ItemRegistry.LIME_SPOTTED_CARPET);
                        entries.add(ItemRegistry.GREEN_SPOTTED_CARPET);
                        entries.add(ItemRegistry.CYAN_SPOTTED_CARPET);
                        entries.add(ItemRegistry.LIGHT_BLUE_SPOTTED_CARPET);
                        entries.add(ItemRegistry.BLUE_SPOTTED_CARPET);
                        entries.add(ItemRegistry.PURPLE_SPOTTED_CARPET);
                        entries.add(ItemRegistry.MAGENTA_SPOTTED_CARPET);
                        entries.add(ItemRegistry.PINK_SPOTTED_CARPET);
                        entries.add(ItemRegistry.AMBER_SPOTTED_CARPET);
                        entries.add(ItemRegistry.AQUA_SPOTTED_CARPET);
                        entries.add(ItemRegistry.INDIGO_SPOTTED_CARPET);
                        entries.add(ItemRegistry.MAROON_SPOTTED_CARPET);
                    }).build();

    public static void registerItemGroup() {
        System.out.println("register ItemGroup");
        Registry.register(Registries.ITEM_GROUP, "nekomasfixed", NEKOMASFIXED);
        Registry.register(Registries.ITEM_GROUP, "nekomasfixedcolours", NEKOMASFIXEDCOLOURS);
    }
}
