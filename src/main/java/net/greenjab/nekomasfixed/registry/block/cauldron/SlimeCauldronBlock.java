package net.greenjab.nekomasfixed.registry.block.cauldron;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.Map;

public class SlimeCauldronBlock extends AbstractCauldronBlock {
    public static final MapCodec<SlimeCauldronBlock> CODEC = createCodec(SlimeCauldronBlock::new);

    public static final IntProperty SLIME_LEVEL = IntProperty.of("slime_level", 1, 4);
    public static final int MAX_LEVEL = 4;

    public SlimeCauldronBlock(Settings settings) {
        super(settings, createBehaviorMap());
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(SLIME_LEVEL, MAX_LEVEL));
    }

    @Override
    protected MapCodec<? extends AbstractCauldronBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SLIME_LEVEL);
    }

    private static CauldronBehavior.CauldronBehaviorMap createBehaviorMap() {
        CauldronBehavior.CauldronBehaviorMap behaviorMap = CauldronBehavior.createMap("slime");
        Map<Item, CauldronBehavior> map = behaviorMap.map();

        map.put(Items.AIR, (state, world, pos, player, hand, stack) -> {
            if(state.get(SLIME_LEVEL) == MAX_LEVEL) {
                if (!world.isClient()) {
                    player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.SLIME_BLOCK)));
                    world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
                    world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
            }
        });

        map.put(Items.SLIME_BALL, (state, world, pos, player, hand, stack) -> {
            int level = state.get(SLIME_LEVEL);
            if (level < MAX_LEVEL) {
                if (!world.isClient()) {
                    stack.decrementUnlessCreative(1, player);
                    world.setBlockState(pos, state.with(SLIME_LEVEL, level + 1));
                    world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY,
                            SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
            return ActionResult.SUCCESS;
        });

        return behaviorMap;
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isClient()) {
            int currentLevel = state.get(SLIME_LEVEL);
            if (currentLevel < MAX_LEVEL) {
                world.setBlockState(pos, state.with(SLIME_LEVEL, currentLevel + 1));
                world.playSound(null, pos, SoundEvents.BLOCK_SLIME_BLOCK_BREAK,
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
        return (4.0 + state.get(SLIME_LEVEL) * 3.0) / 16.0;
    }

    @Override
    public boolean isFull(BlockState state) {
        return state.get(SLIME_LEVEL) == MAX_LEVEL;
    }
}