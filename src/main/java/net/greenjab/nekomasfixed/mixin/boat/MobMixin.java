package net.greenjab.nekomasfixed.mixin.boat;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Mob.class)
public class MobMixin {

    @ModifyExpressionValue(method = "checkMobSpawnRules", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isValidSpawn(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/EntityType;)Z"))
    private static boolean allowPirateSpawning(boolean original, @Local(argsOnly = true) LevelAccessor level, @Local(argsOnly = true) EntitySpawnReason spawnReason, @Local(argsOnly = true) BlockPos pos) {
        if (spawnReason == EntitySpawnReason.PATROL) {
            if (level.getBlockState(pos).is(Blocks.AIR)) return true;
        }
        return original;
    }
}
