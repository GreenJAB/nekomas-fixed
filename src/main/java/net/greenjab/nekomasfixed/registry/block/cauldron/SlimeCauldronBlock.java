package net.greenjab.nekomasfixed.registry.block.cauldron;

import com.mojang.serialization.MapCodec;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
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

public class SlimeCauldronBlock extends AbstractCauldronBlock {
    public static final MapCodec<SlimeCauldronBlock> CODEC = simpleCodec(SlimeCauldronBlock::new);

    public static final IntegerProperty SLIME_LEVEL = IntegerProperty.create("slime_level", 1, 4);
    public static final int MAX_LEVEL = 4;

    public SlimeCauldronBlock(Properties settings) {
        super(settings, createBehaviorMap());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(SLIME_LEVEL, MAX_LEVEL));
    }

    protected @NonNull ItemStack getCloneItemStack(@NonNull LevelReader world, @NonNull BlockPos pos, @NonNull BlockState state, boolean includeData) {
        return Items.CAULDRON.getDefaultInstance();
    }

    @Override
    protected @NonNull MapCodec<? extends AbstractCauldronBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SLIME_LEVEL);
    }

    private static CauldronInteraction.InteractionMap createBehaviorMap() {
        CauldronInteraction.InteractionMap behaviorMap = CauldronInteraction.newInteractionMap("slime");
        Map<Item, CauldronInteraction> map = behaviorMap.map();

        map.put(Items.AIR, (state, world, pos, player, hand, stack) -> {
            if(state.getValue(SLIME_LEVEL) == MAX_LEVEL) {
                if (!world.isClientSide()) {
                    player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.SLIME_BLOCK)));
                    world.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
                    world.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.TRY_WITH_EMPTY_HAND;
            }
        });

        map.put(Items.SLIME_BALL, (state, world, pos, player, hand, stack) -> {
            int level = state.getValue(SLIME_LEVEL);
            if (level < MAX_LEVEL) {
                if (!world.isClientSide()) {
                    stack.consume(1, player);
                    world.setBlockAndUpdate(pos, state.setValue(SLIME_LEVEL, level + 1));
                    world.playSound(null, pos, SoundEvents.BOTTLE_EMPTY,
                            SoundSource.BLOCKS, 1.0F, 1.0F);
                }
            }
            return InteractionResult.SUCCESS;
        });

        return behaviorMap;
    }

    @Override
    protected void tick(@NonNull BlockState state, ServerLevel world, @NonNull BlockPos pos, @NonNull RandomSource random) {
        if (!world.isClientSide()) {
            int currentLevel = state.getValue(SLIME_LEVEL);
            if (currentLevel < MAX_LEVEL) {
                world.setBlockAndUpdate(pos, state.setValue(SLIME_LEVEL, currentLevel + 1));
                world.playSound(null, pos, SoundEvents.SLIME_BLOCK_BREAK,
                        SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
        world.scheduleTick(pos, this, 2000);
    }

    @Override
    protected void onPlace(@NonNull BlockState state, Level world, @NonNull BlockPos pos, @NonNull BlockState oldState, boolean notify) {
        if (!world.isClientSide()) {
            world.scheduleTick(pos, this, 2000);
        }
    }

    @Override
    protected double getContentHeight(BlockState state) {
        return (4.0 + state.getValue(SLIME_LEVEL) * 3.0) / 16.0;
    }

    @Override
    public boolean isFull(BlockState state) {
        return state.getValue(SLIME_LEVEL) == MAX_LEVEL;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, @NonNull Level world, @NonNull BlockPos pos, @NonNull Direction direction) {
        return state.getValue(SLIME_LEVEL);
    }
}