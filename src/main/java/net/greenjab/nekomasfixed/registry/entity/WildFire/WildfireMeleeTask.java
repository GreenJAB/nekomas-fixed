package net.greenjab.nekomasfixed.registry.entity.WildFire;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Unit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class WildfireMeleeTask extends Behavior<WildfireEntity> {
	private static final int MELEE_CHARGING_EXPIRY = Math.round(20.0F);
	private static final int MELEE_EXPIRY = Math.round(120.0F);
	private static final int MELEE_HIT_COOLDOWN_EXPIRY = Math.round(4.0F);

	@VisibleForTesting
	public WildfireMeleeTask() {
		super(
			ImmutableMap.of(
					MemoryModuleType.ATTACK_TARGET,
					MemoryStatus.VALUE_PRESENT,
					MemoryModuleType.WALK_TARGET,
					MemoryStatus.VALUE_ABSENT,
					MemoryModuleType.BREEZE_SHOOT_COOLDOWN,
					MemoryStatus.VALUE_ABSENT
			),
				MELEE_CHARGING_EXPIRY + MELEE_EXPIRY
		);
	}

	protected boolean checkExtraStartConditions(@NonNull ServerLevel serverWorld, WildfireEntity wildFireEntity) {
		if (wildFireEntity.getPose() != Pose.SPIN_ATTACK) return false;
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

	protected boolean canStillUse(@NonNull ServerLevel serverWorld, WildfireEntity wildFireEntity, long l) {
		return wildFireEntity.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET) && wildFireEntity.getBrain().hasMemoryValue(MemoryModuleType.BREEZE_SHOOT);
	}

	protected void start(@NonNull ServerLevel serverWorld, WildfireEntity wildFireEntity, long l) {
		wildFireEntity.setPose(Pose.STANDING);
		wildFireEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_SHOOT_CHARGING, Unit.INSTANCE, MELEE_CHARGING_EXPIRY);
		wildFireEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_SHOOT, Unit.INSTANCE,MELEE_CHARGING_EXPIRY + MELEE_EXPIRY);
		wildFireEntity.playSound(SoundEvents.BREEZE_INHALE, 1.0F, 1.0F);
		wildFireEntity.setFireActive(true);
	}

	protected void stop(@NonNull ServerLevel serverWorld, WildfireEntity wildFireEntity, long l) {
		wildFireEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_SHOOT_COOLDOWN, Unit.INSTANCE, 200L);
		wildFireEntity.getBrain().eraseMemory(MemoryModuleType.BREEZE_SHOOT);
		wildFireEntity.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
		//Brain b = wildFireEntity.getBrain();
		wildFireEntity.setFireActive(false);
	}

	protected void tick(@NonNull ServerLevel serverWorld, WildfireEntity wildFireEntity, long l) {
		Brain<WildfireEntity> brain = wildFireEntity.getBrain();
		LivingEntity livingEntity = brain.getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
		if (livingEntity != null) {
			wildFireEntity.lookAt(EntityAnchorArgument.Anchor.EYES, livingEntity.position());
			if (brain.getMemory(MemoryModuleType.BREEZE_SHOOT_CHARGING).isEmpty()
				&& brain.getMemory(MemoryModuleType.BREEZE_SHOOT_RECOVERING).isEmpty()) {
				brain.setMemoryWithExpiry(MemoryModuleType.BREEZE_SHOOT_RECOVERING, Unit.INSTANCE, MELEE_HIT_COOLDOWN_EXPIRY);

				if (serverWorld.getBlockState(wildFireEntity.blockPosition()).is(BlockTags.REPLACEABLE)) {
					serverWorld.setBlockAndUpdate(wildFireEntity.blockPosition(), Blocks.FIRE.defaultBlockState());
				}

				List<LivingEntity> list = serverWorld.getEntitiesOfClass(LivingEntity.class, wildFireEntity.getBoundingBox().inflate(1.5, 0, 1.5), e -> !(e instanceof WildfireEntity) && e.isAlive());
				for (LivingEntity entity : list) {
					double f = entity.getX() - wildFireEntity.getX();
					double g = entity.getZ() - wildFireEntity.getZ();
					double h = Math.max(f * f + g * g, 0.1);
					entity.push(f / h * 2.0, 0.2F, g / h * 2.0);

					DamageSource damageSource = wildFireEntity.damageSources().mobAttack(wildFireEntity);
					entity.hurtServer(serverWorld, damageSource, wildFireEntity.isSoulActive()?6.0F:4.0F);
					EnchantmentHelper.doPostAttackEffects(serverWorld, entity, damageSource);
				}
			}
			wildFireEntity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(BlockPos.containing(livingEntity.position()), wildFireEntity.isSoulActive()?0.65F:0.5F, 0));

		}
	}

	private static boolean isTargetWithinRange(WildfireEntity wildFire, LivingEntity target) {
		double d = wildFire.position().distanceToSqr(target.position());
		return d < 1024.0;
	}
}
