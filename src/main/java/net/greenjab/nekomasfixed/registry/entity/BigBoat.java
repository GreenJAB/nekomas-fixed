package net.greenjab.nekomasfixed.registry.entity;

import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

import java.util.function.Supplier;

public class BigBoat extends ChestBoat {
    private static final EntityDataAccessor<Boolean> CHEST = SynchedEntityData.defineId(BigBoat.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<ItemStack> BANNER = SynchedEntityData.defineId(BigBoat.class, EntityDataSerializers.ITEM_STACK);

    private final Supplier<Item> item;
    private FakeBoat front;
    private FakeBoat back;

    public BigBoat(EntityType<? extends Boat> type, Level level, Supplier<Item> item) {
        super(type, level);
        this.item = item;
        this.setVariant(Boat.Type.OAK);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CHEST, false);
        builder.define(BANNER, ItemStack.EMPTY);
    }

    @Override
    public @NonNull Item getDropItem() {
        return item.get();
    }

    protected double rideHeight(EntityDimensions dimensions) {
        return dimensions.height() / 3.0F + 0.2F;
    }

    @Override
    protected @NonNull Vec3 getPassengerAttachmentPoint(@NonNull Entity passenger, @NonNull EntityDimensions dimensions, float partialTick) {
        float f = 0.8F - this.getPassengers().indexOf(passenger) * 1.0F;
        return new Vec3(0.0, this.rideHeight(dimensions), f).yRot(-this.getYRot() * (float) (Math.PI / 180.0));
    }

    @Override
    protected int getMaxPassengers() {
        return hasChest() ? 2 : 3;
    }

    @Override
    protected void addAdditionalSaveData(@NonNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Chest", hasChest());
        if (!getBanner().isEmpty()) tag.put("Banner", getBanner().save(this.registryAccess()));
    }

    @Override
    protected void readAdditionalSaveData(@NonNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setHasChest(tag.getBoolean("Chest"));
        setBanner(ItemStack.parseOptional(this.registryAccess(), tag.getCompound("Banner")));
    }

    @Override
    public void tick() {
        super.tick();
        if (!(this.level() instanceof ServerLevel)) return;

        if (front == null || !front.isAlive()) front = spawnFake();
        if (back == null || !back.isAlive()) back = spawnFake();

        double dx = fakeOffset() * Math.cos((getYRot() + 90.0F) * Math.PI / 180.0F);
        double dz = fakeOffset() * Math.sin((getYRot() + 90.0F) * Math.PI / 180.0F);
        front.setPos(this.getX() + dx, this.getY(), this.getZ() + dz);
        back.setPos(this.getX() - dx, this.getY(), this.getZ() - dz);
        front.resetCounter();
        back.resetCounter();
    }

    private FakeBoat spawnFake() {
        FakeBoat fake = EntityTypeRegistry.FAKE_BOAT.create(this.level());
        fake.setOwner(this);
        fake.setPos(this.position());
        this.level().addFreshEntity(fake);
        return fake;
    }

    public float fakeOffset() {
        return 1.15F;
    }

    @Override
    public @NonNull AABB getBoundingBoxForCulling() {
        double r = fakeOffset() + 1.0;
        return this.getBoundingBox().inflate(r, 0.0, r).expandTowards(0.0, 3.5, 0.0);
    }

    @Override
    public @NonNull InteractionResult interact(Player player, @NonNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(Items.CHEST)) {
            if (!hasChest() && getPassengers().size() < 4) {
                setHasChest(true);
                stack.consume(1, player);
                this.playSound(SoundEvents.DONKEY_CHEST, 1.0F, 1.0F);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else if (stack.is(ItemTags.BANNERS)) {
            if (getBanner().isEmpty()) {
                setBanner(stack.copyWithCount(1));
                stack.consume(1, player);
                this.playSound(SoundEvents.DONKEY_CHEST, 1.0F, 1.0F);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else if (stack.is(Items.SHEARS)) {
            if (!getBanner().isEmpty()) {
                this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
                if (!this.level().isClientSide) {
                    this.spawnAtLocation(getBanner().copy(), 1.5F);
                    stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                }
                setBanner(ItemStack.EMPTY);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.interact(player, hand);
    }

    public void setHasChest(boolean hasChest) {
        this.entityData.set(CHEST, hasChest);
    }

    public boolean hasChest() {
        return this.entityData.get(CHEST);
    }

    public void setBanner(ItemStack banner) {
        this.entityData.set(BANNER, banner);
    }

    public ItemStack getBanner() {
        return this.entityData.get(BANNER);
    }

    public float getSpeed() {
        return 0.4F + countRowable() * 0.1F + (!getBanner().isEmpty() ? 0.15F : 0.0F);
    }

    public float getRotationSpeed() {
        return 0.6F;
    }

    public int countRowable() {
        int i = 0;
        for (Entity e : getPassengers()) {
            if (e instanceof Player || e instanceof Villager || e instanceof Raider) i++;
        }
        return i;
    }

    @Override
    public void remove(@NonNull RemovalReason reason) {
        if (front != null) front.discard();
        if (back != null) back.discard();
        if (!this.level().isClientSide && reason.shouldDestroy()) {
            if (hasChest()) Containers.dropItemStack(this.level(), this.getX(), this.getY(), this.getZ(), new ItemStack(Items.CHEST));
            Containers.dropItemStack(this.level(), this.getX(), this.getY(), this.getZ(), getBanner());
        }
        super.remove(reason);
    }

    @Override
    public @NonNull InteractionResult interactWithContainerVehicle(@NonNull Player player) {
        return hasChest() ? super.interactWithContainerVehicle(player) : InteractionResult.PASS;
    }

    @Override
    public void openCustomInventoryScreen(@NonNull Player player) {
        if (hasChest()) super.openCustomInventoryScreen(player);
    }

    @Override
    public @NonNull SlotAccess getChestVehicleSlot(int slot) {
        return hasChest() ? super.getChestVehicleSlot(slot) : SlotAccess.NULL;
    }

    @Override
    public boolean canCollideWith(@NonNull Entity other) {
        return !(other instanceof FakeBoat) && super.canCollideWith(other);
    }

    @Override
    public boolean hurt(@NonNull DamageSource source, float amount) {
        return super.hurt(source, amount * 0.8F);
    }
}
