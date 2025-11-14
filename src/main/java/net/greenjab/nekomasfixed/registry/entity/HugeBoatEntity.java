package net.greenjab.nekomasfixed.registry.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.entity.vehicle.AbstractChestBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class HugeBoatEntity extends BigBoatEntity {

	public HugeBoatEntity(EntityType<? extends BigBoatEntity> entityType, World world, Supplier<Item> supplier) {
		super(entityType, world, supplier);
	}

	@Override
	protected int getMaxPassengers() {
		return hasChest()?3:4;
	}

	@Override
	protected Vec3d getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
		float f = 1.6f- this.getPassengerList().indexOf(passenger)*1.25f;
		return new Vec3d(0.0, this.getPassengerAttachmentY(dimensions), f).rotateY(-this.getYaw() * (float) (Math.PI / 180.0));
	}

	@Override
	public float getSpeed() {
		float s = 0.3f+countRowable()*0.1f+(!getBanner().isEmpty()?0.2f:0f);
		return getFirstPassenger() instanceof RaiderEntity ? Math.min(s, 0.6f) : s;
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
	public boolean damage(ServerWorld world, DamageSource source, float amount) {
		return super.damage(world, source, amount*0.6f);
	}
}