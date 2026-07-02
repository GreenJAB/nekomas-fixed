package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.conversion.EntityConversionContext;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkeletonEntity.class)
public abstract class SkeletonEntityMixin extends HostileEntity {

    @Unique private int inWaterTime = 0;

    protected SkeletonEntityMixin(EntityType<? extends HostileEntity> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tickDrenchedConversion(CallbackInfo ci) {
        if (this.getEntityWorld() instanceof ServerWorld serverWorld && this.isAlive() && this.isSubmergedIn(FluidTags.WATER)) {
            this.inWaterTime++;
            if (this.inWaterTime >= 900) {
                this.convertTo(EntityTypeRegistry.DRENCHED, EntityConversionContext.create((SkeletonEntity)(Object)this, true, true),drenched -> {});
                if (!this.isSilent()) serverWorld.syncWorldEvent(null, WorldEvents.ZOMBIE_CONVERTS_TO_DROWNED, this.getBlockPos(), 0);
            }
        } else this.inWaterTime = 0;
    }
}