package net.greenjab.nekomasfixed.registry.block.entity;

import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;

public class StackedCakeBlockEntity extends BlockEntity {
    public BlockState LAYER_2_STATE = Blocks.AIR.defaultBlockState();
    public BlockState LAYER_3_STATE = Blocks.AIR.defaultBlockState();
    public BlockState CANDLE_STATE = Blocks.AIR.defaultBlockState();
    public StackedCakeBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeRegistry.STACKED_CAKE_BLOCK_ENTITY, pos, state);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput view) {
        super.saveAdditional(view);
        view.store("layer_2", BlockState.CODEC, LAYER_2_STATE);
        view.store("layer_3", BlockState.CODEC, LAYER_3_STATE);
        view.store("candle", BlockState.CODEC, CANDLE_STATE);
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput view) {
        super.loadAdditional(view);
        LAYER_2_STATE = view.read("layer_2", BlockState.CODEC).orElse(Blocks.AIR.defaultBlockState());
        LAYER_3_STATE = view.read("layer_3", BlockState.CODEC).orElse(Blocks.AIR.defaultBlockState());
        CANDLE_STATE = view.read("candle", BlockState.CODEC).orElse(Blocks.AIR.defaultBlockState());
    }

    @Override
    public @NonNull CompoundTag getUpdateTag(HolderLookup.@NonNull Provider registries) {
        return saveWithoutMetadata(registries);
    }
}
