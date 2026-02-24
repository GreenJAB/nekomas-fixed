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
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractCauldronBlock.class)
public class AbstractCauldronMixin {

    @Inject(method = "onUseWithItem", at = @At("HEAD"), cancellable = true)
    private void onCauldronUse(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        System.out.println(stack);

        // ICE CAULDRON - Water bucket in cold biome creates ice cauldron
//        if (stack.isOf(Items.WATER_BUCKET) && state.getBlock() == Blocks.CAULDRON) {
//
//                Biome biome = world.getBiome(pos).value();
//            Biome.Precipitation precipitation = biome.getPrecipitation(pos, world.getSeaLevel());
//            if (biome.isCold(pos, 63) && precipitation == Biome.Precipitation.SNOW && !world.isClient()) {
//                world.setBlockState(pos, BlockRegistry.ICE_CAULDRON.getDefaultState());
//                System.out.println("In a cold biome");
//                player.setStackInHand(hand, new ItemStack(Items.BUCKET));
//                world.playSound(null, pos, SoundEvents.BLOCK_GLASS_PLACE,
//                        SoundCategory.BLOCKS, 1.0F, 1.0F);
//                cir.setReturnValue(ActionResult.SUCCESS);
//                return;
//            }
//        }


        if (stack.isEmpty() && state.getBlock() == BlockRegistry.ICE_CAULDRON) {
            if (!world.isClient()) {
                player.getInventory().offerOrDrop(new ItemStack(Items.ICE, 1));
                world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
                world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            cir.setReturnValue(ActionResult.SUCCESS);
            return;
        }


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


        if (state.getBlock() == BlockRegistry.MAGMA_CAULDRON) {
            int level = state.get(MagmaCauldronBlock.MAGMA_LEVEL);

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


        if (state.getBlock() == BlockRegistry.SLIME_CAULDRON) {
            int level = state.get(SlimeCauldronBlock.SLIME_LEVEL);

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
                    player.getInventory().offerOrDrop(new ItemStack(Items.SLIME_BLOCK, 1));
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