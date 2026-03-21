package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin {

    @Shadow
    public abstract ItemStack getItemStack();

    @Inject(method = "onHit", at = @At("HEAD"))
    private void onHit(LivingEntity target, CallbackInfo ci) {
        if (((PersistentProjectileEntity)(Object) this) instanceof ArrowEntity arrowEntity && !arrowEntity.getCommandTags().contains("areaEffect")) {
            ItemStack arrow = arrowEntity.getItemStack();
            PotionContentsComponent contents = arrow.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT);
            if (contents != null && contents != PotionContentsComponent.DEFAULT) {
                AreaEffectCloudEntity areaEffectCloudEntity = makeAreaEffectCloudEntity(target.getEntityWorld(), target.getX(), target.getY(), target.getZ(), contents);
                target.getEntityWorld().spawnEntity(areaEffectCloudEntity);
                arrowEntity.addCommandTag("areaEffect");
            }
        }
    }

    @Inject(method = "setInGround", at = @At("HEAD"))
    private void onHit(boolean inGround, CallbackInfo ci) {
        if (((PersistentProjectileEntity)(Object) this) instanceof ArrowEntity arrowEntity && !arrowEntity.getCommandTags().contains("areaEffect")) {
            ItemStack arrow = arrowEntity.getItemStack();
            PotionContentsComponent contents = arrow.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT);
            if (contents != null && contents != PotionContentsComponent.DEFAULT) {
                AreaEffectCloudEntity areaEffectCloudEntity = makeAreaEffectCloudEntity(arrowEntity.getEntityWorld(), arrowEntity.getX(), arrowEntity.getY(), arrowEntity.getZ(), contents);
                arrowEntity.getEntityWorld().spawnEntity(areaEffectCloudEntity);
                arrowEntity.addCommandTag("areaEffect");
            }
        }
    }

    @ModifyExpressionValue(method = "tryPickup", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;asItemStack()Lnet/minecraft/item/ItemStack;"))
    private ItemStack removeEffectsIfPiecing(ItemStack original) {
        PersistentProjectileEntity PPE = (PersistentProjectileEntity)(Object) this;
        if (PPE instanceof ArrowEntity) return Items.ARROW.getDefaultStack();
        return original;
    }

    @Unique
    @NotNull
    private static AreaEffectCloudEntity makeAreaEffectCloudEntity(World target, double target1, double target2, double target3, PotionContentsComponent contents) {
        AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(target, target1, target2, target3);
        areaEffectCloudEntity.setRadius(2.0F);
        areaEffectCloudEntity.setRadiusOnUse(0F);
        areaEffectCloudEntity.setDuration(100);
        areaEffectCloudEntity.setWaitTime(5);
        areaEffectCloudEntity.setPotionContents(contents);
        areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float) areaEffectCloudEntity.getDuration());
        return areaEffectCloudEntity;
    }

}
