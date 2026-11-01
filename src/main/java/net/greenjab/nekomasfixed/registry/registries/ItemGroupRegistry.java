package net.greenjab.nekomasfixed.registry.registries;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.greenjab.nekomasfixed.util.AllDyes;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.greenjab.nekomasfixed.util.ItemDyeMap;
import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Map;

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
                        entries.add(ItemRegistry.GEYSER);
                        entries.add(ItemRegistry.KILN);
                        entries.add(ItemRegistry.PYROTECHNICS_TABLE);
                        entries.add(ItemRegistry.ENDERMAN_HEAD);
                        entries.add(ItemRegistry.REDSTONE_STRIKER);
                        entries.add(ItemRegistry.GLOW_TORCH);
                        entries.add(ItemRegistry.CLEAR_ITEM_FRAME);
                        entries.add(ItemRegistry.TARGET_DUMMY);
                        entries.add(ItemRegistry.TURTLE_CHESTPLATE);
                        entries.add(ItemRegistry.TURTLE_LEGGINGS);
                        entries.add(ItemRegistry.TURTLE_BOOTS);

                        entries.add(ItemRegistry.MOOBLOOM_SPAWN_EGG);
                        entries.add(ItemRegistry.DRENCHED_SPAWN_EGG);
                        entries.add(ItemRegistry.RIME_SPAWN_EGG);
                        entries.add(ItemRegistry.DERELICT_SPAWN_EGG);
                        entries.add(ItemRegistry.ANCHOR);
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

                        entries.add(BlockRegistry.BAOBAB_LOG);
                        entries.add(BlockRegistry.BAOBAB_WOOD);
                        entries.add(BlockRegistry.STRIPPED_BAOBAB_LOG);
                        entries.add(BlockRegistry.STRIPPED_BAOBAB_WOOD);
                        entries.add(BlockRegistry.BAOBAB_PLANKS);
                        entries.add(BlockRegistry.BAOBAB_STAIRS);
                        entries.add(BlockRegistry.BAOBAB_SLAB);
                        entries.add(BlockRegistry.BAOBAB_FENCE);
                        entries.add(BlockRegistry.BAOBAB_FENCE_GATE);
                        entries.add(BlockRegistry.BAOBAB_DOOR);
                        entries.add(BlockRegistry.BAOBAB_TRAPDOOR);
                        entries.add(BlockRegistry.BAOBAB_PRESSURE_PLATE);
                        entries.add(BlockRegistry.BAOBAB_BUTTON);
                        entries.add(BlockRegistry.BAOBAB_LEAVES);
                        entries.add(BlockRegistry.BAOBAB_SAPLING);
                        entries.add(ItemRegistry.BAOBAB_SEEDS);
                        entries.add(ItemRegistry.BAOBAB_FRUIT);
                        entries.add(ItemRegistry.ROPE);
                        entries.add(ItemRegistry.BAOBAB_SHELF);
                        entries.add(ItemRegistry.BAOBAB_SIGN);
                        entries.add(ItemRegistry.BAOBAB_HANGING_SIGN);
                        entries.add(ItemRegistry.BAOBAB_BOAT);
                        entries.add(ItemRegistry.BAOBAB_CHEST_BOAT);

                        entries.add(ItemRegistry.TERMITE_SPAWN_EGG);
                        entries.add(ItemRegistry.TERMITE_HIVE);
                        entries.add(ItemRegistry.TERMITE_BLOCK);
                        entries.add(ItemRegistry.HOLLOW_OAK_LOG);
                        entries.add(ItemRegistry.HOLLOW_SPRUCE_LOG);
                        entries.add(ItemRegistry.HOLLOW_BIRCH_LOG);
                        entries.add(ItemRegistry.HOLLOW_JUNGLE_LOG);
                        entries.add(ItemRegistry.HOLLOW_ACACIA_LOG);
                        entries.add(ItemRegistry.HOLLOW_DARK_OAK_LOG);
                        entries.add(ItemRegistry.HOLLOW_MANGROVE_LOG);
                        entries.add(ItemRegistry.HOLLOW_CHERRY_LOG);
                        entries.add(ItemRegistry.HOLLOW_PALE_OAK_LOG);
                        entries.add(ItemRegistry.HOLLOW_BAMBOO_BLOCK);
                        entries.add(ItemRegistry.HOLLOW_CRIMSON_STEM);
                        entries.add(ItemRegistry.HOLLOW_WARPED_STEM);
                        entries.add(ItemRegistry.HOLLOW_BAOBAB_LOG);

                        entries.add(ItemRegistry.BOAT_UPGRADE_TEMPLATE);
                        entries.add(ItemRegistry.BIG_OAK_BOAT);
                        entries.add(ItemRegistry.BIG_SPRUCE_BOAT);
                        entries.add(ItemRegistry.BIG_BIRCH_BOAT);
                        entries.add(ItemRegistry.BIG_JUNGLE_BOAT);
                        entries.add(ItemRegistry.BIG_ACACIA_BOAT);
                        entries.add(ItemRegistry.BIG_DARK_OAK_BOAT);
                        entries.add(ItemRegistry.BIG_MANGROVE_BOAT);
                        entries.add(ItemRegistry.BIG_CHERRY_BOAT);
                        entries.add(ItemRegistry.BIG_PALE_OAK_BOAT);
                        entries.add(ItemRegistry.BIG_BAMBOO_BOAT);
                        entries.add(ItemRegistry.BIG_BAOBAB_BOAT);

                        entries.add(ItemRegistry.HUGE_OAK_BOAT);
                        entries.add(ItemRegistry.HUGE_SPRUCE_BOAT);
                        entries.add(ItemRegistry.HUGE_BIRCH_BOAT);
                        entries.add(ItemRegistry.HUGE_JUNGLE_BOAT);
                        entries.add(ItemRegistry.HUGE_ACACIA_BOAT);
                        entries.add(ItemRegistry.HUGE_DARK_OAK_BOAT);
                        entries.add(ItemRegistry.HUGE_MANGROVE_BOAT);
                        entries.add(ItemRegistry.HUGE_CHERRY_BOAT);
                        entries.add(ItemRegistry.HUGE_PALE_OAK_BOAT);
                        entries.add(ItemRegistry.HUGE_BAMBOO_BOAT);
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

                        entries.addAll(orderDyedItems(ItemDyeMap.BRUSH));
                        entries.addAll(orderDyedItems(BlockDyeMap.BRICKS));
                        entries.addAll(orderDyedItems(BlockDyeMap.BRICK_SLAB));
                        entries.addAll(orderDyedItems(BlockDyeMap.BRICK_STAIRS));
                        entries.addAll(orderDyedItems(BlockDyeMap.BRICK_WALL));

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

                        entries.addAll(orderDyedItems(BlockDyeMap.SPOTTED_WOOL));
                        entries.addAll(orderDyedItems(BlockDyeMap.SPOTTED_CARPET));
                        entries.addAll(orderDyedItems(BlockDyeMap.GLOWING_WOOL));
                        entries.addAll(orderDyedItems(BlockDyeMap.GLOWING_CARPET));
                        //TODO uncomment when DyeColor is extended
