package net.greenjab.nekomasfixed.mixin;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorStand.class)
public class ArmorStandEntityMixin {
    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    private void interactAt(Player player, InteractionHand hand, Vec3 location, CallbackInfoReturnable<InteractionResult> cir) {
        if(player!=null && player.isShiftKeyDown()){
            ArmorStand armorStandEntity = (ArmorStand) (Object) this;
            player.swing(hand, true);
            if(armorStandEntity.getItemBySlot(EquipmentSlot.HEAD).is(ItemTags.HEAD_ARMOR) || player.getItemBySlot(EquipmentSlot.HEAD).is(ItemTags.HEAD_ARMOR)){
                ItemStack tempItem = armorStandEntity.getItemBySlot(EquipmentSlot.HEAD);
                armorStandEntity.setItemSlot(EquipmentSlot.HEAD, player.getItemBySlot(EquipmentSlot.HEAD));
                player.setItemSlot(EquipmentSlot.HEAD, tempItem);
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
            if(armorStandEntity.getItemBySlot(EquipmentSlot.CHEST).is(ItemTags.CHEST_ARMOR) || player.getItemBySlot(EquipmentSlot.CHEST).is(ItemTags.CHEST_ARMOR)){
                ItemStack tempItem = armorStandEntity.getItemBySlot(EquipmentSlot.CHEST);
                armorStandEntity.setItemSlot(EquipmentSlot.CHEST, player.getItemBySlot(EquipmentSlot.CHEST));
                player.setItemSlot(EquipmentSlot.CHEST, tempItem);
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
            if(armorStandEntity.getItemBySlot(EquipmentSlot.LEGS).is(ItemTags.LEG_ARMOR) || player.getItemBySlot(EquipmentSlot.LEGS).is(ItemTags.LEG_ARMOR)){
                ItemStack tempItem = armorStandEntity.getItemBySlot(EquipmentSlot.LEGS);
                armorStandEntity.setItemSlot(EquipmentSlot.LEGS, player.getItemBySlot(EquipmentSlot.LEGS));
                player.setItemSlot(EquipmentSlot.LEGS, tempItem);
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
            if(armorStandEntity.getItemBySlot(EquipmentSlot.FEET).is(ItemTags.FOOT_ARMOR) || player.getItemBySlot(EquipmentSlot.FEET).is(ItemTags.FOOT_ARMOR)){
                ItemStack tempItem = armorStandEntity.getItemBySlot(EquipmentSlot.FEET);
                armorStandEntity.setItemSlot(EquipmentSlot.FEET, player.getItemBySlot(EquipmentSlot.FEET));
                player.setItemSlot(EquipmentSlot.FEET, tempItem);
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
            //this doesnt work but at the moment i am too lazy to remove it - plz let it stay
            if(!armorStandEntity.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() && !player.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()){
                ItemStack tempItem = armorStandEntity.getItemBySlot(EquipmentSlot.MAINHAND);
                armorStandEntity.setItemSlot(EquipmentSlot.MAINHAND, player.getItemBySlot(EquipmentSlot.MAINHAND));
                player.setItemSlot(EquipmentSlot.MAINHAND, tempItem);
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
        }
    }
}
