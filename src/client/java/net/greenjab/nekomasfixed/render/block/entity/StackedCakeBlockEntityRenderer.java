package net.greenjab.nekomasfixed.render.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.impl.client.rendering.ArmorRendererRegistryImpl;
import net.greenjab.nekomasfixed.registry.block.entity.StackedCakeBlockEntity;
import net.greenjab.nekomasfixed.render.block.entity.state.StackedCakeBlockEntityRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.properties.select.TrimMaterialProperty;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.predicates.TrimPredicate;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import net.minecraft.world.item.equipment.trim.TrimPatterns;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import static net.minecraft.client.renderer.entity.DisplayRenderer.BLOCK_DISPLAY_CONTEXT;

public class StackedCakeBlockEntityRenderer implements BlockEntityRenderer<StackedCakeBlockEntity, StackedCakeBlockEntityRenderState> {
    protected final BlockModelResolver blockModelResolver;
    public StackedCakeBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.blockModelResolver = ctx.blockModelResolver();
    }

    public void extractRenderState(@NonNull StackedCakeBlockEntity blockEntity, @NonNull StackedCakeBlockEntityRenderState state, float f, @NonNull Vec3 vec3d, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlayCommand) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, f, vec3d, crumblingOverlayCommand);
        if (blockEntity.LAYER_2_STATE.is(Blocks.AIR)) state.displayBlockModel_layer_2.clear();
        else this.blockModelResolver.update(state.displayBlockModel_layer_2, blockEntity.LAYER_2_STATE, BLOCK_DISPLAY_CONTEXT);
        if (blockEntity.LAYER_3_STATE.is(Blocks.AIR)) state.displayBlockModel_layer_3.clear();
        else this.blockModelResolver.update(state.displayBlockModel_layer_3, blockEntity.LAYER_3_STATE, BLOCK_DISPLAY_CONTEXT);
        if (blockEntity.CANDLE_STATE.is(Blocks.AIR)) state.displayBlockModel_candle.clear();
        else this.blockModelResolver.update(state.displayBlockModel_candle, blockEntity.CANDLE_STATE, BLOCK_DISPLAY_CONTEXT);
    }

    @Override
    public @NonNull StackedCakeBlockEntityRenderState createRenderState() {
        return new StackedCakeBlockEntityRenderState();
    }



    @Override
    public boolean shouldRenderOffScreen() {
        return true;
    }

    @Override
    public void submit(StackedCakeBlockEntityRenderState state, @NonNull PoseStack matrices, @NonNull SubmitNodeCollector queue, @NonNull CameraRenderState cameraState) {
        int height = 1;
        BlockModelRenderState displayBlockModel_layer_2 = state.displayBlockModel_layer_2;
        if (!displayBlockModel_layer_2.isEmpty()) {
            matrices.pushPose();
            float scale = (float)(1.0 - (0.2 * height));
            matrices.translate((1.0f - scale) / 2.0f, height * 0.5f, (1.0f - scale) / 2.0f);
            matrices.scale(scale, scale, scale);
            displayBlockModel_layer_2.submit(matrices, queue, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            matrices.popPose();
            height =2;
            BlockModelRenderState displayBlockModel_layer_3 = state.displayBlockModel_layer_3;
            if (!displayBlockModel_layer_3.isEmpty()) {
                matrices.pushPose();
                scale = (float)(1.0 - (0.2 * height));
                matrices.translate((1.0f - scale) / 2.0f, height *0.5 -0.1, (1.0f - scale) / 2.0f);
                matrices.scale(scale, scale, scale);
                displayBlockModel_layer_3.submit(matrices, queue, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
                matrices.popPose();
                height =3;
            }
        }

        BlockModelRenderState displayBlockModel_candle = state.displayBlockModel_candle;
        if (!displayBlockModel_candle.isEmpty()) {
            matrices.pushPose();
            matrices.translate(0, height * 0.5f - ((height -1) * 0.1) - ((Math.max(0, height -2)) * 0.1), 0);
            displayBlockModel_candle.submit(matrices, queue, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            matrices.popPose();
        }
    }
}
