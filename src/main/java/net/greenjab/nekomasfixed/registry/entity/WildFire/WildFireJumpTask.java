package net.greenjab.nekomasfixed.registry.entity.WildFire;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.ai.brain.task.StayAboveWaterTask;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.LongJumpUtil;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.RaycastContext;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class WildFireJumpTask extends MultiTickTask<WildFireEntity> {
	private static final int JUMP_INHALING_EXPIRY = Math.round(10.0F);
	private static final ObjectArrayList<Integer> POSSIBLE_JUMP_ANGLES = new ObjectArrayList<>(Lists.newArrayList(40, 55, 60, 75, 80));

	@VisibleForTesting
	public WildFireJumpTask() {
		super(
			Map.of(
				MemoryModuleType.ATTACK_TARGET,
				MemoryModuleState.VALUE_PRESENT,
				MemoryModuleType.BREEZE_JUMP_COOLDOWN,
				MemoryModuleState.VALUE_ABSENT,
				MemoryModuleType.BREEZE_JUMP_INHALING,
				MemoryModuleState.REGISTERED,
				MemoryModuleType.BREEZE_JUMP_TARGET,
				MemoryModuleState.REGISTERED,
				MemoryModuleType.BREEZE_SHOOT,
				MemoryModuleState.VALUE_ABSENT,
				MemoryModuleType.WALK_TARGET,
				MemoryModuleState.VALUE_ABSENT,
				MemoryModuleType.BREEZE_LEAVING_WATER,
				MemoryModuleState.REGISTERED
			),
			200
		);
	}

	public static boolean shouldJump(ServerWorld world, WildFireEntity wildFire) {
		if (!wildFire.isOnGround() && !wildFire.isTouchingWater()) {
			return false;
		} else if (StayAboveWaterTask.isUnderwater(wildFire)) {
			return false;
		} else if (wildFire.getBrain().isMemoryInState(MemoryModuleType.BREEZE_JUMP_TARGET, MemoryModuleState.VALUE_PRESENT)) {
			return true;
		} else {
			LivingEntity livingEntity = wildFire.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
			if (livingEntity == null) {
				return false;
			} else if (isTargetOutOfRange(wildFire, livingEntity)) {
				wildFire.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
				return false;
			} else if (isTargetTooClose(wildFire, livingEntity)) {
				return false;
			} else if (!hasRoomToJump(world, wildFire)) {
				return false;
			} else {
				BlockPos blockPos = getPosToJumpTo(wildFire, WildFireMovementUtil.getRandomPosBehindTarget(livingEntity, wildFire.getRandom()));
				if (blockPos == null) {
					return false;
				} else {
					BlockState blockState = world.getBlockState(blockPos.down());
					if (wildFire.getType().isInvalidSpawn(blockState)) {
						return false;
					} else if (!WildFireMovementUtil.canMoveTo(wildFire, blockPos.toCenterPos()) && !WildFireMovementUtil.canMoveTo(wildFire, blockPos.up(4).toCenterPos())) {
						return false;
					} else {
						wildFire.getBrain().remember(MemoryModuleType.BREEZE_JUMP_TARGET, blockPos);
						return true;
					}
				}
			}
		}
	}

	protected boolean shouldRun(ServerWorld serverWorld, WildFireEntity wildFireEntity) {
		return shouldJump(serverWorld, wildFireEntity);
	}

	protected boolean shouldKeepRunning(ServerWorld serverWorld, WildFireEntity wildFireEntity, long l) {
		return wildFireEntity.getPose() != EntityPose.STANDING && !wildFireEntity.getBrain().hasMemoryModule(MemoryModuleType.BREEZE_JUMP_COOLDOWN);
	}

	protected void run(ServerWorld serverWorld, WildFireEntity wildFireEntity, long l) {
		if (wildFireEntity.getBrain().isMemoryInState(MemoryModuleType.BREEZE_JUMP_INHALING, MemoryModuleState.VALUE_ABSENT)) {
			wildFireEntity.getBrain().remember(MemoryModuleType.BREEZE_JUMP_INHALING, Unit.INSTANCE, JUMP_INHALING_EXPIRY);
		}

		wildFireEntity.setPose(EntityPose.INHALING);
		serverWorld.playSoundFromEntity(null, wildFireEntity, SoundEvents.ENTITY_BREEZE_CHARGE, SoundCategory.HOSTILE, 1.0F, 1.0F);
		wildFireEntity.getBrain()
			.getOptionalRegisteredMemory(MemoryModuleType.BREEZE_JUMP_TARGET)
			.ifPresent( jumpTarget -> wildFireEntity.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, jumpTarget.toCenterPos()));
	}

	protected void keepRunning(ServerWorld serverWorld, WildFireEntity wildFireEntity, long l) {
		boolean bl = wildFireEntity.isTouchingWater();
		if (!bl && wildFireEntity.getBrain().isMemoryInState(MemoryModuleType.BREEZE_LEAVING_WATER, MemoryModuleState.VALUE_PRESENT)) {
			wildFireEntity.getBrain().forget(MemoryModuleType.BREEZE_LEAVING_WATER);
		}

		if (shouldStopInhalingPose(wildFireEntity)) {
			Vec3d vec3d = wildFireEntity.getBrain()
				.getOptionalRegisteredMemory(MemoryModuleType.BREEZE_JUMP_TARGET)
				.flatMap( jumpTarget -> getJumpingVelocity(wildFireEntity, wildFireEntity.getRandom(), Vec3d.ofBottomCenter(jumpTarget)))
				.orElse(null);
			if (vec3d == null) {
				wildFireEntity.setPose(EntityPose.STANDING);
				return;
			}

			if (bl) {
				wildFireEntity.getBrain().remember(MemoryModuleType.BREEZE_LEAVING_WATER, Unit.INSTANCE);
			}

			wildFireEntity.playSound(SoundEvents.ENTITY_BREEZE_JUMP, 1.0F, 1.0F);
			wildFireEntity.setPose(EntityPose.LONG_JUMPING);
			wildFireEntity.setYaw(wildFireEntity.bodyYaw);
			wildFireEntity.setNoDrag(true);
			wildFireEntity.setVelocity(vec3d);
		} else if (shouldStopLongJumpingPose(wildFireEntity)) {
			wildFireEntity.playSound(SoundEvents.ENTITY_BREEZE_LAND, 1.0F, 1.0F);
			wildFireEntity.setPose(EntityPose.STANDING);
			wildFireEntity.setNoDrag(false);
			boolean bl2 = wildFireEntity.getBrain().hasMemoryModule(MemoryModuleType.HURT_BY);
			wildFireEntity.getBrain().remember(MemoryModuleType.BREEZE_JUMP_COOLDOWN, Unit.INSTANCE, bl2 ? 2L : 10L);
			wildFireEntity.getBrain().remember(MemoryModuleType.BREEZE_SHOOT, Unit.INSTANCE, 100L);
		}
	}

	protected void finishRunning(ServerWorld serverWorld, WildFireEntity wildFireEntity, long l) {
		if (wildFireEntity.getPose() == EntityPose.LONG_JUMPING || wildFireEntity.getPose() == EntityPose.INHALING) {
			wildFireEntity.setPose(EntityPose.STANDING);
		}

		wildFireEntity.getBrain().forget(MemoryModuleType.BREEZE_JUMP_TARGET);
		wildFireEntity.getBrain().forget(MemoryModuleType.BREEZE_JUMP_INHALING);
		wildFireEntity.getBrain().forget(MemoryModuleType.BREEZE_LEAVING_WATER);
	}

	private static boolean shouldStopInhalingPose(WildFireEntity wildFire) {
		return wildFire.getBrain().getOptionalRegisteredMemory(MemoryModuleType.BREEZE_JUMP_INHALING).isEmpty() && wildFire.getPose() == EntityPose.INHALING;
	}

	private static boolean shouldStopLongJumpingPose(WildFireEntity wildFire) {
		boolean bl = wildFire.getPose() == EntityPose.LONG_JUMPING;
		boolean bl2 = wildFire.isOnGround();
		boolean bl3 = wildFire.isTouchingWater() && wildFire.getBrain().isMemoryInState(MemoryModuleType.BREEZE_LEAVING_WATER, MemoryModuleState.VALUE_ABSENT);
		return bl && (bl2 || bl3);
	}

	@Nullable
	private static BlockPos getPosToJumpTo(LivingEntity wildFire, Vec3d pos) {
		RaycastContext raycastContext = new RaycastContext(
			pos, pos.offset(Direction.DOWN, 10.0), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, wildFire
		);
		HitResult hitResult = wildFire.getEntityWorld().raycast(raycastContext);
		if (hitResult.getType() == HitResult.Type.BLOCK) {
			return BlockPos.ofFloored(hitResult.getPos()).up();
		} else {
			RaycastContext raycastContext2 = new RaycastContext(
				pos, pos.offset(Direction.UP, 10.0), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, wildFire
			);
			HitResult hitResult2 = wildFire.getEntityWorld().raycast(raycastContext2);
			return hitResult2.getType() == HitResult.Type.BLOCK ? BlockPos.ofFloored(hitResult2.getPos()).up() : null;
		}
	}

	private static boolean isTargetOutOfRange(WildFireEntity wildFire, LivingEntity target) {
		return !target.isInRange(wildFire, wildFire.getAttributeValue(EntityAttributes.FOLLOW_RANGE));
	}

	private static boolean isTargetTooClose(WildFireEntity wildFire, LivingEntity target) {
		return target.distanceTo(wildFire) - 4.0F <= 0.0F;
	}

	private static boolean hasRoomToJump(ServerWorld world, WildFireEntity wildFire) {
		BlockPos blockPos = wildFire.getBlockPos();
		if (world.getBlockState(blockPos).isOf(Blocks.HONEY_BLOCK)) {
			return false;
		} else {
			for (int i = 1; i <= 4; i++) {
				BlockPos blockPos2 = blockPos.offset(Direction.UP, i);
				if (!world.getBlockState(blockPos2).isAir() && !world.getFluidState(blockPos2).isIn(FluidTags.WATER)) {
					return false;
				}
			}

			return true;
		}
	}

	private static Optional<Vec3d> getJumpingVelocity(WildFireEntity wildFire, Random random, Vec3d jumpTarget) {
		for (int i : Util.copyShuffled(POSSIBLE_JUMP_ANGLES, random)) {
			float f = 0.058333334F * (float)wildFire.getAttributeValue(EntityAttributes.FOLLOW_RANGE);
			Optional<Vec3d> optional = LongJumpUtil.getJumpingVelocity(wildFire, jumpTarget, f, i, false);
			if (optional.isPresent()) {
				if (wildFire.hasStatusEffect(StatusEffects.JUMP_BOOST)) {
					double d = (optional.get()).normalize().y * wildFire.getJumpBoostVelocityModifier();
					return optional.map(vec3d -> vec3d.add(0.0, d, 0.0));
				}

				return optional;
			}
		}

		return Optional.empty();
	}
}
