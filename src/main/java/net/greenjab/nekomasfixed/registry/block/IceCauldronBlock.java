package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.CollisionEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class IceCauldronBlock extends AbstractCauldronBlock {
    public static final MapCodec<IceCauldronBlock> CODEC = createCodec(IceCauldronBlock::new);
    private static final VoxelShape ICE_SHAPE = Block.createColumnShape(12.0, 4.0, 15.0);
    private static final VoxelShape INSIDE_COLLISION_SHAPE = VoxelShapes.union(AbstractCauldronBlock.OUTLINE_SHAPE, ICE_SHAPE);

    @Override
    public MapCodec<IceCauldronBlock> getCodec() {
        return CODEC;
    }

    public IceCauldronBlock(AbstractBlock.Settings settings) {
        super(settings, createBehaviorMap());
    }

    private static CauldronBehavior.CauldronBehaviorMap createBehaviorMap() {
        var behaviorMap = CauldronBehavior.createMap("ice");
        var map = behaviorMap.map();


        return behaviorMap;
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isClient()) {
            TagKey<Biome> COLD_BIOMES = TagKey.of(RegistryKeys.BIOME,
                    Identifier.of("c", "climate/cold"));
            boolean isCold = world.getBiome(pos).isIn(COLD_BIOMES);

            if (!isCold) {
                // Not cold - melt to water cauldron
                world.setBlockState(pos, Blocks.WATER_CAULDRON.getDefaultState()
                        .with(LeveledCauldronBlock.LEVEL, 3));
                world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);

            }
        }
        world.scheduleBlockTick(pos, this, 200);
    }

    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.isClient()) {
            world.scheduleBlockTick(pos, this, 200);
        }
    }

    @Override
    protected double getFluidHeight(BlockState state) {
        return 0.9375;
    }

    @Override
    public boolean isFull(BlockState state) {
        return true;
    }

    @Override
    protected VoxelShape getInsideCollisionShape(BlockState state, BlockView world, BlockPos pos, Entity entity) {
        return INSIDE_COLLISION_SHAPE;
    }

    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos, Direction direction) {
        return 3;
    }
}