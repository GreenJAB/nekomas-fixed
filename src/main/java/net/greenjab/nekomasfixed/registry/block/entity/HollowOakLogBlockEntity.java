package net.greenjab.nekomasfixed.registry.block.entity;

import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class HollowOakLogBlockEntity extends BlockEntity  {
    private BlockState storedBlock = Blocks.AIR.getDefaultState();

    public HollowOakLogBlockEntity( BlockPos pos, BlockState state) {
        super(BlockEntityTypeRegistry.HOLLOW_OAK_LOG_BLOCK_ENTITY_TYPE, pos, state);
    }

    public BlockState getStoredBlock() {
        return this.storedBlock;
    }

    public void setStoredBlock(BlockState state) {
        this.storedBlock = state;
        markDirty();
    }


}
