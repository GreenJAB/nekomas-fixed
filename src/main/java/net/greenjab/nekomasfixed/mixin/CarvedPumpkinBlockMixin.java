package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.entity.TargetDummyEntity;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(CarvedPumpkinBlock.class)
public abstract class CarvedPumpkinBlockMixin {

    @Shadow
    private static void spawnEntity(World world, BlockPattern.Result patternResult, Entity entity, BlockPos pos) {
    }

    @Shadow @Final private static Predicate<BlockState> IS_GOLEM_HEAD_PREDICATE;
    @Unique
    @Nullable
    private BlockPattern targetDummyPattern;

    @Unique
    private BlockPattern getTargetDummyPattern() {
        if (this.targetDummyPattern == null) {
            this.targetDummyPattern = BlockPatternBuilder.start()
                    .aisle("^", "#")
                    .where('^', CachedBlockPosition.matchesBlockState(IS_GOLEM_HEAD_PREDICATE))
                    .where('#', CachedBlockPosition.matchesBlockState(/* method_72574 */ state -> state.isOf(Blocks.HAY_BLOCK)))
                    .build();
        }

        return this.targetDummyPattern;
    }

    @Inject(method="trySpawnEntity", at = @At( value = "HEAD"), cancellable = true)
    private void spawnTargetDummy(World world, BlockPos pos, CallbackInfo ci) {
        BlockState block = world.getBlockState(pos);
        BlockPattern.Result result3 = this.getTargetDummyPattern().searchAround(world, pos);
        if (result3 != null) {
            TargetDummyEntity targetDummyEntity = EntityTypeRegistry.TARGET_DUMMY.create(world, SpawnReason.TRIGGERED);
            if (targetDummyEntity != null) {
                spawnEntity(world, result3, targetDummyEntity, result3.translate(0, 1, 0).getBlockPos());
                if (block.isOf(Blocks.CARVED_PUMPKIN)) {
                    targetDummyEntity.refreshPositionAndAngles(result3.translate(0, 1, 0).getBlockPos(), block.get(HorizontalFacingBlock.FACING).getPositiveHorizontalDegrees(),0);
                }
                ci.cancel();
            }
        }
    }
}
