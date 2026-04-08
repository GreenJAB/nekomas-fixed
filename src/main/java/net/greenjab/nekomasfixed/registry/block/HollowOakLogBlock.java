package net.greenjab.nekomasfixed.registry.block;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.greenjab.nekomasfixed.registry.block.entity.HollowOakLogBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;

public class HollowOakLogBlock extends PillarBlock implements BlockEntityProvider {
    private BlockState storedBlock;
    private static final VoxelShape RAYCAST_SHAPE = Block.createColumnShape(12.0F, 4.0F, 16.0F);
    public static final VoxelShape OUTLINE_SHAPE = Util.make(() -> VoxelShapes.combineAndSimplify(
            VoxelShapes.fullCube(),
            VoxelShapes.union(Block.createColumnShape(16.0F, 8.0F, 0.0F, 0.0F),
                    Block.createColumnShape(8.0F, 16.0F, 0.0F, 0.0F),
                    Block.createColumnShape(12.0F, 0.0F, 0.0F), RAYCAST_SHAPE),
            BooleanBiFunction.ONLY_FIRST));
    public HollowOakLogBlock(Settings settings) {
        super(settings);
        storedBlock = Blocks.AIR.getDefaultState();
    }

    public void setStoredBlock(BlockState state){
        this.storedBlock = state;
    }

    public BlockState getStoredBlock(){return this.storedBlock;}

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPE;
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return RAYCAST_SHAPE;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HollowOakLogBlockEntity(pos, state);
    }


}
