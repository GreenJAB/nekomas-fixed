package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.HoneyCauldronBlock;
import net.greenjab.nekomasfixed.registry.block.IceCauldronBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {

    @Inject(method = "onBlockAdded", at = @At("TAIL"))
    private void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
        if (world.isClient()) return;
        BlockPos abovePos = pos.up(2);
        BlockState aboveState = world.getBlockState(abovePos);
        int honeyLevel = 0;
        if(aboveState.isOf(Blocks.BEEHIVE) || aboveState.isOf(Blocks.BEE_NEST)){
            honeyLevel= aboveState.get(BeehiveBlock.HONEY_LEVEL);
        }

        if (state.getBlock() == Blocks.CAULDRON) {
            TagKey<Biome> COLD_BIOMES = TagKey.of(RegistryKeys.BIOME,
                    Identifier.of("c", "climate/cold"));
            boolean isCold = world.getBiome(pos).isIn(COLD_BIOMES);

            if (isCold && !world.isClient()) {
                world.setBlockState(pos, BlockRegistry.ICE_CAULDRON.getDefaultState());
                world.playSound(null, pos, SoundEvents.BLOCK_GLASS_PLACE,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);
                return;
            }
        }

        if (state.getBlock() == Blocks.CAULDRON) {
            abovePos = pos.up(2);
            aboveState = world.getBlockState(abovePos);
            if ((aboveState.isOf(Blocks.BEE_NEST) && honeyLevel==5 && world.getBlockState(pos.up(1)).isOf(Blocks.AIR))
                    ||
                    (aboveState.isOf(Blocks.BEEHIVE) && honeyLevel == 5) && world.getBlockState(pos.up(1)).isOf(Blocks.AIR)) {
                world.setBlockState(pos, BlockRegistry.HONEY_CAULDRON.getDefaultState()
                        .with(HoneyCauldronBlock.HONEY_LEVEL, 1));
            }
        }

        if ((aboveState.isOf(Blocks.BEE_NEST) && honeyLevel==5) || (aboveState.isOf(Blocks.BEEHIVE) && honeyLevel == 5)) {
            BlockPos downPos = pos.down(2);
            BlockState downState = world.getBlockState(downPos);
            if ((downState.isOf(Blocks.CAULDRON) && world.getBlockState(pos.down(1)).isOf(Blocks.AIR)) || (downState.isOf(BlockRegistry.HONEY_CAULDRON) && world.getBlockState(pos.down(1)).isOf(Blocks.AIR))) {
                world.setBlockState(pos.down(2), BlockRegistry.HONEY_CAULDRON.getDefaultState()
                        .with(HoneyCauldronBlock.HONEY_LEVEL, 1));
            }
        }
        if (state.getBlock() == BlockRegistry.HONEY_CAULDRON || state.getBlock() == Blocks.BEE_NEST || state.getBlock() == Blocks.BEEHIVE) {
            world.scheduleBlockTick(pos, state.getBlock(), 20);
        }
    }
}