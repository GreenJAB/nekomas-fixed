package net.greenjab.nekomasfixed.registry.entity;

import com.mojang.serialization.Codec;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.vehicle.boat.AbstractChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

import java.util.function.Supplier;

public class BigBoatEntity extends AbstractChestBoat {

	protected static final EntityDataAccessor<Boolean> CHEST = SynchedEntityData.defineId(BigBoatEntity.class, EntityDataSerializers.BOOLEAN);
	protected static final EntityDataAccessor<ItemStack> BANNER = SynchedEntityData.defineId(BigBoatEntity.class, EntityDataSerializers.ITEM_STACK);

	private FakeBoatEntity front;
	private FakeBoatEntity back;

	public BigBoatEntity(EntityType<? extends AbstractChestBoat> entityType, Level world, Supplier<Item> supplier) {
		super(entityType, world, supplier);

	}

	@Override
	protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder) {
		super.defineSynchedData(builder);
		builder.define(CHEST, false);
		builder.define(BANNER, ItemStack.EMPTY);
	}

    @Override
	protected double rideHeight(EntityDimensions dimensions) {
		return dimensions.height() / 3.0F +0.2f;
	}

	@Override
	protected @NonNull Vec3 getPassengerAttachmentPoint(@NonNull Entity passenger, @NonNull EntityDimensions dimensions, float scaleFactor) {
		float f = 0.8f- this.getPassengers().indexOf(passenger)*1.0f;
		return new Vec3(0.0, this.rideHeight(dimensions), f).yRot(-this.getYRot() * (float) (Math.PI / 180.0));
	}

	@Override
	protected int getMaxPassengers() {
		return hasChest()?2:3;
	}

	@Override
	protected void addAdditionalSaveData(@NonNull ValueOutput view) {
		super.addAdditionalSaveData(view);
		view.putBoolean("Chest", hasChest());
		if (!getBanner().isEmpty()) {
			view.store("Banner", ItemStack.CODEC, getBanner());
		}
	}

	@Override
	protected void readAdditionalSaveData(@NonNull ValueInput view) {
		super.readAdditionalSaveData(view);
		setHasChest(view.read("Chest", Codec.BOOL).orElse(false));
		setBanner(view.read("Banner", ItemStack.CODEC).orElse(ItemStack.EMPTY));
	}

	@Override
	public void tick() {
		super.tick();

		if (front==null || !front.isAlive()) {
			front = EntityTypeRegistry.FAKE_BOAT.create(this.level(), EntitySpawnReason.MOB_SUMMONED);
			if (front!=null) {
				front.owner = this;
				this.level().addFreshEntity(front);
			}
		}
		if (back==null || !back.isAlive()) {
			back = EntityTypeRegistry.FAKE_BOAT.create(this.level(), EntitySpawnReason.MOB_SUMMONED);
			if (back!=null) {
				back.owner = this;
				this.level().addFreshEntity(back);
			}
		}

		double dx = fakeOffset() * Math.cos((getYRot()+90f) * Math.PI / 180f);
		double dz = fakeOffset() * Math.sin((getYRot()+90f) * Math.PI / 180f);
		front.setPos(this.getX() + dx, this.getY(), this.getZ() + dz);
		back.setPos(this.getX() - dx, this.getY(), this.getZ() - dz);
		front.resetCounter();
		back.resetCounter();
	}

	public float fakeOffset() {
		return 1.15f;
	}

	@Override
	public @NonNull InteractionResult interact(Player player, @NonNull InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (itemStack.is(Items.CHEST)) {
			if (!hasChest() && getPassengers().size()<4) {
				setHasChest(true);
				itemStack.shrink(1);
				player.level().playSound(null, this, SoundEvents.DONKEY_CHEST, SoundSource.PLAYERS, 1.0F, 1.0F);
			}
			return InteractionResult.SUCCESS;
		} else if (itemStack.is(ItemTags.BANNERS)) {
			if (getBanner().isEmpty()) {
				setBanner(itemStack.copyWithCount(1));
				itemStack.shrink(1);
				player.level().playSound(null, this, SoundEvents.DONKEY_CHEST, SoundSource.PLAYERS, 1.0F, 1.0F);
			}
			return InteractionResult.SUCCESS;
		} else if (itemStack.is(Items.SHEARS)) {
			if (!getBanner().isEmpty()) {
				player.level().playSound(null, this, SoundEvents.COPPER_GOLEM_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
				ItemStack banner = getBanner().copy();
				setBanner(ItemStack.EMPTY);
				if (player.level() instanceof ServerLevel serverWorld) {
					this.spawnAtLocation(serverWorld, banner, 1.5F);
					itemStack.hurtWithoutBreaking(1, player);
				}
			}
			return InteractionResult.SUCCESS;
		} else {
			return super.interact(player, hand);
		}
	}

	public void setHasChest(boolean hasChest) {
		this.entityData.set(CHEST, hasChest);
	}
	public boolean hasChest() { return this.entityData.get(CHEST);}

	public void setBanner(ItemStack banner) {
		this.entityData.set(BANNER, banner);
	}
	public ItemStack getBanner() {return this.entityData.get(BANNER);}


	public float getSpeed() {
		float s = 0.4f+countRowable()*0.1f+(!getBanner().isEmpty()?0.15f:0f);
		return getFirstPassenger() instanceof Raider ? Math.min(s, 0.6f) : s;
	}

	public float getRotationSpeed() {
		return 0.6f;
	}

	public int countRowable() {
		int i = 0;
		java.util.Iterator<Entity> iter = getPassengers().stream().iterator();
		while (iter.hasNext()){
			Entity e = iter.next();
			if (e instanceof Player || e instanceof Villager || e instanceof Raider) {
				i++;
			}
		}
		return i;
	}

	@Override
	public void destroy(@NonNull ServerLevel world, @NonNull DamageSource damageSource) {
		if (front!=null) this.front.remove(RemovalReason.DISCARDED);
		if (back!=null) this.back.remove(RemovalReason.DISCARDED);
		this.destroy(world, this.getDropItem());
		if (world.getGameRules().get(GameRules.ENTITY_DROPS)) {
			if (hasChest()) Containers.dropItemStack(world, this.getX(), this.getY(), this.getZ(), Items.CHEST.getDefaultInstance());
			Containers.dropItemStack(world, this.getX(), this.getY(), this.getZ(), getBanner());
		}
		this.chestVehicleDestroyed(damageSource, world, this);
	}

	@Override
	public void remove(@NonNull RemovalReason reason) {
		if (front!=null) this.front.remove(RemovalReason.DISCARDED);
		if (back!=null) this.back.remove(RemovalReason.DISCARDED);
		if (!this.level().isClientSide() && reason.shouldDestroy()) {
			if (hasChest()) Containers.dropItemStack(this.level(), this.getX(), this.getY(), this.getZ(), Items.CHEST.getDefaultInstance());
			Containers.dropItemStack(this.level(), this.getX(), this.getY(), this.getZ(), getBanner());
		}

		super.remove(reason);
	}

	@Override
	public void chestVehicleDestroyed(@NonNull DamageSource source, @NonNull ServerLevel world, @NonNull Entity vehicle) {
		if (front!=null) this.front.remove(RemovalReason.DISCARDED);
		if (back!=null) this.back.remove(RemovalReason.DISCARDED);
		if (world.getGameRules().get(GameRules.ENTITY_DROPS)) {
			if (hasChest()) Containers.dropItemStack(world, vehicle.getX(), vehicle.getY(), vehicle.getZ(), Items.CHEST.getDefaultInstance());
			Containers.dropItemStack(world, vehicle.getX(), vehicle.getY(), vehicle.getZ(), getBanner());
		}
		super.chestVehicleDestroyed(source, world, vehicle);
	}


	public @NonNull InteractionResult interactWithContainerVehicle(@NonNull Player player) {
		if (hasChest()) return super.interactWithContainerVehicle(player);
		return InteractionResult.PASS;
	}

	@Override
	public void openCustomInventoryScreen(@NonNull Player player) {
		if (hasChest()) super.openCustomInventoryScreen(player);

	}

	@Override
	public SlotAccess getChestVehicleSlot(int slot) {
		if (hasChest()) return super.getChestVehicleSlot(slot);
		return null;
	}

	public boolean canHaveALeashAttachedTo(@NonNull Entity entity) {
		return false;
	}

	@Override
	public boolean canCollideWith(@NonNull Entity other) {
		return !(other==front||other==back) && super.canCollideWith(other);
	}

	@Override
	public boolean hurtServer(@NonNull ServerLevel world, @NonNull DamageSource source, float amount) {
		return super.hurtServer(world, source, amount*0.8f);
	}
}