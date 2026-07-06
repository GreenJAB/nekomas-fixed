package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayeredCauldronBlock.class)
public class LeveledCauldronBlockMixin {

    @Shadow @Final private Biome.Precipitation precipitationType;

    @Inject(method = "handlePrecipitation", at = @At(value = "HEAD"), cancellable = true)
    private void turnIntoIce(BlockState state, Level world, BlockPos pos, Biome.Precipitation precipitation2, CallbackInfo ci) {
        if (precipitationType==Biome.Precipitation.RAIN && precipitation2 == Biome.Precipitation.SNOW && state.getValue(LayeredCauldronBlock.LEVEL)==3) {
            BlockState blockState = BlockRegistry.ICE_CAULDRON.defaultBlockState();
            world.setBlockAndUpdate(pos, blockState);
            world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(blockState));
            ci.cancel();
        }
    }

}