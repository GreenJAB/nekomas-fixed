package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.entity.DrenchedEntity;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.conversion.EntityConversionContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkeletonEntity.class)
public abstract class SkeletonEntityMixin extends HostileEntity {

    @Unique private int drenchedInWaterTime = 0;

    protected SkeletonEntityMixin(EntityType<? extends HostileEntity> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tickDrenchedConversion(CallbackInfo ci) {
        if (this.getEntityWorld().isClient() || (Object) this instanceof DrenchedEntity) {
            return;
        }

        if (this.isAlive() && this.isSubmergedIn(FluidTags.WATER)) {
            this.drenchedInWaterTime++;
            if (this.drenchedInWaterTime >= 600) {
                this.convertToDrenched((ServerWorld) this.getEntityWorld());
            }
        } else {
            this.drenchedInWaterTime = 0;
        }
    }

    @Unique
    private void convertToDrenched(ServerWorld world) {
        this.convertTo(EntityTypeRegistry.DRENCHED, EntityConversionContext.create(this, true, true), (drenched) -> {
            if (drenched.getMainHandStack().isOf(Items.BOW)) {
                drenched.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            }
        });
    }
}