package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.item.HeldItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {

    @ModifyConstant(method="renderFirstPersonItem", constant = @Constant(floatValue = 20.0f))
    private float slingshotFasterPulltime(float constant, @Local(argsOnly = true) AbstractClientPlayerEntity player) {
       if (player.getActiveItem().isOf(ItemRegistry.SLINGSHOT)) {
           return constant/2;
       }
       return constant;
    }
}
