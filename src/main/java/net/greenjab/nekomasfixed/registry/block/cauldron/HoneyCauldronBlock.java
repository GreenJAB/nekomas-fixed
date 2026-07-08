package net.greenjab.nekomasfixed.registry.block.cauldron;

import com.mojang.serialization.MapCodec;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.cauldron.CauldronInteractions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
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

public class HoneyCauldronBlock extends AbstractCauldronBlock {
    public static final MapCodec<HoneyCauldronBlock> CODEC = simpleCodec(HoneyCauldronBlock::new);

    public static final IntegerProperty HONEY_LEVEL = IntegerProperty.create("honey_level", 1, 4);
    public static final int MAX_LEVEL = 4;

    public HoneyCauldronBlock(Properties settings) {
        super(settings, createBehaviorMap());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(HONEY_LEVEL, MAX_LEVEL));
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
        builder.add(HONEY_LEVEL);
    }

    private static CauldronInteraction.Dispatcher newDispatcher() {
        CauldronInteraction.Dispatcher result = new CauldronInteraction.Dispatcher();
        CauldronInteractions.ID_MAPPER.put("honey", result);
        return result;
    }

    private static CauldronInteraction.Dispatcher createBehaviorMap() {
        CauldronInteraction.Dispatcher map = new CauldronInteraction.Dispatcher();
        CauldronInteractions.ID_MAPPER.put("honey", map);

        map.put(Items.AIR, (state, world, pos, player, hand, stack) -> {
            if(state.getValue(HONEY_LEVEL) == MAX_LEVEL) {
                if (!world.isClientSide()) {
                    player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.HONEY_BLOCK)));
                    world.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
                    world.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.TRY_WITH_EMPTY_HAND;
            }
        });

        map.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
            if (!world.isClientSide()) {
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.HONEY_BOTTLE)));
                int level = state.getValue(HONEY_LEVEL);
                if (level > 1) world.setBlockAndUpdate(pos, state.setValue(HONEY_LEVEL, level - 1));
                else  world.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());

                world.playSound(null, pos, SoundEvents.BOTTLE_FILL,
                        SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return InteractionResult.SUCCESS;
        });

        map.put(Items.HONEY_BOTTLE, (state, world, pos, player, hand, stack) -> {
            int level = state.getValue(HONEY_LEVEL);
            if (level < MAX_LEVEL) {
                if (!world.isClientSide()) {
                    world.setBlockAndUpdate(pos, state.setValue(HONEY_LEVEL, level + 1));
                    player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
                    world.playSound(null, pos, SoundEvents.BOTTLE_EMPTY,
                            SoundSource.BLOCKS, 1.0F, 1.0F);
                }
            }
            return InteractionResult.SUCCESS;
        });

        return map;
    }

    protected void entityInside(@NonNull BlockState state, @NonNull Level world, @NonNull BlockPos pos, Entity entity, @NonNull InsideBlockEffectApplier handler, boolean bl) {
        if (entity.asLivingEntity()!=null)
            entity.asLivingEntity().forceAddEffect(new MobEffectInstance(MobEffects.SLOWNESS, 3*20), entity.asLivingEntity());
    }

    @Override
    protected void tick(@NonNull BlockState state, ServerLevel world, @NonNull BlockPos pos, @NonNull RandomSource random) {
        if (!world.isClientSide()) {
            boolean hasBeehive = isBeeHiveAbove(pos, world);
            if (hasBeehive) {
                int currentLevel = state.getValue(HONEY_LEVEL);
                if (currentLevel < MAX_LEVEL) {
                    world.setBlockAndUpdate(pos, state.setValue(HONEY_LEVEL, currentLevel + 1));
                    world.playSound(null, pos, SoundEvents.BOTTLE_EMPTY,
                            SoundSource.BLOCKS, 1.0F, 1.0F);
                }
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

    private boolean isBeeHiveAbove(BlockPos pos, Level world) {
        BlockPos abovePos = new BlockPos(pos.getX(), pos.getY() + 2, pos.getZ());
        Block block = world.getBlockState(abovePos).getBlock();

        return block == Blocks.BEEHIVE || block == Blocks.BEE_NEST;
    }

    @Override
    protected double getContentHeight(BlockState state) {
        return (4.0 + state.getValue(HONEY_LEVEL) * 3.0) / 16.0;
    }

    @Override
    public boolean isFull(BlockState state) {
        return state.getValue(HONEY_LEVEL) == MAX_LEVEL;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, @NonNull Level world, @NonNull BlockPos pos, @NonNull Direction direction) {
        return state.getValue(HONEY_LEVEL);
    }
}