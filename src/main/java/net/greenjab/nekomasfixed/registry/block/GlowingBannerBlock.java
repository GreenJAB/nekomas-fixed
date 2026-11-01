package net.greenjab.nekomasfixed.registry.block;

import net.minecraft.block.BannerBlock;
import net.minecraft.util.DyeColor;

public class GlowingBannerBlock extends BannerBlock {
    // Used to not update BannerBlock map in BannerBlock class
    public GlowingBannerBlock(DyeColor dyeColor, Settings settings) {
        super(dyeColor, settings);
    }
}
