package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.util.SpottedSheepAccess;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Sheep.class)
public abstract class SheepMixin extends Animal implements SpottedSheepAccess {

    protected SheepMixin(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    @Unique
    private static final EntityDataAccessor<Boolean> SPOTTED = SynchedEntityData.defineId(Sheep.class, EntityDataSerializers.BOOLEAN);

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void initSpottedTracker(SynchedEntityData.Builder entityData, CallbackInfo ci) {
        entityData.define(SPOTTED, false);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void writeSpottedNbt(ValueOutput output, CallbackInfo ci) {
        output.putBoolean("Spotted", this.entityData.get(SPOTTED));
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readSpottedNbt(ValueInput input, CallbackInfo ci) {
        this.entityData.set(SPOTTED, input.getBooleanOr("Spotted", false));
    }

    @Override
    public boolean nekomasfixed$isSpotted() {
        return this.entityData.get(SPOTTED);
    }

    @Override
    public void nekomasfixed$setSpotted(boolean spotted) {
        this.entityData.set(SPOTTED, spotted);
    }
}