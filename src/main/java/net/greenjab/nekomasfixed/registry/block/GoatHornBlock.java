package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.enums.GoatHornTorchType;
import net.greenjab.nekomasfixed.registry.block.enums.GoatHornType;
import net.minecraft.block.*;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.block.WireOrientation;
import net.minecraft.world.tick.ScheduledTickView;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static net.minecraft.util.math.Direction.*;

public class GoatHornBlock extends HorizontalFacingBlock implements Waterloggable {
    public static final MapCodec<GoatHornBlock> CODEC = createCodec(GoatHornBlock::new);
    public static final Property<Boolean> WATERLOGGED = Properties.WATERLOGGED;
    public static final EnumProperty<GoatHornTorchType> TORCH = EnumProperty.of("torch", GoatHornTorchType.class);
    public static final EnumProperty<GoatHornType> HORN = EnumProperty.of("horn", GoatHornType.class);
    public static final BooleanProperty POWERED = Properties.POWERED;
    Map<Direction, VoxelShape> SHAPE = VoxelShapes.createHorizontalFacingShapeMap(VoxelShapes.union(
            Block.createCuboidShape(7, 3, 0, 9, 5, 7),
            Block.createCuboidShape(6.5, 4, 4, 9.5, 6, 8),
            Block.createCuboidShape(6, 5, 5, 10, 10, 9)
    ));

    public GoatHornBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, NORTH)
                .with(WATERLOGGED, false)
                .with(HORN, GoatHornType.CALL)
                .with(TORCH, GoatHornTorchType.NONE)
                .with(POWERED, false)
        );
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE.get(state.get(FACING));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getOutlineShape(state, world, pos, context);
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return getOutlineShape(state, world, pos, ShapeContext.absent());
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient()){
            if (state.get(TORCH) != GoatHornTorchType.NONE) {
                if (stack.isOf(Items.SHEARS)) {
                    world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), state.get(TORCH).toItem().getDefaultStack()));
                    world.setBlockState(pos, state.with(TORCH, GoatHornTorchType.NONE));
                    world.playSoundFromEntity(null, player, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    stack.damage(1, player);
                    return ActionResult.SUCCESS;
                }
            } else {
                GoatHornTorchType type = GoatHornTorchType.fromItem(stack.getItem(), state.get(WATERLOGGED));
                if (type != GoatHornTorchType.NONE) {
                    world.setBlockState(pos, state.with(TORCH, type));
                    stack.decrementUnlessCreative(1, player);
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        Direction facing = state.get(FACING);
        double d = pos.getX() + 0.5;
        double e = pos.getY() + 1;
        double f = pos.getZ() + 0.5;

        switch (facing){
            case SOUTH -> f+= 0.1;
            case NORTH -> f =(f-0.1) + 0.03;
            case EAST -> d += 0.1;
            case WEST -> d = (d-0.1) + 0.03;
        }

        GoatHornTorchType type = state.get(TORCH);
        if (type == GoatHornTorchType.NONE || type == GoatHornTorchType.GLOW_TORCH_OFF) {return;}
        world.addParticleClient(ParticleTypes.SMOKE, d, e, f, 0, 0, 0);
        world.addParticleClient(type.getParticle(), d, e, f, 0, 0, 0);
    }

    @Override
    protected List<ItemStack> getDroppedStacks(BlockState state, LootWorldContext.Builder builder) {
        if (this.lootTableKey.isEmpty()) {
            return Collections.emptyList();
        } else {
            LootWorldContext lootWorldContext = builder.add(LootContextParameters.BLOCK_STATE, state).build(LootContextTypes.BLOCK);
            ServerWorld serverWorld = lootWorldContext.getWorld();
            assert serverWorld.getServer() != null;
            LootTable lootTable = serverWorld.getServer().getReloadableRegistries().getLootTable(this.lootTableKey.get());
            List<ItemStack> drops = lootTable.generateLoot(lootWorldContext);

            if (state.get(TORCH) != GoatHornTorchType.NONE) {
                drops.add(state.get(TORCH).toItem().getDefaultStack());
            }

            RegistryEntry<Instrument> entry = serverWorld.getRegistryManager().getOrThrow(RegistryKeys.INSTRUMENT).getOrThrow(state.get(HORN).getInstrument());
            drops.add(GoatHornItem.getStackForInstrument(Items.GOAT_HORN, entry));

            return drops;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, HORN, TORCH, POWERED);
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        if (!world.isClient()) {
            boolean bl = state.get(POWERED);
            if (bl != world.isReceivingRedstonePower(pos)) {
                if (bl) world.scheduleBlockTick(pos, this, 20);
                else {
                    RegistryEntry<Instrument> entry = world.getRegistryManager()
                            .getOrThrow(net.minecraft.registry.RegistryKeys.INSTRUMENT)
                            .getOrThrow(state.get(GoatHornBlock.HORN).getInstrument());
                    world.playSound(null, pos, entry.value().soundEvent().value(), SoundCategory.RECORDS, 3.0F, 1.0F);
                    world.setBlockState(pos, state.cycle(POWERED), Block.NOTIFY_LISTENERS);
                    if (world instanceof ServerWorld serverWorld) {
                        serverWorld.spawnParticles(ParticleTypes.NOTE, pos.getX()+0.5,pos.getY() +0.85,pos.getZ()+0.5,
                                0,0.1, 0.1, 0.1,0);
                    }
                }
            }
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        if (ctx.getWorld().getBlockState(ctx.getBlockPos().down()).isAir() || ctx.getWorld().getBlockState(ctx.getBlockPos().up()).isAir()) return null;
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(WATERLOGGED)) tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        if (!state.canPlaceAt(world, pos)) tickView.scheduleBlockTick(pos, this, 1);
        return state;
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!state.get(Properties.WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {
            if (!world.isClient()) {
                if (state.get(TORCH)==GoatHornTorchType.GLOW_TORCH_OFF) state = state.with(TORCH, GoatHornTorchType.GLOW_TORCH);
                world.setBlockState(pos, state.with(Properties.WATERLOGGED, true), Block.NOTIFY_ALL);
                world.scheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            }
            return true;
        } else return false;
    }

    @Override
    public ItemStack tryDrainFluid(@Nullable LivingEntity drainer, WorldAccess world, BlockPos pos, BlockState state) {
        if (state.get(Properties.WATERLOGGED)) {
            if (state.get(TORCH)==GoatHornTorchType.GLOW_TORCH) state = state.with(TORCH, GoatHornTorchType.GLOW_TORCH_OFF);
            world.setBlockState(pos, state.with(Properties.WATERLOGGED, false), Block.NOTIFY_ALL);
            if (!state.canPlaceAt(world, pos)) world.breakBlock(pos, true);
            return new ItemStack(Items.WATER_BUCKET);
        } else return ItemStack.EMPTY;
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction facing = state.get(FACING);
        BlockPos supportPos = pos.offset(facing);
        return world.getBlockState(supportPos).isSideSolidFullSquare(world, supportPos, facing.getOpposite());
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        } else {
            if (state.get(POWERED) && !world.isReceivingRedstonePower(pos)) {
                world.setBlockState(pos, state.cycle(POWERED), Block.NOTIFY_LISTENERS);
            }
        }
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }
}
