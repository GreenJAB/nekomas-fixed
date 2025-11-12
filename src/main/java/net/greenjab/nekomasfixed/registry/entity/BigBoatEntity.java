package net.greenjab.nekomasfixed.registry.entity;

import com.mojang.serialization.Codec;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.entity.vehicle.AbstractChestBoatEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
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

import java.util.function.Supplier;

public class BigBoatEntity extends AbstractChestBoatEntity {

	protected static final TrackedData<Boolean> CHEST = DataTracker.registerData(BigBoatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	protected static final TrackedData<ItemStack> BANNER = DataTracker.registerData(BigBoatEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

	private FakeBoatEntity front;
	private FakeBoatEntity back;

	public BigBoatEntity(EntityType<? extends AbstractChestBoatEntity> entityType, World world, Supplier<Item> supplier) {
		super(entityType, world, supplier);

	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(CHEST, false);
		builder.add(BANNER, ItemStack.EMPTY);
	}

    @Override
	protected double getPassengerAttachmentY(EntityDimensions dimensions) {
		return dimensions.height() / 3.0F +0.2f;
	}

	@Override
	protected Vec3d getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
		float f = 0.8f- this.getPassengerList().indexOf(passenger)*1.0f;
		return new Vec3d(0.0, this.getPassengerAttachmentY(dimensions), f).rotateY(-this.getYaw() * (float) (Math.PI / 180.0));
	}

	@Override
	protected int getMaxPassengers() {
		return hasChest()?2:3;
	}

	@Override
	protected void writeCustomData(WriteView view) {
		super.writeCustomData(view);
		view.putBoolean("Chest", hasChest());
		if (!getBanner().isEmpty()) {
			view.put("Banner", ItemStack.CODEC, getBanner());
		}
	}

	@Override
	protected void readCustomData(ReadView view) {
		super.readCustomData(view);
		setHasChest(view.read("Chest", Codec.BOOL).orElse(false));
		setBanner(view.read("Banner", ItemStack.CODEC).orElse(ItemStack.EMPTY));
	}

	@Override
	public void tick() {
		super.tick();

		if (front==null || !front.isAlive()) {
			front = EntityTypeRegistry.FAKE_BOAT.create(this.getEntityWorld(), SpawnReason.MOB_SUMMONED);
			front.owner = this;
			this.getEntityWorld().spawnEntity(front);
		}
		if (back==null || !back.isAlive()) {
			back = EntityTypeRegistry.FAKE_BOAT.create(this.getEntityWorld(), SpawnReason.MOB_SUMMONED);
			back.owner = this;
			this.getEntityWorld().spawnEntity(back);
		}

		double dx = fakeOffset() * Math.cos((getYaw()+90f) * Math.PI / 180f);
		double dz = fakeOffset() * Math.sin((getYaw()+90f) * Math.PI / 180f);
		front.setPosition(this.getX() + dx, this.getY(), this.getZ() + dz);
		back.setPosition(this.getX() - dx, this.getY(), this.getZ() - dz);
		front.resetCounter();
		back.resetCounter();
	}

	public float fakeOffset() {
		return 1.25f;
	}

	@Override
	public ActionResult interact(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(Items.CHEST)) {
			if (!hasChest() && getPassengerList().size()<4) {
				setHasChest(true);
				itemStack.decrement(1);
				player.getEntityWorld().playSoundFromEntity(null, this, SoundEvents.ENTITY_DONKEY_CHEST, SoundCategory.PLAYERS, 1.0F, 1.0F);
			}
			return ActionResult.SUCCESS;
		} else if (itemStack.isIn(ItemTags.BANNERS)) {
			if (getBanner().isEmpty()) {
				setBanner(itemStack.copyWithCount(1));
				itemStack.decrement(1);
				player.getEntityWorld().playSoundFromEntity(null, this, SoundEvents.ENTITY_DONKEY_CHEST, SoundCategory.PLAYERS, 1.0F, 1.0F);
			}
			return ActionResult.SUCCESS;
		} else if (itemStack.isOf(Items.SHEARS)) {
			if (!getBanner().isEmpty()) {
				player.getEntityWorld().playSoundFromEntity(null, this, SoundEvents.ENTITY_COPPER_GOLEM_SHEAR, SoundCategory.PLAYERS, 1.0F, 1.0F);
				ItemStack banner = getBanner().copy();
				setBanner(ItemStack.EMPTY);
				if (player.getEntityWorld() instanceof ServerWorld serverWorld) {
					this.dropStack(serverWorld, banner, 1.5F);
					itemStack.damage(1, player);
				}
			}
			return ActionResult.SUCCESS;
		} else {
			return super.interact(player, hand);
		}
	}

