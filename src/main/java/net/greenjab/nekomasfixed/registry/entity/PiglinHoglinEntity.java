//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.greenjab.nekomasfixed.registry.entity;

import java.util.Objects;
import java.util.function.DoubleSupplier;
import net.minecraft.entity.EntityAttachmentType;
import net.minecraft.entity.EntityAttachments;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.Leashable;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;

public class PiglinHoglinEntity extends AbstractHorseEntity {
    private static final float field_63324 = 42.16F;
    private static final double field_63325 = (double)0.5F;
    private static final double field_63326 = 0.06666666666666667;
    private static final double field_63327 = (double)9.0F;
    private static final double field_63323 = (double)1.0F;
    private static final EntityDimensions BABY_BASE_DIMENSIONS;

    public PiglinHoglinEntity(EntityType<? extends PiglinHoglinEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_OTHER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, -1.0F);
    }

    public static DefaultAttributeContainer.Builder createZombieHorseAttributes() {
        return createBaseHorseAttributes().add(EntityAttributes.MAX_HEALTH, (double)25.0F);
    }

    public ActionResult interact(PlayerEntity player, Hand hand) {
        this.setPersistent();
        return super.interact(player, hand);
    }

    public boolean canImmediatelyDespawn(double distanceSquared) {
        return true;
    }

    public boolean isControlledByMob() {
        return this.getFirstPassenger() instanceof MobEntity;
    }

    protected void initAttributes(Random random) {
        EntityAttributeInstance var10000 = this.getAttributeInstance(EntityAttributes.JUMP_STRENGTH);
        Objects.requireNonNull(random);
        var10000.setBaseValue(getBaseJumpStrength(random::nextDouble));
        var10000 = this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
        Objects.requireNonNull(random);
        var10000.setBaseValue(getBaseMovementSpeed(random::nextDouble));
    }

    private static double getBaseJumpStrength(DoubleSupplier randomSupplier) {
        return (double)0.5F + randomSupplier.getAsDouble() * 0.06666666666666667 + randomSupplier.getAsDouble() * 0.06666666666666667 + randomSupplier.getAsDouble() * 0.06666666666666667;
    }

    private static double getBaseMovementSpeed(DoubleSupplier randomSupplier) {
        return ((double)9.0F + randomSupplier.getAsDouble() * (double)1.0F + randomSupplier.getAsDouble() * (double)1.0F + randomSupplier.getAsDouble() * (double)1.0F) / (double)42.16F;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ZOMBIE_HORSE_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ZOMBIE_HORSE_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_ZOMBIE_HORSE_HURT;
    }

    protected SoundEvent getAngrySound() {
        return SoundEvents.ENTITY_ZOMBIE_HORSE_ANGRY;
    }

    protected SoundEvent getEatSound() {
        return SoundEvents.ENTITY_ZOMBIE_HORSE_EAT;
    }

    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    public boolean canEat() {
        return false;
    }

    protected void initCustomGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(3, new TemptGoal(this, (double)1.25F, (stack) -> stack.isIn(ItemTags.ZOMBIE_HORSE_FOOD), false));
    }

    public @Nullable EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        if (spawnReason == SpawnReason.NATURAL) {
            PiglinEntity piglinentity = (PiglinEntity)EntityType.PIGLIN.create(this.getEntityWorld(), SpawnReason.JOCKEY);
            if (piglinentity != null) {
                piglinentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0F);
                piglinentity.initialize(world, difficulty, spawnReason, (EntityData)null);
                piglinentity.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SPEAR));
                piglinentity.startRiding(this, false, false);
            }
        }

        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        boolean bl = !this.isBaby() && this.isTame() && player.shouldCancelInteraction();
        if (!this.hasPassengers() && !bl) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (!itemStack.isEmpty()) {
                if (this.isBreedingItem(itemStack)) {
                    return this.interactHorse(player, itemStack);
                }

                if (!this.isTame()) {
                    this.playAngrySound();
                    return ActionResult.SUCCESS;
                }
            }

            return super.interactMob(player, hand);
        } else {
            return super.interactMob(player, hand);
        }
    }

    public boolean canUseSlot(EquipmentSlot slot) {
        return true;
    }

    public boolean canBeLeashed() {
        return this.isTame() || !this.isControlledByMob();
    }

    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(ItemTags.ZOMBIE_HORSE_FOOD);
    }

    protected EquipmentSlot getDaylightProtectionSlot() {
        return EquipmentSlot.BODY;
    }

    public Vec3d[] getQuadLeashOffsets() {
        return Leashable.createQuadLeashOffsets(this, 0.04, 0.41, 0.18, 0.73);
    }

    public EntityDimensions getBaseDimensions(EntityPose pose) {
        return this.isBaby() ? BABY_BASE_DIMENSIONS : super.getBaseDimensions(pose);
    }

    public float getRiderChargingSpeedMultiplier() {
        return 1.4F;
    }

    static {
        BABY_BASE_DIMENSIONS = EntityType.ZOMBIE_HORSE.getDimensions().withAttachments(EntityAttachments.builder().add(EntityAttachmentType.PASSENGER, 0.0F, EntityType.ZOMBIE_HORSE.getHeight() - 0.03125F, 0.0F)).scaled(0.5F);
    }
}
