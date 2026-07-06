package net.greenjab.nekomasfixed.registry.block.entity;

import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.util.SoupCauldronAnimator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SoupCauldronBlockEntity extends BlockEntity implements LidBlockEntity {
    private final List<ItemStack> inputs = new ArrayList<>();
    public boolean hasStirred = false;
    private final SoupCauldronAnimator CookingAnimator = new SoupCauldronAnimator();


    public SoupCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeRegistry.SOUP_CAULDRON_BLOCK_ENTITY, pos, state);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public boolean addInput(ItemStack stack) {
        if (stack.is(Items.AIR)) return false;
        setChanged();

        if (!stack.hasNonDefault(DataComponents.POTION_CONTENTS)) for (ItemStack existing : inputs) if (ItemStack.isSameItem(existing, stack)) return false;

        if (inputs.size() < 4) {
            inputs.add(stack.copyWithCount(1));
            if(level != null && !level.isClientSide()) level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
        return true;
    }

    public ItemStack removeInput() {
        if (inputs.isEmpty()) return Items.AIR.getDefaultInstance();
        setChanged();

        if(level != null && !level.isClientSide()) level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        ItemStack removed = inputs.remove(inputs.size()-1);
        if (inputs.isEmpty()) level.setBlockAndUpdate(worldPosition, Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3));
        return removed;
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput view) {
        super.saveAdditional(view);

        view.putBoolean("HasStirred", hasStirred);
        view.store("inputs", ItemStack.CODEC.listOf(), inputs);
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput view) {
        super.loadAdditional(view);
        hasStirred = view.getBooleanOr("HasStirred", false);
        inputs.clear();
        inputs.addAll(view.read("inputs", ItemStack.CODEC.listOf()).orElse(List.of()));
    }

    @Override
    public @NonNull CompoundTag getUpdateTag(HolderLookup.@NonNull Provider registries) {
        return saveWithoutMetadata(registries);
    }

    public void setStirred(Level world) {
        this.hasStirred = true;
        if (world instanceof ServerLevel serverWorld) {
            List<ItemStack> updatedInputs = new ArrayList<>();
            for (ItemStack item : inputs) {
                SingleRecipeInput singleStackRecipeInput = new SingleRecipeInput(item);
                Optional<RecipeHolder<SmeltingRecipe>> optional = serverWorld
                        .recipeAccess()
                        .getRecipeFor(RecipeType.SMELTING, singleStackRecipeInput, world);
                if (optional.isPresent() && !item.is(Items.CHORUS_FRUIT)) {
                    ItemStack itemStack = (((RecipeHolder)optional.get()).value()).assemble(singleStackRecipeInput, world.registryAccess());
                    if (!itemStack.isEmpty()) updatedInputs.add(itemStack);
                    else updatedInputs.add(item);
                } else updatedInputs.add(item);
            }
            inputs.clear();
            inputs.addAll(updatedInputs);
        }
        setChanged();

        if (world != null && !world.isClientSide()) {
            world.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    public List<ItemStack> getInputs() {return inputs;}


    public static void clientTick(Level world, BlockPos pos, BlockState state, SoupCauldronBlockEntity blockEntity) {
        blockEntity.CookingAnimator.setStarted(blockEntity.hasStirred);
        blockEntity.CookingAnimator.step();
        float progress = blockEntity.CookingAnimator.getProgress(0);
        if (progress>0&&progress<1){
            blockEntity.level.addParticle(ParticleTypes.BUBBLE_POP, pos.getX()+0.5+world.random.nextGaussian()*0.25, pos.getY()+1, pos.getZ()+0.5+world.random.nextGaussian()*0.25, 0.0, 0.0, 0.0);
            blockEntity.level.sendBlockUpdated(pos, state, state, 3);
        }
    }

    @Override
    public float getOpenNess(float tickProgress) {
        return this.CookingAnimator.getProgress(tickProgress);
    }
}
