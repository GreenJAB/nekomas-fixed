package net.greenjab.nekomasfixed.registry.block.cauldron;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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

public class HoneyCauldronBlock extends AbstractCauldronBlock {
    public static final MapCodec<HoneyCauldronBlock> CODEC = createCodec(HoneyCauldronBlock::new);

    public static final IntProperty HONEY_LEVEL = IntProperty.of("honey_level", 1, 4);
    public static final int MAX_LEVEL = 4;

    public HoneyCauldronBlock(Settings settings) {
        super(settings, createBehaviorMap());
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(HONEY_LEVEL, MAX_LEVEL));
    }

    @Override
    protected MapCodec<? extends AbstractCauldronBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HONEY_LEVEL);
    }

    private static CauldronBehavior.CauldronBehaviorMap createBehaviorMap() {
        CauldronBehavior.CauldronBehaviorMap behaviorMap = CauldronBehavior.createMap("honey");
        Map<Item, CauldronBehavior> map = behaviorMap.map();

        map.put(Items.AIR, (state, world, pos, player, hand, stack) -> {
            if(state.get(HONEY_LEVEL) == MAX_LEVEL) {
                if (!world.isClient()) {
                    player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.HONEY_BLOCK)));
                    world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
                    world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
            }
        });

        map.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient()) {
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.HONEY_BOTTLE)));
                int level = state.get(HONEY_LEVEL);
                if (level > 1) world.setBlockState(pos, state.with(HONEY_LEVEL, level - 1));
                else  world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());

                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return ActionResult.SUCCESS;
        });

        map.put(Items.HONEY_BOTTLE, (state, world, pos, player, hand, stack) -> {
            int level = state.get(HONEY_LEVEL);
            if (level < MAX_LEVEL) {
                if (!world.isClient()) {
                    world.setBlockState(pos, state.with(HONEY_LEVEL, level + 1));
                    player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
                    world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY,
                            SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
            return ActionResult.SUCCESS;
        });

        return behaviorMap;
    }

    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, EntityCollisionHandler handler, boolean bl) {
        if (entity.getEntity()!=null)
            entity.getEntity().setStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 3*20), entity.getEntity());
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isClient()) {
            boolean hasBeehive = isBeeHiveAbove(pos, world);
            if (hasBeehive) {
                int currentLevel = state.get(HONEY_LEVEL);
                if (currentLevel < MAX_LEVEL) {
                    world.setBlockState(pos, state.with(HONEY_LEVEL, currentLevel + 1));
                    world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY,
                            SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
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

    private boolean isBeeHiveAbove(BlockPos pos, World world) {
        BlockPos abovePos = new BlockPos(pos.getX(), pos.getY() + 2, pos.getZ());
        Block block = world.getBlockState(abovePos).getBlock();

        return block == Blocks.BEEHIVE || block == Blocks.BEE_NEST;
    }

    @Override
    protected double getFluidHeight(BlockState state) {
        return (4.0 + state.get(HONEY_LEVEL) * 3.0) / 16.0;
    }

    @Override
    public boolean isFull(BlockState state) {
        return state.get(HONEY_LEVEL) == MAX_LEVEL;
    }
}