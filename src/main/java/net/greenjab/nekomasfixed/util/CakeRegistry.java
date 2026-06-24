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
        register(BlockRegistry.SWEETBERRY_CAKE, ItemRegistry.SWEETBERRY_CAKE);
        register(BlockRegistry.APPLE_CAKE, ItemRegistry.APPLE_CAKE);
        register(BlockRegistry.VANILLA_CAKE, ItemRegistry.VANILLA_CAKE);
        register(BlockRegistry.COOKIE_CAKE, ItemRegistry.COOKIE_CAKE);
        register(BlockRegistry.CHOCOLATE_CAKE, ItemRegistry.CHOCOLATE_CAKE);
        register(BlockRegistry.PAN_CAKE, ItemRegistry.PAN_CAKE);
        register(BlockRegistry.BEETROOT_CAKE, ItemRegistry.BEETROOT_CAKE);
        register(BlockRegistry.GLOWBERRY_CAKE, ItemRegistry.GLOWBERRY_CAKE);
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