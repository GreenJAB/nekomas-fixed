package net.greenjab.nekomasfixed.registry.block.cauldron;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
    public static final CauldronInteraction.InteractionMap INTERACTIONS = CauldronInteraction.newInteractionMap("honey");

    public HoneyCauldronBlock(Properties settings) {
        super(settings, INTERACTIONS);
        this.registerDefaultState(this.stateDefinition.any().setValue(HONEY_LEVEL, MAX_LEVEL));
    }

    @Override
    public @NonNull MapCodec<HoneyCauldronBlock> codec() {
        return CODEC;
    }

    @Override
    public @NonNull ItemStack getCloneItemStack(@NonNull LevelReader level, @NonNull BlockPos pos, @NonNull BlockState state) {
        return Items.CAULDRON.getDefaultInstance();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HONEY_LEVEL);
    }

    // Registration is deferred to CauldronBehaviour.register(): the block class initializes
    // during a nested BlockRegistry clinit inside Items clinit (via the ItemsMixin clock
    // override), where Items fields declared after CLOCK would still be null.
    static void registerInteractions() {
        INTERACTIONS.map().put(Items.AIR, (state, level, pos, player, hand, stack) -> {
            if (state.getValue(HONEY_LEVEL) == MAX_LEVEL) {
                if (!level.isClientSide()) {
                    player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.HONEY_BLOCK)));
                    level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
                    level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                return ItemInteractionResult.SUCCESS;
            } else {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
        });

        INTERACTIONS.map().put(Items.GLASS_BOTTLE, (state, level, pos, player, hand, stack) -> {
            if (!level.isClientSide()) {
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.HONEY_BOTTLE)));
                if (state.getValue(HONEY_LEVEL) > 1)
                    level.setBlockAndUpdate(pos, state.setValue(HONEY_LEVEL, state.getValue(HONEY_LEVEL) - 1));
                else
                    level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
                level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return ItemInteractionResult.SUCCESS;
        });

        INTERACTIONS.map().put(Items.HONEY_BOTTLE, (state, level, pos, player, hand, stack) -> {
            if (state.getValue(HONEY_LEVEL) < MAX_LEVEL) {
                if (!level.isClientSide()) {
                    level.setBlockAndUpdate(pos, state.setValue(HONEY_LEVEL, state.getValue(HONEY_LEVEL) + 1));
                    player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
                    level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
            }
            return ItemInteractionResult.SUCCESS;
        });

    }

    @Override
    protected void entityInside(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Entity entity) {
        if (this.isEntityInsideContent(state, pos, entity) && entity instanceof LivingEntity living) {
            living.forceAddEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60), living);
        }
    }

    @Override
    protected void tick(@NonNull BlockState state, @NonNull ServerLevel level, @NonNull BlockPos pos, @NonNull RandomSource random) {
        if (!level.isClientSide()) {
            if (isBeeHiveAbove(pos, level)) {
                int currentLevel = state.getValue(HONEY_LEVEL);
                if (currentLevel < MAX_LEVEL) {
                    level.setBlockAndUpdate(pos, state.setValue(HONEY_LEVEL, currentLevel + 1));
                    level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
            }
        }
    }

    private boolean isBeeHiveAbove(BlockPos pos, Level level) {
        BlockPos abovePos = new BlockPos(pos.getX(), pos.getY() + 2, pos.getZ());
        Block block = level.getBlockState(abovePos).getBlock();
        return block == Blocks.BEEHIVE || block == Blocks.BEE_NEST;
    }

    @Override
    protected double getContentHeight(@NonNull BlockState state) {
        return (4.0 + state.getValue(HONEY_LEVEL) * 3.0) / 16.0;
    }

    @Override
    public boolean isFull(@NonNull BlockState state) {
        return state.getValue(HONEY_LEVEL) == MAX_LEVEL;
    }

    @Override
    protected int getAnalogOutputSignal(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos) {
        return state.getValue(HONEY_LEVEL);
    }
}
