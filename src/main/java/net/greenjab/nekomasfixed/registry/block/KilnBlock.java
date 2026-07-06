package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.entity.KilnBlockEntity;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class KilnBlock extends AbstractFurnaceBlock {
    public static final MapCodec<KilnBlock> CODEC = simpleCodec(KilnBlock::new);

    @Override
    public @NonNull MapCodec<KilnBlock> codec() {
        return CODEC;
    }

    public KilnBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    public BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
        return new KilnBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NonNull Level world, @NonNull BlockState state, @NonNull BlockEntityType<T> type) {
        return createFurnaceTicker(world, type, BlockEntityTypeRegistry.KILN_BLOCK_ENTITY);
    }

    @Override
    protected void openContainer(Level world, @NonNull BlockPos pos, @NonNull Player player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof KilnBlockEntity) {
            player.openMenu((MenuProvider)blockEntity);
            player.awardStat(Stats.INTERACT_WITH_BLAST_FURNACE);
        }
    }

    @Override
    public void animateTick(BlockState state, @NonNull Level world, @NonNull BlockPos pos, @NonNull RandomSource random) {
        if (state.getValue(LIT)) {
            double d = pos.getX() + 0.5;
            double e = pos.getY();
            double f = pos.getZ() + 0.5;
            if (random.nextDouble() < 0.1) {
                world.playLocalSound(d, e, f, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = state.getValue(FACING);
            Direction.Axis axis = direction.getAxis();
            double h = random.nextDouble() * 0.6 - 0.3;
            double i = axis == Direction.Axis.X ? direction.getStepX() * 0.52 : h;
            double j = random.nextDouble() * 9.0 / 16.0;
            double k = axis == Direction.Axis.Z ? direction.getStepZ() * 0.52 : h;
            world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0, 0.0, 0.0);
        }
    }


}
