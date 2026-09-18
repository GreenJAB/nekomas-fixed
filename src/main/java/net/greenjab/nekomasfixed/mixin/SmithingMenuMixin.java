package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.mixin.accessor.ItemCombinerMenuAccessor;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Turtle armour can't be worked (trimmed/upgraded) in the smithing table — faithful to main,
// which rejects the pieces there. 1.21.1's #minecraft:trimmable_armor derives from the slot
// tags (chest/leg/foot_armor), so the turtle pieces would otherwise be trimmable.
@Mixin(SmithingMenu.class)
public class SmithingMenuMixin {

    @Inject(method = "createResult", at = @At("HEAD"), cancellable = true)
    private void blockTurtlePieces(CallbackInfo ci) {
        ItemCombinerMenuAccessor access = (ItemCombinerMenuAccessor) this;
        ItemStack gear = access.getInputSlots().getItem(1);
        if (gear.is(ItemRegistry.TURTLE_CHESTPLATE)
                || gear.is(ItemRegistry.TURTLE_LEGGINGS)
                || gear.is(ItemRegistry.TURTLE_BOOTS)) {
            access.getResultSlots().setItem(0, ItemStack.EMPTY);
            ci.cancel();
        }
    }
}