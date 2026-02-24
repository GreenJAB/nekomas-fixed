package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CauldronBlock.class)
public class CauldronBlockMixin {
    @Unique
    private static boolean canFillWithPrecipitation(World world, Biome.Precipitation precipitation) {
        if (precipitation == Biome.Precipitation.RAIN) {
            return world.getRandom().nextFloat() < 0.05F;
        } else {
            return precipitation == Biome.Precipitation.SNOW && world.getRandom().nextFloat() < 0.1F;
        }
    }
    @Inject(method = "precipitationTick", at = @At("HEAD"), cancellable = true)
    void fillIceIfCan(BlockState state, World world, BlockPos pos, Biome.Precipitation precipitation, CallbackInfo ci) {
        if (canFillWithPrecipitation(world, precipitation)) {
            if (precipitation == Biome.Precipitation.SNOW) {
                world.setBlockState(pos, BlockRegistry.ICE_CAULDRON.getDefaultState());
                world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }
        }
    }
}
