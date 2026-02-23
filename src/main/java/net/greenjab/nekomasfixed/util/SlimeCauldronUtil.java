package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.registry.block.MagmaCauldronBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SlimeCauldronUtil {
    public static boolean incrementMagmaLevel(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) return false;
        if (state.getBlock() != BlockRegistry.MAGMA_CAULDRON) return false;

        int currentLevel = state.get(MagmaCauldronBlock.MAGMA_LEVEL);
        if (currentLevel >= MagmaCauldronBlock.MAX_LEVEL) return false;

        world.setBlockState(pos, state.with(MagmaCauldronBlock.MAGMA_LEVEL, currentLevel + 1));
        world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);


        return true;
    }
}
