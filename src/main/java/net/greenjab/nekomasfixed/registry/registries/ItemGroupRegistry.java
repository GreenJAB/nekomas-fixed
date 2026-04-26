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
                         entries.add(ItemRegistry.BOABAB_SAPLING);
                         entries.add(ItemRegistry.BOABAB_LOG);
                         entries.add(ItemRegistry.ROPE_ITEM);
                         entries.add(ItemRegistry.GLOW_TORCH);
                        entries.add(ItemRegistry.TURTLE_CHESTPLATE);
                        entries.add(ItemRegistry.TURTLE_LEGGINGS);
                        entries.add(ItemRegistry.TURTLE_BOOTS);
                        entries.add(ItemRegistry.TARGET_DUMMY);
                        entries.add(ItemRegistry.ENDERMAN_HEAD);

                        entries.add(ItemRegistry.WILD_FIRE_SPAWN_EGG);
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
                        entries.add(ItemRegistry.MAROON_DYE);

                         entries.add(ItemRegistry.AMBER_WOOL);
                         entries.add(ItemRegistry.AQUA_WOOL);
                         entries.add(ItemRegistry.INDIGO_WOOL);
                         entries.add(ItemRegistry.MAROON_WOOL);
                         entries.add(ItemRegistry.AMBER_CARPET);
                         entries.add(ItemRegistry.AQUA_CARPET);
                         entries.add(ItemRegistry.INDIGO_CARPET);
                         entries.add(ItemRegistry.MAROON_CARPET);

                         entries.add(ItemRegistry.AMBER_CONCRETE);
                         entries.add(ItemRegistry.AQUA_CONCRETE);
                         entries.add(ItemRegistry.INDIGO_CONCRETE);
                         entries.add(ItemRegistry.MAROON_CONCRETE);
                         entries.add(ItemRegistry.AMBER_CONCRETE_POWDER);
                         entries.add(ItemRegistry.AQUA_CONCRETE_POWDER);
                         entries.add(ItemRegistry.INDIGO_CONCRETE_POWDER);
                         entries.add(ItemRegistry.MAROON_CONCRETE_POWDER);

                        entries.add(ItemRegistry.AMBER_TERRACOTTA);
                        entries.add(ItemRegistry.AQUA_TERRACOTTA);
                        entries.add(ItemRegistry.INDIGO_TERRACOTTA);
                        entries.add(ItemRegistry.MAROON_TERRACOTTA);
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

                         entries.add(ItemRegistry.AMBER_BUNDLE);
                         entries.add(ItemRegistry.AQUA_BUNDLE);
                         entries.add(ItemRegistry.INDIGO_BUNDLE);
                         entries.add(ItemRegistry.MAROON_BUNDLE);

                         entries.add(ItemRegistry.AMBER_BED);
                         entries.add(ItemRegistry.AQUA_BED);
                         entries.add(ItemRegistry.INDIGO_BED);
                         entries.add(ItemRegistry.MAROON_BED);

                         entries.add(ItemRegistry.AMBER_CANDLE);
                         entries.add(ItemRegistry.AQUA_CANDLE);
                         entries.add(ItemRegistry.INDIGO_CANDLE);
                         entries.add(ItemRegistry.MAROON_CANDLE);

                         entries.add(ItemRegistry.AMBER_HARNESS);
                         entries.add(ItemRegistry.AQUA_HARNESS);
                         entries.add(ItemRegistry.INDIGO_HARNESS);
                         entries.add(ItemRegistry.MAROON_HARNESS);


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

                         entries.add(ItemRegistry.PYROTECHNICS_TABLE);
                         entries.add(ItemRegistry.JUNGLE_ZOMBIE_SPAWN_EGG);
                         entries.add(ItemRegistry.SNOW_ZOMBIE_SPAWN_EGG);

                         entries.add(ItemRegistry.BEETROOT_CAKE);
                         entries.add(ItemRegistry.APPLE_CAKE);
                         entries.add(ItemRegistry.HONEY_CAKE);
                         entries.add(ItemRegistry.GLOWBERRY_CAKE);
                         entries.add(ItemRegistry.COOKIE_CAKE);
                         entries.add(ItemRegistry.COCOA_CAKE);



                    }).build();


    public static void registerItemGroup() {
        System.out.println("register ItemGroup");
        Registry.register(Registries.ITEM_GROUP, "nekomasfixed", NEKOMASFIXED);
    }
}
