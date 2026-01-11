package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.entity.TargetDummyEntity;
import net.minecraft.block.Block;
import net.minecraft.block.WeightedPressurePlateBlock;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(WeightedPressurePlateBlock.class)
public class WeightedPressurePlateBlockMixin {

    @Inject(method="getRedstoneOutput(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)I", at = @At( value = "HEAD"), cancellable = true)
    private void TargetDummyOutput(World world, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        List<TargetDummyEntity> list = world.getEntitiesByClass(TargetDummyEntity.class, Block.createColumnShape(14.0, 0.0, 4.0).getBoundingBoxes().get(0).offset(pos), EntityPredicates.VALID_ENTITY);
        if (!list.isEmpty()) {
            cir.setReturnValue(Math.min(list.get(0).getLastDamage(), 15));
        }
    }
}
