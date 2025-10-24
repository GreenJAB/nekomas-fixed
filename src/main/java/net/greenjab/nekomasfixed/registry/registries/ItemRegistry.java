package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.*;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.Direction;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ItemRegistry {


    public static final Item CLAM = register(BlockRegistry.CLAM, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT));
    public static final Item CLAM_BLUE = register(BlockRegistry.CLAM_BLUE, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT));
    public static final Item CLAM_PINK = register(BlockRegistry.CLAM_PINK, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT));
    public static final Item CLAM_PURPLE = register(BlockRegistry.CLAM_PURPLE, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT));

    public static final Item PEARL = register("pearl");
    public static final Item PEARL_BLOCK = register(BlockRegistry.PEARL_BLOCK);

    public static final Item GLOW_TORCH = register(
            BlockRegistry.GLOW_TORCH,
            ((block, settings) -> new VerticallyAttachableBlockItem(block, BlockRegistry.GLOW_WALL_TORCH, Direction.DOWN, settings))
    );

    public static final Item ACACIA_MEGA_BOAT = register("acacia_mega_boat", settings -> new BoatItem(EntityTypeRegistry.ACACIA_MEGA_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item BAMBOO_MEGA_BOAT = register("bamboo_mega_boat", settings -> new BoatItem(EntityTypeRegistry.BAMBOO_MEGA_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item BIRCH_MEGA_BOAT = register("birch_mega_boat", settings -> new BoatItem(EntityTypeRegistry.BIRCH_MEGA_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item CHERRY_MEGA_BOAT = register("cherry_mega_boat", settings -> new BoatItem(EntityTypeRegistry.CHERRY_MEGA_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item DARK_OAK_MEGA_BOAT = register("dark_oak_mega_boat", settings -> new BoatItem(EntityTypeRegistry.DARK_OAK_MEGA_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item JUNGLE_MEGA_BOAT = register("jungle_mega_boat", settings -> new BoatItem(EntityTypeRegistry.JUNGLE_MEGA_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item MANGROVE_MEGA_BOAT = register("mangrove_mega_boat", settings -> new BoatItem(EntityTypeRegistry.MANGROVE_MEGA_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item OAK_MEGA_BOAT = register("oak_mega_boat", settings -> new BoatItem(EntityTypeRegistry.OAK_MEGA_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item PALE_OAK_MEGA_BOAT = register("pale_oak_mega_boat", settings -> new BoatItem(EntityTypeRegistry.PALE_OAK_MEGA_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item SPRUCE_MEGA_BOAT = register("spruce_mega_boat", settings -> new BoatItem(EntityTypeRegistry.SPRUCE_MEGA_BOAT, settings), new Item.Settings().maxCount(1));

    public static Item register(String id, Item.Settings settings) {
        return register(keyOf(id), Item::new, settings);
    }
    public static Item register(String id, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return register(keyOf(id), factory, settings);
    }
    private static RegistryKey<Item> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, NekomasFixed.id(id));
    }
    public static Item register(RegistryKey<Item> key, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = factory.apply(settings.registryKey(key));
        if (item instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        }
        return Registry.register(Registries.ITEM, key, item);
    }
    public static Item register(Block block) {
        return register(block, BlockItem::new, new Item.Settings());
    }
    public static Item register(String id) {
        return register(keyOf(id), Item::new, new Item.Settings());
    }
    public static Item register(Block block, Item.Settings settings) {
        return register(block, BlockItem::new, settings);
    }
    public static Item register(Block block, BiFunction<Block, Item.Settings, Item> factory) {
        return register(block, factory, new Item.Settings());
    }
    public static Item register(Block block, BiFunction<Block, Item.Settings, Item> factory, Item.Settings settings) {
        return register(
                keyOf(block.getRegistryEntry().registryKey()),
                itemSettings -> factory.apply(block, itemSettings),
                settings.useBlockPrefixedTranslationKey()
        );
    }
    private static RegistryKey<Item> keyOf(RegistryKey<Block> blockKey) {
        return RegistryKey.of(RegistryKeys.ITEM, blockKey.getValue());
    }

    public static void registerItems() {
        System.out.println("register Items");
    }
}
