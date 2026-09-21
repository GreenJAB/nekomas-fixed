package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.target_access_class.SpottedSheepAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Sheep.class)
public abstract class SpottedSheepMixin extends Animal implements SpottedSheepAccess {
    @Unique
    private static final EntityDataAccessor<Boolean> SPOTTED = SynchedEntityData.defineId(Sheep.class, EntityDataSerializers.BOOLEAN);

    protected SpottedSheepMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void defineSpottedTracker(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(SPOTTED, false);
    }

    @Override
    public boolean nekomasfixed$isSpotted() {
        return this.entityData.get(SPOTTED);
    }

    @Override
    public void nekomasfixed$setSpotted(boolean spotted) {
        this.entityData.set(SPOTTED, spotted);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void writeSpottedNbt(CompoundTag tag, CallbackInfo ci) {
        tag.putBoolean("nekomasfixed.spotted", nekomasfixed$isSpotted());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readSpottedNbt(CompoundTag tag, CallbackInfo ci) {
        nekomasfixed$setSpotted(tag.getBoolean("nekomasfixed.spotted"));
    }
}