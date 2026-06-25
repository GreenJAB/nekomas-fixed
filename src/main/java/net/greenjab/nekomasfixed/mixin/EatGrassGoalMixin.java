package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.greenjab.nekomasfixed.util.SpottedSheepAccess;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.SheepEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EatGrassGoal.class)
public abstract class EatGrassGoalMixin {
    @Shadow
    @Final
    private MobEntity mob;

    @WrapOperation(method = {"canStart"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
    private boolean canStartOnMycelium(BlockState instance, Block block, Operation<Boolean> original) {
        if (instance.isOf(Blocks.MYCELIUM)) return true;
        return original.call(instance, block);
    }

    @WrapOperation(method = {"tick"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
    private boolean eatMycelium(BlockState instance, Block block, Operation<Boolean> original) {
        if (mob instanceof SheepEntity sheep) {
            if (instance.isOf(Blocks.MYCELIUM)) {
                if (sheep.isSheared()) ((SpottedSheepAccess) sheep).nekomasfixed$setSpotted(true);
                return true;
            } else {
                if (sheep.isSheared()) ((SpottedSheepAccess) sheep).nekomasfixed$setSpotted(false);
            }
        }
        return original.call(instance, block);
    }
}