package net.greenjab.nekomasfixed.mixin.target_dummy;

import net.greenjab.nekomasfixed.registry.entity.TargetDummy;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
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
    @Final
    private static Predicate<BlockState> PUMPKINS_PREDICATE;
    @Unique
    private BlockPattern targetDummyPattern;

    @Shadow
    private static void spawnGolemInWorld(Level level, BlockPattern.BlockPatternMatch match, Entity golem, BlockPos spawnPos) {
    }

    @Unique
    private BlockPattern getTargetDummyPattern() {
        if (this.targetDummyPattern == null) {
            this.targetDummyPattern = BlockPatternBuilder.start()
                    .aisle("^", "#")
                    .where('^', BlockInWorld.hasState(PUMPKINS_PREDICATE))
                    .where('#', BlockInWorld.hasState(state -> state.is(Blocks.HAY_BLOCK)))
                    .build();
        }
        return this.targetDummyPattern;
    }

    @Inject(method = "trySpawnGolem", at = @At("HEAD"), cancellable = true)
    private void spawnTargetDummy(Level level, BlockPos topPos, CallbackInfo ci) {
        BlockState block = level.getBlockState(topPos);
        BlockPattern.BlockPatternMatch match = this.getTargetDummyPattern().find(level, topPos);
        if (match != null) {
            TargetDummy targetDummy = EntityTypeRegistry.TARGET_DUMMY.create(level);
            if (targetDummy != null) {
                spawnGolemInWorld(level, match, targetDummy, match.getBlock(0, 1, 0).getPos());
                if (block.is(Blocks.CARVED_PUMPKIN)) {
                    targetDummy.moveTo(match.getBlock(0, 1, 0).getPos(),
                            block.getValue(HorizontalDirectionalBlock.FACING).toYRot(), 0);
                }
                ci.cancel();
            }
        }
    }
}