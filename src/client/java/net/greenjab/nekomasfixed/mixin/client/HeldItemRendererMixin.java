package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemInHandRenderer.class)
public class HeldItemRendererMixin {

    @ModifyConstant(method="renderArmWithItem", constant = @Constant(floatValue = 20.0f))
    private float slingshotFasterPulltime(float constant, @Local(argsOnly = true) AbstractClientPlayer player) {
       if (player.getUseItem().is(ItemRegistry.SLINGSHOT)) {
           return constant/2;
       }
       return constant;
    }
}
