package net.greenjab.nekomasfixed.registry.block.cauldron;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

public class IceCauldronBlock extends AbstractCauldronBlock {
    public static final MapCodec<IceCauldronBlock> CODEC = simpleCodec(IceCauldronBlock::new);
    public static final CauldronInteraction.InteractionMap INTERACTIONS = CauldronInteraction.newInteractionMap("ice");

    public IceCauldronBlock(BlockBehaviour.Properties settings) {
        super(settings, INTERACTIONS);
    }

    @Override
    public @NonNull MapCodec<IceCauldronBlock> codec() {
        return CODEC;
    }

    @Override
    public @NonNull ItemStack getCloneItemStack(@NonNull LevelReader level, @NonNull BlockPos pos, @NonNull BlockState state) {
        return Items.CAULDRON.getDefaultInstance();
    }

    // Deferred population — see HoneyCauldronBlock.registerInteractions().
    static void registerInteractions() {
        INTERACTIONS.map().put(Items.AIR, (state, level, pos, player, hand, stack) -> {
            if (!level.isClientSide()) {
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.ICE)));
                level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
                level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return ItemInteractionResult.SUCCESS;
        });
    }

    @Override
    protected void entityInside(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Entity entity) {
        if (this.isEntityInsideContent(state, pos, entity) && entity.canFreeze()) {
            entity.setTicksFrozen(entity.getTicksFrozen() + 1);
        }
    }

    @Override
    protected double getContentHeight(@NonNull BlockState state) {
        return 0.9375;
    }

    @Override
    public boolean isFull(@NonNull BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos) {
        return 3;
    }
}
