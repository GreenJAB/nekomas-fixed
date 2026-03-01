package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.logging.LogUtils;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.block.enums.TrialSpawnerState;
import net.minecraft.block.spawner.TrialSpawnerConfig;
import net.minecraft.block.spawner.TrialSpawnerData;
import net.minecraft.block.spawner.TrialSpawnerLogic;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.NbtReadView;
import net.minecraft.util.ErrorReporter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import java.util.Optional;

@Mixin(TrialSpawnerState.class)
public class TrialSpawnerStateMixin {


    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/spawner/TrialSpawnerData;hasSpawnedAllMobs(Lnet/minecraft/block/spawner/TrialSpawnerConfig;I)Z"))
    private boolean onlyOneWildfire(TrialSpawnerData instance, TrialSpawnerConfig config, int additionalPlayers, Operation<Boolean> original, @Local(argsOnly = true) TrialSpawnerLogic logic, @Local(argsOnly = true)ServerWorld world) {
        NbtCompound nbt = instance.getSpawnDataNbt(logic.getSpawnerState());
        if (nbt.getString("id").isPresent()) {
            try (ErrorReporter.Logging logging = new ErrorReporter.Logging(LogUtils.getLogger())) {
                Optional<EntityType<?>> entityType = EntityType.fromData(NbtReadView.create(logging, world.getRegistryManager(), nbt));
                if (entityType.isPresent()) {
                    if (entityType.get() == EntityTypeRegistry.WILD_FIRE) {
                        return instance.totalSpawnedMobs > (logic.isOminous()?1:0);
                    }
                }
            }
        }
        return original.call(instance, config, additionalPlayers);
    }

}
