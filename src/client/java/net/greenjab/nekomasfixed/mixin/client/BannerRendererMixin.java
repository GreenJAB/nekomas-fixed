package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.greenjab.nekomasfixed.target_access_class.BannerAccess;
import net.greenjab.nekomasfixed.util.CanvasRenderer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.WallBannerBlock;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BannerRenderer.class)
public class BannerRendererMixin {
    @Unique
    private ModelPart patterns;
    @Final
    @Shadow
    private ModelPart pole;
    @Final
    @Shadow
    private ModelPart bar;

    @Shadow @Final private ModelPart flag;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void BannerBlockEntityRenderer(BlockEntityRendererProvider.Context ctx, CallbackInfo ci) {
        ModelPart modelPart = ctx.bakeLayer(ModelLayers.BANNER);
        this.patterns = modelPart.getChild("patterns");
    }

    @Inject(method = "createBodyLayer", at = @At("TAIL"))
    private static void injected(CallbackInfoReturnable<LayerDefinition> cir, @Local PartDefinition modelPartData) {
        modelPartData.addOrReplaceChild("patterns", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, 0.0F, -2.0F, 20.0F, 40.0F, 1.0F), PartPose.ZERO);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/geom/ModelPart;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V"), method = "render(Lnet/minecraft/world/level/block/entity/BannerBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V")
	private void render(BannerBlockEntity bannerBlockEntity, float f, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, int j, CallbackInfo info) {
        this.bar.visible = true;
        this.flag.visible = true;
        if (((BannerAccess) bannerBlockEntity).nekomasfixed$getBannerEffects().isBackgroundHidden()) {
            this.pole.visible = false;
            this.bar.visible = false;
            this.flag.visible = false;
        }
	}

    @Inject(
            method = "render(Lnet/minecraft/world/level/block/entity/BannerBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
            cancellable = true,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/BannerRenderer;renderPatterns(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/model/geom/ModelPart;Lnet/minecraft/client/resources/model/Material;ZLnet/minecraft/world/item/DyeColor;Lnet/minecraft/world/level/block/entity/BannerPatternLayers;)V")
    )
    private void renderCanvas(BannerBlockEntity bannerBlockEntity, float f, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, int j, CallbackInfo ci) {
        BannerPatternLayers patterns = bannerBlockEntity.getPatterns();
        BannerAccess banner = (BannerAccess) bannerBlockEntity;
        this.patterns.xRot = this.flag.xRot;
        this.patterns.y = this.flag.y;
        DyeColor color = bannerBlockEntity.getBaseColor();
        boolean isWall = bannerBlockEntity.getBlockState().getBlock() instanceof WallBannerBlock;
        CanvasRenderer.renderCanvas(matrixStack, vertexConsumerProvider, i, j, this.flag, this.patterns, ModelBakery.BANNER_BASE, true, color, patterns, false, banner.nekomasfixed$getBannerEffects(), isWall);
        // Rest of code
        matrixStack.popPose();
        matrixStack.popPose();
        ci.cancel();
    }
}