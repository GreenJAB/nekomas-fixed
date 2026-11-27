package net.greenjab.nekomasfixed.registry.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class FakeBoat extends Entity {
    private static final EntityDataAccessor<Integer> OWNER = SynchedEntityData.defineId(FakeBoat.class, EntityDataSerializers.INT);

    @Nullable
    private BigBoat owner;
    private int counter = 0;

    public FakeBoat(EntityType<FakeBoat> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder) {
        builder.define(OWNER, -1);
    }

    public void setOwner(BigBoat owner) {
        this.owner = owner;
        this.entityData.set(OWNER, owner.getId());
    }

    @Nullable
    private BigBoat getOwner() {
        if (owner == null && this.level().getEntity(this.entityData.get(OWNER)) instanceof BigBoat boat) owner = boat;
        return owner;
    }

    @Override
    protected void readAdditionalSaveData(@NonNull CompoundTag tag) {
    }

    @Override
    protected void addAdditionalSaveData(@NonNull CompoundTag tag) {
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        BigBoat owner = getOwner();
        return owner != null ? owner.getPickResult() : null;
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    public boolean hurt(@NonNull DamageSource source, float amount) {
        BigBoat owner = getOwner();
        if (owner == null || owner.getPassengers().contains(source.getEntity())) return false;
        return owner.hurt(source, amount);
    }

    @Override
    public @NonNull InteractionResult interact(@NonNull Player player, @NonNull InteractionHand hand) {
        BigBoat owner = getOwner();
        if (owner == null || owner.getPassengers().contains(player)) return InteractionResult.PASS;
        return owner.interact(player, hand);
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean canCollideWith(@NonNull Entity other) {
        return (other.canBeCollidedWith() || other.isPushable()) && !this.isPassengerOfSameVehicle(other) && other != getOwner();
    }

    public void resetCounter() {
        counter = 0;
    }

    @Override
    public void tick() {
        if (this.level() instanceof ServerLevel && ++counter >= 10) {
            this.discard();
        }
        BigBoat owner = getOwner();
        if (owner == null) return;
        List<Entity> list = this.level().getEntities(this, this.getBoundingBox().inflate(0.2F, -0.01F, 0.2F), EntitySelector.pushableBy(owner));
        boolean canBoard = !this.level().isClientSide && !(owner.getControllingPassenger() instanceof Player);
        for (Entity entity : list) {
            if (entity.hasPassenger(owner) || owner.getPassengers().contains(entity)) continue;
            if (canBoard
                    && owner.getPassengers().size() < owner.getMaxPassengers()
                    && !entity.isPassenger()
                    && owner.hasEnoughSpaceFor(entity)
                    && entity instanceof LivingEntity
                    && !(entity instanceof WaterAnimal)
                    && !(entity instanceof Player)) {
                entity.startRiding(owner);
            } else {
                this.push(entity);
            }
        }
    }
}
