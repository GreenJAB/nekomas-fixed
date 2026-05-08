package net.greenjab.nekomasfixed.registry.entity.Moobloom;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AbstractCowEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MoobloomEntity extends CowEntity {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState runAnimationState = new AnimationState();
    private int flowerRegrowTimer = 20 * 60 * 5;
    public static final TrackedData<String> VARIANT = DataTracker.registerData(MoobloomEntity.class, TrackedDataHandlerRegistry.STRING);
    public static final TrackedData<Boolean> SHEARED = DataTracker.registerData(MoobloomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public MoobloomEntity(EntityType<? extends CowEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        EntityData data = super.initialize(world, difficulty, spawnReason, entityData);
        this.dataTracker.set(VARIANT, MoobloomEntityVariants.getRandomVariant().path);
        return data;
    }

    public static DefaultAttributeContainer.Builder createAttributes(){
        return AbstractCowEntity.createCowAttributes();
    }

    @Override
    protected void writeCustomData(WriteView view) {
        super.writeCustomData(view);
        view.putBoolean("Sheared", this.dataTracker.get(SHEARED));
        view.putInt("FlowerRegrowTimer", this.flowerRegrowTimer);
        view.putString("VariantPath", this.dataTracker.get(VARIANT));
    }

    @Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);
        this.setSheared(view.getBoolean("Sheared", false));
        this.flowerRegrowTimer = view.getInt("FlowerRegrowTimer", 20 * 60 * 5);
        this.dataTracker.set(VARIANT, view.getString("VariantPath", "ancient_cow_1"));

    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.SHEARS)) {
            World var5 = this.getEntityWorld();
            if (var5 instanceof ServerWorld serverWorld) {
                if (this.isShearable()) {
                    this.sheared(serverWorld, SoundCategory.PLAYERS, itemStack);
                    this.emitGameEvent(GameEvent.SHEAR, player);
                    itemStack.damage(1, player, hand.getEquipmentSlot());
                    return ActionResult.SUCCESS_SERVER;
                }
            }

            return ActionResult.CONSUME;
        }
        else if (itemStack.isOf(Items.BOWL)) {
            World world = this.getEntityWorld();

            if (!world.isClient() && world instanceof ServerWorld) {
                ItemStack stew = new ItemStack(Items.SUSPICIOUS_STEW);
                stew.set(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS, new SuspiciousStewEffectsComponent(List.of(MoobloomEntityVariants.fromPath(this.dataTracker.get(VARIANT)).effect)));
                player.getStackInHand(Hand.MAIN_HAND).decrementUnlessCreative(1, player);
                player.giveOrDropStack( stew);

            }

            return ActionResult.CONSUME;
        }
                else {
            return super.interactMob(player, hand);
        }
    }

    public void sheared(ServerWorld world, SoundCategory shearedSoundCategory, ItemStack shears) {
        world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, shearedSoundCategory, 1.0F, 1.0F);

            for(int i = 0; i < shears.getCount(); ++i) {
                ItemEntity itemEntity = this.dropStack(world, MoobloomEntityVariants.fromPath(this.dataTracker.get(VARIANT)).flower, 1.0F);
                if (itemEntity != null) {
                    itemEntity.setVelocity(itemEntity.getVelocity().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F));
                }
            }


        this.setSheared(true);
    }


    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(SHEARED, false);
        builder.add(VARIANT, "ancient_cow_1");
    }

    public void setSheared(boolean val){
        this.dataTracker.set(SHEARED, val);
        this.flowerRegrowTimer = 20 * 60 * 5;}
    public boolean isShearable(){return !this.dataTracker.get(SHEARED);}

    public void regrowFlowers(){
        this.flowerRegrowTimer = 20 * 60 * 5;
        this.setSheared(false);
    }


    @Override
    public void mobTick(ServerWorld world) {
        super.mobTick(world);

        if (this.dataTracker.get(SHEARED)) {
            if (this.flowerRegrowTimer > 0) {
                this.flowerRegrowTimer--;
            }

            if (this.flowerRegrowTimer <= 0) {
                this.regrowFlowers();
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getEntityWorld().isClient()) {

            if (this.getVelocity().horizontalLengthSquared() > 0.0001) {
                runAnimationState.startIfNotRunning(this.age);
                idleAnimationState.stop();
            } else {
                idleAnimationState.startIfNotRunning(this.age);
                runAnimationState.stop();
            }
        }
    }
}
