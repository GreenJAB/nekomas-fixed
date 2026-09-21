package net.greenjab.nekomasfixed.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.greenjab.nekomasfixed.target_access_class.DecoratedPotAccess;
import net.greenjab.nekomasfixed.util.GlobalVariables;
import net.greenjab.nekomasfixed.registry.registries.GlowingDecoratedPotPatternRegistry;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.Set;

@Mixin(DecoratedPotRenderer.class)
public abstract class DecoratedPotRendererMixin {
    @Mutable
    @Final
    @Shadow
    private final ModelPart frontSide;
    @Mutable
    @Final
    @Shadow
    private final ModelPart backSide;
    @Mutable
    @Final
    @Shadow
    private final ModelPart leftSide;
    @Mutable
    @Final
    @Shadow
    private final ModelPart rightSide;

    protected DecoratedPotRendererMixin(ModelPart front, ModelPart back, ModelPart left, ModelPart right) {
        this.frontSide = front;
        this.backSide = back;
        this.leftSide = left;
        this.rightSide = right;
    }

    @Shadow
    @Nullable
    private static Material getSideMaterial(Optional<Item> sherd) {
        return null;
    }

    @Shadow
    protected abstract void renderSide(ModelPart part, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, @Nullable Material textureId);


    @Inject(
            method = "render(Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity;getDecorations()Lnet/minecraft/world/level/block/entity/PotDecorations;"
            ),
            cancellable = true
    )
    private void injected(DecoratedPotBlockEntity decoratedPotBlockEntity, float f, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, int j, CallbackInfo ci) {
        PotDecorations sherds = decoratedPotBlockEntity.getDecorations();
        DecoratedPotAccess nekomasfixedPot = (DecoratedPotAccess) decoratedPotBlockEntity;
        Set.of(
                Triple.of(this.frontSide, nekomasfixedPot.FRONT, sherds.front()),
                Triple.of(this.backSide, nekomasfixedPot.BACK, sherds.back()),
                Triple.of(this.leftSide, nekomasfixedPot.LEFT, sherds.left()),
                Triple.of(this.rightSide, nekomasfixedPot.RIGHT, sherds.right())
        ).forEach(t -> renderSide(
                t.getLeft(),
                matrixStack,
                vertexConsumerProvider,
                nekomasfixedPot.nekomasfixed$getSherdGlow(t.getMiddle()) ? GlobalVariables.GLOW_STRENGTH : i,
                j,
                nekomasfixed$getTextureIdFromSherd(
                        t.getRight(),
                        nekomasfixedPot.nekomasfixed$getSherdGlow(t.getMiddle())
                )
        ));
        matrixStack.popPose();
        ci.cancel();
    }

    @Unique
    @Nullable
    private static Material nekomasfixed$getTextureIdFromSherd(Optional<Item> item, boolean glowing) {
        if (item.isPresent() && glowing) {
            Material spriteIdentifier = Sheets.getDecoratedPotMaterial(GlowingDecoratedPotPatternRegistry.fromSherd(item.get()));
            if (spriteIdentifier != null) {
                return spriteIdentifier;
            }
        }
        return getSideMaterial(item);
    }
}
