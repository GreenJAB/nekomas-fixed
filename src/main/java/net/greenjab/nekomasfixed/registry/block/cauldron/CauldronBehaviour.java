package net.greenjab.nekomasfixed.registry.block.cauldron;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;

import static net.minecraft.core.cauldron.CauldronInteraction.EMPTY;

public class CauldronBehaviour {

    public static void register() {
        // Custom interaction maps are populated here — after all class initialization —
        // because Items fields declared after CLOCK in vanilla Items would still be null
        // if read from the block constructors (nested BlockRegistry clinit inside Items clinit).
        HoneyCauldronBlock.registerInteractions();
        MagmaCauldronBlock.registerInteractions();
        SlimeCauldronBlock.registerInteractions();
        IceCauldronBlock.registerInteractions();

        EMPTY.map().put(Items.HONEY_BOTTLE, (state, level, pos, player, hand, stack) -> {
            if (!level.isClientSide()) {
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
                level.setBlockAndUpdate(pos, BlockRegistry.HONEY_CAULDRON.defaultBlockState()
                        .setValue(HoneyCauldronBlock.HONEY_LEVEL, 1));
                level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return ItemInteractionResult.SUCCESS;
        });

        EMPTY.map().put(Items.MAGMA_CREAM, (state, level, pos, player, hand, stack) -> {
            if (!level.isClientSide()) {
                stack.consume(1, player);
                level.setBlockAndUpdate(pos, BlockRegistry.MAGMA_CAULDRON.defaultBlockState()
                        .setValue(MagmaCauldronBlock.MAGMA_LEVEL, 1));
                level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return ItemInteractionResult.SUCCESS;
        });

        EMPTY.map().put(Items.SLIME_BALL, (state, level, pos, player, hand, stack) -> {
            if (!level.isClientSide()) {
                stack.consume(1, player);
                level.setBlockAndUpdate(pos, BlockRegistry.SLIME_CAULDRON.defaultBlockState()
                        .setValue(SlimeCauldronBlock.SLIME_LEVEL, 1));
                level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return ItemInteractionResult.SUCCESS;
        });
    }
}
