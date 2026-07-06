package net.greenjab.nekomasfixed.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;

@Mixin(FlowerPotBlock.class)
public interface FlowerPotBlockAccessor {
    @Accessor("POTTED_BY_CONTENT")
    static Map<Block, Block> getContentToPotted() {
        throw new AssertionError();
    }
}
