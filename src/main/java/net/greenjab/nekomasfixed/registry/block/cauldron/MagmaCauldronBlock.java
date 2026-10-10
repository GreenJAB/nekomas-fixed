package net.greenjab.nekomasfixed.registry.block.cauldron;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jspecify.annotations.NonNull;

public class MagmaCauldronBlock extends AbstractCauldronBlock {
    public static final MapCodec<MagmaCauldronBlock> CODEC = simpleCodec(MagmaCauldronBlock::new);
    public static final IntegerProperty MAGMA_LEVEL = IntegerProperty.create("magma_level", 1, 4);
    public static final int MAX_LEVEL = 4;
    public static final CauldronInteraction.InteractionMap INTERACTIONS = CauldronInteraction.newInteractionMap("magma");

    public MagmaCauldronBlock(Properties settings) {
        super(settings, INTERACTIONS);
        this.registerDefaultState(this.stateDefinition.any().setValue(MAGMA_LEVEL, MAX_LEVEL));
    }

    @Override
    public @NonNull MapCodec<MagmaCauldronBlock> codec() {
        return CODEC;
    }

    @Override
    public @NonNull ItemStack getCloneItemStack(@NonNull LevelReader level, @NonNull BlockPos pos, @NonNull BlockState state) {
        return Items.CAULDRON.getDefaultInstance();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MAGMA_LEVEL);
    }

    // Deferred population — see HoneyCauldronBlock.registerInteractions().
    static void registerInteractions() {
        INTERACTIONS.map().put(Items.AIR, (state, level, pos, player, hand, stack) -> {
            if (state.getValue(MAGMA_LEVEL) == MAX_LEVEL) {
                if (!level.isClientSide()) {
                    player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.MAGMA_BLOCK)));
                    level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
                    level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                return ItemInteractionResult.SUCCESS;
            } else {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
        });

        INTERACTIONS.map().put(Items.MAGMA_CREAM, (state, level, pos, player, hand, stack) -> {
            if (state.getValue(MAGMA_LEVEL) < MAX_LEVEL) {
                if (!level.isClientSide()) {
                    stack.consume(1, player);
                    level.setBlockAndUpdate(pos, state.setValue(MAGMA_LEVEL, state.getValue(MAGMA_LEVEL) + 1));
                    level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
            }
            return ItemInteractionResult.SUCCESS;
        });

    }

    @Override
    protected void entityInside(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Entity entity) {
        if (this.isEntityInsideContent(state, pos, entity)) {
            entity.setTicksFrozen(0);
            entity.lavaHurt();
        }
    }

    @Override
    protected void tick(@NonNull BlockState state, @NonNull ServerLevel level, @NonNull BlockPos pos, @NonNull RandomSource random) {
        if (!level.isClientSide()) {
            if (state.getValue(MAGMA_LEVEL) < MAX_LEVEL) {
                level.setBlockAndUpdate(pos, state.setValue(MAGMA_LEVEL, state.getValue(MAGMA_LEVEL) + 1));
                level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
    }

    @Override
    protected double getContentHeight(@NonNull BlockState state) {
        return (4.0 + state.getValue(MAGMA_LEVEL) * 3.0) / 16.0;
    }

    @Override
    public boolean isFull(@NonNull BlockState state) {
        return state.getValue(MAGMA_LEVEL) == MAX_LEVEL;
    }

    @Override
    protected int getAnalogOutputSignal(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos) {
        return state.getValue(MAGMA_LEVEL);
    }
}
