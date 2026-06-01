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
                         entries.add(ItemRegistry.BOAT_UPGRADE_TEMPLATE);
                         entries.add(ItemRegistry.BIG_OAK_BOAT);
                         entries.add(ItemRegistry.HUGE_OAK_BOAT);

                        entries.add(ItemRegistry.GLISTERING_MELON);
                        entries.add(ItemRegistry.NAUTILUS_BLOCK);
                        entries.add(ItemRegistry.ZOMBIE_NAUTILUS_BLOCK);
                        entries.add(ItemRegistry.CORAL_NAUTILUS_BLOCK);
                         entries.add(ItemRegistry.KILN);
                         entries.add(ItemRegistry.ROPE_ITEM);
                         entries.add(ItemRegistry.GLOW_TORCH);
                        entries.add(ItemRegistry.TURTLE_CHESTPLATE);
                        entries.add(ItemRegistry.TURTLE_LEGGINGS);
                        entries.add(ItemRegistry.TURTLE_BOOTS);
                        entries.add(ItemRegistry.TARGET_DUMMY);
                        entries.add(ItemRegistry.ENDERMAN_HEAD);

                        entries.add(ItemRegistry.WILD_FIRE_SPAWN_EGG);
                        entries.add(ItemRegistry.SUS_SPIDER_SPAWN_EGG);
                        entries.add(ItemRegistry.NETHER_HEART);
                        entries.add(ItemRegistry.SOULFIRE_TRIDENT);
                        entries.add(ItemRegistry.SOULFIRE_SHIELD);
                         entries.add(ItemRegistry.JEWEL_ARMOR_TRIM_SMITHING_TEMPLATE);

                         entries.add(ItemRegistry.SLINGSHOT);
                        entries.add(ItemRegistry.WOODEN_SICKLE);
                        entries.add(ItemRegistry.STONE_SICKLE);
                        entries.add(ItemRegistry.COPPER_SICKLE);
                        entries.add(ItemRegistry.IRON_SICKLE);
                        entries.add(ItemRegistry.GOLDEN_SICKLE);
                        entries.add(ItemRegistry.DIAMOND_SICKLE);
                        entries.add(ItemRegistry.NETHERITE_SICKLE);

                         entries.add(ItemRegistry.CROWN_SMITHING_TEMPLATE);
                         entries.add(ItemRegistry.COPPER_CROWN);
                         entries.add(ItemRegistry.IRON_CROWN);
                         entries.add(ItemRegistry.GOLDEN_CROWN);
                         entries.add(ItemRegistry.DIAMOND_CROWN);
                         entries.add(ItemRegistry.NETHERITE_CROWN);


						entries.add(ItemRegistry.AMBER_DYE);
                        entries.add(ItemRegistry.AQUA_DYE);
                        entries.add(ItemRegistry.INDIGO_DYE);
                        entries.add(ItemRegistry.CRIMSON_DYE);

                         entries.add(ItemRegistry.AMBER_WOOL);
                         entries.add(ItemRegistry.AQUA_WOOL);
                         entries.add(ItemRegistry.INDIGO_WOOL);
                         entries.add(ItemRegistry.CRIMSON_WOOL);
                         entries.add(ItemRegistry.AMBER_CARPET);
                         entries.add(ItemRegistry.AQUA_CARPET);
                         entries.add(ItemRegistry.INDIGO_CARPET);
                         entries.add(ItemRegistry.CRIMSON_CARPET);

                         entries.add(ItemRegistry.AMBER_CONCRETE);
                         entries.add(ItemRegistry.AQUA_CONCRETE);
                         entries.add(ItemRegistry.INDIGO_CONCRETE);
                         entries.add(ItemRegistry.CRIMSON_CONCRETE);
                         entries.add(ItemRegistry.AMBER_CONCRETE_POWDER);
                         entries.add(ItemRegistry.AQUA_CONCRETE_POWDER);
                         entries.add(ItemRegistry.INDIGO_CONCRETE_POWDER);
                         entries.add(ItemRegistry.CRIMSON_CONCRETE_POWDER);

                        entries.add(ItemRegistry.AMBER_TERRACOTTA);
                        entries.add(ItemRegistry.AQUA_TERRACOTTA);
                        entries.add(ItemRegistry.INDIGO_TERRACOTTA);
                        entries.add(ItemRegistry.CRIMSON_TERRACOTTA);
                        entries.add(ItemRegistry.AMBER_GLAZED_TERRACOTTA);
                        entries.add(ItemRegistry.AQUA_GLAZED_TERRACOTTA);
                        entries.add(ItemRegistry.INDIGO_GLAZED_TERRACOTTA);
                        entries.add(ItemRegistry.CRIMSON_GLAZED_TERRACOTTA);

                         entries.add(ItemRegistry.AMBER_STAINED_GLASS);
                         entries.add(ItemRegistry.AQUA_STAINED_GLASS);
                         entries.add(ItemRegistry.INDIGO_STAINED_GLASS);
                         entries.add(ItemRegistry.CRIMSON_STAINED_GLASS);
                         entries.add(ItemRegistry.AMBER_STAINED_GLASS_PANE);
                         entries.add(ItemRegistry.AQUA_STAINED_GLASSS_PANE);
                         entries.add(ItemRegistry.INDIGO_STAINED_GLASSS_PANE);
                         entries.add(ItemRegistry.CRIMSON_STAINED_GLASSS_PANE);
                         entries.add(ItemRegistry.AMBER_SHULKER_BOX);
                         entries.add(ItemRegistry.AQUA_SHULKER_BOX);
                         entries.add(ItemRegistry.INDIGO_SHULKER_BOX);
                         entries.add(ItemRegistry.CRIMSON_SHULKER_BOX);

                         entries.add(ItemRegistry.AMBER_BUNDLE);
                         entries.add(ItemRegistry.AQUA_BUNDLE);
                         entries.add(ItemRegistry.INDIGO_BUNDLE);
                         entries.add(ItemRegistry.CRIMSON_BUNDLE);

                         entries.add(ItemRegistry.AMBER_BED);
                         entries.add(ItemRegistry.AQUA_BED);
                         entries.add(ItemRegistry.INDIGO_BED);
                         entries.add(ItemRegistry.CRIMSON_BED);

                         entries.add(ItemRegistry.AMBER_CANDLE);
                         entries.add(ItemRegistry.AQUA_CANDLE);
                         entries.add(ItemRegistry.INDIGO_CANDLE);
                         entries.add(ItemRegistry.CRIMSON_CANDLE);

                         entries.add(ItemRegistry.AMBER_HARNESS);
                         entries.add(ItemRegistry.AQUA_HARNESS);
                         entries.add(ItemRegistry.INDIGO_HARNESS);
                         entries.add(ItemRegistry.CRIMSON_HARNESS);

                         entries.add(ItemRegistry.WHITE_BRICKS);
                         entries.add(ItemRegistry.ORANGE_BRICKS);
                         entries.add(ItemRegistry.MAGENTA_BRICKS);
                         entries.add(ItemRegistry.LIGHT_BLUE_BRICKS);
                         entries.add(ItemRegistry.YELLOW_BRICKS);
                         entries.add(ItemRegistry.LIME_BRICKS);
                         entries.add(ItemRegistry.PINK_BRICKS);
                         entries.add(ItemRegistry.GRAY_BRICKS);
                         entries.add(ItemRegistry.LIGHT_GRAY_BRICKS);
                         entries.add(ItemRegistry.CYAN_BRICKS);
                         entries.add(ItemRegistry.PURPLE_BRICKS);
                         entries.add(ItemRegistry.BLUE_BRICKS);
                         entries.add(ItemRegistry.BROWN_BRICKS);
                         entries.add(ItemRegistry.GREEN_BRICKS);
                         entries.add(ItemRegistry.RED_BRICKS);
                         entries.add(ItemRegistry.BLACK_BRICKS);

                         entries.add(ItemRegistry.WHITE_BRICK_SLAB);
                         entries.add(ItemRegistry.ORANGE_BRICK_SLAB);
                         entries.add(ItemRegistry.MAGENTA_BRICK_SLAB);
                         entries.add(ItemRegistry.LIGHT_BLUE_BRICK_SLAB);
                         entries.add(ItemRegistry.YELLOW_BRICK_SLAB);
                         entries.add(ItemRegistry.LIME_BRICK_SLAB);
                         entries.add(ItemRegistry.PINK_BRICK_SLAB);
                         entries.add(ItemRegistry.GRAY_BRICK_SLAB);
                         entries.add(ItemRegistry.LIGHT_GRAY_BRICK_SLAB);
                         entries.add(ItemRegistry.CYAN_BRICK_SLAB);
                         entries.add(ItemRegistry.PURPLE_BRICK_SLAB);
                         entries.add(ItemRegistry.BLUE_BRICK_SLAB);
                         entries.add(ItemRegistry.BROWN_BRICK_SLAB);
                         entries.add(ItemRegistry.GREEN_BRICK_SLAB);
                         entries.add(ItemRegistry.RED_BRICK_SLAB);
                         entries.add(ItemRegistry.BLACK_BRICK_SLAB);

                         entries.add(ItemRegistry.WHITE_BRICK_STAIRS);
                         entries.add(ItemRegistry.ORANGE_BRICK_STAIRS);
                         entries.add(ItemRegistry.MAGENTA_BRICK_STAIRS);
                         entries.add(ItemRegistry.LIGHT_BLUE_BRICK_STAIRS);
                         entries.add(ItemRegistry.YELLOW_BRICK_STAIRS);
                         entries.add(ItemRegistry.LIME_BRICK_STAIRS);
                         entries.add(ItemRegistry.PINK_BRICK_STAIRS);
                         entries.add(ItemRegistry.GRAY_BRICK_STAIRS);
                         entries.add(ItemRegistry.LIGHT_GRAY_BRICK_STAIRS);
                         entries.add(ItemRegistry.CYAN_BRICK_STAIRS);
                         entries.add(ItemRegistry.PURPLE_BRICK_STAIRS);
                         entries.add(ItemRegistry.BLUE_BRICK_STAIRS);
                         entries.add(ItemRegistry.BROWN_BRICK_STAIRS);
                         entries.add(ItemRegistry.GREEN_BRICK_STAIRS);
                         entries.add(ItemRegistry.RED_BRICK_STAIRS);
                         entries.add(ItemRegistry.BLACK_BRICK_STAIRS);

                         entries.add(ItemRegistry.WHITE_BRICK_WALL);
                         entries.add(ItemRegistry.ORANGE_BRICK_WALL);
                         entries.add(ItemRegistry.MAGENTA_BRICK_WALL);
                         entries.add(ItemRegistry.LIGHT_BLUE_BRICK_WALL);
                         entries.add(ItemRegistry.YELLOW_BRICK_WALL);
                         entries.add(ItemRegistry.LIME_BRICK_WALL);
                         entries.add(ItemRegistry.PINK_BRICK_WALL);
                         entries.add(ItemRegistry.GRAY_BRICK_WALL);
                         entries.add(ItemRegistry.LIGHT_GRAY_BRICK_WALL);
                         entries.add(ItemRegistry.CYAN_BRICK_WALL);
                         entries.add(ItemRegistry.PURPLE_BRICK_WALL);
                         entries.add(ItemRegistry.BLUE_BRICK_WALL);
                         entries.add(ItemRegistry.BROWN_BRICK_WALL);
                         entries.add(ItemRegistry.GREEN_BRICK_WALL);
                         entries.add(ItemRegistry.RED_BRICK_WALL);
                         entries.add(ItemRegistry.BLACK_BRICK_WALL);

                         //new things
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
                      
                         entries.add(ItemRegistry.TERMITE_BLOCK);
                         entries.add(ItemRegistry.TERMITE_HIVE);
                         entries.add(ItemRegistry.TERMITE_SPAWN_EGG);

                         entries.add(ItemRegistry.WHITE_DYED_BRUSH);
                         entries.add(ItemRegistry.ORANGE_DYED_BRUSH);
                         entries.add(ItemRegistry.MAGENTA_DYED_BRUSH);
                         entries.add(ItemRegistry.LIGHT_BLUE_DYED_BRUSH);
                         entries.add(ItemRegistry.YELLOW_DYED_BRUSH);
                         entries.add(ItemRegistry.LIME_DYED_BRUSH);
                         entries.add(ItemRegistry.PINK_DYED_BRUSH);
                         entries.add(ItemRegistry.GRAY_DYED_BRUSH);
                         entries.add(ItemRegistry.LIGHT_GRAY_DYED_BRUSH);
                         entries.add(ItemRegistry.CYAN_DYED_BRUSH);
                         entries.add(ItemRegistry.PURPLE_DYED_BRUSH);
                         entries.add(ItemRegistry.BLUE_DYED_BRUSH);
                         entries.add(ItemRegistry.BROWN_DYED_BRUSH);
                         entries.add(ItemRegistry.GREEN_DYED_BRUSH);
                         entries.add(ItemRegistry.RED_DYED_BRUSH);
                         entries.add(ItemRegistry.BLACK_DYED_BRUSH);

                         entries.add(ItemRegistry.REDSTONE_STRIKER);
                         entries.add(ItemRegistry.GEYSER);
                         entries.add(ItemRegistry.MOOBLOOM_SPAWN_EGG);

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

                         entries.add(ItemRegistry.WHITE_FROGLIGHT);
                         entries.add(ItemRegistry.LIGHT_GRAY_FROGLIGHT);
                         entries.add(ItemRegistry.GRAY_FROGLIGHT);
                         entries.add(ItemRegistry.BLACK_FROGLIGHT);
                         entries.add(ItemRegistry.BROWN_FROGLIGHT);
                         entries.add(ItemRegistry.RED_FROGLIGHT);
                         entries.add(ItemRegistry.ORANGE_FROGLIGHT);
                         entries.add(ItemRegistry.GREEN_FROGLIGHT);
                         entries.add(ItemRegistry.CYAN_FROGLIGHT);
                         entries.add(ItemRegistry.LIGHT_BLUE_FROGLIGHT);
                         entries.add(ItemRegistry.BLUE_FROGLIGHT);
                         entries.add(ItemRegistry.PURPLE_FROGLIGHT);
                         entries.add(ItemRegistry.PINK_FROGLIGHT);
                         entries.add(ItemRegistry.AMBER_FROGLIGHT);
                         entries.add(ItemRegistry.AQUA_FROGLIGHT);
                         entries.add(ItemRegistry.INDIGO_FROGLIGHT);
                         entries.add(ItemRegistry.CRIMSON_FROGLIGHT);

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


                    }).build();


    public static void registerItemGroup() {
        System.out.println("register ItemGroup");
        Registry.register(Registries.ITEM_GROUP, "nekomasfixed", NEKOMASFIXED);
    }
}
