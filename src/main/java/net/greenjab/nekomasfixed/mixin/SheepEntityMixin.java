package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.util.SpottedSheepAccess;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin extends AnimalEntity implements SpottedSheepAccess {

    protected SheepEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    private static final TrackedData<Boolean> SPOTTED = DataTracker.registerData(SheepEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void initSpottedTracker(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(SPOTTED, false);
    }

    @Inject(method = "writeCustomData", at = @At("TAIL"))
    private void writeSpottedNbt(WriteView view, CallbackInfo ci) {
        view.putBoolean("Spotted", this.dataTracker.get(SPOTTED));
    }

    @Inject(method = "readCustomData", at = @At("TAIL"))
    private void readSpottedNbt(ReadView view, CallbackInfo ci) {
        this.dataTracker.set(SPOTTED, view.getBoolean("Spotted", false));
    }

    @Override
    public boolean nekomasfixed$isSpotted() {
        return this.dataTracker.get(SPOTTED);
    }

    @Override
    public void nekomasfixed$setSpotted(boolean spotted) {
        this.dataTracker.set(SPOTTED, spotted);
    }
}