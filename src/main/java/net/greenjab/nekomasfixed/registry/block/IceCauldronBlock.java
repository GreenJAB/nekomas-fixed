package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;

public class IceCauldronBlock extends AbstractCauldronBlock {
    public static final MapCodec<IceCauldronBlock> CODEC = createCodec(IceCauldronBlock::new);

    // For ice cauldron, level 1 = has ice, level 0 = no ice (empty)
    public static final IntProperty ICE_LEVEL = IntProperty.of("ice_level", 0, 1);
    public static final int HAS_ICE = 1;
    public static final int NO_ICE = 0;

    public IceCauldronBlock(Settings settings) {
        super(settings, createBehaviorMap());
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(ICE_LEVEL, HAS_ICE));
    }

    @Override
    protected MapCodec<? extends AbstractCauldronBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ICE_LEVEL);
    }

    private static CauldronBehavior.CauldronBehaviorMap createBehaviorMap() {
        var behaviorMap = CauldronBehavior.createMap("ice");
        var map = behaviorMap.map();

        return behaviorMap;
    }

//    @Override
//    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
//        if (!world.isClient()) {
//            TagKey<Biome> COLD_BIOMES = TagKey.of(RegistryKeys.BIOME,
//                    Identifier.of("c", "climate/cold"));
//            boolean isCold = world.getBiome(pos).isIn(COLD_BIOMES);
//
//
//
//            if (isCold && state.getBlock().equals(Blocks.WATER_CAULDRON) && ) {
//                if (belowState.getBlock() == Blocks.WATER_CAULDRON) {
//                    world.setBlockState(belowPos, BlockRegistry.ICE_CAULDRON.getDefaultState()
//                            .with(ICE_LEVEL, HAS_ICE));
//                }
//            } else {
//                // Not cold - melt to water
//                world.setBlockState(pos, Blocks.WATER_CAULDRON.getDefaultState()
//                        .with(LeveledCauldronBlock.LEVEL, 3));
//                world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH,
//                        SoundCategory.BLOCKS, 1.0F, 1.0F);
//                System.out.println("Ice cauldron melted to water at " + pos);
//            }
//        }
//        world.scheduleBlockTick(pos, this, 200); // Check every 10 seconds
//    }

    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.isClient()) {
            world.scheduleBlockTick(pos, this, 200);
        }
    }

    @Override
    protected double getFluidHeight(BlockState state) {
        return state.get(ICE_LEVEL) == HAS_ICE ? 0.9375 : 0.0; // Full height if ice present
    }

    @Override
    public boolean isFull(BlockState state) {
        return state.get(ICE_LEVEL) == HAS_ICE;
    }
}