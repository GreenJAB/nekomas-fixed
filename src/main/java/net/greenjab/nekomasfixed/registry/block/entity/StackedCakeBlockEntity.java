package net.greenjab.nekomasfixed.registry.block.entity;

import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;

public class StackedCakeBlockEntity extends BlockEntity {
    public BlockState LAYER_2_STATE = Blocks.AIR.getDefaultState();
    public BlockState LAYER_3_STATE = Blocks.AIR.getDefaultState();
    public BlockState CANDLE_STATE = Blocks.AIR.getDefaultState();
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
        view.put("layer_2", BlockState.CODEC, LAYER_2_STATE);
        view.put("layer_3", BlockState.CODEC, LAYER_3_STATE);
        view.put("candle", BlockState.CODEC, CANDLE_STATE);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        LAYER_2_STATE = view.read("layer_2", BlockState.CODEC).orElse(Blocks.AIR.getDefaultState());
        LAYER_3_STATE = view.read("layer_3", BlockState.CODEC).orElse(Blocks.AIR.getDefaultState());
        CANDLE_STATE = view.read("candle", BlockState.CODEC).orElse(Blocks.AIR.getDefaultState());
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }
}
