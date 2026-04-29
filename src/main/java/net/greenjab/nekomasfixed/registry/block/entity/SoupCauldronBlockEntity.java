package net.greenjab.nekomasfixed.registry.block.entity;

import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class SoupCauldronBlockEntity extends BlockEntity {
    private final List<ItemStack> inputs = new ArrayList<>();
    private boolean hasStirred = false;

    public SoupCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeRegistry.SOUP_CAULDRON_BLOCK_ENTITY, pos, state);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public void addInput(ItemStack stack) {
        if (stack.isOf(Items.AIR) || hasStirred) {
            return;
        }
        markDirty();

        if(world != null && !world.isClient()) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
        for (ItemStack existing : inputs) {
            if (ItemStack.areItemsAndComponentsEqual(existing, stack)) {
                return;
            }
        }

        if (inputs.size() < 4) {
            inputs.add(stack.copyWithCount(1));
            markDirty();
        }
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);

        view.putBoolean("HasStirred", hasStirred);
        view.put("inputs", ItemStack.CODEC.listOf(), inputs);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        hasStirred = view.getBoolean("HasStirred", false);
        inputs.clear();
        inputs.addAll(view.read("inputs", ItemStack.CODEC.listOf()).orElse(List.of()));
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }

    public boolean canBePicked(){return this.hasStirred;}
    public void setHasStirred(boolean val){ this.hasStirred = val;}

    public List<ItemStack> getInputs() {return inputs;}
}
