package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SmithingScreen.class)
public class SmithingScreenMixin {

    @Inject(method = "updateArmorStandPreview", at = @At("HEAD"), cancellable = true)
    private void renderGoatHornHelmetOnArmorStandPreview(ItemStack itemStack, CallbackInfo ci) {
        SmithingScreen screen = (SmithingScreen)(Object)this;
        screen.armorStandPreview.leftHandItemStack = ItemStack.EMPTY;
        screen.armorStandPreview.leftHandItemState.clear();
        screen.armorStandPreview.headEquipment = ItemStack.EMPTY;
        screen.armorStandPreview.headItem.clear();
        screen.armorStandPreview.chestEquipment = ItemStack.EMPTY;
        screen.armorStandPreview.legsEquipment = ItemStack.EMPTY;
        screen.armorStandPreview.feetEquipment = ItemStack.EMPTY;
        if (!itemStack.isEmpty()) {
            Equippable equippable = (Equippable)itemStack.get(DataComponents.EQUIPPABLE);
            EquipmentSlot slot = equippable != null ? equippable.slot() : null;
            ItemModelResolver itemModelResolver = screen.minecraft.getItemModelResolver();
            switch (slot) {
                case HEAD:
                    if (HumanoidArmorLayer.shouldRender(itemStack, EquipmentSlot.HEAD) || itemStack.is(ModTags.GOAT_HORN_HELMETS)) {
                        screen.armorStandPreview.headEquipment = itemStack.copy();
                    } else {
                        itemModelResolver.updateForTopItem(screen.armorStandPreview.headItem, itemStack, ItemDisplayContext.HEAD, screen.minecraft.level, null, 0);
                    }
                    break;
                case CHEST:
                    screen.armorStandPreview.chestEquipment = itemStack.copy();
                    break;
                case LEGS:
                    screen.armorStandPreview.legsEquipment = itemStack.copy();
                    break;
                case FEET:
                    screen.armorStandPreview.feetEquipment = itemStack.copy();
                    break;
                case null:
                default:
                    screen.armorStandPreview.leftHandItemStack = itemStack.copy();
                    itemModelResolver.updateForTopItem(
                            screen.armorStandPreview.leftHandItemState, itemStack, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, screen.minecraft.level, null, 0
                    );
            }
        }
        ci.cancel();
    }
}
