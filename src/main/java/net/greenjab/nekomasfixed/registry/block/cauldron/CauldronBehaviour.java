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
        
        Map<Item, CauldronBehavior> emptyMap = CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.map();
        Map<Item, CauldronBehavior> magmaMap = CauldronBehavior.createMap("magma").map();

        magmaMap.put(Items.MAGMA_CREAM, (state, world, pos, player, hand, stack) -> {

            if (!world.isClient()) {

                if (state.getBlock() == BlockRegistry.MAGMA_CAULDRON) {
                    int level = state.get(MagmaCauldronBlock.MAGMA_LEVEL);
                    if (level < MagmaCauldronBlock.MAX_LEVEL) {
                        world.setBlockState(pos, state.with(MagmaCauldronBlock.MAGMA_LEVEL, level + 1));
                        stack.decrement(1);
                        world.playSound(null, pos, SoundEvents.ENTITY_MAGMA_CUBE_SQUISH,
                                SoundCategory.BLOCKS, 1.0F, 1.0F);

                    }
                } else if (state.getBlock() == Blocks.CAULDRON) {

                    world.setBlockState(pos, BlockRegistry.MAGMA_CAULDRON.getDefaultState()
                            .with(MagmaCauldronBlock.MAGMA_LEVEL, 1));
                    stack.decrement(1);
                    world.playSound(null, pos, SoundEvents.ENTITY_MAGMA_CUBE_SQUISH,
                            SoundCategory.BLOCKS, 1.0F, 1.0F);

                }
            }
            return ActionResult.SUCCESS;
        });


        magmaMap.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {

            if (!world.isClient() && state.getBlock() == BlockRegistry.MAGMA_CAULDRON) {
                int level = state.get(MagmaCauldronBlock.MAGMA_LEVEL);

                player.getInventory().offerOrDrop(new ItemStack(Items.MAGMA_CREAM));

                if (level > 1) {
                    world.setBlockState(pos, state.with(MagmaCauldronBlock.MAGMA_LEVEL, level - 1));
                } else {
                    world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
                }

                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return ActionResult.SUCCESS;
        });


            emptyMap.put(Items.HONEY_BOTTLE, (state, world, pos, player, hand, stack) -> {

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

    }
    private static void registerHoneyCauldronBehaviors() {
        CauldronBehavior.CauldronBehaviorMap honeyMap =
                CauldronBehavior.createMap("honey_cauldron");

        Map<Item, CauldronBehavior> map = honeyMap.map();

        map.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
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

        map.put(Items.HONEY_BOTTLE, (state, world, pos, player, hand, stack) -> {

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


    }

}