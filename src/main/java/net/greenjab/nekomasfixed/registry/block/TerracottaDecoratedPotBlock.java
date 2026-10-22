package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.entity.TerracottaDecoratedPotBlockEntity;
import net.greenjab.nekomasfixed.registry.other.PotEngravingDecoration;
import net.greenjab.nekomasfixed.registry.other.PotFaceDecoration;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.TrialChambersStructurePools;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class TerracottaDecoratedPotBlock extends DecoratedPotBlock implements SimpleWaterloggedBlock {
    public static final Identifier SHERDS_DYNAMIC_DROP_ID = Identifier.withDefaultNamespace("sherds");
    public static final EnumProperty<Direction> HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty CRACKED = BlockStateProperties.CRACKED;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SHAPE = Block.column(14.0, 0.0, 16.0);

    public TerracottaDecoratedPotBlock(final BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(WATERLOGGED, false).setValue(CRACKED, false));
    }

    @Override
    protected BlockState updateShape(
            final BlockState state,
            final LevelReader level,
            final ScheduledTickAccess ticks,
            final BlockPos pos,
            final Direction directionToNeighbour,
            final BlockPos neighbourPos,
            final BlockState neighbourState,
            final RandomSource random
    ) {
        if (state.getValue(WATERLOGGED)) {
            ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        FluidState replacedFluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(HORIZONTAL_FACING, context.getHorizontalDirection())
                .setValue(WATERLOGGED, replacedFluidState.is(Fluids.WATER))
                .setValue(CRACKED, false);
    }


    @Override
    public void spawnDestroyParticles(Level level, BlockPos pos, BlockState state) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof TerracottaDecoratedPotBlockEntity potEntity) {
            Block faceBlock = potEntity.getPotFace().getSafeBlock();
            if(faceBlock.defaultBlockState().is(Blocks.BRICKS)){
                faceBlock = Blocks.TERRACOTTA;
            }
            level.levelEvent(2001, pos, Block.getId(faceBlock.defaultBlockState()));
        } else {
            super.spawnDestroyParticles(level, pos, state);
        }
    }

    @Override
    protected @NonNull InteractionResult useItemOn(final ItemStack itemStack, final BlockState state, final Level level, final BlockPos pos, final Player player, final InteractionHand hand, final BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof TerracottaDecoratedPotBlockEntity decoratedPot) {
            if (level.isClientSide()) {
                return InteractionResult.SUCCESS;
            }

            ItemStack potItem = decoratedPot.getTheItem();
            if (!itemStack.isEmpty() && (potItem.isEmpty() || ItemStack.isSameItemSameComponents(potItem, itemStack) && potItem.getCount() < potItem.getMaxStackSize())) {
                decoratedPot.wobble(TerracottaDecoratedPotBlockEntity.WobbleStyle.POSITIVE);
                player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
                ItemStack awardedItem = itemStack.consumeAndReturn(1, player);
                float pitchBend;
                if (decoratedPot.isEmpty()) {
                    decoratedPot.setTheItem(awardedItem);
                    pitchBend = (float)awardedItem.getCount() / awardedItem.getMaxStackSize();
                } else {
                    potItem.grow(1);
                    pitchBend = (float)potItem.getCount() / potItem.getMaxStackSize();
                }

                level.playSound(null, pos, SoundEvents.DECORATED_POT_INSERT, SoundSource.BLOCKS, 1.0F, 0.7F + 0.5F * pitchBend);
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.DUST_PLUME, pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, 7, 0.0, 0.0, 0.0, 0.0);
                }

                decoratedPot.setChanged();
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.TRY_WITH_EMPTY_HAND;
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    protected InteractionResult useWithoutItem(final BlockState state, final Level level, final BlockPos pos, final Player player, final BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof TerracottaDecoratedPotBlockEntity decoratedPot) {
            level.playSound(null, pos, SoundEvents.DECORATED_POT_INSERT_FAIL, SoundSource.BLOCKS, 1.0F, 1.0F);
            decoratedPot.wobble(TerracottaDecoratedPotBlockEntity.WobbleStyle.NEGATIVE);
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    protected boolean isPathfindable(final BlockState state, final PathComputationType type) {
        return false;
    }

    @Override
    protected VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, WATERLOGGED, CRACKED);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(final BlockPos worldPosition, final BlockState blockState) {
        return new TerracottaDecoratedPotBlockEntity(worldPosition, blockState);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    protected void affectNeighborsAfterRemoval(final BlockState state, final ServerLevel level, final BlockPos pos, final boolean movedByPiston) {
        Containers.updateNeighboursAfterDestroy(state, level, pos);
    }




    public static boolean isEmptySide(Direction ofHit, TerracottaDecoratedPotBlockEntity blockEntity, BlockState state) {
        Direction potFacing = state.getValue(TerracottaDecoratedPotBlock.HORIZONTAL_FACING);
        String sideName = getSideNameFromHit(ofHit, potFacing);
        PotEngravingDecoration decorations = blockEntity.getEngravingDecorations();

        if (sideName == null) return true;
        return isSideEmpty(blockEntity.getDecorations(), sideName);
    }

    public static String getSideNameFromHit(Direction hitFace, Direction potFacing) {
        if (hitFace.getAxis().isVertical()) {
            return null;
        }
        switch (potFacing) {
            case NORTH:
                if (hitFace == Direction.NORTH) return "front";
                if (hitFace == Direction.SOUTH) return "back";
                if (hitFace == Direction.WEST)  return "left";
                if (hitFace == Direction.EAST)  return "right";
                break;
            case SOUTH:
                if (hitFace == Direction.SOUTH) return "front";
                if (hitFace == Direction.NORTH) return "back";
                if (hitFace == Direction.EAST)  return "left";
                if (hitFace == Direction.WEST)  return "right";
                break;
            case EAST:
                if (hitFace == Direction.EAST)  return "front";
                if (hitFace == Direction.WEST)  return "back";
                if (hitFace == Direction.NORTH) return "left";
                if (hitFace == Direction.SOUTH) return "right";
                break;
            case WEST:
                if (hitFace == Direction.WEST)  return "front";
                if (hitFace == Direction.EAST)  return "back";
                if (hitFace == Direction.SOUTH) return "left";
                if (hitFace == Direction.NORTH) return "right";
                break;
            default: break;
        }
        return null;
    }

    public static boolean isSideEmpty(PotDecorations decorations, String side) {
        if (decorations == null || side == null) return true;
        return switch (side.toLowerCase()) {
            case "back"  -> decorations.back().isEmpty();
            case "left"  -> decorations.left().isEmpty();
            case "right" -> decorations.right().isEmpty();
            case "front" -> decorations.front().isEmpty();
            default -> true;
        };
    }

    @Override
    protected List<ItemStack> getDrops(final BlockState state, final LootParams.Builder params) {
        BlockEntity maybeEntity = params.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (maybeEntity instanceof TerracottaDecoratedPotBlockEntity entity) {
            params.withDynamicDrop(SHERDS_DYNAMIC_DROP_ID, output -> {
                PotDecorations dec = entity.getDecorations();
                dec.back().ifPresent(template -> output.accept(template.create()));
                dec.left().ifPresent(template -> output.accept(template.create()));
                dec.right().ifPresent(template -> output.accept(template.create()));
                dec.front().ifPresent(template -> output.accept(template.create()));
            });
        }

        return super.getDrops(state, params);
    }

    @Override
    public BlockState playerWillDestroy(final Level level, final BlockPos pos, final BlockState state, final Player player) {
        ItemStack destroyedWith = player.getMainHandItem();
        BlockState nextState = state;
        if (destroyedWith.is(ItemTags.BREAKS_DECORATED_POTS) && !EnchantmentHelper.hasTag(destroyedWith, EnchantmentTags.PREVENTS_DECORATED_POT_SHATTERING)) {
            nextState = state.setValue(CRACKED, true);
            level.setBlock(pos, nextState, 260);
        }

        return super.playerWillDestroy(level, pos, nextState, player);
    }

    @Override
    protected FluidState getFluidState(final BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected SoundType getSoundType(final BlockState state) {
        return state.getValue(CRACKED) ? SoundType.DECORATED_POT_CRACKED : SoundType.DECORATED_POT;
    }

    @Override
    protected void onProjectileHit(final Level level, final BlockState state, final BlockHitResult blockHit, final Projectile projectile) {
        BlockPos pos = blockHit.getBlockPos();
        if (level instanceof ServerLevel serverLevel && projectile.mayInteract(serverLevel, pos) && projectile.mayBreak(serverLevel, pos)) {
            level.setBlock(pos, state.setValue(CRACKED, true), 260);
            level.destroyBlock(pos, true, projectile);
        }
    }

    @Override
    protected ItemStack getCloneItemStack(final LevelReader level, final BlockPos pos, final BlockState state, final boolean includeData) {
        if (level.getBlockEntity(pos) instanceof TerracottaDecoratedPotBlockEntity decoratedPotBlockEntity) {
            PotDecorations decorations = decoratedPotBlockEntity.getDecorations();
            PotEngravingDecoration engravingDecorations = decoratedPotBlockEntity.getEngravingDecorations();
            PotFaceDecoration potFaceDecoration = decoratedPotBlockEntity.getPotFace();
            return TerracottaDecoratedPotBlockEntity.createDecoratedPotInstance(decorations, engravingDecorations, potFaceDecoration);
        } else {
            return super.getCloneItemStack(level, pos, state, includeData);
        }
    }

    @Override
    protected boolean hasAnalogOutputSignal(final BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(final BlockState state, final Level level, final BlockPos pos, final Direction direction) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
    }

    @Override
    protected BlockState rotate(final BlockState state, final Rotation rotation) {
        return state.setValue(HORIZONTAL_FACING, rotation.rotate(state.getValue(HORIZONTAL_FACING)));
    }

    @Override
    protected BlockState mirror(final BlockState state, final Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(HORIZONTAL_FACING)));
    }
}
