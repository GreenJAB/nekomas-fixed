package net.greenjab.nekomasfixed.mixin.targetdummy;

import net.greenjab.nekomasfixed.registry.entity.TargetDummyEntity;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
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
    private static void spawnGolemInWorld(Level world, BlockPattern.BlockPatternMatch patternResult, Entity entity, BlockPos pos) {
    }

    @Shadow @Final private static Predicate<BlockState> PUMPKINS_PREDICATE;
    @Unique
    @Nullable
    private BlockPattern targetDummyPattern;

    @Unique
    private BlockPattern getTargetDummyPattern() {
        if (this.targetDummyPattern == null) {
            this.targetDummyPattern = BlockPatternBuilder.start()
                    .aisle("^", "#")
                    .where('^', BlockInWorld.hasState(PUMPKINS_PREDICATE))
                    .where('#', BlockInWorld.hasState(/* method_72574 */ state -> state.is(Blocks.HAY_BLOCK)))
                    .build();
        }

        return this.targetDummyPattern;
    }

    @Inject(method="trySpawnGolem", at = @At( value = "HEAD"), cancellable = true)
    private void spawnTargetDummy(Level world, BlockPos pos, CallbackInfo ci) {
        BlockState block = world.getBlockState(pos);
        BlockPattern.BlockPatternMatch result3 = this.getTargetDummyPattern().find(world, pos);
        if (result3 != null) {
            TargetDummyEntity targetDummyEntity = EntityTypeRegistry.TARGET_DUMMY.create(world, EntitySpawnReason.TRIGGERED);
            if (targetDummyEntity != null) {
                spawnGolemInWorld(world, result3, targetDummyEntity, result3.getBlock(0, 1, 0).getPos());
                if (block.is(Blocks.CARVED_PUMPKIN)) {
                    targetDummyEntity.snapTo(result3.getBlock(0, 1, 0).getPos(), block.getValue(HorizontalDirectionalBlock.FACING).toYRot(),0);
                }
                ci.cancel();
            }
        }
    }
}
