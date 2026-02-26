package net.greenjab.nekomasfixed.registry.block.cauldron;


import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import java.util.Map;

public class CauldronBehaviour {

    public static void register() {
        Map<Item, CauldronBehavior> waterMap = CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map();

            System.out.println("CauldronBehaviour.register() started");

            Map<Item, CauldronBehavior> emptyMap = CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.map();


        Map<Item, CauldronBehavior> magmaMap = CauldronBehavior.createMap("magma").map();

// Magma cream adds to cauldron
        magmaMap.put(Items.MAGMA_CREAM, (state, world, pos, player, hand, stack) -> {
            System.out.println("🔥 MAGMA CREAM used on cauldron at " + pos);

            if (!world.isClient()) {
                // Check if it's already a magma cauldron
                if (state.getBlock() == BlockRegistry.MAGMA_CAULDRON) {
                    int level = state.get(MagmaCauldronBlock.MAGMA_LEVEL);
                    if (level < MagmaCauldronBlock.MAX_LEVEL) {
                        world.setBlockState(pos, state.with(MagmaCauldronBlock.MAGMA_LEVEL, level + 1));
                        stack.decrement(1);
                        world.playSound(null, pos, SoundEvents.ENTITY_MAGMA_CUBE_SQUISH,
                                SoundCategory.BLOCKS, 1.0F, 1.0F);
                        System.out.println("✅ Magma level increased to " + (level + 1));
                    }
                } else if (state.getBlock() == Blocks.CAULDRON) {
                    // Convert empty cauldron to magma cauldron
                    world.setBlockState(pos, BlockRegistry.MAGMA_CAULDRON.getDefaultState()
                            .with(MagmaCauldronBlock.MAGMA_LEVEL, 1));
                    stack.decrement(1);
                    world.playSound(null, pos, SoundEvents.ENTITY_MAGMA_CUBE_SQUISH,
                            SoundCategory.BLOCKS, 1.0F, 1.0F);
                    System.out.println("✅ Empty cauldron converted to magma cauldron");
                }
            }
            return ActionResult.SUCCESS;
        });

// Glass bottle takes magma cream
        magmaMap.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
            System.out.println("🧪 GLASS BOTTLE used on magma cauldron at " + pos);

            if (!world.isClient() && state.getBlock() == BlockRegistry.MAGMA_CAULDRON) {
                int level = state.get(MagmaCauldronBlock.MAGMA_LEVEL);

                // Give magma cream to player
                player.getInventory().offerOrDrop(new ItemStack(Items.MAGMA_CREAM));

                if (level > 1) {
                    world.setBlockState(pos, state.with(MagmaCauldronBlock.MAGMA_LEVEL, level - 1));
                    System.out.println("✅ Magma level decreased to " + (level - 1));
                } else {
                    world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
                    System.out.println("✅ Magma cauldron emptied");
                }

                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return ActionResult.SUCCESS;
        });

// Empty hand harvest when full
            emptyMap.put(Items.HONEY_BOTTLE, (state, world, pos, player, hand, stack) -> {
                System.out.println("EMPTY CAULDRON: Honey bottle used at " + pos);
                if (!world.isClient()) {
                    world.setBlockState(pos, BlockRegistry.HONEY_CAULDRON.getDefaultState()
                            .with(LeveledCauldronBlock.LEVEL, 1));

                    player.setStackInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
                    world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY,
                            SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
                return ActionResult.SUCCESS;
            });

            registerHoneyCauldronBehaviors();

            System.out.println("CauldronBehaviour.register() finished");

    }
    private static void registerHoneyCauldronBehaviors() {
        System.out.println("Registering honey cauldron behaviors...");

        // Get the behavior map for honey cauldron
        // You need to create a custom behavior map for your honey cauldron
        CauldronBehavior.CauldronBehaviorMap honeyMap =
                CauldronBehavior.createMap("honey_cauldron");

        Map<Item, CauldronBehavior> map = honeyMap.map();

        // Glass bottle takes honey
        map.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
            System.out.println("HONEY CAULDRON: Glass bottle used at " + pos);
            if (!world.isClient()) {
                int level = state.get(LeveledCauldronBlock.LEVEL);
                player.setStackInHand(hand, new ItemStack(Items.HONEY_BOTTLE));

                if (level > 1) {
                    world.setBlockState(pos, state.with(LeveledCauldronBlock.LEVEL, level - 1));
                } else {
                    world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
                }

                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return ActionResult.SUCCESS;
        });

        // Honey bottle adds honey
        map.put(Items.HONEY_BOTTLE, (state, world, pos, player, hand, stack) -> {
            System.out.println("HONEY CAULDRON: Honey bottle used at " + pos);
            if (!world.isClient()) {
                int level = state.get(LeveledCauldronBlock.LEVEL);
                if (level < 3) {
                    world.setBlockState(pos, state.with(LeveledCauldronBlock.LEVEL, level + 1));
                    player.setStackInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
                    world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY,
                            SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
            return ActionResult.SUCCESS;
        });

        // Now you need to associate this behavior map with your honey cauldron block
        // This might require a mixin or a different approach
        System.out.println("Honey cauldron behaviors registered, map size: " + map.size());
    }

}