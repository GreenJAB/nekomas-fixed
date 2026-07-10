package net.greenjab.nekomasfixed.registry.entity;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class FakeBoat extends Entity {
	@Nullable
	public BigBoat owner = null;
	private int counter = 0;

	public FakeBoat(EntityType<FakeBoat> fakeBoatEntityEntityType, Level level) {
        super(fakeBoatEntityEntityType, level);
    }

	@Override
	protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder) {
	}

	@Override
	protected void readAdditionalSaveData(@NonNull ValueInput view) {
	}

	@Override
	protected void addAdditionalSaveData(@NonNull ValueOutput view) {
	}

	@Nullable
	@Override
	public ItemStack getPickResult() {
		return (owner!=null)?this.owner.getPickResult():null;
	}

	@Override
	public boolean shouldBeSaved() {
		return false;
	}

	@Override
	public boolean hurtServer(@NonNull ServerLevel level, @NonNull DamageSource source, float amount) {
		if (owner==null || owner.getPassengers().contains(source.getEntity())) return false;
		return owner.hurtServer(level, source, amount);
	}
	@Override
	public @NonNull InteractionResult interact(@NonNull Player player, @NonNull InteractionHand hand, @NonNull Vec3 location) {
		if (owner==null || owner.getPassengers().contains(player)) return InteractionResult.PASS;
		return owner.interact(player, hand, location);
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	public boolean canBeCollidedWith(@Nullable Entity entity) {
		return true;
	}

	@Override
	public boolean canCollideWith(Entity other) {
		return (other.canBeCollidedWith(this) || other.isPushable()) && !this.isPassengerOfSameVehicle(other) && other!=owner;
	}

	public void resetCounter(){
		counter=0;
	}

	@Override
	public void tick() {
		if (this.level() instanceof ServerLevel) {
			counter++;
			if (counter >= 10) {
				this.discard();
			}
		}
		if (owner==null) return;
		List<Entity> list = this.level().getEntities(this, this.getBoundingBox().inflate(0.2F, -0.01F, 0.2F), EntitySelector.pushableBy(owner));
		if (!list.isEmpty()) {
			boolean bl = !this.level().isClientSide() && !(owner.getControllingPassenger() instanceof Player);

			for (Entity entity : list) {
				if (!entity.hasPassenger(owner) && !owner.getPassengers().contains(entity)) {
					if (bl
							&& owner.getPassengers().size() < owner.getMaxPassengers()
							&& !entity.isPassenger()
							&& owner.hasEnoughSpaceFor(entity)
							&& entity instanceof LivingEntity
							&& !entity.is(EntityTypeTags.CANNOT_BE_PUSHED_ONTO_BOATS)) {
						entity.startRiding(owner);
					} else {
						this.push(entity);
					}
				}
			}
		}
	}
}