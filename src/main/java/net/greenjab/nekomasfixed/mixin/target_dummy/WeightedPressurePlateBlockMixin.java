package net.greenjab.nekomasfixed.mixin.target_dummy;

import net.greenjab.nekomasfixed.registry.entity.TargetDummy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WeightedPressurePlateBlock;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(WeightedPressurePlateBlock.class)
public class WeightedPressurePlateBlockMixin {

    @Inject(method = "getSignalStrength", at = @At("HEAD"), cancellable = true)
    private void targetDummyOutput(Level level, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        AABB box = new AABB(pos).inflate(0.25, 0.0, 0.25).expandTowards(0.0, 4.0, 0.0);
        List<TargetDummy> list = level.getEntitiesOfClass(TargetDummy.class, box, EntitySelector.ENTITY_STILL_ALIVE);
        if (!list.isEmpty()) {
            cir.setReturnValue(Math.min(list.getFirst().getLastDamage(), 15));
        }
    }
}