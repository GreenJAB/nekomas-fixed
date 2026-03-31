package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.entity.TermiteHiveBlockEntity;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockStateComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.rule.GameRules;
import net.minecraft.world.tick.ScheduledTickView;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class TermiteHiveBlock extends BlockWithEntity {
    public static final MapCodec<TermiteHiveBlock> CODEC = createCodec(TermiteHiveBlock::new);
    public static IntProperty TERMITES = IntProperty.of("termites", 0, 2);
    protected TermiteHiveBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(TERMITES, 0));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {return CODEC;}

    private boolean hasBees(World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof TermiteHiveBlockEntity termiteHiveBlockEntity) {
            return !termiteHiveBlockEntity.hasNoTermites();
        } else {
            return false;
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockEntity blockEntity = ctx.getWorld().getBlockEntity(ctx.getBlockPos());
        if(blockEntity instanceof TermiteHiveBlockEntity){
            if(((TermiteHiveBlockEntity) blockEntity).getTermiteCount()>0){
                return this.getDefaultState().with(TERMITES, ((TermiteHiveBlockEntity) blockEntity).getTermiteCount());
            }
        }
        return this.getDefaultState();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TERMITES);
    }

    @Override
    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient()) {
            return null;
        }
        if (type == BlockEntityTypeRegistry.TERMITE_HIVE_BLOCK_ENTITY) {
            return (world1, pos, state1, blockEntity) -> {
                if (blockEntity instanceof TermiteHiveBlockEntity hive) {
                    TermiteHiveBlockEntity.serverTick(world1, pos, state1, hive);
                }
            };
        }

        return null;
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (world instanceof ServerWorld serverWorld) {
            if (player.shouldSkipBlockDrops() && serverWorld.getGameRules().getValue(GameRules.DO_TILE_DROPS)) {
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof TermiteHiveBlockEntity termiteHiveBlockEntity) {
                    int i = state.get(TERMITES);
                    boolean bl = !termiteHiveBlockEntity.hasNoTermites();
                    if (bl || i > 0) {
                        ItemStack itemStack = new ItemStack(this);
                        itemStack.applyComponentsFrom(termiteHiveBlockEntity.createComponentMap());
                        itemStack.set(DataComponentTypes.BLOCK_STATE, BlockStateComponent.DEFAULT.with(TERMITES, i));
                        ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                        itemEntity.setToDefaultPickupDelay();
                        world.spawnEntity(itemEntity);
                    }
                }
            }
        }

        return super.onBreak(world, pos, state, player);
    }

    @Override
    protected List<ItemStack> getDroppedStacks(BlockState state, LootWorldContext.Builder builder) {
        Entity entity = builder.getOptional(LootContextParameters.THIS_ENTITY);
        return super.getDroppedStacks(state, builder);
    }

    @Override
    protected ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean includeData) {
        ItemStack itemStack = super.getPickStack(world, pos, state, includeData);
        if (includeData) {
            itemStack.set(DataComponentTypes.BLOCK_STATE, BlockStateComponent.DEFAULT.with(TERMITES, state.get(TERMITES)));
        }

        return itemStack;
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TermiteHiveBlockEntity(pos, state);
    }
}
