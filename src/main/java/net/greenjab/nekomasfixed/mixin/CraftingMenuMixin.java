package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftingMenu.class)
public class CraftingMenuMixin {

//    @Inject(method = "quickMoveStack", at = @At("HEAD"))
//    private void quickMoveStack(Player player, int slotIndex, CallbackInfoReturnable<ItemStack> cir) {
//        CraftingMenu menu = (CraftingMenu) (Object)this;
//        if(slotIndex==0){
//            for(Slot slot : menu.getInputGridSlots()){
//                ItemStack stack = slot.getItem();
//
//                if(stack.is(ItemRegistry.TIPPED_ARROW_CUSTOM)){
//                    continue;
//                }
//
//                if(stack.is(Items.ARROW)){
//                    slot.set(Items.AIR.getDefaultInstance());
//                }
//
//                if(stack.is(Items.POTION)){
//                    slot.set(Items.GLASS_BOTTLE.getDefaultInstance());
//                }
//            }
//        }
//
//
//    }

}
