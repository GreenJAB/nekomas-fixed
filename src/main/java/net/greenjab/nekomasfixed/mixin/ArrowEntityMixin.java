package net.greenjab.nekomasfixed.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArrowEntity.class)
public class ArrowEntityMixin {
    @Inject(method = "onHit", at = @At("HEAD"), cancellable = true)
    private void onHit(LivingEntity target, CallbackInfo ci) {
        ArrowEntity arrowEntity = (ArrowEntity)(Object)this;
        ItemStack arrow = arrowEntity.getItemStack();
        PotionContentsComponent contents = arrow.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT);

        if(arrow!=null && contents!=null && contents != PotionContentsComponent.DEFAULT){
            AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(target.getEntityWorld(), target.getX(), target.getY(), target.getZ());
            areaEffectCloudEntity.setRadius(5.0F);
            areaEffectCloudEntity.setRadiusOnUse(-1F);
            areaEffectCloudEntity.setDuration(600);
            areaEffectCloudEntity.setWaitTime(10);
            areaEffectCloudEntity.setPotionContents(contents);
            areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());
            target.getEntityWorld().spawnEntity(areaEffectCloudEntity);
        }

    }
}
