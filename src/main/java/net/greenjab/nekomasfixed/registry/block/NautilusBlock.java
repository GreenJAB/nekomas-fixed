package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.registry.block.entity.NautilusBlockEntity;
import net.greenjab.nekomasfixed.registry.block.enums.NautilusBlockType;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.LeadItem;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class NautilusBlock extends BaseEntityBlock {

    public static final MapCodec<NautilusBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    NautilusBlockType.CODEC.fieldOf("nautilus_block_type").forGetter(NautilusBlock::getNautilusBlockType),
                    propertiesCodec()
            ).apply(instance, NautilusBlock::new)
    );

    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

    public static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;

    private final NautilusBlockType nautilusBlockType;

    public NautilusBlock(NautilusBlockType nautilusBlockType, Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(OCCUPIED, false).setValue(FACING, Direction.NORTH));
        this.nautilusBlockType = nautilusBlockType;
    }

    @Override
    protected @NonNull MapCodec<? extends NautilusBlock> codec() {
        return CODEC;
    }

    @Override
    protected @NonNull RenderShape getRenderShape(@NonNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected boolean hasAnalogOutputSignal(@NonNull BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos) {
        return state.getValue(OCCUPIED) ? 15 : 0;
    }

    @Override
    protected @NonNull ItemInteractionResult useItemOn(@NonNull ItemStack stack, @NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hit) {
        boolean occupied = hasAnimal(level, pos);
        if (level instanceof ServerLevel) {
            if (occupied) {
                if (level.getBlockEntity(pos) instanceof NautilusBlockEntity nautilusBlockEntity) {
                    List<Entity> list = nautilusBlockEntity.tryReleaseAnimal(state);
                    if (!list.isEmpty()) {
                        level.setBlockAndUpdate(pos, state.setValue(NautilusBlock.OCCUPIED, false));
                        if (stack.is(Items.LEAD) && list.getFirst() instanceof Leashable leashable) {
                            leashable.setLeashedTo(player, true);
                            if (!player.getAbilities().instabuild) {
                                stack.shrink(1);
                            }
                            player.swing(hand);
                        }
                        return ItemInteractionResult.sidedSuccess(level.isClientSide);
                    }
                }
            } else {
                List<Leashable> list = LeadItem.leashableInArea(level, pos, leashable -> leashable.getLeashHolder() == player);
                for (Leashable leashable : list) {
                    if (leashable instanceof Animal animalEntity
                            && !player.isSecondaryUseActive()
                            && animalEntity.isAlive()
                            && animalEntity.distanceToSqr(Vec3.atCenterOf(pos)) < 10) {
                        if (level.getBlockEntity(pos) instanceof NautilusBlockEntity nautilusBlockEntity) {
                            if (animalEntity.getBoundingBox().getXsize() < 1 &&
                                    animalEntity.getBoundingBox().getYsize() < 1.5f) {
                                nautilusBlockEntity.tryEnterNautilus(animalEntity);
                                level.setBlockAndUpdate(pos, state.setValue(NautilusBlock.OCCUPIED, true));
                                return ItemInteractionResult.SUCCESS;
                            }
                        }
                    }
                }
            }
            return ItemInteractionResult.SUCCESS;
        } else {
            return occupied ? ItemInteractionResult.SUCCESS : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
    }

    private boolean hasAnimal(Level level, BlockPos pos) {
        return level.getBlockEntity(pos) instanceof NautilusBlockEntity nautilusBlockEntity && nautilusBlockEntity.hasAnimal();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(OCCUPIED, FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
        return new NautilusBlockEntity(pos, state);
    }

    @Override
    public @NonNull BlockState playerWillDestroy(@NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState state, @NonNull Player player) {
        if (level instanceof ServerLevel serverLevel
                && player.getAbilities().instabuild
                && serverLevel.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)
                && level.getBlockEntity(pos) instanceof NautilusBlockEntity nautilusBlockEntity) {
            boolean occupied = state.getValue(OCCUPIED);
            boolean bl = nautilusBlockEntity.hasAnimal();
            if (bl || occupied) {
                ItemStack itemStack = getItemStack(this.getNautilusBlockType());
                itemStack.applyComponents(nautilusBlockEntity.collectComponents());
                itemStack.set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(OCCUPIED, occupied));
                ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                itemEntity.setDefaultPickUpDelay();
                level.addFreshEntity(itemEntity);
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    public static ItemStack getItemStack(@Nullable NautilusBlockType nautilusBlockType) {
        return new ItemStack(get(nautilusBlockType));
    }

    public static Block get(@Nullable NautilusBlockType nautilusBlockType) {
        if (nautilusBlockType == null) {
            return BlockRegistry.NAUTILUS_BLOCK;
        } else {
            return switch (nautilusBlockType) {
                case REGULAR -> BlockRegistry.NAUTILUS_BLOCK;
                case ZOMBIE -> BlockRegistry.ZOMBIE_NAUTILUS_BLOCK;
                case CORAL -> BlockRegistry.CORAL_NAUTILUS_BLOCK;
            };
        }
    }

    @Override
    public @NonNull ItemStack getCloneItemStack(@NonNull LevelReader level, @NonNull BlockPos pos, @NonNull BlockState state) {
        ItemStack itemStack = super.getCloneItemStack(level, pos, state);
        itemStack.set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(OCCUPIED, state.getValue(OCCUPIED)));
        return itemStack;
    }

    @Override
    public @NonNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public @NonNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    public NautilusBlockType getNautilusBlockType() {
        return this.nautilusBlockType;
    }
}
