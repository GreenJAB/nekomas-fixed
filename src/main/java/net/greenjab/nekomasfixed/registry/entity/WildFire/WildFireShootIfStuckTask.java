package net.greenjab.nekomasfixed.registry.entity.WildFire;

import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;

import java.util.Map;

public class WildFireShootIfStuckTask extends MultiTickTask<WildFireEntity> {
	public WildFireShootIfStuckTask() {
		super(
			Map.of(
				MemoryModuleType.ATTACK_TARGET,
				MemoryModuleState.VALUE_PRESENT,
				MemoryModuleType.BREEZE_JUMP_INHALING,
				MemoryModuleState.VALUE_ABSENT,
				MemoryModuleType.BREEZE_JUMP_TARGET,
				MemoryModuleState.VALUE_ABSENT,
				MemoryModuleType.WALK_TARGET,
				MemoryModuleState.VALUE_ABSENT,
				MemoryModuleType.BREEZE_SHOOT,
				MemoryModuleState.VALUE_ABSENT
			)
		);
	}

	protected boolean shouldRun(ServerWorld serverWorld, WildFireEntity wildFireEntity) {
		return wildFireEntity.hasVehicle() || wildFireEntity.isTouchingWater() || wildFireEntity.getStatusEffect(StatusEffects.LEVITATION) != null;
	}

	/*protected boolean shouldKeepRunning(ServerWorld serverWorld, WildFireEntity wildFireEntity, long l) {
		return false;
	}*/

	protected void run(ServerWorld serverWorld, WildFireEntity wildFireEntity, long l) {
		wildFireEntity.getBrain().remember(MemoryModuleType.BREEZE_SHOOT, Unit.INSTANCE, 60L);
	}
}
