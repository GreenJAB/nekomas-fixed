package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.registry.registries.LootTableRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GhostPepperShrubBlock extends VegetationBlock implements BonemealableBlock {
    public static final MapCodec<GhostPepperShrubBlock> CODEC = simpleCodec(GhostPepperShrubBlock::new);
    public static final int MAX_AGE = 3;
    public static final IntegerProperty AGE;
    private static final VoxelShape SHAPE_SAPLING;
    private static final VoxelShape SHAPE_GROWING;


    public GhostPepperShrubBlock(Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(AGE, 0));
    }

    @Override
    protected boolean mayPlaceOn(final BlockState state, final BlockGetter level, final BlockPos pos) {
        return state.is(Blocks.SOUL_SAND) || state.is(Blocks.SOUL_SOIL);
    }

    @Override
    protected boolean canSurvive(final BlockState state, final LevelReader level, final BlockPos pos) {
        BlockPos below = pos.below();
        return this.mayPlaceOn(level.getBlockState(below), level, below);
    }

    @Override
    protected MapCodec<GhostPepperShrubBlock> codec() {
        return CODEC;
    }

    protected ItemStack getCloneItemStack(final LevelReader level, final BlockPos pos, final BlockState state, final boolean includeData) {
        return new ItemStack(ItemRegistry.GHOST_PEPPER);
    }

    protected VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        VoxelShape voxelShape;
        switch ((Integer)state.getValue(AGE)) {
            case 0 -> voxelShape = SHAPE_SAPLING;
            case 4 -> voxelShape = Shapes.block();
            default -> voxelShape = SHAPE_GROWING;
        }
        return voxelShape;
    }

    protected boolean isRandomlyTicking(final BlockState state) {
        return (Integer)state.getValue(AGE) < 4;
    }

    protected void randomTick(final BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource random) {
        int age = (Integer)state.getValue(AGE);
        if (age < 5 && random.nextInt(5) == 0 && level.getRawBrightness(pos.above(), 0) >= 9) {
            BlockState newState = (BlockState)state.setValue(AGE, age + 1);
            level.setBlock(pos, newState, 2);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(newState));
        }
    }

    protected InteractionResult useItemOn(final ItemStack itemStack, final BlockState state, final Level level, final BlockPos pos, final Player player, final InteractionHand hand, final BlockHitResult hitResult) {
        int age = (Integer)state.getValue(AGE);
        boolean isMaxAge = age == 4;
        return (InteractionResult)(!isMaxAge && itemStack.is(Items.BONE_MEAL) ? InteractionResult.PASS : super.useItemOn(itemStack, state, level, pos, player, hand, hitResult));
    }

    protected InteractionResult useWithoutItem(final BlockState state, final Level level, final BlockPos pos, final Player player, final BlockHitResult hitResult) {
        if ((Integer)state.getValue(AGE) > 1) {
            if (level instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel)level;
                Block.dropFromBlockInteractLootTable(serverLevel, LootTableRegistry.GHOST_PEPPER_LOOT_TABLE, state, level.getBlockEntity(pos), (ItemInstance)null, player, (serverlvl, itemStack) -> Block.popResource(serverlvl, pos, itemStack));
                serverLevel.playSound((Entity)null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + serverLevel.getRandom().nextFloat() * 0.4F);
                BlockState newState = (BlockState)state.setValue(AGE, 1);
                serverLevel.setBlock(pos, newState, 2);
                serverLevel.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, newState));
            }
            return InteractionResult.SUCCESS;
        } else {
            return super.useWithoutItem(state, level, pos, player, hitResult);
        }
    }

    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{AGE});
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return (Integer)state.getValue(AGE) < 4;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        int newAge = Math.min(4, (Integer)state.getValue(AGE) + 1);
        level.setBlock(pos, (BlockState)state.setValue(AGE, newAge), 2);
    }

    static {
        AGE = BlockStateProperties.AGE_4;
        SHAPE_SAPLING = Block.column((double)10.0F, (double)0.0F, (double)8.0F);
        SHAPE_GROWING = Block.column((double)14.0F, (double)0.0F, (double)16.0F);
    }
}