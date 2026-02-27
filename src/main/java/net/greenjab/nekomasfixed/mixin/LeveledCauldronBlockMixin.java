package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LeveledCauldronBlock.class)
public class LeveledCauldronBlockMixin {

    @Shadow @Final private Biome.Precipitation precipitation;

    @Inject(method = "precipitationTick", at = @At(value = "HEAD"), cancellable = true)
    private void turnIntoIce(BlockState state, World world, BlockPos pos, Biome.Precipitation precipitation2, CallbackInfo ci) {
        if (precipitation==Biome.Precipitation.RAIN && precipitation2 == Biome.Precipitation.SNOW && state.get(LeveledCauldronBlock.LEVEL)==3) {
            BlockState blockState = BlockRegistry.ICE_CAULDRON.getDefaultState();
            world.setBlockState(pos, blockState);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
            ci.cancel();
        }
    }

}