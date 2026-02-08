package net.greenjab.nekomasfixed.registry.entity.WildFire;

import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Map;

public class WildFireSlideTowardsTargetTask extends MultiTickTask<WildFireEntity> {
	public WildFireSlideTowardsTargetTask() {
		super(
			Map.of(
				MemoryModuleType.ATTACK_TARGET,
				MemoryModuleState.VALUE_PRESENT,
				MemoryModuleType.WALK_TARGET,
				MemoryModuleState.VALUE_ABSENT,
				MemoryModuleType.BREEZE_JUMP_COOLDOWN,
				MemoryModuleState.VALUE_ABSENT,
				MemoryModuleType.BREEZE_SHOOT,
				MemoryModuleState.VALUE_ABSENT
			)
		);
	}

	protected boolean shouldRun(ServerWorld serverWorld, WildFireEntity wildFireEntity) {
		return wildFireEntity.isOnGround() && !wildFireEntity.isTouchingWater() && wildFireEntity.getPose() == EntityPose.STANDING;
	}

	protected void run(ServerWorld serverWorld, WildFireEntity wildFireEntity, long l) {
		LivingEntity livingEntity = wildFireEntity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
		if (livingEntity != null) {
			boolean bl = wildFireEntity.isWithinShortRange(livingEntity.getEntityPos());
			Vec3d vec3d = null;
			if (bl) {
				Vec3d vec3d2 = NoPenaltyTargeting.findFrom(wildFireEntity, 5, 5, livingEntity.getEntityPos());
				if (vec3d2 != null
					&& WildFireMovementUtil.canMoveTo(wildFireEntity, vec3d2)
					&& livingEntity.squaredDistanceTo(vec3d2.x, vec3d2.y, vec3d2.z) > livingEntity.squaredDistanceTo(wildFireEntity)) {
					vec3d = vec3d2;
				}
			}

			if (vec3d == null) {
				vec3d = wildFireEntity.getRandom().nextBoolean()
					? WildFireMovementUtil.getRandomPosBehindTarget(livingEntity, wildFireEntity.getRandom())
					: getRandomPosInMediumRange(wildFireEntity, livingEntity);
			}

			wildFireEntity.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(BlockPos.ofFloored(vec3d), 0.6F, 1));
		}
	}

	private static Vec3d getRandomPosInMediumRange(WildFireEntity wildFire, LivingEntity target) {
		Vec3d vec3d = target.getEntityPos().subtract(wildFire.getEntityPos());
		double d = vec3d.length() - MathHelper.lerp(wildFire.getRandom().nextDouble(), 8.0, 4.0);
		Vec3d vec3d2 = vec3d.normalize().multiply(d, d, d);
		return wildFire.getEntityPos().add(vec3d2);
	}
}
