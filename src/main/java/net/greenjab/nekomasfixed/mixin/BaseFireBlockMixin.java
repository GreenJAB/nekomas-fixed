package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.SulfurFireBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.SoulFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseFireBlock.class)
public class BaseFireBlockMixin {
    @Inject(method = "getState", at = @At("HEAD"), cancellable = true)
    private static void getState(BlockGetter level, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
        BlockPos below = pos.below();
        BlockState belowState = level.getBlockState(below);
        if(SulfurFireBlock.canSurviveOnBlock(belowState)) cir.setReturnValue(BlockRegistry.SULFUR_FIRE.defaultBlockState());
        else if(SoulFireBlock.canSurviveOnBlock(belowState)) cir.setReturnValue(Blocks.SOUL_FIRE.defaultBlockState());
        else cir.setReturnValue(((FireBlock)Blocks.FIRE).getStateForPlacement(level, pos));

    }
}
