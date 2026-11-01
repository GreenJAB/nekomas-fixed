package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.greenjab.nekomasfixed.NekomasFixedClient;
import net.greenjab.nekomasfixed.target_access.DecoratedPotAccess;
import net.minecraft.block.DecoratedPotPattern;
import net.minecraft.block.DecoratedPotPatterns;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.block.entity.Sherds;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.DecoratedPotBlockEntityRenderer;
import net.minecraft.client.render.block.entity.state.DecoratedPotBlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteHolder;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Vec3d;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Optional;

@Mixin(DecoratedPotBlockEntityRenderer.class)
public abstract class DecoratedPotBlockEntityRendererMixin {
    @Mutable
    @Final
    @Shadow
    private final ModelPart front;
    @Mutable
    @Final
    @Shadow
    private final ModelPart back;
    @Mutable
    @Final
    @Shadow
    private final ModelPart left;
    @Mutable
    @Final
    @Shadow
    private final ModelPart right;
    @Mutable
    @Final
    @Shadow
    private final SpriteHolder materials;

    @Unique
    private DecoratedPotAccess nekomasfixed$storedPot;

    protected DecoratedPotBlockEntityRendererMixin(ModelPart front, ModelPart back, ModelPart left, ModelPart right, SpriteHolder materials) {
        this.front = front;
        this.back = back;
        this.left = left;
        this.right = right;
        this.materials = materials;
    }

    @Inject(
            method = "updateRenderState(Lnet/minecraft/block/entity/DecoratedPotBlockEntity;Lnet/minecraft/client/render/block/entity/state/DecoratedPotBlockEntityRenderState;FLnet/minecraft/util/math/Vec3d;Lnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;)V",
            at = @At("TAIL")
    )
    private void updateRenderState(DecoratedPotBlockEntity decoratedPotBlockEntity, DecoratedPotBlockEntityRenderState decoratedPotBlockEntityRenderState, float f, Vec3d vec3d, ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlayCommand, CallbackInfo ci) {
        DecoratedPotAccess potEntity = (DecoratedPotAccess) decoratedPotBlockEntity;
        DecoratedPotAccess potRenderState = (DecoratedPotAccess) decoratedPotBlockEntityRenderState;
        for (int i = 0; i < 4; i++) {
            potRenderState.nekomasfixed$setSherdGlow(i, potEntity.nekomasfixed$getSherdGlow(i));
        }
    }


    @WrapOperation(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;IILnet/minecraft/block/entity/Sherds;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;submitModelPart(Lnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/RenderLayer;IILnet/minecraft/client/texture/Sprite;ZZILnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;I)V"
            ),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/block/entity/Sherds;front()Ljava/util/Optional;"
                    )
            )
    )
    private void renderSherd(OrderedRenderCommandQueue instance, ModelPart part, MatrixStack matrices, RenderLayer renderLayer, int light, int overlay, @Nullable Sprite sprite, boolean sheeted, boolean hasGlint, int tintedColor, ModelCommandRenderer.@Nullable CrumblingOverlayCommand crumblingOverlay, int i, Operation<Void> original, MatrixStack m, OrderedRenderCommandQueue queue, int l, int o, Sherds sherds) {
        final Map<ModelPart, Pair<Integer, Optional<Item>>> partToSherd = Map.of(
                this.front, new Pair<>(DecoratedPotAccess.FRONT, sherds.front()),
                this.back, new Pair<>(DecoratedPotAccess.BACK, sherds.back()),
                this.left, new Pair<>(DecoratedPotAccess.LEFT, sherds.left()),
                this.right, new Pair<>(DecoratedPotAccess.RIGHT, sherds.right())
        );

        // Replace sherd if it's glowing
        Pair<Integer, Optional<Item>> pair = partToSherd.get(part);
        if (pair != null) {
            if (nekomasfixed$storedPot != null && nekomasfixed$storedPot.nekomasfixed$getSherdGlow(pair.getLeft())) {
                // If glowing, swap textures nad set light
                light = NekomasFixedClient.GLOW_STRENGTH;
                SpriteIdentifier spriteIdentifier = pair.getRight().map(DecoratedPotBlockEntityRendererMixin::nekomasfixed$glowingPatternTextureId).orElse(null);
                if (spriteIdentifier != null) {
                    renderLayer = spriteIdentifier.getRenderLayer(RenderLayers::entitySolid);
                    sprite = materials.getSprite(spriteIdentifier);
                }
            }
        }
        original.call(instance, part, matrices, renderLayer, light, overlay, sprite, sheeted, hasGlint, tintedColor, crumblingOverlay, i);
    }

    @Unique
    private static @Nullable SpriteIdentifier nekomasfixed$glowingPatternTextureId(Item sherd) {
        RegistryKey<DecoratedPotPattern> baseKey = DecoratedPotPatterns.fromSherd(sherd);
        if (baseKey != null) {
            RegistryKey<DecoratedPotPattern> glowingKey = RegistryKey.of(RegistryKeys.DECORATED_POT_PATTERN, baseKey.getValue().withSuffixedPath("_glowing"));
            return TexturedRenderLayers.getDecoratedPotPatternTextureId(glowingKey);
        }
        return null;
    }

    @Inject(
            method = "render(Lnet/minecraft/client/render/block/entity/state/DecoratedPotBlockEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V",
            at = @At("HEAD")
    )
    private void injected(DecoratedPotBlockEntityRenderState decoratedPotBlockEntityRenderState, MatrixStack matrixStack, OrderedRenderCommandQueue queue, CameraRenderState cameraRenderState, CallbackInfo ci) {
        nekomasfixed$storedPot = (DecoratedPotAccess) decoratedPotBlockEntityRenderState;
    }
}
