package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.entity.TermitehiveBlockEntity;
import net.greenjab.nekomasfixed.registry.entity.Termite.TermiteEntity;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.FireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockStateComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.rule.GameRules;
import net.minecraft.world.tick.ScheduledTickView;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.function.BiConsumer;

public class TermitehiveBlock extends BlockWithEntity {
    public static final MapCodec<TermitehiveBlock> CODEC = createCodec(TermitehiveBlock::new);
    public static IntProperty TERMITES = IntProperty.of("termites", 0, 2);
    public TermitehiveBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(TERMITES, 0));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {return CODEC;}

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TERMITES);
    }

    @Override
    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient()) return null;

        if (type == BlockEntityTypeRegistry.TERMITE_HIVE_BLOCK_ENTITY) {
            return (world1, pos, state1, blockEntity) -> {
                if (blockEntity instanceof TermitehiveBlockEntity hive) {
                    TermitehiveBlockEntity.serverTick(world1, pos, state1, hive);

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
        if (world instanceof ServerWorld serverWorld
                && player.shouldSkipBlockDrops()
                && serverWorld.getGameRules().getValue(GameRules.DO_TILE_DROPS)
                && world.getBlockEntity(pos) instanceof TermitehiveBlockEntity termitehiveBlockEntity) {
            boolean bl = !termitehiveBlockEntity.hasNoTermites();
            if (bl) {
                ItemStack itemStack = new ItemStack(this);
                itemStack.applyComponentsFrom(termitehiveBlockEntity.createComponentMap());
                ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            }
        }
        return super.onBreak(world, pos, state, player);
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
    protected List<ItemStack> getDroppedStacks(BlockState state, LootWorldContext.Builder builder) {
        Entity entity = builder.getOptional(LootContextParameters.THIS_ENTITY);
        if (entity instanceof TntEntity
                || entity instanceof CreeperEntity
                || entity instanceof WitherSkullEntity
                || entity instanceof WitherEntity
                || entity instanceof TntMinecartEntity) {
            BlockEntity blockEntity = builder.getOptional(LootContextParameters.BLOCK_ENTITY);
            if (blockEntity instanceof TermitehiveBlockEntity termitehiveBlockEntity) {
                termitehiveBlockEntity.angerTermites(TermitehiveBlockEntity.TermiteState.EMERGENCY);
            }
        }

        return super.getDroppedStacks(state, builder);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(
            BlockState state,
            WorldView world,
            ScheduledTickView tickView,
            BlockPos pos,
            Direction direction,
            BlockPos neighborPos,
            BlockState neighborState,
            Random random
    ) {
        if (world.getBlockState(neighborPos).getBlock() instanceof FireBlock && world.getBlockEntity(pos) instanceof TermitehiveBlockEntity termitehiveBlockEntity) {
            termitehiveBlockEntity.angerTermites(TermitehiveBlockEntity.TermiteState.EMERGENCY);
        }

        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }


    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TermitehiveBlockEntity(pos, state);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.afterBreak(world, player, pos, state, blockEntity, tool);
        if (!world.isClient() && blockEntity instanceof TermitehiveBlockEntity termitehiveBlockEntity) {
            if (!EnchantmentHelper.hasAnyEnchantmentsIn(tool, EnchantmentTags.PREVENTS_BEE_SPAWNS_WHEN_MINING)) {
                termitehiveBlockEntity.angerTermites(TermitehiveBlockEntity.TermiteState.EMERGENCY);
                ItemScatterer.onStateReplaced(state, world, pos);
                this.angerNearbyTermites(world, pos);
            }

            Criteria.BEE_NEST_DESTROYED.trigger((ServerPlayerEntity)player, state, tool, termitehiveBlockEntity.getTermiteCount());
        }
    }

    @Override
    protected void onExploded(BlockState state, ServerWorld world, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> stackMerger) {
        super.onExploded(state, world, pos, explosion, stackMerger);
        this.angerNearbyTermites(world, pos);
    }

    private void angerNearbyTermites(World world, BlockPos pos) {
        Box box = new Box(pos).expand(8.0, 6.0, 8.0);
        List<TermiteEntity> list = world.getNonSpectatingEntities(TermiteEntity.class, box);
        if (!list.isEmpty()) {
            List<PlayerEntity> list2 = world.getNonSpectatingEntities(PlayerEntity.class, box);
            if (list2.isEmpty()) {
                return;
            }

            for (TermiteEntity termiteEntity : list) {
                if (termiteEntity.getTarget() == null) {
                    PlayerEntity playerEntity = Util.getRandom(list2, world.random);
                    termiteEntity.setTarget(playerEntity);
                }
            }
        }
    }
}
