package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import static net.greenjab.nekomasfixed.util.MagmaCauldronUtil.incrementMagmaLevel;

public class MagmaCauldronBlock extends AbstractCauldronBlock {
    public static final MapCodec<MagmaCauldronBlock> CODEC = createCodec(MagmaCauldronBlock::new);

    public static final IntProperty MAGMA_LEVEL = IntProperty.of("magma_level", 1, 4);
    public static final int MAX_LEVEL = 4;

    public MagmaCauldronBlock(Settings settings) {
        super(settings, createBehaviorMap());
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(MAGMA_LEVEL, MAX_LEVEL));
    }

    @Override
    protected MapCodec<? extends AbstractCauldronBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(MAGMA_LEVEL);
    }

    private static CauldronBehavior.CauldronBehaviorMap createBehaviorMap() {
        var behaviorMap = CauldronBehavior.createMap("magma");
        var map = behaviorMap.map();
/*
        map.put(Items.MAGMA_CREAM, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient()) {
                int level = state.get(MAGMA_LEVEL);
                player.setStackInHand(hand, new ItemStack(Items.HONEY_BOTTLE));

                if (level > 1) {
                    world.setBlockState(pos, state.with(MAGMA_LEVEL, level - 1));
                } else {
                    world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
                }

                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return ActionResult.SUCCESS;
        });*/

        map.put(Items.MAGMA_CREAM, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient()) {
                incrementMagmaLevel(state, world, pos, player, hand);
            }
            return ActionResult.SUCCESS;
        });

        return behaviorMap;
    }

    // New method to increment honey level
    public static void incrementMagmaLevel(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand) {
        if (world.isClient()) return;

        int level = state.get(MAGMA_LEVEL);
        if (level < MAX_LEVEL) {
            world.setBlockState(pos, state.with(MAGMA_LEVEL, level + 1));

            world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY,
                    SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    // Overloaded method without player (for automatic filling)
    public static void incrementMagmaLevel(BlockState state, World world, BlockPos pos) {
        if (world.isClient()) return;

        int level = state.get(MAGMA_LEVEL);
        if (level < MAX_LEVEL) {
            world.setBlockState(pos, state.with(MAGMA_LEVEL, level + 1));
            world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY,
                    SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isClient()) {
                int currentLevel = state.get(MAGMA_LEVEL);
                if (currentLevel < MAX_LEVEL) {
                    world.setBlockState(pos, state.with(MAGMA_LEVEL, currentLevel + 1));
                    world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY,
                            SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
        }
        world.scheduleBlockTick(pos, this, 2000);
    }

    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.isClient()) {
            world.scheduleBlockTick(pos, this, 2000);
        }
    }

    @Override
    protected double getFluidHeight(BlockState state) {
        return (4.0 + state.get(MAGMA_LEVEL) * 3.0) / 16.0;
    }

    @Override
    public boolean isFull(BlockState state) {
        return state.get(MAGMA_LEVEL) == MAX_LEVEL;
    }
}