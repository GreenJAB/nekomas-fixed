package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.InsideBlockEffectType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PotentSulfurBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SulfurFireBlock extends BaseFireBlock {
    public static final MapCodec<SulfurFireBlock> CODEC = simpleCodec(SulfurFireBlock::new);
    private final ParticleOptions PARTICLE_GAS = ParticleTypes.NOXIOUS_GAS;
    private final ParticleOptions PARTICLE_SMOKE = ParticleTypes.LARGE_SMOKE;

    public SulfurFireBlock(Properties properties) {
        super(properties, 1.5f);
    }

    protected BlockState updateShape(final BlockState state, final LevelReader level, final ScheduledTickAccess ticks, final BlockPos pos, final Direction directionToNeighbour, final BlockPos neighbourPos, final BlockState neighbourState, final RandomSource random) {
        return this.canSurvive(state, level, pos) ? this.defaultBlockState() : Blocks.AIR.defaultBlockState();
    }

    protected boolean canSurvive(final BlockState state, final LevelReader level, final BlockPos pos) {
        return canSurviveOnBlock(level.getBlockState(pos.below()));
    }

    public static boolean canSurviveOnBlock(final BlockState state) {
        return state.is(ModTags.SULFUR_BLOCKS);
    }

    @Override
    protected MapCodec<? extends BaseFireBlock> codec() {
        return CODEC;
    }

    @Override
    protected boolean canBurn(BlockState state) {
        return true;
    }

    protected boolean hasNausea(Player player){
        for(MobEffectInstance effect : player.getActiveEffects()){
            if(effect.is(MobEffects.NAUSEA)){
                return true;
            }
        }
        return false;
    }

    @Override
    protected void entityInside(final BlockState state, final Level level, final BlockPos pos, final Entity entity, final InsideBlockEffectApplier effectApplier, final boolean isPrecise) {
        effectApplier.apply(InsideBlockEffectType.CLEAR_FREEZE);
        effectApplier.apply(InsideBlockEffectType.FIRE_IGNITE);
        effectApplier.runAfter(InsideBlockEffectType.FIRE_IGNITE, (e) -> e.hurt(e.level().damageSources().inFire(), super.fireDamage));
        if(entity instanceof Player player ){
            player.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 80, 0, true, true));

        }
    }


    @Override
    public void animateTick(final BlockState state, final Level level, final BlockPos pos, final RandomSource random) {
        if (random.nextInt(24) == 0) {
            level.playLocalSound((double)pos.getX() + (double)0.5F, (double)pos.getY() + (double)0.5F, (double)pos.getZ() + (double)0.5F, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
        }

        BlockPos below = pos.below();
        BlockState belowState = level.getBlockState(below);
        if (!this.canBurn(belowState) && !belowState.isFaceSturdy(level, below, Direction.UP)) {
            if (this.canBurn(level.getBlockState(pos.west()))) {
                for(int i = 0; i < 1; ++i) {
                    double xx = (double)pos.getX() + random.nextDouble() * (double)0.1F;
                    double yy = (double)pos.getY() + random.nextDouble() + 1;
                    double zz = (double)pos.getZ() + random.nextDouble();
                    level.addParticle(PARTICLE_SMOKE, xx, yy, zz, (double)0.0F, (double)0.0F, (double)0.0F);
                    if(i<1) level.addParticle(PARTICLE_GAS, xx, yy, zz, (double)0.0F, (double)0.0F, (double)0.0F);
                }
            }

            if (this.canBurn(level.getBlockState(pos.east()))) {
                for(int i = 0; i < 1; ++i) {
                    double xx = (double)(pos.getX() + 1) - random.nextDouble() * (double)0.1F;
                    double yy = (double)pos.getY() + random.nextDouble() + 1;
                    double zz = (double)pos.getZ() + random.nextDouble();
                    level.addParticle(PARTICLE_SMOKE, xx, yy, zz, (double)0.0F, (double)0.0F, (double)0.0F);
                    if(i<1) level.addParticle(PARTICLE_GAS, xx, yy, zz, (double)0.0F, (double)0.0F, (double)0.0F);

                }
            }

            if (this.canBurn(level.getBlockState(pos.north()))) {
                for(int i = 0; i < 1; ++i) {
                    double xx = (double)pos.getX() + random.nextDouble();
                    double yy = (double)pos.getY() + random.nextDouble() + 1;
                    double zz = (double)pos.getZ() + random.nextDouble() * (double)0.1F;
                    level.addParticle(PARTICLE_SMOKE, xx, yy, zz, (double)0.0F, (double)0.0F, (double)0.0F);
                    if(i<1) level.addParticle(PARTICLE_GAS, xx, yy, zz, (double)0.0F, (double)0.0F, (double)0.0F);

                }
            }

            if (this.canBurn(level.getBlockState(pos.south()))) {
                for(int i = 0; i < 1; ++i) {
                    double xx = (double)pos.getX() + random.nextDouble();
                    double yy = (double)pos.getY() + random.nextDouble() + 1;
                    double zz = (double)(pos.getZ() + 1) - random.nextDouble() * (double)0.1F;
                    level.addParticle(PARTICLE_SMOKE, xx, yy, zz, (double)0.0F, (double)0.0F, (double)0.0F);
                    if(i<1) level.addParticle(PARTICLE_GAS, xx, yy, zz, (double)0.0F, (double)0.0F, (double)0.0F);
                }
            }

            if (this.canBurn(level.getBlockState(pos.above()))) {
                for(int i = 0; i < 1; ++i) {
                    double xx = (double)pos.getX() + random.nextDouble();
                    double yy = (double)(pos.getY() + 1) - random.nextDouble() * (double)1;
                    double zz = (double)pos.getZ() + random.nextDouble();
                    level.addParticle(PARTICLE_SMOKE, xx, yy, zz, (double)0.0F, (double)0.0F, (double)0.0F);
                    if(i<1) level.addParticle(PARTICLE_GAS, xx, yy, zz, (double)0.0F, (double)0.0F, (double)0.0F);
                }
            }
        } else {
            for(int i = 0; i < 3; ++i) {
                double xx = (double)pos.getX() + random.nextDouble();
                double yy = (double)pos.getY() + random.nextDouble() * (double)0.5F + (double)1F;
                double zz = (double)pos.getZ() + random.nextDouble();
                level.addParticle(PARTICLE_SMOKE, xx, yy, zz, (double)0.0F, (double)0.0F, (double)0.0F);
                if(i<1) level.addParticle(PARTICLE_GAS, xx, yy, zz, (double)0.0F, (double)0.0F, (double)0.0F);
            }
        }

    }
}
