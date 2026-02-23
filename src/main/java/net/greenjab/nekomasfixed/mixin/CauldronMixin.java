package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.HoneyCauldronBlock;
import net.greenjab.nekomasfixed.registry.block.MagmaCauldronBlock;
import net.greenjab.nekomasfixed.registry.block.SlimeCauldronBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractCauldronBlock.class)
public class CauldronMixin {

    @Inject(method = "onUseWithItem", at = @At("HEAD"), cancellable = true)
    private void onCauldronUse(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        System.out.println(stack);

        // Handle magma cream on EMPTY cauldron (convert to magma cauldron)
        if (stack.getItem() == Items.MAGMA_CREAM && state.getBlock() == Blocks.CAULDRON) {
            if (!world.isClient()) {
                world.setBlockState(pos, BlockRegistry.MAGMA_CAULDRON.getDefaultState()
                        .with(MagmaCauldronBlock.MAGMA_LEVEL, 1));
                stack.decrement(1);
                world.playSound(null, pos, SoundEvents.ENTITY_MAGMA_CUBE_SQUISH,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            cir.setReturnValue(ActionResult.SUCCESS);
            return;
        }

        // Handle magma cream on EXISTING magma cauldron
        if (state.getBlock() == BlockRegistry.MAGMA_CAULDRON) {
            int level = state.get(MagmaCauldronBlock.MAGMA_LEVEL);

            // Magma cream adds to cauldron
            if (stack.getItem() == Items.MAGMA_CREAM && level < MagmaCauldronBlock.MAX_LEVEL) {
                if (!world.isClient()) {
                    world.setBlockState(pos, state.with(MagmaCauldronBlock.MAGMA_LEVEL, level + 1));
                    stack.decrement(1);
                    world.playSound(null, pos, SoundEvents.ENTITY_MAGMA_CUBE_SQUISH,
                            SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
                cir.setReturnValue(ActionResult.SUCCESS);
                return;
            }
        }

        if(stack.isEmpty() && state.getBlock() == BlockRegistry.MAGMA_CAULDRON) {
            System.out.println("🎯 MIXIN: Magma cauldron interaction");
            int level = state.get(MagmaCauldronBlock.MAGMA_LEVEL);
            if(level == MagmaCauldronBlock.MAX_LEVEL) {
                if (!world.isClient()) {
                    player.getInventory().offerOrDrop(new ItemStack(Items.MAGMA_BLOCK, 1));
                    world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
                    world.playSound(null, pos, SoundEvents.ENTITY_MAGMA_CUBE_SQUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
                cir.setReturnValue(ActionResult.SUCCESS);
                return;
            }
        }


        if (stack.getItem() == Items.SLIME_BALL && state.getBlock() == Blocks.CAULDRON) {
            if (!world.isClient()) {
                world.setBlockState(pos, BlockRegistry.SLIME_CAULDRON.getDefaultState()
                        .with(SlimeCauldronBlock.SLIME_LEVEL, 1));
                stack.decrement(1);
                world.playSound(null, pos, SoundEvents.BLOCK_SLIME_BLOCK_BREAK,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            cir.setReturnValue(ActionResult.SUCCESS);
            return;
        }

        // Handle magma cream on EXISTING magma cauldron
        if (state.getBlock() == BlockRegistry.SLIME_CAULDRON) {
            int level = state.get(SlimeCauldronBlock.SLIME_LEVEL);

            // Magma cream adds to cauldron
            if (stack.getItem() == Items.SLIME_BALL && level < SlimeCauldronBlock.MAX_LEVEL) {
                if (!world.isClient()) {
                    world.setBlockState(pos, state.with(SlimeCauldronBlock.SLIME_LEVEL, level + 1));
                    stack.decrement(1);
                    world.playSound(null, pos, SoundEvents.BLOCK_SLIME_BLOCK_BREAK,
                            SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
                cir.setReturnValue(ActionResult.SUCCESS);
                return;
            }
        }

        if(stack.isEmpty() && state.getBlock() == BlockRegistry.SLIME_CAULDRON) {
            int level = state.get(SlimeCauldronBlock.SLIME_LEVEL);
            if(level == SlimeCauldronBlock.MAX_LEVEL) {
                if (!world.isClient()) {
                    player.getInventory().offerOrDrop(new ItemStack(Items.SLIME_BALL, 1));
                    world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
                    world.playSound(null, pos, SoundEvents.BLOCK_SLIME_BLOCK_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
                cir.setReturnValue(ActionResult.SUCCESS);
                return;
            }
        }




        if(stack.isEmpty() && state.getBlock() == BlockRegistry.HONEY_CAULDRON) {
            int level = state.get(HoneyCauldronBlock.HONEY_LEVEL);
            if(level == HoneyCauldronBlock.MAX_LEVEL) {
                if (!world.isClient()) {
                    player.getInventory().offerOrDrop(new ItemStack(Items.HONEY_BLOCK, 1));
                    world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
                    world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
                cir.setReturnValue(ActionResult.SUCCESS);
                return;
            }
        }

        if (stack.getItem() == Items.HONEY_BOTTLE && state.getBlock() == Blocks.CAULDRON ) {
            if (!world.isClient()) {
                world.setBlockState(pos, BlockRegistry.HONEY_CAULDRON.getDefaultState()
                        .with(HoneyCauldronBlock.HONEY_LEVEL, 1));
                stack.decrement(1);
                player.getInventory().offerOrDrop(new ItemStack(Items.GLASS_BOTTLE));
            }
            cir.setReturnValue(ActionResult.SUCCESS);
            return;
        }

        if (state.getBlock() == BlockRegistry.HONEY_CAULDRON) {
            int level = state.get(HoneyCauldronBlock.HONEY_LEVEL);

            // Glass bottle takes honey
            if (stack.getItem() == Items.GLASS_BOTTLE) {
                if (!world.isClient()) {
                    player.getInventory().offerOrDrop(new ItemStack(Items.HONEY_BOTTLE));
                    stack.decrement(1);

                    if (level > 1) {
                        world.setBlockState(pos, state.with(HoneyCauldronBlock.HONEY_LEVEL, level - 1));
                    } else {
                        world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
                    }
                }
                cir.setReturnValue(ActionResult.SUCCESS);
                return;
            }

            // Honey bottle adds honey
            if (stack.getItem() == Items.HONEY_BOTTLE && level < HoneyCauldronBlock.MAX_LEVEL) {
                if (!world.isClient()) {
                    world.setBlockState(pos, state.with(HoneyCauldronBlock.HONEY_LEVEL, level + 1));
                    stack.decrement(1);
                    player.getInventory().offerOrDrop(new ItemStack(Items.GLASS_BOTTLE));
                }
                cir.setReturnValue(ActionResult.SUCCESS);
                return;
            }
        }
    }
}