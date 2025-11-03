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
                        entries.add(ItemRegistry.ACACIA_MEGA_BOAT);
                        entries.add(ItemRegistry.BAMBOO_MEGA_BOAT);
                        entries.add(ItemRegistry.BIRCH_MEGA_BOAT);
                        entries.add(ItemRegistry.CHERRY_MEGA_BOAT);
                        entries.add(ItemRegistry.DARK_OAK_MEGA_BOAT);
                        entries.add(ItemRegistry.JUNGLE_MEGA_BOAT);
                        entries.add(ItemRegistry.MANGROVE_MEGA_BOAT);
                        entries.add(ItemRegistry.OAK_MEGA_BOAT);
                        entries.add(ItemRegistry.PALE_OAK_MEGA_BOAT);
                        entries.add(ItemRegistry.SPRUCE_MEGA_BOAT);
                        entries.add(ItemRegistry.MEGA_BOAT_UPGRADE_TEMPLATE);
                    }).build();


    public static void registerItemGroup() {
        System.out.println("register ItemGroup");
        Registry.register(Registries.ITEM_GROUP, "nekomasfixed", NEKOMASFIXED);
    }
}
