package net.greenjab.nekomasfixed.registry.entity;

import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.registry.registries.SoundEventRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ClearItemFrameEntity extends ItemFrame {
    public ClearItemFrameEntity(EntityType<? extends ItemFrame> entityType, Level world) {
        super(entityType, world);
    }

    public ClearItemFrameEntity(Level world, BlockPos blockPos, Direction direction) {
        super(EntityTypeRegistry.CLEAR_ITEM_FRAME, world, blockPos, direction);
    }

    public void setItem(ItemStack value, boolean update) {
        this.setInvisible(!value.isEmpty());
        super.setItem(value, update);
    }

    public @NotNull SoundEvent getRemoveItemSound() {
        return SoundEventRegistry.ENTITY_CLEAR_ITEM_FRAME_REMOVE_ITEM;
    }

    public @NotNull SoundEvent getBreakSound() {
        return SoundEventRegistry.ENTITY_CLEAR_ITEM_FRAME_BREAK;
    }

    public @NotNull SoundEvent getPlaceSound() {
        return SoundEventRegistry.ENTITY_CLEAR_ITEM_FRAME_PLACE;
    }

    public @NotNull SoundEvent getAddItemSound() {
        return SoundEventRegistry.ENTITY_CLEAR_ITEM_FRAME_ADD_ITEM;
    }

    public @NotNull SoundEvent getRotateItemSound() {
        return SoundEventRegistry.ENTITY_CLEAR_ITEM_FRAME_ROTATE_ITEM;
    }

    protected @NotNull ItemStack getFrameItemStack() {
        return new ItemStack(ItemRegistry.CLEAR_ITEM_FRAME);
    }
}

