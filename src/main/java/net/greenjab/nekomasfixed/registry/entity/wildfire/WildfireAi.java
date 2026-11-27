package net.greenjab.nekomasfixed.registry.entity.wildfire;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraft.world.entity.schedule.Activity;

import java.util.Set;

public class WildfireAi {

    protected static Brain<?> makeBrain(WildfireEntity wildFire, Brain<WildfireEntity> brain) {
        initCoreActivity(brain);
        initIdleActivity(brain);
        initFightActivity(wildFire, brain);
        brain.setCoreActivities(Set.of(Activity.CORE));
        brain.setDefaultActivity(Activity.FIGHT);
        brain.useDefaultActivity();
        return brain;
    }

    static void updateActivities(WildfireEntity wildFire) {
        wildFire.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
    }

    private static void initCoreActivity(Brain<WildfireEntity> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(new Swim(0.8F), new LookAtTargetSink(45, 90)));
    }

    private static void initIdleActivity(Brain<WildfireEntity> brain) {
        brain.addActivity(Activity.IDLE, ImmutableList.of(
                Pair.of(0, StartAttacking.<WildfireEntity>create(wildFire -> wildFire.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE))),
                Pair.of(1, StartAttacking.<WildfireEntity>create(wildFire -> wildFire.getBrain().getMemory(MemoryModuleType.HURT_BY)
                        .map(DamageSource::getEntity).filter(entity -> entity instanceof LivingEntity).map(entity -> (LivingEntity) entity))),
                Pair.of(2, new SlideAroundTask(20, 40)),
                Pair.of(3, new RunOne<>(ImmutableList.of(Pair.of(new DoNothing(20, 100), 1), Pair.of(new WildfireSlideTowardsTargetTask(), 3))))));
    }

    private static void initFightActivity(WildfireEntity wildFire, Brain<WildfireEntity> brain) {
        brain.addActivityWithConditions(Activity.FIGHT, ImmutableList.of(
                Pair.of(0, StopAttackingIfTargetInvalid.create(target -> !Sensor.isEntityAttackable(wildFire, target))),
                Pair.of(1, new WildfireShootTask()),
                Pair.of(2, new WildfireMeleeTask()),
                Pair.of(3, new WildfireJumpTask()),
                Pair.of(4, new WildfireBombTask()),
                Pair.of(5, new WildfireSlideTowardsTargetTask())
        ), ImmutableSet.of(Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT), Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT)));
    }

    public static class SlideAroundTask extends MoveToTargetSink {
        public SlideAroundTask(int i, int j) {
            super(i, j);
        }

        @Override
        protected void start(ServerLevel level, Mob mobEntity, long l) {
            super.start(level, mobEntity, l);
            mobEntity.playSound(SoundEvents.BREEZE_SLIDE);
            mobEntity.setPose(Pose.STANDING);
        }

        @Override
        protected void stop(ServerLevel level, Mob mobEntity, long l) {
            super.stop(level, mobEntity, l);
            if (mobEntity.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
                mobEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_SHOOT, Unit.INSTANCE, 60L);
            }
        }
    }
}
