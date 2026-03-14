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
                        entries.add(ItemRegistry.GLOW_TORCH);
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
                        entries.add(ItemRegistry.BIG_OAK_BOAT);
                        entries.add(ItemRegistry.HUGE_OAK_BOAT);
                        entries.add(ItemRegistry.BIG_PALE_OAK_BOAT);
                        entries.add(ItemRegistry.HUGE_PALE_OAK_BOAT);
                        entries.add(ItemRegistry.BIG_SPRUCE_BOAT);
                        entries.add(ItemRegistry.HUGE_SPRUCE_BOAT);
                        entries.add(ItemRegistry.BOAT_UPGRADE_TEMPLATE);
                        entries.add(ItemRegistry.GLISTERING_MELON);
                        entries.add(ItemRegistry.NAUTILUS_BLOCK);
                        entries.add(ItemRegistry.ZOMBIE_NAUTILUS_BLOCK);
                        entries.add(ItemRegistry.CORAL_NAUTILUS_BLOCK);
                        entries.add(ItemRegistry.TURTLE_CHESTPLATE);
                        entries.add(ItemRegistry.TURTLE_LEGGINGS);
                        entries.add(ItemRegistry.TURTLE_BOOTS);
                        entries.add(ItemRegistry.TARGET_DUMMY);
                        entries.add(ItemRegistry.ENDERMAN_HEAD);
                        entries.add(ItemRegistry.SLINGSHOT);
                        entries.add(ItemRegistry.WILD_FIRE_SPAWN_EGG);
                        entries.add(ItemRegistry.WILD_FIRE_SMITHING_TEMPLATE);
                        entries.add(ItemRegistry.NETHER_HEART);
                        entries.add(ItemRegistry.SOULFIRE_TRIDENT);
                        entries.add(ItemRegistry.SOULFIRE_SHIELD);
                        entries.add(ItemRegistry.KILN);

                        entries.add(ItemRegistry.WOODEN_SICKLE);
                        entries.add(ItemRegistry.STONE_SICKLE);
                        entries.add(ItemRegistry.COPPER_SICKLE);
                        entries.add(ItemRegistry.IRON_SICKLE);
                        entries.add(ItemRegistry.GOLDEN_SICKLE);
                        entries.add(ItemRegistry.DIAMOND_SICKLE);
						
						entries.add(ItemRegistry.AMBER_DYE);
                        entries.add(ItemRegistry.INDIGO_DYE);
                        entries.add(ItemRegistry.CRIMSON_DYE);
                        entries.add(ItemRegistry.AQUA_DYE);
                        entries.add(ItemRegistry.AMBER_TERRACOTTA);
                        entries.add(ItemRegistry.INDIGO_TERRACOTTA);
                        entries.add(ItemRegistry.CRIMSON_TERRACOTTA);
                        entries.add(ItemRegistry.AQUA_TERRACOTTA);
                        entries.add(ItemRegistry.AMBER_GLAZED_TERRACOTTA);
                        entries.add(ItemRegistry.CRIMSON_GLAZED_TERRACOTTA);
                        entries.add(ItemRegistry.INDIGO_GLAZED_TERRACOTTA);

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

                         entries.add(ItemRegistry.WHITE_FROGLIGHT);
                         entries.add(ItemRegistry.LIGHT_GRAY_FROGLIGHT);
                         entries.add(ItemRegistry.GRAY_FROGLIGHT);
                         entries.add(ItemRegistry.BLACK_FROGLIGHT);
                         entries.add(ItemRegistry.BROWN_FROGLIGHT);
                         entries.add(ItemRegistry.RED_FROGLIGHT);
                         entries.add(ItemRegistry.ORANGE_FROGLIGHT);
                         entries.add(ItemRegistry.LIME_FROGLIGHT);
                         entries.add(ItemRegistry.CYAN_FROGLIGHT);
                         entries.add(ItemRegistry.LIGHT_BLUE_FROGLIGHT);
                         entries.add(ItemRegistry.BLUE_FROGLIGHT);
                         entries.add(ItemRegistry.PURPLE_FROGLIGHT);
                         entries.add(ItemRegistry.PINK_FROGLIGHT);
                         entries.add(ItemRegistry.AMBER_FROGLIGHT);
                         entries.add(ItemRegistry.AQUA_FROGLIGHT);
                         entries.add(ItemRegistry.CRIMSON_FROGLIGHT);
                         entries.add(ItemRegistry.INDIGO_FROGLIGHT);

                         entries.add(ItemRegistry.AMBER_CANDLE);
                         entries.add(ItemRegistry.AQUA_CANDLE);
                         entries.add(ItemRegistry.CRIMSON_CANDLE);
                         entries.add(ItemRegistry.INDIGO_CANDLE);

                         entries.add(ItemRegistry.AMBER_CONCRETE_POWDER);
                         entries.add(ItemRegistry.AQUA_CONCRETE_POWDER);
                         entries.add(ItemRegistry.INDIGO_CONCRETE_POWDER);
                         entries.add(ItemRegistry.CRIMSON_CONCRETE_POWDER);

                         entries.add(ItemRegistry.AMBER_HARNESS);
                         entries.add(ItemRegistry.AQUA_HARNESS);
                         entries.add(ItemRegistry.CRIMSON_HARNESS);
                         entries.add(ItemRegistry.INDIGO_HARNESS);

                         entries.add(ItemRegistry.NETHERITE_CROWN);
                         entries.add(ItemRegistry.COPPER_CROWN);
                         entries.add(ItemRegistry.IRON_CROWN);
                         entries.add(ItemRegistry.GOLDEN_CROWN);
                         entries.add(ItemRegistry.DIAMOND_CROWN);

                        entries.add(ItemRegistry.AQUA_GLAZED_TERRACOTTA);


                    }).build();


    public static void registerItemGroup() {
        System.out.println("register ItemGroup");
        Registry.register(Registries.ITEM_GROUP, "nekomasfixed", NEKOMASFIXED);
    }
}
