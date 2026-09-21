package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.greenjab.nekomasfixed.util.EchoingKeyframe;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;

import static net.greenjab.nekomasfixed.registry.registries.ComponentRegistry.ECHOING_LAYERS;
import static net.minecraft.core.component.DataComponents.TRIM;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Unique
    private static float nekomasfixed$nextAlpha = 1.0f;

    @Unique
    public void nekomasfixed$applyEchoingKeyFrame(ItemStack stack, EchoingKeyframe keyframe, ArmorTrim original) {
        if (keyframe.hidden()) {
            stack.remove(TRIM);
        } else {
            stack.set(TRIM, keyframe.toTrim(original));
        }
    }
    @WrapOperation(
            method = "render(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/resources/model/BakedModel;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderModelLists(Lnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/world/item/ItemStack;IILcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;)V"
            )
    )
    private void renderEchoingLayers(ItemRenderer instance, BakedModel model, ItemStack stack, int light, int overlay, PoseStack matrices, VertexConsumer vertices, Operation<Void> original) {
        LocalPlayer player =  Minecraft.getInstance().player;
        if (player != null) {
            ClientLevel world = player.clientLevel;
            if (stack.has(TRIM) && stack.has(ECHOING_LAYERS)) {
                ArmorTrim savedTrim = stack.get(TRIM);

                long worldTime = world.getGameTime();
                float t = Math.floorMod(worldTime, 10L) * 0.1f;
                long time = Math.floorDiv(worldTime, 10L);
                Tuple<EchoingKeyframe, EchoingKeyframe> pair = EchoingKeyframe.buildKeyFrames(stack, time);

                nekomasfixed$applyEchoingKeyFrame(stack, pair.getA(), savedTrim);
                BakedModel model1 = instance.getModel(stack, world, null, 0);
                original.call(instance, model1, stack, light, overlay, matrices, vertices);
                if (
                        pair.getA().hidden() != pair.getB().hidden() ||
                                !pair.getA().material().equals(pair.getB().material())
                ) {
                    nekomasfixed$nextAlpha = -(Mth.cos(Mth.PI * t) + 1) * 0.5f;

                    nekomasfixed$applyEchoingKeyFrame(stack, pair.getB(), savedTrim);
                    BakedModel model2 = instance.getModel(stack, world, null, 0);
                    original.call(instance, model2, stack, light, overlay, matrices, vertices);

                    nekomasfixed$nextAlpha = 1f;
                }

                stack.set(TRIM, savedTrim);
                return;
            }
        }
        original.call(instance, model, stack, light, overlay, matrices, vertices);
    }

    @ModifyArg(
            method = "renderQuadList",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;putBulkData(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/minecraft/client/renderer/block/model/BakedQuad;FFFFII)V"
            ),
            index = 5
    )
    private float applyAlpha(float alpha) {
        return alpha * nekomasfixed$nextAlpha;
    }
}
