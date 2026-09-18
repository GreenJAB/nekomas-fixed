package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin {

    @Unique
    private static void spawnCloud(Arrow arrowEntity, Level level, double x, double y, double z) {
        ItemStack arrow = arrowEntity.getPickupItemStackOrigin();
        PotionContents contents = arrow.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        if (contents != PotionContents.EMPTY) {
            AreaEffectCloud cloud = new AreaEffectCloud(level, x, y, z);
            cloud.setRadius(2.0F);
            cloud.setRadiusOnUse(0.0F);
            cloud.setDuration(100);
            cloud.setWaitTime(5);
            cloud.setPotionContents(contents);
            cloud.setRadiusPerTick(-cloud.getRadius() / (float) cloud.getDuration());
            level.addFreshEntity(cloud);
            arrowEntity.addTag("areaEffect");
        }
    }

    // Tipped arrow leaving a lingering cloud on an entity hit.
    @Inject(method = "doPostHurtEffects", at = @At("HEAD"))
    private void onHit(LivingEntity mob, CallbackInfo ci) {
        if (((AbstractArrow) (Object) this) instanceof Arrow arrowEntity && !arrowEntity.getTags().contains("areaEffect")) {
            spawnCloud(arrowEntity, mob.level(), mob.getX(), mob.getY(), mob.getZ());
        }
    }

    // Tipped arrow leaving a lingering cloud when it sticks in a block.
    @Inject(method = "onHitBlock", at = @At("HEAD"))
    private void onHit(BlockHitResult hit, CallbackInfo ci) {
        AbstractArrow self = (AbstractArrow) (Object) this;
        if (self instanceof Arrow arrowEntity && !arrowEntity.getTags().contains("areaEffect")) {
            spawnCloud(arrowEntity, self.level(), self.getX(), self.getY(), self.getZ());
        }
    }

    // Picking up a tipped arrow returns a plain arrow instead.
    @ModifyExpressionValue(method = "tryPickup", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;getPickupItem()Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack removeEffectsIfPiecing(ItemStack original) {
        AbstractArrow self = (AbstractArrow) (Object) this;
        if (self instanceof Arrow) {
            return Items.ARROW.getDefaultInstance();
        }
        return original;
    }
}