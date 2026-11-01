package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.greenjab.nekomasfixed.NekomasFixedClient;
import net.greenjab.nekomasfixed.registry.other.BannerEffects;
import net.greenjab.nekomasfixed.target_access.BannerAccess;
import net.greenjab.nekomasfixed.util.MixinGlobals;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.block.entity.state.BannerBlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteHolder;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(BannerBlockEntityRenderer.class)
public class BannerBlockEntityRendererMixin {

    @Inject(
            method = "updateRenderState(Lnet/minecraft/block/entity/BannerBlockEntity;Lnet/minecraft/client/render/block/entity/state/BannerBlockEntityRenderState;FLnet/minecraft/util/math/Vec3d;Lnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;)V",
            at = @At("TAIL")
    )
    private void updateRenderState(BannerBlockEntity bannerBlockEntity, BannerBlockEntityRenderState bannerBlockEntityRenderState, float f, Vec3d vec3d, ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlayCommand, CallbackInfo ci) {
        ((BannerAccess)bannerBlockEntityRenderState).nekomasfixed$setBannerEffects(((BannerAccess)bannerBlockEntity).nekomasfixed$getBannerEffects());
    }

    @Inject(
            method = "render(Lnet/minecraft/client/render/block/entity/state/BannerBlockEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V",
            at = @At("HEAD")
    )
    private void readEffects(BannerBlockEntityRenderState bannerBlockEntityRenderState, MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState, CallbackInfo ci) {
        MixinGlobals.storedBannerEffects = ((BannerAccess)bannerBlockEntityRenderState).nekomasfixed$getBannerEffects();
    }

    @Inject(
            method = "render(Lnet/minecraft/client/render/block/entity/state/BannerBlockEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V",
            at = @At("TAIL")
    )
    private void clearEffects(CallbackInfo ci) {
        // Clear effects to prevent banner items having them
        MixinGlobals.storedBannerEffects = BannerEffects.EMPTY;
        // This prevents items being invisible
    }

    @WrapOperation(
            method = "render(Lnet/minecraft/client/texture/SpriteHolder;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;IIFLnet/minecraft/client/render/block/entity/model/BannerBlockModel;Lnet/minecraft/client/render/block/entity/model/BannerFlagBlockModel;FLnet/minecraft/util/DyeColor;Lnet/minecraft/component/type/BannerPatternsComponent;Lnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/RenderLayer;IIILnet/minecraft/client/texture/Sprite;ILnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;)V")
    )
    private static void renderBaseBlock(OrderedRenderCommandQueue instance, Model<Unit> model, Object state, MatrixStack matrices, RenderLayer renderLayer, int light, int overlay, int tintedColor, Sprite sprite, int outlineColor, ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay, Operation<Void> original) {
        if (!MixinGlobals.storedBannerEffects.isBackgroundHidden()) {
            // If not, just render banner base like normal
            original.call(instance, model, state, matrices, renderLayer, light, overlay, tintedColor, sprite, outlineColor, crumblingOverlay);
        }
    }

    @WrapOperation(
            method = "renderCanvas",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/RenderLayer;IIILnet/minecraft/client/texture/Sprite;ILnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;)V",
                    ordinal = 0
            )
    )
    private static void renderBaseFlag(OrderedRenderCommandQueue instance, Model<Unit> model, Object o, MatrixStack matrixStack, RenderLayer renderLayer, int light, int overlay, int tintedColor, Sprite sprite, int outlineColor, ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlayCommand, Operation<Void> original, SpriteHolder s, MatrixStack m, OrderedRenderCommandQueue q, int l, int ov, Model<Unit> mo, Object state, SpriteIdentifier sp, boolean useBannerLayer) {
        if (!(useBannerLayer && MixinGlobals.storedBannerEffects.isBackgroundHidden())) {
            original.call(instance, model, o, matrixStack, renderLayer, light, overlay, tintedColor, sprite, outlineColor, crumblingOverlayCommand);
        }
    }

    @WrapOperation(
            method = "renderCanvas",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/block/entity/BannerBlockEntityRenderer;renderLayer(Lnet/minecraft/client/texture/SpriteHolder;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;IILnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/client/util/SpriteIdentifier;Lnet/minecraft/util/DyeColor;Lnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;)V",
                    ordinal = 0
            )
    )
    private static void renderBaseFlagPattern(SpriteHolder materials, MatrixStack matrices, OrderedRenderCommandQueue queue, int light, int overlay, Model<Float> model, Object state, SpriteIdentifier spriteId, DyeColor color, ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay, Operation<Void> original) {
        if (!MixinGlobals.storedBannerEffects.isBackgroundHidden()) {
            original.call(materials, matrices, queue, light, overlay, model, state, spriteId, color, crumblingOverlay);
        }
    }

    @WrapOperation(
            method = "renderCanvas",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/block/entity/BannerBlockEntityRenderer;renderLayer(Lnet/minecraft/client/texture/SpriteHolder;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;IILnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/client/util/SpriteIdentifier;Lnet/minecraft/util/DyeColor;Lnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;)V",
                    ordinal = 1
            )
    )
    private static void renderBasePattern(SpriteHolder materials, MatrixStack matrices, OrderedRenderCommandQueue queue, int light, int overlay, Model<Float> model, Object state, SpriteIdentifier spriteId, DyeColor color, ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay, Operation<Void> original) {
        light = MixinGlobals.storedBannerEffects.isGlowing() ? NekomasFixedClient.GLOW_STRENGTH : light;
        original.call(materials, matrices, queue, light, overlay, model, state, spriteId, color, crumblingOverlay);
    }

    @ModifyArg(
            method = "renderLayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/SpriteIdentifier;getRenderLayer(Ljava/util/function/Function;)Lnet/minecraft/client/render/RenderLayer;"
            )
    )
    private static Function<Identifier, RenderLayer> changeRenderLayer(Function<Identifier, RenderLayer> layerFactory) {
        // To prevent pattern from displaying clouds behind it etc
        return RenderLayers::itemEntityTranslucentCull;
    }
}