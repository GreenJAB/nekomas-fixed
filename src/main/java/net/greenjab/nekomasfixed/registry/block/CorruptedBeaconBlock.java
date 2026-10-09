package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.entity.CorruptedBeaconBlockEntity;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class CorruptedBeaconBlock extends BaseEntityBlock {
    public static final MapCodec<CorruptedBeaconBlock> CODEC = simpleCodec(CorruptedBeaconBlock::new);

    public CorruptedBeaconBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NonNull MapCodec<? extends CorruptedBeaconBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
        return new CorruptedBeaconBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NonNull Level level, @NonNull BlockState blockState, @NonNull BlockEntityType<T> type) {
        return createTickerHelper(type, BlockEntityTypeRegistry.CORRUPTED_BEACON_BLOCK_ENTITY, CorruptedBeaconBlockEntity::tick);
    }

    // BaseEntityBlock defaults to INVISIBLE (model suppressed, BE-renderer-only). The beacon
    // has a real block model AND a beam renderer, so it needs MODEL like vanilla BeaconBlock.
    @Override
    protected @NonNull RenderShape getRenderShape(@NonNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void spawnAfterBreak(@NonNull BlockState state, @NonNull ServerLevel level, @NonNull BlockPos pos, @NonNull ItemStack tool, boolean dropExperience) {
        super.spawnAfterBreak(state, level, pos, tool, dropExperience);
        if (dropExperience) {
            int xp = 50 + level.getRandom().nextInt(51);
            this.popExperience(level, pos, xp);
        }
    }

    @Override
    protected void onPlace(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (!oldState.is(state.getBlock()) && !level.isClientSide()) {
            level.playSound(null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1.0F, 0.7F);
        }
    }

    @Override
    protected void onRemove(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        level.playSound(null, pos, SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS, 1.0F, 0.7F);
        level.playSound(null, pos, SoundEvents.TOTEM_USE, SoundSource.BLOCKS, 0.3F, 0.7F);
    }

    @Override
    public void animateTick(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull RandomSource random) {
        if (random.nextInt(2) != 0) return;
        ParticleUtils.spawnParticlesOnBlockFace(level, pos, ParticleTypes.END_ROD, UniformInt.of(1, 1), Direction.getRandom(level.getRandom()), () -> new Vec3(0, 0, 0), 0.55);
    }
}
