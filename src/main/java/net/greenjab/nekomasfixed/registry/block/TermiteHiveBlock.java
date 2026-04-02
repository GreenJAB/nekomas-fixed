package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.entity.TermiteHiveBlockEntity;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockStateComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.rule.GameRules;
import org.jspecify.annotations.Nullable;

import java.util.List;

import static net.greenjab.nekomasfixed.NekomasFixed.enchantLevel;

public class TermiteHiveBlock extends BlockWithEntity {
    public static final MapCodec<TermiteHiveBlock> CODEC = createCodec(TermiteHiveBlock::new);
    public static IntProperty TERMITES = IntProperty.of("termites", 0, 2);
    public static final EnumProperty<Direction> FACING = EnumProperty.of("facing", Direction.class);
    public TermiteHiveBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(TERMITES, 0).with(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {return CODEC;}

    private boolean hasTermites(World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof TermiteHiveBlockEntity termiteHiveBlockEntity) {
            return !termiteHiveBlockEntity.hasNoTermites();
        } else {
            return false;
        }
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TERMITES, FACING);
    }

    @Override
    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient()) return null;

        if (type == BlockEntityTypeRegistry.TERMITE_HIVE_BLOCK_ENTITY) {
            return (world1, pos, state1, blockEntity) -> {
                if (blockEntity instanceof TermiteHiveBlockEntity hive) {
                    TermiteHiveBlockEntity.serverTick(world1, pos, state1, hive);

                    int current = state1.get(TERMITES);
                    int actual = hive.getTermiteCount();

                    if (current != actual) {
                        world1.setBlockState(pos, state1.with(TERMITES, actual), 3);
                    }
                }
            };
        }

        return null;
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (world instanceof ServerWorld serverWorld) {
            if (player.shouldSkipBlockDrops() && !world.isClient() && serverWorld.getGameRules().getValue(GameRules.DO_TILE_DROPS)) {
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof TermiteHiveBlockEntity termiteHiveBlockEntity) {
                    int i = termiteHiveBlockEntity.getTermiteCount();
                    if ( i > 0) {
                        ItemStack itemStack = new ItemStack(this);
                        itemStack.applyComponentsFrom(termiteHiveBlockEntity.createComponentMap());
                        if (enchantLevel(player.getMainHandStack(), "silk_touch") <= 0) {
//                            termiteHiveBlockEntity.();
                            i = 0;
                        }
                        itemStack.set(DataComponentTypes.BLOCK_STATE, BlockStateComponent.DEFAULT.with(TERMITES, i));
                        ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                        itemEntity.setToDefaultPickupDelay();
                        if (world.getBlockEntity(pos) instanceof TermiteHiveBlockEntity termiteHive) {
                            termiteHive.releaseAllTermites(world, state);
                        }
                        itemStack.applyComponentsFrom(termiteHiveBlockEntity.createComponentMap());
                        world.spawnEntity(itemEntity);
                    }
                }
            }
        }
        super.onBreak(world, pos, state, player);
        return state;
    }

    @Override
    protected List<ItemStack> getDroppedStacks(BlockState state, LootWorldContext.Builder builder) {
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
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TermiteHiveBlockEntity(pos, state);
    }
}
