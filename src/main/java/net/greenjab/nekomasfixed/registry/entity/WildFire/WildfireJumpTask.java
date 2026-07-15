package net.greenjab.nekomasfixed.registry.entity.WildFire;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

import java.util.Map;
import java.util.Optional;

public class WildfireJumpTask extends Behavior<WildfireEntity> {
	private static final int JUMP_INHALING_EXPIRY = 20;

    @VisibleForTesting
	public WildfireJumpTask() {
		super(Map.of(
				MemoryModuleType.ATTACK_TARGET,
				MemoryStatus.VALUE_PRESENT,
				MemoryModuleType.WALK_TARGET,
				MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.BREEZE_SHOOT_COOLDOWN,
				MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.BREEZE_SHOOT,
				MemoryStatus.REGISTERED,
				MemoryModuleType.BREEZE_JUMP_TARGET,
				MemoryStatus.REGISTERED,
				MemoryModuleType.BREEZE_JUMP_COOLDOWN,
				MemoryStatus.REGISTERED,
				MemoryModuleType.BREEZE_JUMP_INHALING,
				MemoryStatus.REGISTERED,
				MemoryModuleType.BREEZE_LEAVING_WATER,
				MemoryStatus.REGISTERED
		), 120);
	}

	public static boolean shouldJump(ServerLevel level, WildfireEntity wildFire) {
		if (wildFire.getPose() != Pose.LONG_JUMPING) return false;
		if (wildFire.getBrain().checkMemory(MemoryModuleType.BREEZE_JUMP_TARGET, MemoryStatus.VALUE_PRESENT)) return true;
		LivingEntity livingEntity = wildFire.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
		if (livingEntity == null) return false;
		BlockPos blockPos = wildFire.getSpawnPos().offset(0, 3, 0);
		if (!blockPos.closerThan(wildFire.blockPosition(), 15))
			blockPos = livingEntity.blockPosition().offset(0, 4, 0);
		BlockState blockState = level.getBlockState(blockPos.below());
		if (wildFire.getType().isBlockDangerous(blockState)) return false;
		else if (WildfireMovementUtil.cantMoveTo(wildFire, Vec3.atCenterOf(blockPos))
				&& WildfireMovementUtil.cantMoveTo(wildFire, Vec3.atCenterOf(blockPos.above(4)))) return false;
		else {
			wildFire.getBrain().setMemory(MemoryModuleType.BREEZE_JUMP_TARGET, blockPos);
			return true;
		}
	}

	protected boolean checkExtraStartConditions(@NonNull ServerLevel level, @NonNull WildfireEntity wildFireEntity) {
		return shouldJump(level, wildFireEntity);
	}

	protected boolean canStillUse(@NonNull ServerLevel level, WildfireEntity wildFireEntity, long l) {
		return !wildFireEntity.getBrain().hasMemoryValue(MemoryModuleType.BREEZE_JUMP_COOLDOWN);
	}

	protected void start(@NonNull ServerLevel level, WildfireEntity wildFireEntity, long l) {
		if (wildFireEntity.getBrain().checkMemory(MemoryModuleType.BREEZE_JUMP_INHALING, MemoryStatus.VALUE_ABSENT)) {
			wildFireEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_JUMP_INHALING, Unit.INSTANCE, JUMP_INHALING_EXPIRY);
		}
		wildFireEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_SHOOT, Unit.INSTANCE,120);
		wildFireEntity.setPose(Pose.DIGGING);
		level.playSound(null, wildFireEntity, SoundEvents.BREEZE_CHARGE, SoundSource.HOSTILE, 1.0F, 1.0F);
		wildFireEntity.getBrain().getMemory(MemoryModuleType.BREEZE_JUMP_TARGET)
			.ifPresent( jumpTarget -> wildFireEntity.lookAt(EntityAnchorArgument.Anchor.EYES, Vec3.atCenterOf(jumpTarget)));
	}

	protected void tick(@NonNull ServerLevel level, @NonNull WildfireEntity wildFireEntity, long l) {
		if (shouldStopInhalingPose(wildFireEntity)) {
			Vec3 vec3d = wildFireEntity.getBrain().getMemory(MemoryModuleType.BREEZE_JUMP_TARGET)
				.flatMap( jumpTarget -> getJumpingVelocity(wildFireEntity, Vec3.atBottomCenterOf(jumpTarget)))
				.orElse(null);
			if (vec3d == null) return;
			wildFireEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_LEAVING_WATER, Unit.INSTANCE, 60L);
			wildFireEntity.playSound(SoundEvents.BREEZE_JUMP, 1.0F, 1.0F);
			wildFireEntity.setYRot(wildFireEntity.yBodyRot);
			wildFireEntity.setDiscardFriction(true);
			wildFireEntity.setDeltaMovement(vec3d);
		} else if (shouldStopLongJumpingPose(wildFireEntity)) {
			wildFireEntity.setDeltaMovement(0, 0, 0);
			wildFireEntity.eyeOffset = -3;
			wildFireEntity.playSound(SoundEvents.BREEZE_LAND, 1.0F, 1.0F);
			wildFireEntity.setDiscardFriction(false);
			boolean bl2 = wildFireEntity.getBrain().hasMemoryValue(MemoryModuleType.HURT_BY);
			wildFireEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_JUMP_COOLDOWN, Unit.INSTANCE, bl2 ? 2L : 10L);
		}
	}

	public static Optional<Vec3> getJumpingVelocity(Mob entity, Vec3 jumpTarget) {
		Vec3 vec3d = entity.position();
		Vec3 vec3d2 = new Vec3(jumpTarget.x - vec3d.x, 0.0, jumpTarget.z - vec3d.z).normalize().scale(0.5);
		Vec3 vec3d3 = jumpTarget.subtract(vec3d2);
		Vec3 vec3d4 = vec3d3.subtract(vec3d);

		double h = vec3d4.y;
		double g = entity.getGravity();
		double vy = Math.sqrt(2* g *h);
		double vx = g *vec3d4.horizontalDistance()/ vy;

		double d = Math.atan2(vec3d4.z, vec3d4.x);
		double n = Math.sin(d);
		double o = Math.cos(d);

		if (vy / vx < 0.3) return Optional.empty();
		else return Optional.of(new Vec3(vx * o, vy, vx * n).scale(0.95F));
	}

	protected void stop(@NonNull ServerLevel level, WildfireEntity wildFireEntity, long l) {
		wildFireEntity.getBrain().eraseMemory(MemoryModuleType.BREEZE_JUMP_TARGET);
		wildFireEntity.getBrain().eraseMemory(MemoryModuleType.BREEZE_JUMP_INHALING);
		wildFireEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_SHOOT_COOLDOWN, Unit.INSTANCE, 200L);
		wildFireEntity.setDiscardFriction(false);
	}

	private static boolean shouldStopInhalingPose(WildfireEntity wildFire) {
		return wildFire.getBrain().getMemory(MemoryModuleType.BREEZE_JUMP_INHALING).isEmpty() &&
				wildFire.getBrain().getMemory(MemoryModuleType.BREEZE_LEAVING_WATER).isEmpty();
	}

	private static boolean shouldStopLongJumpingPose(WildfireEntity wildFire) {
        return wildFire.getDeltaMovement().y < -0 && wildFire.getBrain().getMemory(MemoryModuleType.BREEZE_JUMP_INHALING).isEmpty();
    }
}
