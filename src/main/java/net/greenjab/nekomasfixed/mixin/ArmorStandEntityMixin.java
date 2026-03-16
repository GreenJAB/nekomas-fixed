package net.greenjab.nekomasfixed.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorStandEntity.class)
 class ArmorStandEntityMixin {
    @Inject(method = "interactAt", at = @At("HEAD"), cancellable = true)
    private void interactAt(PlayerEntity player, Vec3d hitPos, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if(player!=null && player.isSneaking()){
            ArmorStandEntity armorStandEntity = (ArmorStandEntity) (Object) this;
            if(armorStandEntity.getEquippedStack(EquipmentSlot.HEAD).isIn(ItemTags.HEAD_ARMOR) && player.getEquippedStack(EquipmentSlot.HEAD).isIn(ItemTags.HEAD_ARMOR)){
                ItemStack tempItem = armorStandEntity.getEquippedStack(EquipmentSlot.HEAD);
                armorStandEntity.equipStack(EquipmentSlot.HEAD, player.getEquippedStack(EquipmentSlot.HEAD));
                player.equipStack(EquipmentSlot.HEAD, tempItem);
                cir.setReturnValue(ActionResult.SUCCESS);
            }
            if(armorStandEntity.getEquippedStack(EquipmentSlot.CHEST).isIn(ItemTags.CHEST_ARMOR) ){
                ItemStack tempItem = armorStandEntity.getEquippedStack(EquipmentSlot.CHEST);
                armorStandEntity.equipStack(EquipmentSlot.CHEST, player.getEquippedStack(EquipmentSlot.CHEST));
                player.equipStack(EquipmentSlot.CHEST, tempItem);
                cir.setReturnValue(ActionResult.SUCCESS);
            }
            if(armorStandEntity.getEquippedStack(EquipmentSlot.LEGS).isIn(ItemTags.LEG_ARMOR) && player.getEquippedStack(EquipmentSlot.LEGS).isIn(ItemTags.LEG_ARMOR)){
                ItemStack tempItem = armorStandEntity.getEquippedStack(EquipmentSlot.LEGS);
                armorStandEntity.equipStack(EquipmentSlot.LEGS, player.getEquippedStack(EquipmentSlot.LEGS));
                player.equipStack(EquipmentSlot.LEGS, tempItem);
                cir.setReturnValue(ActionResult.SUCCESS);
            }
            if(armorStandEntity.getEquippedStack(EquipmentSlot.FEET).isIn(ItemTags.FOOT_ARMOR) && player.getEquippedStack(EquipmentSlot.FEET).isIn(ItemTags.FOOT_ARMOR)){
                ItemStack tempItem = armorStandEntity.getEquippedStack(EquipmentSlot.FEET);
                armorStandEntity.equipStack(EquipmentSlot.FEET, player.getEquippedStack(EquipmentSlot.FEET));
                player.equipStack(EquipmentSlot.FEET, tempItem);
                cir.setReturnValue(ActionResult.SUCCESS);
            }
            if(!armorStandEntity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty() && !player.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()){
                ItemStack tempItem = armorStandEntity.getEquippedStack(EquipmentSlot.MAINHAND);
                armorStandEntity.equipStack(EquipmentSlot.MAINHAND, player.getEquippedStack(EquipmentSlot.MAINHAND));
                player.equipStack(EquipmentSlot.MAINHAND, tempItem);
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}
