package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Instrument;
import net.minecraft.item.Instruments;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jspecify.annotations.Nullable;

import static net.minecraft.util.math.Direction.NORTH;

public class GoatHornBlock extends HorizontalFacingBlock implements Waterloggable {
    public static final MapCodec<GoatHornBlock> CODEC = createCodec(GoatHornBlock::new);
    public static final Property<Boolean> WATERLOGGED = Properties.WATERLOGGED;
    public RegistryKey<Instrument> INSTRUMENT = Instruments.CALL_GOAT_HORN;
    private static final VoxelShape NORTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(7, 3, 0, 9, 5, 7),
            Block.createCuboidShape(6.5, 4, 4, 9.5, 6, 8),
            Block.createCuboidShape(6, 5, 5, 10, 10, 9)
    );
    private static final VoxelShape SOUTH_SHAPE = rotateY(NORTH_SHAPE);
    private static final VoxelShape WEST_SHAPE = rotateY(SOUTH_SHAPE);
    private static final VoxelShape EAST_SHAPE = rotateY(WEST_SHAPE);

    public GoatHornBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, NORTH)
                .with(WATERLOGGED, false));

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

    private static VoxelShape rotateY(VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[] { shape, VoxelShapes.empty() };

        for (int i = 0; i < 4; i++) {
            buffer[0].forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = VoxelShapes.union(buffer[1],
                    VoxelShapes.cuboid(
                            1 - maxZ / 16, minY / 16, minX / 16,
                            1 - minZ / 16, maxY / 16, maxX / 16
                    )
            ));
            buffer[0] = buffer[1];
            buffer[1] = VoxelShapes.empty();
        }

        return buffer[0];
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return getOutlineShape(state, world, pos, ShapeContext.absent());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        if(ctx.getWorld().getBlockState(ctx.getBlockPos().down()).isAir() || ctx.getWorld().getBlockState(ctx.getBlockPos().up()).isAir())return null;
        return this.getDefaultState().with(HorizontalFacingBlock.FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }
}
