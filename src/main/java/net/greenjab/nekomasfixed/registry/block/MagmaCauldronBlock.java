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
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagmaCauldronBlock extends AbstractCauldronBlock {
    public static final MapCodec<MagmaCauldronBlock> CODEC = createCodec(MagmaCauldronBlock::new);

    public static final IntProperty MAGMA_LEVEL = IntProperty.of("magma_level", 1, 4);
    public static final int MAX_LEVEL = 4;

    public MagmaCauldronBlock(Settings settings) {
        super(settings, createBehaviorMap());
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(MAGMA_LEVEL, 1)); // Start at level 1 instead of max
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

        // Magma cream adds to cauldron
        map.put(Items.MAGMA_CREAM, (state, world, pos, player, hand, stack) -> {
            System.out.println("🔥 BEHAVIOR MAP: Magma cream used");
            if (!world.isClient()) {
                incrementMagmaLevel(state, world, pos, player, hand, stack);
            }
            return ActionResult.SUCCESS;
        });

        return behaviorMap;
    }

    public static void incrementMagmaLevel(BlockState state, World world, BlockPos pos,
                                           PlayerEntity player, Hand hand, ItemStack stack) {
        if (world.isClient()) return;

        int level = state.get(MAGMA_LEVEL);
        if (level < MAX_LEVEL) {
            world.setBlockState(pos, state.with(MAGMA_LEVEL, level + 1));
            stack.decrement(1);
            world.playSound(null, pos, SoundEvents.ENTITY_MAGMA_CUBE_SQUISH,
                    SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    // Fixed method name
    public static void incrementMagmaLevel(BlockState state, World world, BlockPos pos) {
        if (world.isClient()) return;

        int level = state.get(MAGMA_LEVEL);
        if (level < MAX_LEVEL) {
            world.setBlockState(pos, state.with(MAGMA_LEVEL, level + 1));
            world.playSound(null, pos, SoundEvents.ENTITY_MAGMA_CUBE_SQUISH,
                    SoundCategory.BLOCKS, 1.0F, 1.0F);
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