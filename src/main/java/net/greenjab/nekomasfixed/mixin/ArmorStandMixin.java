package net.greenjab.nekomasfixed.mixin;

import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorStand.class)
public class ArmorStandMixin {
    @Inject(method = "interactAt", at = @At("HEAD"), cancellable = true)
    private void swapArmorSetOnSneak(Player player, Vec3 location, InteractionHand hand,
                                     CallbackInfoReturnable<InteractionResult> cir) {
        if (player.isShiftKeyDown()) {
            this.swapArmorSlot(player, EquipmentSlot.HEAD, ItemTags.HEAD_ARMOR);
            this.swapArmorSlot(player, EquipmentSlot.CHEST, ItemTags.CHEST_ARMOR);
            this.swapArmorSlot(player, EquipmentSlot.LEGS, ItemTags.LEG_ARMOR);
            this.swapArmorSlot(player, EquipmentSlot.FEET, ItemTags.FOOT_ARMOR);
            player.swing(hand, true);
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }

    // Swaps one slot's worn piece with the player's. A slot only swaps when either side
    // holds a matching armor piece, so empty slots don't block the rest of the set.
    @Unique
    private void swapArmorSlot(Player player, EquipmentSlot slot, TagKey<Item> tag) {
        ItemStack standStack = ((ArmorStand) (Object) this).getItemBySlot(slot);
        ItemStack playerStack = player.getItemBySlot(slot);
        if (standStack.is(tag) || playerStack.is(tag)) {
            ((ArmorStand) (Object) this).setItemSlot(slot, playerStack);
            player.setItemSlot(slot, standStack);
        }
    }
}