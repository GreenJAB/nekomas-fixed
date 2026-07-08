package net.greenjab.nekomasfixed.registry.block.cauldron;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.cauldron.CauldronInteractions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.InsideBlockEffectType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

import java.util.Map;

public class IceCauldronBlock extends AbstractCauldronBlock {
    public static final MapCodec<IceCauldronBlock> CODEC = simpleCodec(IceCauldronBlock::new);
    private static final VoxelShape ICE_SHAPE = Block.column(12.0, 4.0, 15.0);
    private static final VoxelShape INSIDE_COLLISION_SHAPE = Shapes.or(AbstractCauldronBlock.SHAPE, ICE_SHAPE);

    @Override
    public @NonNull MapCodec<IceCauldronBlock> codec() {
        return CODEC;
    }

    public IceCauldronBlock(BlockBehaviour.Properties settings) {
        super(settings, createBehaviorMap());
    }

    protected @NonNull ItemStack getCloneItemStack(@NonNull LevelReader world, @NonNull BlockPos pos, @NonNull BlockState state, boolean includeData) {
        return Items.CAULDRON.getDefaultInstance();
    }

    private static CauldronInteraction.Dispatcher createBehaviorMap() {
        CauldronInteraction.Dispatcher map = new CauldronInteraction.Dispatcher();
        CauldronInteractions.ID_MAPPER.put("ice", map);

        map.put(Items.AIR, (state, world, pos, player, hand, stack) -> {
            if (!world.isClientSide()) {
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.ICE)));
                world.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
                world.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return InteractionResult.SUCCESS;
        });
        return map;
    }

    protected void entityInside(@NonNull BlockState state, @NonNull Level world, @NonNull BlockPos pos, @NonNull Entity entity, InsideBlockEffectApplier handler, boolean bl) {
       handler.apply(InsideBlockEffectType.FREEZE);
    }

    @Override
    protected void onPlace(@NonNull BlockState state, Level world, @NonNull BlockPos pos, @NonNull BlockState oldState, boolean notify) {
        if (!world.isClientSide()) {
            world.scheduleTick(pos, this, 200);
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
    protected @NonNull VoxelShape getEntityInsideCollisionShape(@NonNull BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull Entity entity) {
        return INSIDE_COLLISION_SHAPE;
    }

    @Override
    protected int getAnalogOutputSignal(@NonNull BlockState state, @NonNull Level world, @NonNull BlockPos pos, @NonNull Direction direction) {
        return 3;
    }
}