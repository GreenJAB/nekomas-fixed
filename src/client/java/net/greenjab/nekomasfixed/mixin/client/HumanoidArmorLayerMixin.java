package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.util.EchoingKeyframe;
import net.greenjab.nekomasfixed.util.EchoingLayer;
import net.greenjab.nekomasfixed.util.GlobalVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {
    @Mutable
    @Final
    @Shadow
    private final TextureAtlas armorTrimAtlas;
    public HumanoidArmorLayerMixin(RenderLayerParent<T, M> context, TextureAtlas armorTrimsAtlas) {
        super(context);
        this.armorTrimAtlas = armorTrimsAtlas;
    }

    @Redirect(
            method = "renderArmorPiece",
            at = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderTrim(Lnet/minecraft/core/Holder;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/item/armortrim/ArmorTrim;Lnet/minecraft/client/model/HumanoidModel;Z)V"
            )
    )
    private void injected(HumanoidArmorLayer<?, ?, ?> instance, Holder<ArmorMaterial> armorMaterial, PoseStack matrices, MultiBufferSource vertexConsumers, int light, ArmorTrim trim, A model, boolean leggings, @Local ItemStack stack, @Local boolean bl) {
        Item item = stack.getItem();
        if (item instanceof ArmorItem armorItem) {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                long time = player.level().getGameTime();
                float t = Math.floorMod(time, 10L) * 0.1f;
                float w = -(Mth.cos(Mth.PI * t) + 1) * 0.5f;

                List<EchoingLayer> echoingLayers = stack.get(ComponentRegistry.ECHOING_LAYERS);
                if (echoingLayers != null && !echoingLayers.isEmpty()) {
                    Tuple<EchoingKeyframe, EchoingKeyframe> pair = EchoingKeyframe.buildKeyFrames(stack, Math.floorDiv(time, 10L));
                    if (pair.getB().hidden()) {
                        nekomasfixed$renderTrim(armorItem.getMaterial(), matrices, vertexConsumers, light, 1.0f - w, trim, pair.getA(), model, bl);
                        return;
                    }
                    nekomasfixed$renderTrim(armorItem.getMaterial(), matrices, vertexConsumers, light, 1.0f, trim, pair.getA(), model, bl);
                    nekomasfixed$renderTrim(armorItem.getMaterial(), matrices, vertexConsumers, light, w, trim, pair.getB(), model, bl);
                } else {
                    nekomasfixed$renderTrim(armorItem.getMaterial(), matrices, vertexConsumers, light, 1.0f, trim, null, model, bl);
                }
            }
        }
    }

    @Redirect(
            method = "renderModel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/RenderType;armorCutoutNoCull(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;"
            )
    )
    private RenderType getArmorCutoutNoCull(ResourceLocation texture) {
        return RenderType.entityCutoutNoCull(texture);
    }

    @Unique
    private void nekomasfixed$renderTrim(Holder<ArmorMaterial> material, PoseStack matrices, MultiBufferSource vertexConsumers, int light, float alpha, ArmorTrim base, @Nullable EchoingKeyframe keyframe, A model, boolean leggings) {
        ArmorTrim trim;
        if (keyframe != null) {
            if (keyframe.hidden()) {
                return;
            }
            if (keyframe.glowing()) {
                light = GlobalVariables.GLOW_STRENGTH;
            }
            trim = keyframe.toTrim(base);
        } else {
            trim = base;
        }
        TextureAtlasSprite sprite = this.armorTrimAtlas.getSprite(leggings ? trim.innerTexture(material) : trim.outerTexture(material));
        VertexConsumer vertexConsumer = sprite.wrap(vertexConsumers.getBuffer(RenderType.entityTranslucent(Sheets.ARMOR_TRIMS_SHEET)));
        model.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.color(FastColor.as8BitChannel(alpha), 0xFFFFFF));
    }
}
