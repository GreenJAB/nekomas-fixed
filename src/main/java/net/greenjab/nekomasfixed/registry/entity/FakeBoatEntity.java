package net.greenjab.nekomasfixed.registry.entity;

import com.mojang.serialization.Codec;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.entity.vehicle.AbstractChestBoatEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class FakeBoatEntity extends Entity {
	@Nullable
	public BigBoatEntity owner;
	private int counter = 0;

	public FakeBoatEntity(EntityType<FakeBoatEntity> fakeBoatEntityEntityType, World world) {
        super(fakeBoatEntityEntityType, world);
		this.owner = null;
    }

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
	}

	@Override
	protected void readCustomData(ReadView view) {
	}

	@Override
	protected void writeCustomData(WriteView view) {
	}

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return this.owner.getPickBlockStack();
	}


	@Override
	public boolean shouldSave() {
		return false;
	}

	@Override
	public boolean damage(ServerWorld world, DamageSource source, float amount) {
		if (owner==null || owner.getPassengerList().contains(source.getAttacker())) return false;
		return owner.damage(world, source, amount);
	}
	@Override
	public ActionResult interact(PlayerEntity player, Hand hand) {
		if (owner==null || owner.getPassengerList().contains(player)) return ActionResult.PASS;
		return owner.interact(player, hand);
	}


	@Override
	public boolean canHit() {
		return true;
	}

	@Override
	public boolean isCollidable(@Nullable Entity entity) {
		return true;
	}

	@Override
	public boolean collidesWith(Entity other) {
		return (other.isCollidable(this) || other.isPushable()) && !this.isConnectedThroughVehicle(other) && other!=owner;
	}

	public void resetCounter(){
		counter=0;
	}


	@Override
	public void tick() {
		if (this.getEntityWorld() instanceof ServerWorld) {
			counter++;
			if (counter >= 10) {
				this.discard();
			}
		}
		if (owner==null) return;
		List<Entity> list = this.getEntityWorld().getOtherEntities(this, this.getBoundingBox().expand(0.2F, -0.01F, 0.2F), EntityPredicates.canBePushedBy(owner));
		if (!list.isEmpty()) {
			boolean bl = !this.getEntityWorld().isClient() && !(owner.getControllingPassenger() instanceof PlayerEntity);

			for (Entity entity : list) {
				if (!entity.hasPassenger(owner) && !owner.getPassengerList().contains(entity)) {
					if (bl
							&& owner.getPassengerList().size() < owner.getMaxPassengers()
							&& !entity.hasVehicle()
							&& owner.isSmallerThanBoat(entity)
							&& entity instanceof LivingEntity
							&& !entity.getType().isIn(EntityTypeTags.CANNOT_BE_PUSHED_ONTO_BOATS)) {
						entity.startRiding(owner);
					} else {
						this.pushAwayFrom(entity);
					}
				}
			}
		}
	}
}