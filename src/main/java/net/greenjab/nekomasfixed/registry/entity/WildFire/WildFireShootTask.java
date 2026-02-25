package net.greenjab.nekomasfixed.registry.entity.WildFire;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.util.math.Vec3d;

public class WildFireShootTask extends MultiTickTask<WildFireEntity> {
	private static final int SHOOT_CHARGING_EXPIRY = Math.round(40.0F);
	private static final int RECOVER_EXPIRY = Math.round(30.0F);
	private static final int SHOOT_COOLDOWN_EXPIRY = Math.round(3.0F);

	@VisibleForTesting
	public WildFireShootTask() {
		super(
			ImmutableMap.of(
					MemoryModuleType.ATTACK_TARGET,
					MemoryModuleState.VALUE_PRESENT,
					MemoryModuleType.WALK_TARGET,
					MemoryModuleState.VALUE_ABSENT,
					MemoryModuleType.BREEZE_SHOOT_COOLDOWN,
					MemoryModuleState.VALUE_ABSENT
			),
			SHOOT_CHARGING_EXPIRY + RECOVER_EXPIRY
		);
	}

	protected boolean shouldRun(ServerWorld serverWorld, WildFireEntity wildFireEntity) {
		if (wildFireEntity.getPose() != EntityPose.SHOOTING) return false;
		return wildFireEntity.getBrain()
                .getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET)
                .map(target -> isTargetWithinRange(wildFireEntity, target))
                .map(withinRange -> {
                    if (!withinRange) {
                        wildFireEntity.getBrain().forget(MemoryModuleType.BREEZE_SHOOT);
                    }

                    return withinRange;
                })
                .orElse(false);
	}

	protected boolean shouldKeepRunning(ServerWorld serverWorld, WildFireEntity wildFireEntity, long l) {
		return wildFireEntity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET); //&& wildFireEntity.getBrain().hasMemoryModule(MemoryModuleType.BREEZE_SHOOT);
	}

	protected void run(ServerWorld serverWorld, WildFireEntity wildFireEntity, long l) {
		wildFireEntity.setPose(EntityPose.STANDING);
		wildFireEntity.getBrain().remember(MemoryModuleType.BREEZE_SHOOT_CHARGING, Unit.INSTANCE, SHOOT_CHARGING_EXPIRY);
		wildFireEntity.getBrain().remember(MemoryModuleType.BREEZE_SHOOT, Unit.INSTANCE,SHOOT_CHARGING_EXPIRY + RECOVER_EXPIRY);
		wildFireEntity.playSound(SoundEvents.ENTITY_BREEZE_INHALE, 1.0F, 1.0F);
		wildFireEntity.setFireActive(true);
		wildFireEntity.eyeOffset = -6;
	}

	protected void finishRunning(ServerWorld serverWorld, WildFireEntity wildFireEntity, long l) {
		wildFireEntity.getBrain().remember(MemoryModuleType.BREEZE_SHOOT_COOLDOWN, Unit.INSTANCE, 200L);
		wildFireEntity.getBrain().forget(MemoryModuleType.BREEZE_SHOOT);
		wildFireEntity.setFireActive(false);
		wildFireEntity.eyeOffset = -0.5f;
	}

	protected void keepRunning(ServerWorld serverWorld, WildFireEntity wildFireEntity, long l) {
		Brain<WildFireEntity> brain = wildFireEntity.getBrain();
		LivingEntity livingEntity = brain.getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
		if (livingEntity != null) {
			wildFireEntity.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, livingEntity.getEntityPos());
			if (brain.getOptionalRegisteredMemory(MemoryModuleType.BREEZE_SHOOT_CHARGING).isEmpty()
				&& brain.getOptionalRegisteredMemory(MemoryModuleType.BREEZE_SHOOT_RECOVER).isEmpty()) {
				brain.remember(MemoryModuleType.BREEZE_SHOOT_RECOVER, Unit.INSTANCE, SHOOT_COOLDOWN_EXPIRY + (wildFireEntity.isSoulActive()?-1:0));
				double e = livingEntity.getX() - wildFireEntity.getX();
				double f = livingEntity.getBodyY(0.5) - wildFireEntity.getBodyY(0.5);
				double g = livingEntity.getZ() - wildFireEntity.getZ();

				double dd = wildFireEntity.squaredDistanceTo(livingEntity);
				double h = Math.sqrt(Math.sqrt(dd)) * 0.5;
				Vec3d vec3d = new Vec3d(wildFireEntity.getRandom().nextTriangular(e, 1 * h), f, wildFireEntity.getRandom().nextTriangular(g, 1 * h));
				SmallFireballEntity smallFireballEntity = new SmallFireballEntity(wildFireEntity.getEntityWorld(), wildFireEntity, vec3d.normalize());
				smallFireballEntity.setPosition(smallFireballEntity.getX(), wildFireEntity.getBodyY(0.5) + 0.5, smallFireballEntity.getZ());
				wildFireEntity.getEntityWorld().spawnEntity(smallFireballEntity);
				wildFireEntity.playSound(SoundEvents.ENTITY_BREEZE_SHOOT, 1.5F, 1.0F);
			}
		}
	}

	private static boolean isTargetWithinRange(WildFireEntity wildFire, LivingEntity target) {
		double d = wildFire.getEntityPos().squaredDistanceTo(target.getEntityPos());
		return d < 1024;
	}
}
