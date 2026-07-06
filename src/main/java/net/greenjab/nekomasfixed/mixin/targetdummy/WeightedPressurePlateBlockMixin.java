package net.greenjab.nekomasfixed.mixin.targetdummy;

import net.greenjab.nekomasfixed.registry.entity.TargetDummyEntity;
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
    private void TargetDummyOutput(Level world, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        List<TargetDummyEntity> list = world.getEntitiesOfClass(TargetDummyEntity.class, Block.column(14.0, 0.0, 4.0).toAabbs().get(0).move(pos), EntitySelector.ENTITY_STILL_ALIVE);
        if (!list.isEmpty()) {
            cir.setReturnValue(Math.min(list.get(0).getLastDamage(), 15));
        }
    }
}
