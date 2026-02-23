package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.registry.block.HoneyCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;

public class HoneyCauldronUtil {

    public static boolean incrementHoneyLevel(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) return false;
        if (state.getBlock() != BlockRegistry.HONEY_CAULDRON) return false;

        int currentLevel = state.get(HoneyCauldronBlock.HONEY_LEVEL);
        if (currentLevel >= HoneyCauldronBlock.MAX_LEVEL) return false;

        world.setBlockState(pos, state.with(HoneyCauldronBlock.HONEY_LEVEL, currentLevel + 1));
        world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);


        return true;
    }
}