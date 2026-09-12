package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.entity.goal.FollowPlayerIfTrustedGoal;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.feline.Ocelot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Ocelot.class)
public abstract class OcelotMixin {

    @Shadow public abstract void setTrusting(boolean trusting);

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void addFollowPlayerGoal(CallbackInfo ci) {
        Ocelot ocelot = (Ocelot) (Object) this;
        ocelot.goalSelector.addGoal(6, new FollowPlayerIfTrustedGoal(ocelot, 1.1D, 16.0F, 3.0F));
    }

    @Inject(method = "getBreedOffspring", at = @At("RETURN"))
    private void makeBabyTrusted(ServerLevel level, AgeableMob otherParent, CallbackInfoReturnable<Ocelot> cir) {
        Ocelot baby = cir.getReturnValue();
        if (baby != null) {
            baby.setTrusting(true);
        }
    }
}