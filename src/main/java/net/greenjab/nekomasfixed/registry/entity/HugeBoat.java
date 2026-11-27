package net.greenjab.nekomasfixed.registry.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

import java.util.function.Supplier;

public class HugeBoat extends BigBoat {

    public HugeBoat(EntityType<? extends Boat> type, Level level, Supplier<Item> item) {
        super(type, level, item);
    }

    @Override
    protected int getMaxPassengers() {
        return hasChest() ? 3 : 4;
    }

    @Override
    protected @NonNull Vec3 getPassengerAttachmentPoint(@NonNull Entity passenger, @NonNull EntityDimensions dimensions, float partialTick) {
        float f = 1.6F - this.getPassengers().indexOf(passenger) * 1.25F;
        return new Vec3(0.0, this.rideHeight(dimensions), f).yRot(-this.getYRot() * (float) (Math.PI / 180.0));
    }

    @Override
    public float getSpeed() {
        return 0.3F + countRowable() * 0.1F + (!getBanner().isEmpty() ? 0.2F : 0.0F);
    }

    @Override
    public float getRotationSpeed() {
        return 0.4F;
    }

    @Override
    public float fakeOffset() {
        return 2.1F;
    }

    @Override
    public boolean hurt(@NonNull DamageSource source, float amount) {
        return super.hurt(source, amount * 0.6F);
    }
}
