package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.conversion.EntityConversionContext;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin extends HostileEntity {

    @Unique private int inPowderSnowTime = 0;

    protected ZombieEntityMixin(EntityType<? extends HostileEntity> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tickDrenchedConversion(CallbackInfo ci) {
        if (this.getEntityWorld() instanceof ServerWorld serverWorld && this.isAlive() && this.inPowderSnow) {
            this.inPowderSnowTime++;
            if (this.inPowderSnowTime >= 450) {
                ZombieEntity ZE = (ZombieEntity)(Object)this;
                ZE.convertTo(EntityTypeRegistry.SNOW_ZOMBIE, EntityConversionContext.create(ZE, true, true), snowZombie -> {});
                if (!this.isSilent()) serverWorld.syncWorldEvent(null, WorldEvents.SKELETON_CONVERTS_TO_STRAY, this.getBlockPos(), 0);
            }
        } else this.inPowderSnowTime = 0;
    }
}