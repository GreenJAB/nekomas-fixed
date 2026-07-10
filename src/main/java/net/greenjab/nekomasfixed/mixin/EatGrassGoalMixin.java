package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.greenjab.nekomasfixed.util.SpottedSheepAccess;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EatBlockGoal.class)
public abstract class EatGrassGoalMixin {
    @Shadow
    @Final
    private Mob mob;

    @WrapOperation(method = {"canUse"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z"))
    private boolean canStartOnMycelium(BlockState instance, Object o, Operation<Boolean> original) {
        if (instance.is(Blocks.MYCELIUM)) return true;
        return original.call(instance, o);
    }

    @WrapOperation(method = {"tick"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z"))
    private boolean eatMycelium(BlockState instance, Object o, Operation<Boolean> original) {
        if (mob instanceof Sheep sheep) {
            if (instance.is(Blocks.MYCELIUM)) {
                if (sheep.isSheared()) ((SpottedSheepAccess) sheep).nekomasfixed$setSpotted(true);
                return true;
            } else {
                if (sheep.isSheared()) ((SpottedSheepAccess) sheep).nekomasfixed$setSpotted(false);
            }
        }
        return original.call(instance, o);
    }
}