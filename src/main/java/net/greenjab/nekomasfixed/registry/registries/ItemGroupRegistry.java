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
                        entries.add(ItemRegistry.TURTLE_CHESTPLATE);
                        entries.add(ItemRegistry.TURTLE_LEGGINGS);
                        entries.add(ItemRegistry.TURTLE_BOOTS);
                    }).build();


    public static void registerItemGroup() {
        System.out.println("register ItemGroup");
        Registry.register(Registries.ITEM_GROUP, "nekomasfixed", NEKOMASFIXED);
    }
}
