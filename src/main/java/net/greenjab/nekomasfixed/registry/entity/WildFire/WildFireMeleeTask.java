package net.greenjab.nekomasfixed.registry.entity.WildFire;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class WildFireMeleeTask extends MultiTickTask<WildFireEntity> {
	private static final int MELEE_CHARGING_EXPIRY = Math.round(20.0F);
	private static final int MELEE_EXPIRY = Math.round(160.0F);
	private static final int SHOOT_COOLDOWN_EXPIRY = Math.round(3.0F);

	@VisibleForTesting
	public WildFireMeleeTask() {
		super(
			ImmutableMap.of(
				MemoryModuleType.ATTACK_TARGET,
				MemoryModuleState.VALUE_PRESENT,
				MemoryModuleType.BREEZE_SHOOT_COOLDOWN,
				MemoryModuleState.VALUE_ABSENT,
				MemoryModuleType.BREEZE_SHOOT_CHARGING,
				MemoryModuleState.VALUE_ABSENT,
				MemoryModuleType.BREEZE_SHOOT_RECOVER,
				MemoryModuleState.VALUE_ABSENT,
				MemoryModuleType.BREEZE_SHOOT,
				MemoryModuleState.VALUE_PRESENT,
				MemoryModuleType.WALK_TARGET,
				MemoryModuleState.VALUE_ABSENT,
				MemoryModuleType.BREEZE_JUMP_TARGET,
				MemoryModuleState.VALUE_ABSENT
			),
				MELEE_CHARGING_EXPIRY + MELEE_EXPIRY
		);
	}

	protected boolean shouldRun(ServerWorld serverWorld, WildFireEntity wildFireEntity) {
		if (true) return false;
		return wildFireEntity.getPose() == EntityPose.STANDING && wildFireEntity.getBrain()
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
		return wildFireEntity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET) && wildFireEntity.getBrain().hasMemoryModule(MemoryModuleType.BREEZE_SHOOT);
	}

	protected void run(ServerWorld serverWorld, WildFireEntity wildFireEntity, long l) {
		wildFireEntity.getBrain()
			.getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET)
			.ifPresent( target -> wildFireEntity.setPose(EntityPose.SHOOTING));
		wildFireEntity.getBrain().remember(MemoryModuleType.BREEZE_SHOOT_CHARGING, Unit.INSTANCE, MELEE_CHARGING_EXPIRY);
		wildFireEntity.playSound(SoundEvents.ENTITY_BREEZE_INHALE, 1.0F, 1.0F);
		wildFireEntity.setFireActive(true);
	}

	protected void finishRunning(ServerWorld serverWorld, WildFireEntity wildFireEntity, long l) {
		if (wildFireEntity.getPose() == EntityPose.SHOOTING) {
			wildFireEntity.setPose(EntityPose.STANDING);
		}
		wildFireEntity.getBrain().remember(MemoryModuleType.BREEZE_SHOOT_COOLDOWN, Unit.INSTANCE, SHOOT_COOLDOWN_EXPIRY);
		wildFireEntity.getBrain().forget(MemoryModuleType.BREEZE_SHOOT);
		wildFireEntity.setFireActive(false);
	}

	protected void keepRunning(ServerWorld serverWorld, WildFireEntity wildFireEntity, long l) {
		Brain<WildFireEntity> brain = wildFireEntity.getBrain();
		LivingEntity livingEntity = brain.getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
		if (livingEntity != null) {
			wildFireEntity.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, livingEntity.getEntityPos());
			if (brain.getOptionalRegisteredMemory(MemoryModuleType.BREEZE_SHOOT_CHARGING).isEmpty()
				&& brain.getOptionalRegisteredMemory(MemoryModuleType.BREEZE_SHOOT_RECOVER).isEmpty()) {
				brain.remember(MemoryModuleType.BREEZE_SHOOT_RECOVER, Unit.INSTANCE, SHOOT_COOLDOWN_EXPIRY);

				List<LivingEntity> list = serverWorld.getEntitiesByClass(LivingEntity.class, wildFireEntity.getBoundingBox().expand(1, 0, 1), e -> e != wildFireEntity && e.isAlive());
				for (LivingEntity entity : list) {
					double f = entity.getX() - wildFireEntity.getX();
					double g = entity.getZ() - wildFireEntity.getZ();
					double h = Math.max(f * f + g * g, 0.1);
					entity.addVelocity(f / h * 2.0, 0.2F, g / h * 2.0);

					DamageSource damageSource = wildFireEntity.getDamageSources().mobAttack(wildFireEntity);
					entity.damage(serverWorld, damageSource, 5.0F);
					EnchantmentHelper.onTargetDamaged(serverWorld, entity, damageSource);
				}

			}
			wildFireEntity.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(BlockPos.ofFloored(livingEntity.getEntityPos()), 0.6F, 1));

		}
	}

	private static boolean isTargetWithinRange(WildFireEntity wildFire, LivingEntity target) {
		double d = wildFire.getEntityPos().squaredDistanceTo(target.getEntityPos());
		return d < 1024.0;
	}
}
