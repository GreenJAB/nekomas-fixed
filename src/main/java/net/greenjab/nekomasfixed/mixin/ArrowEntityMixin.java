package net.greenjab.nekomasfixed.mixin;

import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArrowEntity.class)
public class ArrowEntityMixin {
    @Inject(method = "onHit", at = @At("HEAD"), cancellable = true)
    private void onHit(LivingEntity target, CallbackInfo ci) {
        BlockPos pos = target.getBlockPos();
        AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(target.getEntityWorld(), target.getX(), target.getY(), target.getZ());
        areaEffectCloudEntity.setRadius(3.0F);
        areaEffectCloudEntity.setRadiusOnUse(-0.5F);
        areaEffectCloudEntity.setDuration(600);
        areaEffectCloudEntity.setWaitTime(10);
        areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());
        target.getEntityWorld().spawnEntity(areaEffectCloudEntity);
    }
}
