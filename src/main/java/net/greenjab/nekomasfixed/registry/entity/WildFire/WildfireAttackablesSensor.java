package net.greenjab.nekomasfixed.registry.entity.WildFire;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestLivingEntitySensor;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.NonNull;

public class WildfireAttackablesSensor extends NearestLivingEntitySensor<WildfireEntity> {
	@Override
	public @NonNull Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.copyOf(Iterables.concat(super.requires(), List.of(MemoryModuleType.NEAREST_ATTACKABLE)));
	}

	protected void doTick(@NonNull ServerLevel serverWorld, @NonNull WildfireEntity wildFireEntity) {
		super.doTick(serverWorld, wildFireEntity);
		wildFireEntity.getBrain()
			.getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES)
			.stream()
			.flatMap(Collection::stream)
			.filter(EntitySelector.NO_CREATIVE_OR_SPECTATOR)
			.filter(target -> Sensor.isEntityAttackable(serverWorld, wildFireEntity, target))
			.filter(target -> target instanceof Player || target instanceof AgeableMob)
			.findFirst()
			.ifPresentOrElse(
				target -> wildFireEntity.getBrain().setMemory(MemoryModuleType.NEAREST_ATTACKABLE, target),
				() -> wildFireEntity.getBrain().eraseMemory(MemoryModuleType.NEAREST_ATTACKABLE)
			);
	}
}
