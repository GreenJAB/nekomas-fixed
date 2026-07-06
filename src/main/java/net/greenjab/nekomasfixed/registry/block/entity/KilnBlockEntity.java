package net.greenjab.nekomasfixed.registry.block.entity;

import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.RecipeRegistry;
import net.greenjab.nekomasfixed.screen.KilnScreenHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.FuelValues;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

public class KilnBlockEntity extends AbstractFurnaceBlockEntity {
    private static final Component CONTAINER_NAME_TEXT = Component.translatable("container.nekomasfixed.kiln");

    public KilnBlockEntity(BlockPos pos, BlockState state) {
        super( BlockEntityTypeRegistry.KILN_BLOCK_ENTITY, pos, state, RecipeRegistry.KILN);
    }

    @Override
    protected @NonNull Component getDefaultName() {
        return CONTAINER_NAME_TEXT;
    }

    @Override
    protected int getBurnDuration(@NonNull FuelValues fuelRegistry, @NonNull ItemStack stack) {
        return super.getBurnDuration(fuelRegistry, stack) / 2;
    }

    @Override
    protected @NonNull AbstractContainerMenu createMenu(int syncId, @NonNull Inventory playerInventory) {
        return new KilnScreenHandler(syncId, playerInventory, this, this.dataAccess);
    }
}