//                        entries.addAll(orderDyedItems(BlockDyeMap.GLOWING_BED));
//                        entries.addAll(orderDyedItems(BlockDyeMap.GLOWING_BANNER));
                    }).build();

    private static <T extends ItemConvertible> List<ItemStack> orderDyedItems(Map<AllDyes, T> map) {
        return List.of(
                new ItemStack(map.get(AllDyes.WHITE).asItem()),
                new ItemStack(map.get(AllDyes.LIGHT_GRAY).asItem()),
                new ItemStack(map.get(AllDyes.GRAY).asItem()),
                new ItemStack(map.get(AllDyes.BLACK).asItem()),
                new ItemStack(map.get(AllDyes.BROWN).asItem()),
                new ItemStack(map.get(AllDyes.RED).asItem()),
                new ItemStack(map.get(AllDyes.ORANGE).asItem()),
                new ItemStack(map.get(AllDyes.YELLOW).asItem()),
                new ItemStack(map.get(AllDyes.LIME).asItem()),
                new ItemStack(map.get(AllDyes.GREEN).asItem()),
                new ItemStack(map.get(AllDyes.CYAN).asItem()),
                new ItemStack(map.get(AllDyes.LIGHT_BLUE).asItem()),
                new ItemStack(map.get(AllDyes.BLUE).asItem()),
                new ItemStack(map.get(AllDyes.PURPLE).asItem()),
                new ItemStack(map.get(AllDyes.MAGENTA).asItem()),
                new ItemStack(map.get(AllDyes.PINK).asItem()),
                new ItemStack(map.get(AllDyes.AMBER).asItem()),
                new ItemStack(map.get(AllDyes.AQUA).asItem()),
                new ItemStack(map.get(AllDyes.INDIGO).asItem()),
                new ItemStack(map.get(AllDyes.MAROON).asItem())
        );
    }

    public static void registerItemGroup() {
        System.out.println("register ItemGroup");
        Registry.register(Registries.ITEM_GROUP, "nekomasfixed", NEKOMASFIXED);
        Registry.register(Registries.ITEM_GROUP, "nekomasfixedcolours", NEKOMASFIXEDCOLOURS);
    }
}
