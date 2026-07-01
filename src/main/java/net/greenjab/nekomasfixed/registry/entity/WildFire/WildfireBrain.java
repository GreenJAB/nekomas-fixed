package net.greenjab.nekomasfixed.registry.entity.WildFire;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Set;

import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.ForgetAttackTargetTask;
import net.minecraft.entity.ai.brain.task.MoveToTargetTask;
import net.minecraft.entity.ai.brain.task.RandomTask;
import net.minecraft.entity.ai.brain.task.StayAboveWaterTask;
import net.minecraft.entity.ai.brain.task.UpdateAttackTargetTask;
import net.minecraft.entity.ai.brain.task.UpdateLookControlTask;
import net.minecraft.entity.ai.brain.task.WaitTask;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Unit;

public class WildfireBrain {
	static final List<SensorType<? extends Sensor<? super WildfireEntity>>> SENSORS = ImmutableList.of(
			SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY, SensorType.NEAREST_PLAYERS, OtherRegistry.WILDFIRE_ATTACK_ENTITY_SENSOR
	);
	static final List<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(
			MemoryModuleType.LOOK_TARGET,
			MemoryModuleType.VISIBLE_MOBS,
			MemoryModuleType.NEAREST_ATTACKABLE,
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
			MemoryModuleType.ATTACK_TARGET,
			MemoryModuleType.WALK_TARGET,
			MemoryModuleType.BREEZE_JUMP_COOLDOWN,
			MemoryModuleType.BREEZE_JUMP_INHALING,
			MemoryModuleType.BREEZE_SHOOT,
			MemoryModuleType.BREEZE_SHOOT_CHARGING,
			MemoryModuleType.BREEZE_SHOOT_RECOVER,
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
		brain.resetPossibleActivities();
		return brain;
	}

	private static void addCoreTasks(Brain<WildfireEntity> brain) {
		brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new StayAboveWaterTask<>(0.8F), new UpdateLookControlTask(45, 90)));
	}

	private static void addIdleTasks(Brain<WildfireEntity> brain) {
		brain.setTaskList(
				Activity.IDLE,
				ImmutableList.of(
						Pair.of(
								0, UpdateAttackTargetTask.create((world, wildFire) -> wildFire.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_ATTACKABLE))
						),
						Pair.of(1, UpdateAttackTargetTask.create((world, wildFire) -> wildFire.getHurtBy())),
						Pair.of(2, new WildfireBrain.SlideAroundTask(20, 40)),
						Pair.of(3, new RandomTask<>(ImmutableList.of(Pair.of(new WaitTask(20, 100), 1),Pair.of(new WildfireSlideTowardsTargetTask(), 3))))
				)
		);
	}

	private static void addFightTasks(WildfireEntity wildFire, Brain<WildfireEntity> brain) {
		brain.setTaskList(
				Activity.FIGHT,
				ImmutableList.of(
						Pair.of(0, ForgetAttackTargetTask.create(Sensor.hasTargetBeenAttackableRecently(wildFire, 1).negate()::test)),
						Pair.of(1, new WildfireShootTask()),
						Pair.of(2, new WildfireMeleeTask()),
						Pair.of(3, new WildfireJumpTask()),
						Pair.of(4, new WildfireBombTask()),
						Pair.of(5, new WildfireSlideTowardsTargetTask())
				),
				ImmutableSet.of(
						Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT), Pair.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT)
				)
		);
	}

	static void updateActivities(WildfireEntity wildFire) {
		wildFire.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
	}

	public static class SlideAroundTask extends MoveToTargetTask {
		@VisibleForTesting
		public SlideAroundTask(int i, int j) {
			super(i, j);
		}

		@Override
		protected void run(ServerWorld serverWorld, MobEntity mobEntity, long l) {
			super.run(serverWorld, mobEntity, l);
			mobEntity.playSoundIfNotSilent(SoundEvents.ENTITY_BREEZE_SLIDE);
			mobEntity.setPose(EntityPose.STANDING);
		}

		@Override
		protected void finishRunning(ServerWorld serverWorld, MobEntity mobEntity, long l) {
			super.finishRunning(serverWorld, mobEntity, l);
			if (mobEntity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET)) {
				mobEntity.getBrain().remember(MemoryModuleType.BREEZE_SHOOT, Unit.INSTANCE, 60L);
			}
		}
	}
}
