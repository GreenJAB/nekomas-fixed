package net.greenjab.nekomasfixed.registry.block.entity;

import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.util.SoupCauldronAnimator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LidOpenable;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SoupCauldronBlockEntity extends BlockEntity implements LidOpenable {
    private final List<ItemStack> inputs = new ArrayList<>();
    public boolean hasStirred = false;
    private final SoupCauldronAnimator CookingAnimator = new SoupCauldronAnimator();


    public SoupCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeRegistry.SOUP_CAULDRON_BLOCK_ENTITY, pos, state);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public boolean addInput(ItemStack stack) {
        if (stack.isOf(Items.AIR)) return false;
        markDirty();

        if(world != null && !world.isClient()) world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        for (ItemStack existing : inputs) if (ItemStack.areItemsEqual(existing, stack)) return false;

        if (inputs.size() < 4) {
            inputs.add(stack.copyWithCount(1));
            if(world != null && !world.isClient()) world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        }
        return true;
    }

    public ItemStack removeInput() {
        if (inputs.isEmpty()) return Items.AIR.getDefaultStack();
        markDirty();

        if(world != null && !world.isClient()) world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        ItemStack removed = inputs.remove(inputs.size()-1);
        if (inputs.isEmpty()) world.setBlockState(pos, Blocks.WATER_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3));
        return removed;
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

    public void setStirred(World world) {
        this.hasStirred = true;
        if (world instanceof ServerWorld serverWorld) {
            List<ItemStack> updatedInputs = new ArrayList<>();
            for (ItemStack item : inputs) {
                SingleStackRecipeInput singleStackRecipeInput = new SingleStackRecipeInput(item);
                Optional<RecipeEntry<SmeltingRecipe>> optional = serverWorld
                        .getRecipeManager()
                        .getFirstMatch(RecipeType.SMELTING, singleStackRecipeInput, world);
                if (optional.isPresent() && !item.isOf(Items.CHORUS_FRUIT)) {
                    ItemStack itemStack = (((RecipeEntry)optional.get()).value()).craft(singleStackRecipeInput, world.getRegistryManager());
                    if (!itemStack.isEmpty()) updatedInputs.add(itemStack);
                    else updatedInputs.add(item);
                } else updatedInputs.add(item);
            }
            inputs.clear();
            inputs.addAll(updatedInputs);
        }
        markDirty();

        if (world != null && !world.isClient()) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        }
    }

    public List<ItemStack> getInputs() {return inputs;}


    public static void clientTick(World world, BlockPos pos, BlockState state, SoupCauldronBlockEntity blockEntity) {
        blockEntity.CookingAnimator.setStarted(blockEntity.hasStirred);
        blockEntity.CookingAnimator.step();
        float progress = blockEntity.CookingAnimator.getProgress(0);
        if (progress>0&&progress<1){
            blockEntity.world.addParticleClient(ParticleTypes.BUBBLE_POP, pos.getX()+0.5+world.random.nextGaussian()*0.25, pos.getY()+1, pos.getZ()+0.5+world.random.nextGaussian()*0.25, 0.0, 0.0, 0.0);
            blockEntity.world.updateListeners(pos, state, state, 3);
        }
    }

    @Override
    public float getAnimationProgress(float tickProgress) {
        return this.CookingAnimator.getProgress(tickProgress);
    }
}
