package net.greenjab.nekomasfixed.registry.entity.WildFire;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.schedule.Activity;
import org.jspecify.annotations.NonNull;

public class WildfireAi {

	protected static List<ActivityData<WildfireEntity>> getActivities(final WildfireEntity wildfire) {
		return List.of(initCoreActivity(), initIdleActivity(), initFightActivity(wildfire));
	}
	static void updateActivities(WildfireEntity wildFire) {
		wildFire.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
	}

	private static ActivityData<WildfireEntity> initCoreActivity() {
		return ActivityData.create(Activity.CORE, 0, ImmutableList.of(new Swim<>(0.8F), new LookAtTargetSink(45, 90)));
	}

	private static ActivityData<WildfireEntity> initIdleActivity() {
		return ActivityData.create(Activity.IDLE, ImmutableList.of(
				Pair.of(0, StartAttacking.create((_, wildFire) -> wildFire.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE))),
				Pair.of(1, StartAttacking.create((_, wildFire) -> wildFire.getBrain().getMemory(MemoryModuleType.HURT_BY)
						.map(DamageSource::getEntity).filter( entity -> entity instanceof LivingEntity).map( entity -> (LivingEntity)entity))),
				Pair.of(2, new WildfireAi.SlideAroundTask(20, 40)),
				Pair.of(3, new RunOne<>(ImmutableList.of(Pair.of(new DoNothing(20, 100), 1),Pair.of(new WildfireSlideTowardsTargetTask(), 3))))));
	}

	private static ActivityData<WildfireEntity> initFightActivity(WildfireEntity wildFire) {
		return ActivityData.create(Activity.FIGHT, ImmutableList.of(
				Pair.of(0, StopAttackingIfTargetInvalid.create(Sensor.wasEntityAttackableLastNTicks(wildFire, 1).negate()::test)),
				Pair.of(1, new WildfireShootTask()),
				Pair.of(2, new WildfireMeleeTask()),
				Pair.of(3, new WildfireJumpTask()),
				Pair.of(4, new WildfireBombTask()),
				Pair.of(5, new WildfireSlideTowardsTargetTask())
		), ImmutableSet.of(Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT), Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT)));
	}

	public static class SlideAroundTask extends MoveToTargetSink {
		@VisibleForTesting
		public SlideAroundTask(int i, int j) {
			super(i, j);
		}

		@Override
		protected void start(@NonNull ServerLevel level, @NonNull Mob mobEntity, long l) {
			super.start(level, mobEntity, l);
			mobEntity.playSound(SoundEvents.BREEZE_SLIDE);
			mobEntity.setPose(Pose.STANDING);
		}

		@Override
		protected void stop(@NonNull ServerLevel level, @NonNull Mob mobEntity, long l) {
			super.stop(level, mobEntity, l);
			if (mobEntity.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
				mobEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_SHOOT, Unit.INSTANCE, 60L);
			}
		}
	}
}
