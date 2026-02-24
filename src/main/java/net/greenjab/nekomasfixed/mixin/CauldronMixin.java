package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.HoneyCauldronBlock;
import net.greenjab.nekomasfixed.registry.block.IceCauldronBlock;
import net.greenjab.nekomasfixed.registry.block.MagmaCauldronBlock;
import net.greenjab.nekomasfixed.registry.block.SlimeCauldronBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(AbstractCauldronBlock.class)
public class CauldronMixin {

    @Unique
    private static final Map<BlockPos, Integer> ICE_FORMATION_TIMERS = new HashMap<>();

    @Inject(method = "onUseWithItem", at = @At("HEAD"), cancellable = true)
    private void onCauldronUse(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        System.out.println(stack);

        // Water bucket in cold biome with delay
        if (stack.isOf(Items.WATER_BUCKET) && state.getBlock() == Blocks.CAULDRON) {
            Biome biome = world.getBiome(pos).value();
            if (biome.isCold(pos, 63) && !world.isClient()) {

                // Start ice formation timer (20 ticks = 1 second)
                ICE_FORMATION_TIMERS.put(pos, 20);

                // Remove water bucket immediately
                player.setStackInHand(hand, new ItemStack(Items.BUCKET));

                System.out.println("Water will freeze in 20 ticks");
                cir.setReturnValue(ActionResult.SUCCESS);
                return;
            }
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

    @Inject(method = "scheduledTick", at = @At("HEAD"))
    private void onScheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {

        if (ICE_FORMATION_TIMERS.containsKey(pos)) {
            int timer = ICE_FORMATION_TIMERS.get(pos) - 1;

            if (timer <= 0) {
                // Time's up - form ice
                world.setBlockState(pos, BlockRegistry.ICE_CAULDRON.getDefaultState());
                world.playSound(null, pos, SoundEvents.BLOCK_GLASS_PLACE,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);
                System.out.println("Ice formed at " + pos);
                ICE_FORMATION_TIMERS.remove(pos);
            } else {
                ICE_FORMATION_TIMERS.put(pos, timer);
            }
        }
    }
}