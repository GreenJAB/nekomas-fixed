package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ConversionParams;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Skeleton.class)
public abstract class SkeletonMixin extends Monster {

    @Unique private int inWaterTime = 0;

    protected SkeletonMixin(EntityType<? extends Monster> type, Level world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tickDrenchedConversion(CallbackInfo ci) {
        if (this.level() instanceof ServerLevel level && this.isAlive() && this.isEyeInFluid(FluidTags.WATER)) {
            this.inWaterTime++;
            if (this.inWaterTime >= 900) {
                this.convertTo(EntityTypeRegistry.DRENCHED, ConversionParams.single((Skeleton)(Object)this, true, true), drenched -> drenched.setVariant(this.random.nextInt(3)));
                if (!this.isSilent()) level.levelEvent(null, LevelEvent.SOUND_ZOMBIE_TO_DROWNED, this.blockPosition(), 0);
            }
        } else this.inWaterTime = 0;
    }
}