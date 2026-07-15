package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.entity.TermitehiveBlockEntity;
import net.greenjab.nekomasfixed.registry.entity.Termite;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;
import net.minecraft.world.entity.vehicle.minecart.MinecartTNT;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.function.BiConsumer;

public class TermitehiveBlock extends BaseEntityBlock {
    public static final MapCodec<TermitehiveBlock> CODEC = simpleCodec(TermitehiveBlock::new);
    public static IntegerProperty TERMITES = IntegerProperty.create("termites", 0, 2);
    public TermitehiveBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(TERMITES, 0));
    }

    @Override
    protected @NonNull MapCodec<? extends BaseEntityBlock> codec() {return CODEC;}

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TERMITES);
    }

    @Override
    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level level, @NonNull BlockState state, @NonNull BlockEntityType<T> type) {
        if (level.isClientSide()) return null;

        if (type == BlockEntityTypeRegistry.TERMITE_HIVE_BLOCK_ENTITY) {
            return (level1, pos, state1, blockEntity) -> {
                if (blockEntity instanceof TermitehiveBlockEntity hive) {
                    TermitehiveBlockEntity.serverTick(level1, pos, state1, hive);
                    int current = state1.getValue(TERMITES);
                    int actual = hive.getTermiteCount();
                    if (current != actual) level1.setBlock(pos, state1.setValue(TERMITES, actual), 3);
                }
            };
        }
        return null;
    }

    @Override
    public @NonNull BlockState playerWillDestroy(@NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState state, @NonNull Player player) {
        if (level instanceof ServerLevel serverLevel
                && player.preventsBlockDrops()
                && serverLevel.getGameRules().get(GameRules.BLOCK_DROPS)
                && level.getBlockEntity(pos) instanceof TermitehiveBlockEntity termitehiveBlockEntity) {
            boolean bl = !termitehiveBlockEntity.hasNoTermites();
            if (bl) {
                ItemStack itemStack = new ItemStack(this);
                itemStack.applyComponents(termitehiveBlockEntity.collectComponents());
                ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                itemEntity.setDefaultPickUpDelay();
                level.addFreshEntity(itemEntity);
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }


    @Override
    protected @NonNull ItemStack getCloneItemStack(@NonNull LevelReader level, @NonNull BlockPos pos, @NonNull BlockState state, boolean includeData) {
        ItemStack itemStack = super.getCloneItemStack(level, pos, state, includeData);
        if (includeData) itemStack.set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(TERMITES, state.getValue(TERMITES)));
        return itemStack;
    }

    @Override
    protected @NonNull List<ItemStack> getDrops(@NonNull BlockState state, LootParams.Builder builder) {
        Entity entity = builder.getOptionalParameter(LootContextParams.THIS_ENTITY);
        if (entity instanceof PrimedTnt
                || entity instanceof Creeper
                || entity instanceof WitherSkull
                || entity instanceof WitherBoss
                || entity instanceof MinecartTNT) {
            BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
            if (blockEntity instanceof TermitehiveBlockEntity termitehiveBlockEntity) {
                termitehiveBlockEntity.angerTermites(TermitehiveBlockEntity.TermiteState.EMERGENCY);
            }
        }

        return super.getDrops(state, builder);
    }

    @Override
    protected @NonNull BlockState updateShape(
            @NonNull BlockState state,
            LevelReader level,
            @NonNull ScheduledTickAccess tickView,
            @NonNull BlockPos pos,
            @NonNull Direction direction,
            @NonNull BlockPos neighborPos,
            @NonNull BlockState neighborState,
            @NonNull RandomSource random
    ) {
        if (level.getBlockState(neighborPos).getBlock() instanceof FireBlock && level.getBlockEntity(pos) instanceof TermitehiveBlockEntity termitehiveBlockEntity) {
            termitehiveBlockEntity.angerTermites(TermitehiveBlockEntity.TermiteState.EMERGENCY);
        }
        return super.updateShape(state, level, tickView, pos, direction, neighborPos, neighborState, random);
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
        return new TermitehiveBlockEntity(pos, state);
    }

    @Override
    public void playerDestroy(@NonNull Level level, @NonNull Player player, @NonNull BlockPos pos, @NonNull BlockState state, @Nullable BlockEntity blockEntity, @NonNull ItemStack tool) {
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
        if (!level.isClientSide() && blockEntity instanceof TermitehiveBlockEntity termitehiveBlockEntity) {
            if (!EnchantmentHelper.hasTag(tool, EnchantmentTags.PREVENTS_BEE_SPAWNS_WHEN_MINING)) {
                termitehiveBlockEntity.angerTermites(TermitehiveBlockEntity.TermiteState.EMERGENCY);
                Containers.updateNeighboursAfterDestroy(state, level, pos);
                this.angerNearbyTermites(level, pos);
            }
            CriteriaTriggers.BEE_NEST_DESTROYED.trigger((ServerPlayer)player, state, tool, termitehiveBlockEntity.getTermiteCount());
        }
    }

    @Override
    protected void onExplosionHit(@NonNull BlockState state, @NonNull ServerLevel level, @NonNull BlockPos pos, @NonNull Explosion explosion, @NonNull BiConsumer<ItemStack, BlockPos> stackMerger) {
        super.onExplosionHit(state, level, pos, explosion, stackMerger);
        this.angerNearbyTermites(level, pos);
    }

    private void angerNearbyTermites(Level level, BlockPos pos) {
        AABB box = new AABB(pos).inflate(8.0, 6.0, 8.0);
        List<Termite> list = level.getEntitiesOfClass(Termite.class, box);
        if (!list.isEmpty()) {
            List<Player> list2 = level.getEntitiesOfClass(Player.class, box);
            if (list2.isEmpty())  return;
            for (Termite termite : list) {
                if (termite.getTarget() == null) {
                    Player playerEntity = Util.getRandom(list2, level.getRandom());
                    termite.setTarget(playerEntity);
                }
            }
        }
    }
}
