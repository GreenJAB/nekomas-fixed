package net.greenjab.nekomasfixed.mixin;

import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.world.spawn.PirateSpawner;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobEntity.class)
public class MobEntityMixin {

    @ModifyExpressionValue(method = "canMobSpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;allowsSpawning(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/EntityType;)Z"))
    private static boolean allowPirateSpawning(boolean original, @Local(argsOnly = true) WorldAccess world, @Local(argsOnly = true) SpawnReason spawnReason, @Local(argsOnly = true) BlockPos pos) {
        BlockPos blockPos = pos.down();
        if (spawnReason == SpawnReason.PATROL) {
            if (world.getBlockState(blockPos).isOf(Blocks.AIR)) return true;
            //System.out.println(original + ", " + world.getBlockState(blockPos));
        }
        return original;
    }
}
