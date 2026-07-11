package net.greenjab.nekomasfixed.registry.entity.WildFire;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.projectile.hurtingprojectile.SmallFireball;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public class WildfireShootTask extends Behavior<WildfireEntity> {
	private static final int SHOOT_CHARGING_EXPIRY = Math.round(40.0F);
	private static final int RECOVER_EXPIRY = Math.round(30.0F);
	private static final int SHOOT_COOLDOWN_EXPIRY = Math.round(3.0F);

	@VisibleForTesting
	public WildfireShootTask() {
		super(ImmutableMap.of(
				MemoryModuleType.ATTACK_TARGET,
				MemoryStatus.VALUE_PRESENT,
				MemoryModuleType.WALK_TARGET,
				MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.BREEZE_SHOOT_COOLDOWN,
				MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.BREEZE_SHOOT,
				MemoryStatus.REGISTERED,
				MemoryModuleType.BREEZE_SHOOT_CHARGING,
				MemoryStatus.REGISTERED,
				MemoryModuleType.BREEZE_SHOOT_RECOVERING,
				MemoryStatus.REGISTERED
		), SHOOT_CHARGING_EXPIRY + RECOVER_EXPIRY);
	}

	protected boolean checkExtraStartConditions(@NonNull ServerLevel level, WildfireEntity wildFireEntity) {
		if (wildFireEntity.getPose() != Pose.SHOOTING) return false;
		return wildFireEntity.getBrain()
                .getMemory(MemoryModuleType.ATTACK_TARGET)
                .map(target -> isTargetWithinRange(wildFireEntity, target))
                .map(withinRange -> {
                    if (!withinRange) {
                        wildFireEntity.getBrain().eraseMemory(MemoryModuleType.BREEZE_SHOOT);
                    }

                    return withinRange;
                })
                .orElse(false);
	}

	protected boolean canStillUse(@NonNull ServerLevel level, WildfireEntity wildFireEntity, long l) {
		return wildFireEntity.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET); //&& wildFireEntity.getBrain().hasMemoryModule(MemoryModuleType.BREEZE_SHOOT);
	}

	protected void start(@NonNull ServerLevel level, WildfireEntity wildFireEntity, long l) {
		wildFireEntity.setPose(Pose.STANDING);
		wildFireEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_SHOOT_CHARGING, Unit.INSTANCE, SHOOT_CHARGING_EXPIRY);
		wildFireEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_SHOOT, Unit.INSTANCE,SHOOT_CHARGING_EXPIRY + RECOVER_EXPIRY);
		wildFireEntity.playSound(SoundEvents.BREEZE_INHALE, 1.0F, 1.0F);
		wildFireEntity.setFireActive(true);
		wildFireEntity.eyeOffset = -6;
	}

	protected void stop(@NonNull ServerLevel level, WildfireEntity wildFireEntity, long l) {
		wildFireEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_SHOOT_COOLDOWN, Unit.INSTANCE, 200L);
		wildFireEntity.getBrain().eraseMemory(MemoryModuleType.BREEZE_SHOOT);
		wildFireEntity.setFireActive(false);
		wildFireEntity.eyeOffset = -0.5f;
	}

	protected void tick(@NonNull ServerLevel level, WildfireEntity wildFireEntity, long l) {
		Brain<WildfireEntity> brain = wildFireEntity.getBrain();
		LivingEntity livingEntity = brain.getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
		if (livingEntity != null) {
			wildFireEntity.lookAt(EntityAnchorArgument.Anchor.EYES, livingEntity.position());
			if (brain.getMemory(MemoryModuleType.BREEZE_SHOOT_CHARGING).isEmpty()
				&& brain.getMemory(MemoryModuleType.BREEZE_SHOOT_RECOVERING).isEmpty()) {
				brain.setMemoryWithExpiry(MemoryModuleType.BREEZE_SHOOT_RECOVERING, Unit.INSTANCE, SHOOT_COOLDOWN_EXPIRY + (wildFireEntity.isSoulActive()?-1:0));
				double e = livingEntity.getX() - wildFireEntity.getX();
				double f = livingEntity.getY(0.5) - wildFireEntity.getY(0.5);
				double g = livingEntity.getZ() - wildFireEntity.getZ();

				double dd = wildFireEntity.distanceToSqr(livingEntity);
				double h = Math.sqrt(Math.sqrt(dd)) * 0.5;
				Vec3 vec3d = new Vec3(wildFireEntity.getRandom().triangle(e, 1 * h), f, wildFireEntity.getRandom().triangle(g, 1 * h));
				SmallFireball smallFireballEntity = new SmallFireball(wildFireEntity.level(), wildFireEntity, vec3d.normalize());
				smallFireballEntity.setPos(smallFireballEntity.getX(), wildFireEntity.getY(0.5) + 0.5, smallFireballEntity.getZ());
				wildFireEntity.level().addFreshEntity(smallFireballEntity);
				wildFireEntity.playSound(SoundEvents.BREEZE_SHOOT, 1.5F, 1.0F);
			}
		}
	}

	private static boolean isTargetWithinRange(WildfireEntity wildFire, LivingEntity target) {
		double d = wildFire.position().distanceToSqr(target.position());
		return d < 1024;
	}
}
