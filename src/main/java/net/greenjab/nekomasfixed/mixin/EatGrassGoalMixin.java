package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.util.SpottedSheepAccess;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EatGrassGoal.class)
public abstract class EatGrassGoalMixin {

    @Shadow @Final private MobEntity mob;
    @Shadow private World world;
    @Shadow private int timer;

    @Inject(method = "canStart", at = @At("RETURN"), cancellable = true)
    private void canStartOnMycelium(CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() || !(this.mob instanceof SheepEntity)) {
            return;
        }

        if (this.mob.getRandom().nextInt(this.mob.isBaby() ? 50 : 1000) != 0) {
            return;
        }

        BlockPos blockPos = this.mob.getBlockPos().down();
        if (this.world.getBlockState(blockPos).isOf(Blocks.MYCELIUM)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void eatMyceliumTick(CallbackInfo ci) {
        if (this.timer == 4 && this.mob instanceof SheepEntity sheep) {
            BlockPos blockPos = sheep.getBlockPos().down();

            if (this.world.getBlockState(blockPos).isOf(Blocks.MYCELIUM)) {
                this.world.syncWorldEvent(2001, blockPos, Block.getRawIdFromState(Blocks.MYCELIUM.getDefaultState()));

                this.world.setBlockState(blockPos, Blocks.DIRT.getDefaultState(), 2);

                ((SpottedSheepAccess) sheep).nekomasfixed$setSpotted(true);
                sheep.onEatingGrass();
            }
        }
    }
}