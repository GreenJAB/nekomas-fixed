package net.greenjab.nekomasfixed.registry.block.entity;

import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;

public class HollowDarkOakLogBlockEntity extends AbstractHollowLogBlockEntity {

    private BlockState storedBlock = Blocks.AIR.getDefaultState();

    public HollowDarkOakLogBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeRegistry.HOLLOW_DARK_OAK_LOG_BLOCK_ENTITY_TYPE, pos, state);
    }

    public BlockState getStoredBlock() {
        return this.storedBlock;
    }

    public void setStoredBlock(BlockState state) {
        this.storedBlock = state;
        markDirty();

        if (world != null && !world.isClient()) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        view.put("StoredBlock", BlockState.CODEC, storedBlock);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        storedBlock = view.read("StoredBlock", BlockState.CODEC)
                .orElse(Blocks.AIR.getDefaultState());
    }
}
