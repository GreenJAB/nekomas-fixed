package net.greenjab.nekomasfixed.registry.entity.WildFire;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import java.util.Map;
import java.util.Optional;

public class WildFireJumpTask extends MultiTickTask<WildFireEntity> {
	private static final int JUMP_INHALING_EXPIRY = 20;

    @VisibleForTesting
	public WildFireJumpTask() {
		super(
			Map.of(
					MemoryModuleType.ATTACK_TARGET,
					MemoryModuleState.VALUE_PRESENT,
					MemoryModuleType.WALK_TARGET,
					MemoryModuleState.VALUE_ABSENT,
					MemoryModuleType.BREEZE_SHOOT_COOLDOWN,
					MemoryModuleState.VALUE_ABSENT
			),
			120
		);
	}

	public static boolean shouldJump(ServerWorld world, WildFireEntity wildFire) {
		if (wildFire.getPose() != EntityPose.LONG_JUMPING) return false;
		if (wildFire.getBrain().isMemoryInState(MemoryModuleType.BREEZE_JUMP_TARGET, MemoryModuleState.VALUE_PRESENT)) {
			return true;
		} else {
			LivingEntity livingEntity = wildFire.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
			if (livingEntity == null) {
				return false;
			} else {
				BlockPos blockPos = livingEntity.getBlockPos().add(0, 7, 0);
                if (blockPos == null) {
					return false;
				} else {
					BlockState blockState = world.getBlockState(blockPos.down());
					if (wildFire.getType().isInvalidSpawn(blockState)) {
						return false;
					} else if (WildFireMovementUtil.cantMoveTo(wildFire, blockPos.toCenterPos()) && WildFireMovementUtil.cantMoveTo(wildFire, blockPos.up(4).toCenterPos())) {
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
		return !wildFireEntity.getBrain().hasMemoryModule(MemoryModuleType.BREEZE_JUMP_COOLDOWN);
	}

	protected void run(ServerWorld serverWorld, WildFireEntity wildFireEntity, long l) {
		if (wildFireEntity.getBrain().isMemoryInState(MemoryModuleType.BREEZE_JUMP_INHALING, MemoryModuleState.VALUE_ABSENT)) {
			wildFireEntity.getBrain().remember(MemoryModuleType.BREEZE_JUMP_INHALING, Unit.INSTANCE, JUMP_INHALING_EXPIRY);
		}

		wildFireEntity.getBrain().remember(MemoryModuleType.BREEZE_SHOOT, Unit.INSTANCE,120);
		wildFireEntity.setPose(EntityPose.DIGGING);
		serverWorld.playSoundFromEntity(null, wildFireEntity, SoundEvents.ENTITY_BREEZE_CHARGE, SoundCategory.HOSTILE, 1.0F, 1.0F);
		wildFireEntity.getBrain()
			.getOptionalRegisteredMemory(MemoryModuleType.BREEZE_JUMP_TARGET)
			.ifPresent( jumpTarget -> wildFireEntity.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, jumpTarget.toCenterPos()));
	}

	protected void keepRunning(ServerWorld serverWorld, WildFireEntity wildFireEntity, long l) {
		if (shouldStopInhalingPose(wildFireEntity)) {
			Vec3d vec3d = wildFireEntity.getBrain()
				.getOptionalRegisteredMemory(MemoryModuleType.BREEZE_JUMP_TARGET)
				.flatMap( jumpTarget -> getJumpingVelocity(wildFireEntity, Vec3d.ofBottomCenter(jumpTarget)))
				.orElse(null);
			if (vec3d == null) {
				return;
			}

			wildFireEntity.getBrain().remember(MemoryModuleType.BREEZE_LEAVING_WATER, Unit.INSTANCE, 60L);

			wildFireEntity.playSound(SoundEvents.ENTITY_BREEZE_JUMP, 1.0F, 1.0F);
			wildFireEntity.setYaw(wildFireEntity.bodyYaw);
			wildFireEntity.setNoDrag(true);
			wildFireEntity.setVelocity(vec3d);
		} else if (shouldStopLongJumpingPose(wildFireEntity)) {
			wildFireEntity.setVelocity(0, 0, 0);
			wildFireEntity.eyeOffset = -7;
			wildFireEntity.playSound(SoundEvents.ENTITY_BREEZE_LAND, 1.0F, 1.0F);
			wildFireEntity.setNoDrag(false);
			boolean bl2 = wildFireEntity.getBrain().hasMemoryModule(MemoryModuleType.HURT_BY);
			wildFireEntity.getBrain().remember(MemoryModuleType.BREEZE_JUMP_COOLDOWN, Unit.INSTANCE, bl2 ? 2L : 10L);
		}
	}

	public static Optional<Vec3d> getJumpingVelocity(MobEntity entity, Vec3d jumpTarget) {
		Vec3d vec3d = entity.getEntityPos();
		Vec3d vec3d2 = new Vec3d(jumpTarget.x - vec3d.x, 0.0, jumpTarget.z - vec3d.z).normalize().multiply(0.5);
		Vec3d vec3d3 = jumpTarget.subtract(vec3d2);
		Vec3d vec3d4 = vec3d3.subtract(vec3d);

		double h = vec3d4.y;
		double i = entity.getFinalGravity();
		double s1 = Math.sqrt(2*i*h);
		double r1 = i*vec3d4.horizontalLength()/s1;

		double d = Math.atan2(vec3d4.z, vec3d4.x);
		double n = Math.sin(d);
		double o = Math.cos(d);

		if (s1/r1 < 0.6) {
			return Optional.empty();
		} else {
			return Optional.of(new Vec3d(r1 * o, s1, r1 * n).multiply(0.95F));
		}
	}

	protected void finishRunning(ServerWorld serverWorld, WildFireEntity wildFireEntity, long l) {
		wildFireEntity.getBrain().forget(MemoryModuleType.BREEZE_JUMP_TARGET);
		wildFireEntity.getBrain().forget(MemoryModuleType.BREEZE_JUMP_INHALING);
		wildFireEntity.getBrain().remember(MemoryModuleType.BREEZE_SHOOT_COOLDOWN, Unit.INSTANCE, 200L);
		wildFireEntity.setNoDrag(false);
	}

	private static boolean shouldStopInhalingPose(WildFireEntity wildFire) {
		return wildFire.getBrain().getOptionalRegisteredMemory(MemoryModuleType.BREEZE_JUMP_INHALING).isEmpty() &&
				wildFire.getBrain().getOptionalRegisteredMemory(MemoryModuleType.BREEZE_LEAVING_WATER).isEmpty();
	}

	private static boolean shouldStopLongJumpingPose(WildFireEntity wildFire) {
        return wildFire.getVelocity().y < -0 && wildFire.getBrain().getOptionalRegisteredMemory(MemoryModuleType.BREEZE_JUMP_INHALING).isEmpty();
    }

}
