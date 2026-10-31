package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.greenjab.nekomasfixed.registry.other.BannerEffects;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.util.MixinGlobals;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.model.special.ShieldModelRenderer;
import net.minecraft.client.render.model.ModelBaker;
import net.minecraft.client.texture.SpriteHolder;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Unit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShieldModelRenderer.class)
public class ShieldModelRendererMixin {
    @WrapOperation(
            method = "render(Lnet/minecraft/component/ComponentMap;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;IIZI)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/block/entity/BannerBlockEntityRenderer;renderCanvas(Lnet/minecraft/client/texture/SpriteHolder;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;IILnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/client/util/SpriteIdentifier;ZLnet/minecraft/util/DyeColor;Lnet/minecraft/component/type/BannerPatternsComponent;ZLnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;I)V")
    )
    private void withEffects(SpriteHolder materials, MatrixStack matrices, OrderedRenderCommandQueue queue, int light, int overlay, Model<Unit> model, Object state, SpriteIdentifier spriteId, boolean useBannerLayer, DyeColor color, BannerPatternsComponent patterns, boolean bl, ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlayCommand, int i, Operation<Void> original, ComponentMap components) {
        MixinGlobals.storedBannerEffects = components.getOrDefault(ComponentRegistry.BANNER_EFFECTS, BannerEffects.EMPTY);
        if (MixinGlobals.storedBannerEffects.isBackgroundHidden()) spriteId = ModelBaker.SHIELD_BASE_NO_PATTERN;

        original.call(materials, matrices, queue, light, overlay, model, state, spriteId, useBannerLayer, color, patterns, bl, crumblingOverlayCommand, i);

        MixinGlobals.storedBannerEffects = BannerEffects.EMPTY;
    }
}
