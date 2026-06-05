package net.greenjab.nekomasfixed.registry.block.entity;

import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
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
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class SoupCauldronBlockEntity extends BlockEntity {
    private final List<ItemStack> inputs = new ArrayList<>();
    public boolean hasStirred = false;
    private int stirTicks = 0;
    public SoupCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeRegistry.SOUP_CAULDRON_BLOCK_ENTITY, pos, state);
    }

    public void startStirAnimation() {
        stirTicks = 40;
        markDirty();

        if(world != null && !world.isClient()) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        }
    }

    public int getStirTicks() {
        return stirTicks;
    }

    public int inputAmount() {
        return inputs.size();
    }

    public static void tick(World world, BlockPos pos, BlockState state, SoupCauldronBlockEntity be) {
        if (be.stirTicks > 0) {
            be.stirTicks--;

            if(!world.isClient() && be.stirTicks % 2 == 0) {
                world.updateListeners(pos,state,state,3);
            }
        }
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
            if(world != null && !world.isClient()) {
                world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
            }
        }
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);

        view.putBoolean("HasStirred", hasStirred);
        view.put("inputs", ItemStack.CODEC.listOf(), inputs);
        view.putInt("stir_ticks", stirTicks);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        hasStirred = view.getBoolean("HasStirred", false);
        inputs.clear();
        inputs.addAll(view.read("inputs", ItemStack.CODEC.listOf()).orElse(List.of()));
        stirTicks = view.getInt("stir_ticks", 0);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }

    public void setHasStirred(boolean val) {
        this.hasStirred = val;
        markDirty();

        if (world != null && !world.isClient()) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        }
    }

    public List<ItemStack> getInputs() {return inputs;}
}
