package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(AbstractContainerMenu.class)
public abstract class AbstractContainerMenuMixin {

    @Inject(method = "clicked", at = @At("HEAD"), cancellable = true)
    //trust me, finding this freaking method took me 3 hours T - T

    private void removeArrowsAndPlaceGlassBottles(int slotIndex, int buttonNum, ContainerInput containerInput, Player player, CallbackInfo ci) {
        AbstractContainerMenu menu = (AbstractContainerMenu) (Object)this;
        if(menu instanceof CraftingMenu crafting){

            if(slotIndex==0 && crafting.getResultSlot().getItem().is(ItemRegistry.TIPPED_ARROW_CUSTOM)){
                player.getInventory().add(crafting.getResultSlot().getItem());
                for(Slot slot : crafting.getInputGridSlots()){
                    ItemStack stack = slot.getItem();
                    if(stack.is(Items.ARROW)){
                        slot.set(ItemStack.EMPTY);
                    }
                    if(stack.is(Items.POTION)){
                        slot.set(Items.GLASS_BOTTLE.getDefaultInstance());
                    }
                }
            }
        }

    }

}
