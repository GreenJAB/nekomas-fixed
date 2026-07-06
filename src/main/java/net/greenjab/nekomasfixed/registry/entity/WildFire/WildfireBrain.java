package net.greenjab.nekomasfixed.registry.entity.WildFire;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Set;

import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import org.jspecify.annotations.NonNull;

public class WildfireBrain {
	static final List<SensorType<? extends Sensor<? super WildfireEntity>>> SENSORS = ImmutableList.of(
			SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY, SensorType.NEAREST_PLAYERS, OtherRegistry.WILDFIRE_ATTACK_ENTITY_SENSOR
	);
	static final List<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(
			MemoryModuleType.LOOK_TARGET,
			MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
			MemoryModuleType.NEAREST_ATTACKABLE,
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
			MemoryModuleType.ATTACK_TARGET,
			MemoryModuleType.WALK_TARGET,
			MemoryModuleType.BREEZE_JUMP_COOLDOWN,
			MemoryModuleType.BREEZE_JUMP_INHALING,
			MemoryModuleType.BREEZE_SHOOT,
			MemoryModuleType.BREEZE_SHOOT_CHARGING,
			MemoryModuleType.BREEZE_SHOOT_RECOVERING,
			MemoryModuleType.BREEZE_SHOOT_COOLDOWN,
			MemoryModuleType.BREEZE_JUMP_TARGET,
			MemoryModuleType.BREEZE_LEAVING_WATER,
			MemoryModuleType.HURT_BY,
			MemoryModuleType.HURT_BY_ENTITY,
			MemoryModuleType.PATH,
			MemoryModuleType.LIKED_NOTEBLOCK_COOLDOWN_TICKS, //bomb counter
			MemoryModuleType.TOUCH_COOLDOWN, //forced fight cooldown
			MemoryModuleType.SNIFF_COOLDOWN //stay in fire to heal
	);

	protected static Brain<?> create(WildfireEntity wildFire, Brain<WildfireEntity> brain) {
		addCoreTasks(brain);
		addIdleTasks(brain);
		addFightTasks(wildFire, brain);
		brain.setCoreActivities(Set.of(Activity.CORE));
		brain.setDefaultActivity(Activity.FIGHT);
		brain.useDefaultActivity();
		return brain;
	}

	private static void addCoreTasks(Brain<WildfireEntity> brain) {
		brain.addActivity(Activity.CORE, 0, ImmutableList.of(new Swim<>(0.8F), new LookAtTargetSink(45, 90)));
	}

	private static void addIdleTasks(Brain<WildfireEntity> brain) {
		brain.addActivity(
				Activity.IDLE,
				ImmutableList.of(
						Pair.of(
								0, StartAttacking.create((world, wildFire) -> wildFire.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE))
						),
						Pair.of(1, StartAttacking.create((world, wildFire) -> wildFire.getHurtBy())),
						Pair.of(2, new WildfireBrain.SlideAroundTask(20, 40)),
						Pair.of(3, new RunOne<>(ImmutableList.of(Pair.of(new DoNothing(20, 100), 1),Pair.of(new WildfireSlideTowardsTargetTask(), 3))))
				)
		);
	}

	private static void addFightTasks(WildfireEntity wildFire, Brain<WildfireEntity> brain) {
		brain.addActivityWithConditions(
				Activity.FIGHT,
				ImmutableList.of(
						Pair.of(0, StopAttackingIfTargetInvalid.create(Sensor.wasEntityAttackableLastNTicks(wildFire, 1).negate()::test)),
						Pair.of(1, new WildfireShootTask()),
						Pair.of(2, new WildfireMeleeTask()),
						Pair.of(3, new WildfireJumpTask()),
						Pair.of(4, new WildfireBombTask()),
						Pair.of(5, new WildfireSlideTowardsTargetTask())
				),
				ImmutableSet.of(
						Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT), Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT)
				)
		);
	}

	static void updateActivities(WildfireEntity wildFire) {
		wildFire.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
	}

	public static class SlideAroundTask extends MoveToTargetSink {
		@VisibleForTesting
		public SlideAroundTask(int i, int j) {
			super(i, j);
		}

		@Override
		protected void start(@NonNull ServerLevel serverWorld, @NonNull Mob mobEntity, long l) {
			super.start(serverWorld, mobEntity, l);
			mobEntity.playSound(SoundEvents.BREEZE_SLIDE);
			mobEntity.setPose(Pose.STANDING);
		}

		@Override
		protected void stop(@NonNull ServerLevel serverWorld, @NonNull Mob mobEntity, long l) {
			super.stop(serverWorld, mobEntity, l);
			if (mobEntity.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
				mobEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_SHOOT, Unit.INSTANCE, 60L);
			}
		}
	}
}
