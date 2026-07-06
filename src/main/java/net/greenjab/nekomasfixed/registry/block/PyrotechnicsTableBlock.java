package net.greenjab.nekomasfixed.registry.block;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.screen.PyrotechnicsTableScreenHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;

public class PyrotechnicsTableBlock extends HorizontalDirectionalBlock {

    public static final MapCodec<PyrotechnicsTableBlock> CODEC = simpleCodec(PyrotechnicsTableBlock::new);
    private static final Component TITLE = Component.translatable("container.nekomasfixed.pyrotechnics_table");

    public PyrotechnicsTableBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected @NonNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    protected @NonNull InteractionResult useWithoutItem(@NonNull BlockState state, Level world, @NonNull BlockPos pos, @NonNull Player player, @NonNull BlockHitResult hit) {
        if (!world.isClientSide()) {
            player.openMenu(state.getMenuProvider(world, pos));
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected MenuProvider getMenuProvider(@NonNull BlockState state, @NonNull Level world, @NonNull BlockPos pos) {
        return new SimpleMenuProvider((syncId, inventory, player) -> new PyrotechnicsTableScreenHandler(syncId, inventory, ContainerLevelAccess.create(world, pos)), TITLE);
    }

    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}

