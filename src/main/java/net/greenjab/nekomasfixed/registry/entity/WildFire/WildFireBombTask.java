package net.greenjab.nekomasfixed.registry.entity.WildFire;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import net.greenjab.nekomasfixed.registry.entity.FireBombEntity;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.util.math.LongJumpUtil;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

public class WildFireBombTask extends MultiTickTask<WildFireEntity> {
	private static final int SHOOT_CHARGING_EXPIRY = Math.round(60.0F);
	private static final int RECOVER_EXPIRY = Math.round(60.0F);
	private static final int SHOOT_COOLDOWN_EXPIRY = Math.round(3.0F);

	@VisibleForTesting
	public WildFireBombTask() {
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
			SHOOT_CHARGING_EXPIRY + RECOVER_EXPIRY
		);
	}

	protected boolean shouldRun(ServerWorld serverWorld, WildFireEntity wildFireEntity) {
		//if (true) return false;
		return /*wildFireEntity.getPose() == EntityPose.STANDING &&*/ wildFireEntity.getBrain()
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
		return wildFireEntity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET)/* && wildFireEntity.getBrain().hasMemoryModule(MemoryModuleType.BREEZE_SHOOT)*/;
	}

	protected void run(ServerWorld serverWorld, WildFireEntity wildFireEntity, long l) {
		wildFireEntity.getBrain()
			.getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET)
			.ifPresent( target -> wildFireEntity.setPose(EntityPose.SHOOTING));
		wildFireEntity.getBrain().remember(MemoryModuleType.BREEZE_SHOOT_CHARGING, Unit.INSTANCE, SHOOT_CHARGING_EXPIRY);
		wildFireEntity.playSound(SoundEvents.ENTITY_BREEZE_INHALE, 1.0F, 1.0F);
		wildFireEntity.setFireActive(true);
		//wildFireEntity.eyeOffset = -6;
	}

	protected void finishRunning(ServerWorld serverWorld, WildFireEntity wildFireEntity, long l) {
		if (wildFireEntity.getPose() == EntityPose.SHOOTING) {
			wildFireEntity.setPose(EntityPose.STANDING);
		}
		wildFireEntity.getBrain().remember(MemoryModuleType.BREEZE_SHOOT_COOLDOWN, Unit.INSTANCE, SHOOT_COOLDOWN_EXPIRY);
		wildFireEntity.getBrain().forget(MemoryModuleType.BREEZE_SHOOT);
		wildFireEntity.setFireActive(false);
		wildFireEntity.eyeOffset = 0.5f;
	}

	protected void keepRunning(ServerWorld serverWorld, WildFireEntity wildFireEntity, long l) {
		Brain<WildFireEntity> brain = wildFireEntity.getBrain();
		LivingEntity livingEntity = brain.getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
		if (livingEntity != null) {
			wildFireEntity.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, livingEntity.getEntityPos());
			if (brain.getOptionalRegisteredMemory(MemoryModuleType.BREEZE_SHOOT_CHARGING).isEmpty() &&
				brain.getOptionalRegisteredMemory(MemoryModuleType.BREEZE_SHOOT_RECOVER).isEmpty()) {
				brain.remember(MemoryModuleType.BREEZE_SHOOT_RECOVER, Unit.INSTANCE, SHOOT_COOLDOWN_EXPIRY * 7L);

				Optional<Vec3d> optional = LongJumpUtil.getJumpingVelocity(wildFireEntity, livingEntity.getEntityPos(), 1.11f, serverWorld.random.nextInt(10) + 60, false);
				if (optional.isPresent()) {
					FireBombEntity fireBombEntity = new FireBombEntity(serverWorld, wildFireEntity);
					fireBombEntity.setPosition(fireBombEntity.getX(), wildFireEntity.getBodyY(0.5) + 0.5, fireBombEntity.getZ());
					ProjectileEntity.spawnWithVelocity(fireBombEntity, serverWorld, ItemStack.EMPTY, optional.get().x, optional.get().y, optional.get().z, (float) optional.get().length() * 1.1f, 0.0F);
					wildFireEntity.playSound(SoundEvents.ENTITY_BREEZE_SHOOT, 1.5F, 1.0F);
				}
			}
		}
	}

	private static boolean isTargetWithinRange(WildFireEntity wildFire, LivingEntity target) {
		double d = wildFire.getEntityPos().squaredDistanceTo(target.getEntityPos());
		return d < 1024;
	}

}
