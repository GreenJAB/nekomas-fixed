package net.greenjab.nekomasfixed.registry.entity.WildFire;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

import java.util.Map;

public class WildfireSlideTowardsTargetTask extends Behavior<WildfireEntity> {
	public WildfireSlideTowardsTargetTask() {
		super(Map.of(
				MemoryModuleType.WALK_TARGET,
				MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.BREEZE_SHOOT,
				MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.BREEZE_LEAVING_WATER,
				MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.TOUCH_COOLDOWN,
				MemoryStatus.REGISTERED,
				MemoryModuleType.SNIFF_COOLDOWN,
				MemoryStatus.REGISTERED,
				MemoryModuleType.BREEZE_SHOOT_COOLDOWN,
				MemoryStatus.REGISTERED
		),200);
	}

    protected boolean canStillUse(@NonNull ServerLevel level, WildfireEntity wildFireEntity, long l) {
		if (wildFireEntity.hurtTime>=9) return false;
		if (wildFireEntity.getBrain().hasMemoryValue(MemoryModuleType.TOUCH_COOLDOWN)) return true;
		if (wildFireEntity.getBrain().hasMemoryValue(MemoryModuleType.SNIFF_COOLDOWN)) {
            return wildFireEntity.getBrain().getTimeUntilExpiry(MemoryModuleType.SNIFF_COOLDOWN) >= 5
					&& wildFireEntity.getBrain().getTimeUntilExpiry(MemoryModuleType.SNIFF_COOLDOWN) <= 50
					&& level.getBlockState(wildFireEntity.blockPosition()).is(BlockTags.FIRE);
		}
		return true;
	}

	protected void start(@NonNull ServerLevel level, WildfireEntity wildFireEntity, long l) {
		wildFireEntity.eyeOffset = -0.5f;
		Vec3 fire = WildfireMovementUtil.findFirePos(wildFireEntity, false);
		if (fire != null) {
            wildFireEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.WALK_TARGET, new WalkTarget(BlockPos.containing(fire), 0.6F, 0), 200L);
			wildFireEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_LEAVING_WATER, Unit.INSTANCE, 200L);
		}
		wildFireEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.TOUCH_COOLDOWN, Unit.INSTANCE, 60L);
		wildFireEntity.setFireActive(false);
	}
	protected void tick(@NonNull ServerLevel level, WildfireEntity wildFireEntity, long l) {
		Brain<WildfireEntity> brain = wildFireEntity.getBrain();
		WalkTarget target = brain.getMemory(MemoryModuleType.WALK_TARGET).orElse(null);
		if (target != null) {
			wildFireEntity.lookAt(EntityAnchorArgument.Anchor.EYES, target.getTarget().currentPosition());
		} else {
			if (brain.hasMemoryValue(MemoryModuleType.TOUCH_COOLDOWN)) {
				if (!level.getBlockState(wildFireEntity.blockPosition()).is(BlockTags.FIRE)) {
					Vec3 fire = WildfireMovementUtil.findFirePos(wildFireEntity, false);
					if (fire != null) {
						wildFireEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.WALK_TARGET, new WalkTarget(BlockPos.containing(fire), 0.6F, 0), 200L);
						wildFireEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_LEAVING_WATER, Unit.INSTANCE, 200L);
					}
				}
			} else {
				if (!brain.hasMemoryValue(MemoryModuleType.BREEZE_LEAVING_WATER)) {
					Vec3 fire = WildfireMovementUtil.findFirePos(wildFireEntity, true);
					if (fire != null) {
						wildFireEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.WALK_TARGET, new WalkTarget(BlockPos.containing(fire), 0.6F, 0), 200L);
						wildFireEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_LEAVING_WATER, Unit.INSTANCE, 200L);
					}
				} else {
					if (!brain.hasMemoryValue(MemoryModuleType.SNIFF_COOLDOWN)) {
						brain.setMemoryWithExpiry(MemoryModuleType.SNIFF_COOLDOWN, Unit.INSTANCE, 65L);
					}
				}
			}
            brain.getMemory(MemoryModuleType.ATTACK_TARGET).ifPresent(livingEntity -> wildFireEntity.lookAt(EntityAnchorArgument.Anchor.EYES, livingEntity.position()));
        }
	}

	protected void stop(@NonNull ServerLevel level, WildfireEntity wildFireEntity, long l) {
		int i = wildFireEntity.getRandom().nextInt(wildFireEntity.getShieldsActive()>1?3:2);
		if (i == 0)	wildFireEntity.setPose(Pose.SHOOTING);
		else if (i == 1) wildFireEntity.setPose(Pose.LONG_JUMPING);
		else if (i == 2) wildFireEntity.setPose(Pose.SPIN_ATTACK);
		wildFireEntity.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
		wildFireEntity.getBrain().eraseMemory(MemoryModuleType.SNIFF_COOLDOWN);
		wildFireEntity.getBrain().eraseMemory(MemoryModuleType.TOUCH_COOLDOWN);
		wildFireEntity.getBrain().eraseMemory(MemoryModuleType.BREEZE_SHOOT_COOLDOWN);
		wildFireEntity.getBrain().eraseMemory(MemoryModuleType.BREEZE_LEAVING_WATER);
	}
}
