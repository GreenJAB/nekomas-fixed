package net.greenjab.nekomasfixed.registry.block.entity;

import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;//    _
import net.minecraft.world.entity.MobCategory;//   =|*|_,
import net.minecraft.world.level.Level;//  (quack)' \___/
import net.minecraft.world.level.block.entity.BeaconBeamOwner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Predicate;

public class CorruptedBeaconBlockEntity extends BlockEntity implements BeaconBeamOwner {
    public static final int BEAM_COLOR = 0xFFFF50FF; // Magenta but lighter. The beam texture quite darkens it

    // Beacon calculates it as (levels * 10 + 10), so this is equivalent to level 2 beacon
    public static final double RANGE = 30;
    // Different from beacon, reduced to shorter length to reduce the delay of the effect running out when the beacon is destroyed
    public static final int DURATION_TICKS = 100;

    private static final List<Holder<MobEffect>> EFFECTS;
    private static final int EFFECT_LEVEL = 1;

    // Selecting every living entity in the MONSTER mob category
    public static final Predicate<LivingEntity> ENTITY_FILTER = entity -> entity.getType().getCategory() == MobCategory.MONSTER;

    public CorruptedBeaconBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeRegistry.CORRUPTED_BEACON_BLOCK_ENTITY, pos, state);
    }

    @Override
    public List<Section> getBeamSections() {
        return List.of(new BeaconBeamOwner.Section(BEAM_COLOR));
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CorruptedBeaconBlockEntity blockEntity) {
        // Every 4 seconds (same as beacon)
        if (level.getGameTime() % 80L == 0L) {
            applyEffects(level, pos);
            level.playSound(null, pos, SoundEvents.BEACON_AMBIENT, SoundSource.BLOCKS, 1.0F, 0.7F);
            level.playSound(null, pos, SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 1.0F, 1.2F);
        }
    }

    private static void applyEffects(Level level, BlockPos pos) {
        if (level instanceof ServerLevel serverLevel) {
            // Area at the size of RANGE inflated all the way up to the build height
            // If I see it correctly it covers RANGE blocks under the block
            AABB area = (new AABB(pos)).inflate(RANGE).expandTowards(0D, level.getHeight(), 0D);

            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area, ENTITY_FILTER);

            for (LivingEntity entity : entities) {
                for (Holder<MobEffect> effect : EFFECTS) {
                    entity.addEffect(new MobEffectInstance(effect, DURATION_TICKS, EFFECT_LEVEL, false, true));
                }
                spawnParticleRay(serverLevel, pos, entity);
            }
        }
    }

    private static void spawnParticleRay(ServerLevel level, BlockPos pos, Entity target) {
        Vec3 center = Vec3.atCenterOf(pos);
        Vec3 relative = target.position().subtract(center).add(0, target.getBbHeight() / 2, 0);
        double distance = relative.length();
        Vec3 direction = relative.scale(1 / distance);

        double density = 3; // Particles per block
        for (int i = 0; i < (int)distance * density; i++) {
            // Skipping more and more particles the further away it is from the entity, at quadratic scale
            if (level.getRandom().nextInt(i*i+1) > 30) continue;
            level.sendParticles(ParticleTypes.GLOW,
                    -direction.x * i / density + target.getX(), // The direction is reversed like this so i=0 is at the entity
                    -direction.y * i / density + target.getY() + target.getBbHeight() / 2,
                    -direction.z * i / density + target.getZ(),
                    1, 0, 0, 0, 0);
        }
    }

    static {
        EFFECTS = List.of(MobEffects.SPEED, MobEffects.STRENGTH, MobEffects.ABSORPTION);
    }
}
