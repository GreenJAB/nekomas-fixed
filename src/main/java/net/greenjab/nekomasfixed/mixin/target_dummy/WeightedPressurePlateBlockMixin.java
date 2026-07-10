package net.greenjab.nekomasfixed.mixin.target_dummy;

import net.greenjab.nekomasfixed.registry.entity.TargetDummy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeightedPressurePlateBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(WeightedPressurePlateBlock.class)
public class WeightedPressurePlateBlockMixin {

    @Inject(method="getSignalStrength(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)I", at = @At( value = "HEAD"), cancellable = true)
    private void TargetDummyOutput(Level level, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        List<TargetDummy> list = level.getEntitiesOfClass(TargetDummy.class, Block.column(14.0, 0.0, 4.0).toAabbs().getFirst().move(pos), EntitySelector.ENTITY_STILL_ALIVE);
        if (!list.isEmpty()) {
            cir.setReturnValue(Math.min(list.getFirst().getLastDamage(), 15));
        }
    }
}
