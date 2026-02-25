package net.greenjab.nekomasfixed.registry.entity.WildFire;

import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Map;

public class WildFireSlideTowardsTargetTask extends MultiTickTask<WildFireEntity> {
	public WildFireSlideTowardsTargetTask() {
		super(
			Map.of(
					MemoryModuleType.WALK_TARGET,
					MemoryModuleState.VALUE_ABSENT,
					MemoryModuleType.BREEZE_SHOOT,
					MemoryModuleState.VALUE_ABSENT,
					MemoryModuleType.BREEZE_LEAVING_WATER,
					MemoryModuleState.VALUE_ABSENT
			),200
		);
	}

    protected boolean shouldKeepRunning(ServerWorld serverWorld, WildFireEntity wildFireEntity, long l) {
		if (wildFireEntity.hurtTime>=9) return false;
		if (wildFireEntity.getBrain().hasMemoryModule(MemoryModuleType.TOUCH_COOLDOWN)) return true;
		if (wildFireEntity.getBrain().hasMemoryModule(MemoryModuleType.SNIFF_COOLDOWN)) {
            return wildFireEntity.getBrain().getMemoryExpiry(MemoryModuleType.SNIFF_COOLDOWN) >= 5
					&& wildFireEntity.getBrain().getMemoryExpiry(MemoryModuleType.SNIFF_COOLDOWN) <= 50
					&& serverWorld.getBlockState(wildFireEntity.getBlockPos()).isIn(BlockTags.FIRE);
		}
		return true;
	}

	protected void run(ServerWorld serverWorld, WildFireEntity wildFireEntity, long l) {
		wildFireEntity.eyeOffset = -0.5f;
		Vec3d fire = WildFireMovementUtil.findFirePos(wildFireEntity, false);
		if (fire != null) {
            wildFireEntity.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(BlockPos.ofFloored(fire), 0.6F, 0));
			wildFireEntity.getBrain().remember(MemoryModuleType.BREEZE_LEAVING_WATER, Unit.INSTANCE, 200L);
		}
		wildFireEntity.getBrain().remember(MemoryModuleType.TOUCH_COOLDOWN, Unit.INSTANCE, 60L);
		wildFireEntity.setFireActive(false);
	}
	protected void keepRunning(ServerWorld serverWorld, WildFireEntity wildFireEntity, long l) {
		Brain<WildFireEntity> brain = wildFireEntity.getBrain();
		WalkTarget target = brain.getOptionalRegisteredMemory(MemoryModuleType.WALK_TARGET).orElse(null);
		if (target != null) {
			wildFireEntity.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, target.getLookTarget().getPos());
		} else {
			if (brain.hasMemoryModule(MemoryModuleType.TOUCH_COOLDOWN)) {
				if (!serverWorld.getBlockState(wildFireEntity.getBlockPos()).isIn(BlockTags.FIRE)) {
					Vec3d fire = WildFireMovementUtil.findFirePos(wildFireEntity, false);
					if (fire != null) {
						wildFireEntity.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(BlockPos.ofFloored(fire), 0.6F, 0));
						wildFireEntity.getBrain().remember(MemoryModuleType.BREEZE_LEAVING_WATER, Unit.INSTANCE, 200L);
					}
				}
			} else {
				if (!brain.hasMemoryModule(MemoryModuleType.BREEZE_LEAVING_WATER)) {
					Vec3d fire = WildFireMovementUtil.findFirePos(wildFireEntity, true);
					if (fire != null) {
						wildFireEntity.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(BlockPos.ofFloored(fire), 0.6F, 0));
						wildFireEntity.getBrain().remember(MemoryModuleType.BREEZE_LEAVING_WATER, Unit.INSTANCE, 200L);
					}
				} else {
					if (!brain.hasMemoryModule(MemoryModuleType.SNIFF_COOLDOWN)) {
						brain.remember(MemoryModuleType.SNIFF_COOLDOWN, Unit.INSTANCE, 65L);
					}
				}
			}

            brain.getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).ifPresent(livingEntity -> wildFireEntity.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, livingEntity.getEntityPos()));
        }
	}

	protected void finishRunning(ServerWorld serverWorld, WildFireEntity wildFireEntity, long l) {
		int i = wildFireEntity.getRandom().nextInt(3);
		if (i == 0)	wildFireEntity.setPose(EntityPose.SHOOTING);
		else if (i == 1) wildFireEntity.setPose(EntityPose.SPIN_ATTACK);
		else if (i == 2) wildFireEntity.setPose(EntityPose.LONG_JUMPING);
		wildFireEntity.getBrain().forget(MemoryModuleType.WALK_TARGET);
		wildFireEntity.getBrain().forget(MemoryModuleType.SNIFF_COOLDOWN);
		wildFireEntity.getBrain().forget(MemoryModuleType.TOUCH_COOLDOWN);
		wildFireEntity.getBrain().forget(MemoryModuleType.BREEZE_SHOOT_COOLDOWN);
		wildFireEntity.getBrain().forget(MemoryModuleType.BREEZE_LEAVING_WATER);

	}

}
