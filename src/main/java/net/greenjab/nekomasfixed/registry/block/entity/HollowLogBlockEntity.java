package net.greenjab.nekomasfixed.registry.block.entity;

import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;

public class HollowLogBlockEntity extends BlockEntity {
    private BlockState storedBlock = Blocks.AIR.getDefaultState();

    public HollowLogBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeRegistry.HOLLOW_LOG_BLOCK_ENTITY, pos, state);
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

    private static boolean checkInTag(BlockState blockItemState, TagKey<Block> tags){
        return blockItemState.isIn(tags);
    }

    public static boolean canStoreABlock(HollowLogBlockEntity logBE, BlockItem blockItem){
        BlockState blockItemState = blockItem.getBlock().getDefaultState();
        return logBE.getStoredBlock().isAir() && blockItemState.isOpaqueFullCube();
        /*return logBE.getStoredBlock().isAir() && !blockItemState.isIn(ItemTags.SKULLS)
                && !checkInTag(blockItemState, BlockTags.BANNERS)
                && !checkInTag(blockItemState, BlockTags.BEDS)
                && !checkInTag(blockItemState, BlockTags.DOORS)
                && !checkInTag(blockItemState, BlockTags.BUTTONS);*/
    }
}
