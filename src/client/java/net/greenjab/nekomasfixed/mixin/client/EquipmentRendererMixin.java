package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Function3;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixedClient;
import net.greenjab.nekomasfixed.registry.other.EchoingLayer;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.util.EchoingKeyframe;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Function;

@Mixin(EquipmentRenderer.class)
@Environment(EnvType.CLIENT)
public class EquipmentRendererMixin {
    @Final
    @Shadow
    private Function<EquipmentRenderer.TrimSpriteKey, Sprite> trimSprites;

    @Unique private static final EquipmentModel turtleArmorModel = createHumanoidOnlyModel("turtle_scute");
    @Unique private static final EquipmentModel netheriteCrownModel = createHumanoidOnlyModel("netherite_crown");
    @Unique private static final EquipmentModel copperCrownModel = createHumanoidOnlyModel("copper_crown");
    @Unique private static final EquipmentModel ironCrownModel = createHumanoidOnlyModel("iron_crown");
    @Unique private static final EquipmentModel goldenCrownModel = createHumanoidOnlyModel("golden_crown");
    @Unique private static final EquipmentModel diamondCrownModel = createHumanoidOnlyModel("diamond_crown");

    @Unique private static final RenderLayer ECHOING_TRIMS_RENDER_LAYER = RenderLayers.armorTranslucent(TexturedRenderLayers.ARMOR_TRIMS_ATLAS_TEXTURE);

    @ModifyExpressionValue(method = "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/util/Identifier;II)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/entity/equipment/EquipmentModelLoader;get(Lnet/minecraft/registry/RegistryKey;)Lnet/minecraft/client/render/entity/equipment/EquipmentModel;"
    ))
    private EquipmentModel useNewArmorModel(EquipmentModel original, @Local(argsOnly = true) RegistryKey<EquipmentAsset> assetKey) {
        if (assetKey.getValue().toString().toLowerCase().contains("turtle_scute")) return turtleArmorModel;
        if(assetKey.getValue().toString().toLowerCase().contains("netherite_crown")) return netheriteCrownModel;
        if(assetKey.getValue().toString().toLowerCase().contains("copper_crown")) return copperCrownModel;
        if(assetKey.getValue().toString().toLowerCase().contains("iron_crown")) return ironCrownModel;
        if(assetKey.getValue().toString().toLowerCase().contains("golden_crown")) return goldenCrownModel;
        if(assetKey.getValue().toString().toLowerCase().contains("diamond_crown")) return diamondCrownModel;
        return original;
    }


    @Inject(
            method = "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/util/Identifier;II)V",
            at = @At(value = "FIELD", target = "Lnet/minecraft/component/DataComponentTypes;TRIM:Lnet/minecraft/component/ComponentType;"),
            cancellable = true
    )
    private <S> void render(EquipmentModel.LayerType layerType, RegistryKey<EquipmentAsset> assetKey, Model<? super S> model, S state, ItemStack stack, MatrixStack matrices, OrderedRenderCommandQueue queue, int light, @Nullable Identifier textureId, int outlineColor, int initialOrder, CallbackInfo ci, @Local(ordinal = 1, argsOnly = true) int j) {
        ArmorTrim armorTrim = stack.get(DataComponentTypes.TRIM);
        if (armorTrim != null) {
            // Trim rendering function
            Function3<EchoingKeyframe, Float, Integer, Void> renderTrim = (keyframe, alphaBlend, order) -> {
                if (keyframe.hidden()) return null;
                Sprite sprite = trimSprites.apply(new EquipmentRenderer.TrimSpriteKey(keyframe.toTrim(armorTrim), layerType, assetKey));
                queue.getBatchingQueue(order).submitModel(
                        model,
                        state,
                        matrices,
                        ECHOING_TRIMS_RENDER_LAYER,
                        keyframe.glowing() ? NekomasFixedClient.GLOW_STRENGTH : light,
                        OverlayTexture.DEFAULT_UV,
                        ColorHelper.scaleAlpha(0xFFFFFFFF, alphaBlend),
                        sprite,
                        outlineColor,
                        null
                );
                return null;
            };

            // Find the keyframes, and smooth transition between them
            World world = MinecraftClient.getInstance().world;
            if (world != null) {
                List<EchoingLayer> echoingLayers = stack.get(ComponentRegistry.ECHOING_LAYERS);
                if (echoingLayers != null && !echoingLayers.isEmpty()) {
                    long time = world.getTime();
                    float t = Math.floorMod(time, 10L) * 0.1f;
                    float w = (MathHelper.cos(MathHelper.PI * t) + 1) * 0.5f;

                    Pair<EchoingKeyframe, EchoingKeyframe> pair = EchoingKeyframe.buildKeyFrames(stack, Math.floorDiv(time, 10L));
                    if (pair.getRight().hidden()) {
                        renderTrim.apply(pair.getLeft(), w, ++j);
                    } else {
                        renderTrim.apply(pair.getLeft(), 1.0f, ++j);
                        renderTrim.apply(pair.getRight(), 1.0f - w, ++j);
                    }
                    ci.cancel();
                    return;
                }
            }
            renderTrim.apply(EchoingKeyframe.of(armorTrim), 1.0f, ++j);
        }
        ci.cancel();
    }

    @Unique
    private static EquipmentModel createHumanoidOnlyModel(String id) {
        return EquipmentModel.builder()
                .addHumanoidLayers(Identifier.ofVanilla(id))
                .build();
    }

}
