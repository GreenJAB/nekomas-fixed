package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.SulfurFireBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.world.level.block.CampfireBlock.LIT;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin {
    @Inject(method = "makeParticles", at = @At("HEAD"), cancellable = true)
    private static void makeParticles(Level level, BlockPos pos, boolean isSignalFire, boolean smoking, CallbackInfo ci) {
        RandomSource random = level.getRandom();
        SimpleParticleType smokeParticle = isSignalFire ? ParticleTypes.CAMPFIRE_SIGNAL_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE;
        boolean isSulfur = level.getBlockState(pos).is(BlockRegistry.SULFUR_CAMPFIRE);
        if(isSulfur){
            smokeParticle = isSignalFire ? ParticleTypes.NOXIOUS_GAS_CLOUD : ParticleTypes.NOXIOUS_GAS;
        }
        level.addAlwaysVisibleParticle(
                smokeParticle,
                true,
                pos.getX() + 0.5 + random.nextDouble() / 3.0 * (random.nextBoolean() ? 1 : -1),
                pos.getY() + random.nextDouble() + random.nextDouble(),
                pos.getZ() + 0.5 + random.nextDouble() / 3.0 * (random.nextBoolean() ? 1 : -1),
                0.0,
                0.07,
                0.0
        );
        if (smoking) {
            level.addParticle(
                    isSulfur? ParticleTypes.NOXIOUS_GAS : ParticleTypes.SMOKE,
                    pos.getX() + 0.5 + random.nextDouble() / 4.0 * (random.nextBoolean() ? 1 : -1),
                    pos.getY() + 0.4,
                    pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (random.nextBoolean() ? 1 : -1),
                    0.0,
                    0.005,
                    0.0
            );
        }
        ci.cancel();
    }
}
