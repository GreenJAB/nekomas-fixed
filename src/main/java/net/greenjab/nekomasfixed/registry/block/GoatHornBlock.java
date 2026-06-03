package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.enums.GoatHornTorchType;
import net.greenjab.nekomasfixed.registry.block.enums.GoatHornType;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.List;

import static net.minecraft.util.math.Direction.*;

public class GoatHornBlock extends HorizontalFacingBlock implements Waterloggable {
    public static final MapCodec<GoatHornBlock> CODEC = createCodec(GoatHornBlock::new);
    public static final Property<Boolean> WATERLOGGED = Properties.WATERLOGGED;
    public static final IntProperty LIGHT_LEVEL = IntProperty.of("light_level", 0, 15);
    public static final EnumProperty<GoatHornTorchType> HAS_TORCH = EnumProperty.of("torch", GoatHornTorchType.class);
    public static final EnumProperty<GoatHornType> HORN = EnumProperty.of("horn", GoatHornType.class);
    private static final VoxelShape NORTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(7, 3, 0, 9, 5, 7),
            Block.createCuboidShape(6.5, 4, 4, 9.5, 6, 8),
            Block.createCuboidShape(6, 5, 5, 10, 10, 9)
    );
    private static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(7, 3, 9, 9, 5, 16),
            Block.createCuboidShape(6.5, 4, 8, 9.5, 6, 12),
            Block.createCuboidShape(6, 5, 7, 10, 10, 11)
    );
    private static final VoxelShape EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(9, 3, 7, 16, 5, 9),
            Block.createCuboidShape(8, 4, 6.5, 12, 6, 9.5),
            Block.createCuboidShape(7, 5, 6, 11, 10, 10)
    );
    private static final VoxelShape WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 3, 7, 7, 5, 9),
            Block.createCuboidShape(4, 4, 6.5, 8, 6, 9.5),
            Block.createCuboidShape(5, 5, 6, 9, 10, 10)
    );

    public GoatHornBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, NORTH)
                .with(WATERLOGGED, false)
                .with(HORN, GoatHornType.CALL)
                .with(LIGHT_LEVEL, 0)
                .with(HAS_TORCH, GoatHornTorchType.NONE)
        );
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            default -> NORTH_SHAPE;
        };
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
            if (stack.isOf(Items.SHEARS) && state.get(HAS_TORCH) != GoatHornTorchType.NONE) {
                world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), GoatHornTorchType.getItem(state.get(HAS_TORCH))));
                BlockState newState = state.with(HAS_TORCH, GoatHornTorchType.NONE).with(LIGHT_LEVEL, 0);
                world.setBlockState(pos, newState);
                world.playSoundFromEntity(null, player, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1.0F, 1.0F);

                stack.damage(1, player);
            }
            if(state.get(HAS_TORCH) != GoatHornTorchType.NONE) return ActionResult.FAIL;
            if(stack.isOf(Items.TORCH)){
                BlockState newState = state
                        .with(HAS_TORCH, GoatHornTorchType.TORCH)
                        .with(LIGHT_LEVEL, 15);
                world.setBlockState(pos, newState);
                stack.decrementUnlessCreative(1, player);
            }
            else if(stack.isOf(Items.COPPER_TORCH)){
                BlockState newState = state
                        .with(HAS_TORCH, GoatHornTorchType.COPPER_TORCH)
                        .with(LIGHT_LEVEL, 13);
                world.setBlockState(pos, newState);
                stack.decrementUnlessCreative(1, player);
            }
            else if(stack.isOf(Items.SOUL_TORCH)){
                BlockState newState = state
                        .with(HAS_TORCH, GoatHornTorchType.SOULFIRE_TORCH)
                        .with(LIGHT_LEVEL, 10);
                world.setBlockState(pos, newState);
                stack.decrementUnlessCreative(1, player);
            }
            else if(stack.isOf(Items.REDSTONE_TORCH)){
                BlockState newState = state
                        .with(HAS_TORCH, GoatHornTorchType.REDSTONE_TORCH)
                        .with(LIGHT_LEVEL, 10);
                world.setBlockState(pos, newState);
                stack.decrementUnlessCreative(1, player);
            }
            else if(stack.isOf(ItemRegistry.GLOW_TORCH)){
                {
                    if(state.get(WATERLOGGED)){
                        BlockState newState = state
                                .with(HAS_TORCH, GoatHornTorchType.GLOW_TORCH)
                                .with(LIGHT_LEVEL, 15);
                        world.setBlockState(pos, newState);
                        stack.decrementUnlessCreative(1, player);
                    }
                    else{
                        BlockState newState = state
                                .with(HAS_TORCH, GoatHornTorchType.GLOW_TORCH_OFF)
                                .with(LIGHT_LEVEL, 0);
                        world.setBlockState(pos, newState);
                        stack.decrementUnlessCreative(1, player);
                    }
                }
            }
        }
        return ActionResult.SUCCESS;
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

        GoatHornTorchType type = state.get(HAS_TORCH);
        if (type == GoatHornTorchType.NONE || type == GoatHornTorchType.GLOW_TORCH_OFF) {return;}
        world.addParticleClient(ParticleTypes.SMOKE, d, e, f, 0, 0, 0);
        var particle = state.get(HAS_TORCH) == GoatHornTorchType.REDSTONE_TORCH ? DustParticleEffect.DEFAULT : GoatHornTorchType.getTorchParticle(type);
        world.addParticleClient(particle, d, e, f, 0, 0, 0);
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

            if (state.get(HAS_TORCH) != GoatHornTorchType.NONE) {
                drops.add(GoatHornTorchType.getItem(state.get(HAS_TORCH)));
            }

            RegistryEntry<Instrument> entry = serverWorld.getRegistryManager().getOrThrow(RegistryKeys.INSTRUMENT).getOrThrow(GoatHornType.getInstrument(state.get(HORN)));
            drops.add(GoatHornItem.getStackForInstrument(Items.GOAT_HORN, entry));

            return drops;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, HORN, LIGHT_LEVEL, HAS_TORCH);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        if (ctx.getWorld().getBlockState(ctx.getBlockPos().down()).isAir() || ctx.getWorld().getBlockState(ctx.getBlockPos().up()).isAir()) return null;
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (!state.canPlaceAt(world, pos)) tickView.scheduleBlockTick(pos, this, 1);
        return state;
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
        }
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }
}
