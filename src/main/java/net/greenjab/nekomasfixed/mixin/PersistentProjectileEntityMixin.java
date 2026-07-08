package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public abstract class PersistentProjectileEntityMixin {

    @Inject(method = "doPostHurtEffects", at = @At("HEAD"))
    private void onHit(LivingEntity target, CallbackInfo ci) {
        if (((AbstractArrow)(Object) this) instanceof Arrow arrowEntity && !arrowEntity.entityTags().contains("areaEffect")) {
            ItemStack arrow = arrowEntity.getPickupItemStackOrigin();
            PotionContents contents = arrow.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
            if (contents != null && contents != PotionContents.EMPTY) {
                AreaEffectCloud areaEffectCloudEntity = makeAreaEffectCloudEntity(target.level(), target.getX(), target.getY(), target.getZ(), contents);
                target.level().addFreshEntity(areaEffectCloudEntity);
                arrowEntity.addTag("areaEffect");
            }
        }
    }

    @Inject(method = "setInGround", at = @At("HEAD"))
    private void onHit(boolean inGround, CallbackInfo ci) {
        if (((AbstractArrow)(Object) this) instanceof Arrow arrowEntity && !arrowEntity.entityTags().contains("areaEffect")) {
            ItemStack arrow = arrowEntity.getPickupItemStackOrigin();
            PotionContents contents = arrow.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
            if (contents != null && contents != PotionContents.EMPTY) {
                AreaEffectCloud areaEffectCloudEntity = makeAreaEffectCloudEntity(arrowEntity.level(), arrowEntity.getX(), arrowEntity.getY(), arrowEntity.getZ(), contents);
                arrowEntity.level().addFreshEntity(areaEffectCloudEntity);
                arrowEntity.addTag("areaEffect");
            }
        }
    }

    @ModifyExpressionValue(method = "tryPickup", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/arrow/AbstractArrow;getPickupItem()Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack removeEffectsIfPiecing(ItemStack original) {
        AbstractArrow PPE = (AbstractArrow)(Object) this;
        if (PPE instanceof Arrow) return Items.ARROW.getDefaultInstance();
        return original;
    }

    @Unique
    @NotNull
    private static AreaEffectCloud makeAreaEffectCloudEntity(Level target, double target1, double target2, double target3, PotionContents contents) {
        AreaEffectCloud areaEffectCloudEntity = new AreaEffectCloud(target, target1, target2, target3);
        areaEffectCloudEntity.setRadius(2.0F);
        areaEffectCloudEntity.setRadiusOnUse(0F);
        areaEffectCloudEntity.setDuration(100);
        areaEffectCloudEntity.setWaitTime(5);
        areaEffectCloudEntity.setPotionContents(contents);
        areaEffectCloudEntity.setRadiusPerTick(-areaEffectCloudEntity.getRadius() / (float) areaEffectCloudEntity.getDuration());
        return areaEffectCloudEntity;
    }

}
