package net.greenjab.nekomasfixed.registry.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

import java.util.function.Supplier;

public class HugeBoatEntity extends BigBoatEntity {

	public HugeBoatEntity(EntityType<? extends BigBoatEntity> entityType, Level world, Supplier<Item> supplier) {
		super(entityType, world, supplier);
	}

	@Override
	protected int getMaxPassengers() {
		return hasChest()?3:4;
	}

	@Override
	protected @NonNull Vec3 getPassengerAttachmentPoint(@NonNull Entity passenger, @NonNull EntityDimensions dimensions, float scaleFactor) {
		float f = 1.6f- this.getPassengers().indexOf(passenger)*1.25f;
		return new Vec3(0.0, this.rideHeight(dimensions), f).yRot(-this.getYRot() * (float) (Math.PI / 180.0));
	}

	@Override
	public float getSpeed() {
		float s = 0.3f+countRowable()*0.1f+(!getBanner().isEmpty()?0.2f:0f);
		return getFirstPassenger() instanceof Raider ? Math.min(s, 0.6f) : s;
	}

	@Override
	public float getRotationSpeed() {
		return 0.4f;
	}

	@Override
	public float fakeOffset() {
		return 2.1f;
	}

	@Override
	public boolean hurtServer(@NonNull ServerLevel world, @NonNull DamageSource source, float amount) {
		return super.hurtServer(world, source, amount*0.6f);
	}
}