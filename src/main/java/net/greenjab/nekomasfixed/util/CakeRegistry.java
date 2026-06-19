package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class CakeRegistry {

    private static final Map<Block, Item> BLOCK_TO_ITEM = new HashMap<>();
    private static final Map<Item, Block> ITEM_TO_BLOCK = new HashMap<>();

    public static void init() {
        register(BlockRegistry.STACKED_STRAWBERRY_CAKE, ItemRegistry.STACKED_STRAWBERRY_CAKE);
        register(BlockRegistry.STACKED_APPLE_CAKE, ItemRegistry.STACKED_APPLE_CAKE);
        register(BlockRegistry.STACKED_VANILLA_CAKE, ItemRegistry.STACKED_VANILLA_CAKE);
        register(BlockRegistry.STACKED_COOKIE_CAKE, ItemRegistry.STACKED_COOKIE_CAKE);
        register(BlockRegistry.STACKED_CHOCOLATE_CAKE, ItemRegistry.STACKED_CHOCOLATE_CAKE);
        register(BlockRegistry.STACKED_PAN_CAKE, ItemRegistry.STACKED_PAN_CAKE);
        register(BlockRegistry.STACKED_BEETROOT_CAKE, ItemRegistry.STACKED_BEETROOT_CAKE);
        register(BlockRegistry.STACKED_GLOWBERRY_CAKE, ItemRegistry.STACKED_GLOWBERRY_CAKE);
    }

    private static void register(Block block, Item item) {
        BLOCK_TO_ITEM.put(block, item);
        ITEM_TO_BLOCK.put(item, block);
    }

    public static ItemStack getStack(BlockState state) {
        Item item = BLOCK_TO_ITEM.get(state.getBlock());
        return item == null ? ItemStack.EMPTY : new ItemStack(item);
    }

    public static BlockState getDefaultState(ItemStack stack) {
        Block block = ITEM_TO_BLOCK.get(stack.getItem());
        return block == null ? Blocks.AIR.getDefaultState() : block.getDefaultState();
    }
}