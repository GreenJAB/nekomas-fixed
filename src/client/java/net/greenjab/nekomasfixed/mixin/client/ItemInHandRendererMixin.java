package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.client.renderer.FirstPersonHandsAndItemsRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(FirstPersonHandsAndItemsRenderer.class)
public class ItemInHandRendererMixin {

    @ModifyConstant(method="submitArmWithItem", constant = @Constant(floatValue = 20.0f))
    private float slingshotFasterPullTime(float constant, @Local(argsOnly = true) ItemStack stack) {
       if (stack.is(ItemRegistry.SLINGSHOT)) {
           return constant/2;
       }
       return constant;
    }
}