package net.greenjab.nekomasfixed.registry.block.cauldron;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import java.util.Map;

public class CauldronBehaviour {

    public static void register() {
        Map<Item, CauldronBehavior> emptyMap = CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.map();

        emptyMap.put(Items.HONEY_BOTTLE, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient()) {
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
                world.setBlockState(pos, BlockRegistry.HONEY_CAULDRON.getDefaultState()
                        .with(HoneyCauldronBlock.HONEY_LEVEL, 1));
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return ActionResult.SUCCESS;
        });

        emptyMap.put(Items.MAGMA_CREAM, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient()) {
                stack.decrementUnlessCreative(1, player);
                world.setBlockState(pos, BlockRegistry.MAGMA_CAULDRON.getDefaultState()
                        .with(MagmaCauldronBlock.MAGMA_LEVEL, 1));
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return ActionResult.SUCCESS;
        });

        emptyMap.put(Items.SLIME_BALL, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient()) {
                stack.decrementUnlessCreative(1, player);
                world.setBlockState(pos, BlockRegistry.SLIME_CAULDRON.getDefaultState()
                        .with(SlimeCauldronBlock.SLIME_LEVEL, 1));
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return ActionResult.SUCCESS;
        });
    }
}