	public void setHasChest(boolean hasChest) {
		this.dataTracker.set(CHEST, hasChest);
	}
	public boolean hasChest() { return this.dataTracker.get(CHEST);}

	public void setBanner(ItemStack banner) {
		this.dataTracker.set(BANNER, banner);
	}
	public ItemStack getBanner() {return this.dataTracker.get(BANNER);}


	public float getSpeed() {
		float s = 0.4f+countRowable()*0.1f+(!getBanner().isEmpty()?0.15f:0f);
		return getFirstPassenger() instanceof RaiderEntity ? Math.min(s, 0.6f) : s;
	}

	public float getRotationSpeed() {
		return 0.6f;
	}

	public int countRowable() {
		int i = 0;
		java.util.Iterator<Entity> iter = getPassengerList().stream().iterator();
		while (iter.hasNext()){
			Entity e = iter.next();
			if (e instanceof PlayerEntity || e instanceof VillagerEntity || e instanceof RaiderEntity) {
				i++;
			}
		}
		return i;
	}

	@Override
	public void killAndDropSelf(ServerWorld world, DamageSource damageSource) {
		if (front!=null) this.front.remove(RemovalReason.DISCARDED);
		if (back!=null) this.back.remove(RemovalReason.DISCARDED);
		world.spawnEntity(this.back);
		this.killAndDropItem(world, this.asItem());
		if (world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
			if (hasChest()) ItemScatterer.spawn(world, this.getX(), this.getY(), this.getZ(), Items.CHEST.getDefaultStack());
			ItemScatterer.spawn(world, this.getX(), this.getY(), this.getZ(), getBanner());
		}
		this.onBroken(damageSource, world, this);
	}

	@Override
	public void remove(RemovalReason reason) {
		if (front!=null) this.front.remove(RemovalReason.DISCARDED);
		if (back!=null) this.back.remove(RemovalReason.DISCARDED);
		if (!this.getEntityWorld().isClient() && reason.shouldDestroy()) {
			if (hasChest()) ItemScatterer.spawn(this.getEntityWorld(), this.getX(), this.getY(), this.getZ(), Items.CHEST.getDefaultStack());
			ItemScatterer.spawn(this.getEntityWorld(), this.getX(), this.getY(), this.getZ(), getBanner());
		}

		super.remove(reason);
	}

	@Override
	public void onBroken(DamageSource source, ServerWorld world, Entity vehicle) {
		if (front!=null) this.front.remove(RemovalReason.DISCARDED);
		if (back!=null) this.back.remove(RemovalReason.DISCARDED);
		if (world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
			if (hasChest()) ItemScatterer.spawn(world, vehicle.getX(), vehicle.getY(), vehicle.getZ(), Items.CHEST.getDefaultStack());
			ItemScatterer.spawn(world, vehicle.getX(), vehicle.getY(), vehicle.getZ(), getBanner());
		}
		super.onBroken(source, world, vehicle);
	}


	public ActionResult open(PlayerEntity player) {
		if (hasChest()) return super.open(player);
		return ActionResult.PASS;
	}

	@Override
	public void openInventory(PlayerEntity player) {
		if (hasChest()) super.openInventory(player);

	}

	@Override
	public StackReference getInventoryStackReference(int slot) {
		if (hasChest()) return super.getInventoryStackReference(slot);
		return StackReference.EMPTY;
	}

	public boolean canBeLeashedTo(Entity entity) {
		return false;
	}

	@Override
	public boolean collidesWith(Entity other) {
		return !(other==front||other==back) && super.collidesWith(other);
	}

}