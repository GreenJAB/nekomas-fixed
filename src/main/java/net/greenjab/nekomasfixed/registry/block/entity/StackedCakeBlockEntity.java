package net.greenjab.nekomasfixed.registry.block.entity;

import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.stb.STBTTPackRange;

import java.util.Stack;

public class StackedCakeBlockEntity extends BlockEntity {
    public Stack<BlockState> STACKED_CAKES = new Stack<>();
    public StackedCakeBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeRegistry.STACKED_CAKE_BLOCK_ENTITY, pos, state);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }


    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);

        view.putInt("count", STACKED_CAKES.size());

        for (int i = 0; i < STACKED_CAKES.size(); i++) {
            view.put("cake_" + i, BlockState.CODEC, STACKED_CAKES.get(i));
        }
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);

        STACKED_CAKES.clear();

        int count = view.getInt("count", 0);

        for (int i = 0; i < count; i++) {
            view.read("cake_" + i, BlockState.CODEC)
                    .ifPresent(STACKED_CAKES::push);
        }
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }


}
