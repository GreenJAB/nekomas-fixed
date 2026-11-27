package net.greenjab.nekomasfixed.mixin.boat;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Mob.class)
public class MobMixin {

    @ModifyExpressionValue(method = "checkMobSpawnRules", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isValidSpawn(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/EntityType;)Z"))
    private static boolean allowPirateSpawning(boolean original, @Local(argsOnly = true) LevelAccessor level, @Local(argsOnly = true) MobSpawnType spawnType, @Local(argsOnly = true) BlockPos pos) {
        if (spawnType == MobSpawnType.PATROL && level.getBlockState(pos).is(Blocks.AIR) && level.getBlockState(pos.below()).is(Blocks.WATER)) {
            return true;
        }
        return original;
    }
}
