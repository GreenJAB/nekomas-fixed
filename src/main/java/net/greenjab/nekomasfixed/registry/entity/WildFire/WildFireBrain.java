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
import net.minecraft.entity.ai.brain.task.StrollTask;
import net.minecraft.entity.ai.brain.task.UpdateAttackTargetTask;
import net.minecraft.entity.ai.brain.task.UpdateLookControlTask;
import net.minecraft.entity.ai.brain.task.WaitTask;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Unit;

public class WildFireBrain {
	static final List<SensorType<? extends Sensor<? super WildFireEntity>>> SENSORS = ImmutableList.of(
			SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY, SensorType.NEAREST_PLAYERS, OtherRegistry.WILD_FIRE_ATTACK_ENTITY_SENSOR
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
			MemoryModuleType.PATH
	);

	protected static Brain<?> create(WildFireEntity wildFire, Brain<WildFireEntity> brain) {
		addCoreTasks(brain);
		addIdleTasks(brain);
		addFightTasks(wildFire, brain);
		brain.setCoreActivities(Set.of(Activity.CORE));
		brain.setDefaultActivity(Activity.FIGHT);
		brain.resetPossibleActivities();
		return brain;
	}

	private static void addCoreTasks(Brain<WildFireEntity> brain) {
		brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new StayAboveWaterTask<>(0.8F), new UpdateLookControlTask(45, 90)));
	}

	private static void addIdleTasks(Brain<WildFireEntity> brain) {
		brain.setTaskList(
				Activity.IDLE,
				ImmutableList.of(
						Pair.of(
								0, UpdateAttackTargetTask.create(/* method_55749 */ (world, wildFire) -> wildFire.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_ATTACKABLE))
						),
						Pair.of(1, UpdateAttackTargetTask.create(/* method_64470 */ (world, wildFire) -> wildFire.getHurtBy())),
						Pair.of(2, new WildFireBrain.SlideAroundTask(20, 40)),
						Pair.of(3, new RandomTask<>(ImmutableList.of(Pair.of(new WaitTask(20, 100), 1), Pair.of(StrollTask.create(0.6F), 2))))
				)
		);
	}

	private static void addFightTasks(WildFireEntity wildFire, Brain<WildFireEntity> brain) {
		brain.setTaskList(
				Activity.FIGHT,
				ImmutableList.of(
						Pair.of(0, ForgetAttackTargetTask.create(Sensor.hasTargetBeenAttackableRecently(wildFire, 100).negate()::test)),
						Pair.of(1, new WildFireShootTask()),
						Pair.of(2, new WildFireJumpTask()),
						Pair.of(3, new WildFireShootIfStuckTask()),
						Pair.of(4, new WildFireSlideTowardsTargetTask())
				),
				ImmutableSet.of(
						Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT), Pair.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT)
				)
		);
	}

	static void updateActivities(WildFireEntity wildFire) {
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
			mobEntity.setPose(EntityPose.SLIDING);
		}

		@Override
		protected void finishRunning(ServerWorld serverWorld, MobEntity mobEntity, long l) {
			super.finishRunning(serverWorld, mobEntity, l);
			mobEntity.setPose(EntityPose.STANDING);
			if (mobEntity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET)) {
				mobEntity.getBrain().remember(MemoryModuleType.BREEZE_SHOOT, Unit.INSTANCE, 60L);
			}
		}
	}
}
