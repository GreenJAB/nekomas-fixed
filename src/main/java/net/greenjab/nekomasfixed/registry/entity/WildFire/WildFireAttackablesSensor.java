package net.greenjab.nekomasfixed.registry.entity.WildFire;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.NearestLivingEntitiesSensor;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class WildFireAttackablesSensor extends NearestLivingEntitiesSensor<WildFireEntity> {
	@Override
	public Set<MemoryModuleType<?>> getOutputMemoryModules() {
		return ImmutableSet.copyOf(Iterables.concat(super.getOutputMemoryModules(), List.of(MemoryModuleType.NEAREST_ATTACKABLE)));
	}

	protected void sense(ServerWorld serverWorld, WildFireEntity wildFireEntity) {
		super.sense(serverWorld, wildFireEntity);
		wildFireEntity.getBrain()
			.getOptionalRegisteredMemory(MemoryModuleType.MOBS)
			.stream()
			.flatMap(Collection::stream)
			.filter(EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR)
			.filter(/* method_54996 */ target -> Sensor.testAttackableTargetPredicate(serverWorld, wildFireEntity, target))
			.findFirst()
			.ifPresentOrElse(
				/* method_54995 */ target -> wildFireEntity.getBrain().remember(MemoryModuleType.NEAREST_ATTACKABLE, target),
				/* method_54994 */ () -> wildFireEntity.getBrain().forget(MemoryModuleType.NEAREST_ATTACKABLE)
			);
	}
}
