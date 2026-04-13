package net.greenjab.nekomasfixed.registry.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractHollowLogBlockEntity extends BlockEntity {
    private BlockState storedBlock = Blocks.AIR.getDefaultState();

    public AbstractHollowLogBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public BlockState getStoredBlock() {
        return this.storedBlock;
    }

    public static void tick(World world, BlockPos pos, BlockState state, AbstractHollowLogBlockEntity be) {
        if (world.isClient()) return;

        BlockState stored = be.getStoredBlock();
        if (stored.getBlock() instanceof CropBlock crop) {
            if (world.random.nextInt(10) == 0) {
                if (!crop.isMature(stored)) {
                    BlockState newState = crop.withAge(crop.getAge(stored) + 1);
                    be.setStoredBlock(newState);
                    be.markDirty();
                }
            }
        }
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

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
}
