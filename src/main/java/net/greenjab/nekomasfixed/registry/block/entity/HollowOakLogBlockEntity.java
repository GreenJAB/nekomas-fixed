package net.greenjab.nekomasfixed.registry.block.entity;

import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

public class HollowOakLogBlockEntity extends BlockEntity  {
    private BlockState storedBlock = Blocks.AIR.getDefaultState();

    public HollowOakLogBlockEntity( BlockPos pos, BlockState state) {
        super(BlockEntityTypeRegistry.HOLLOW_OAK_LOG_BLOCK_ENTITY_TYPE, pos, state);
    }

    public BlockState getStoredBlock() {
        return this.storedBlock;
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }

    public void setStoredBlock(BlockState state) {
        this.storedBlock = state;
        markDirty();

        if (world != null && !world.isClient()) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

}
