package net.greenjab.nekomasfixed.render.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.greenjab.nekomasfixed.registry.block.entity.GoatHornBlockEntity;
import net.greenjab.nekomasfixed.registry.block.entity.HollowLogBlockEntity;
import net.greenjab.nekomasfixed.render.block.entity.state.GoatHornBlockEntityRenderState;
import net.greenjab.nekomasfixed.render.block.entity.state.HollowLogBlockEntityRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Map;

import static net.minecraft.client.renderer.entity.DisplayRenderer.BLOCK_DISPLAY_CONTEXT;

public class GoatHornBlockEntityRenderer implements BlockEntityRenderer<GoatHornBlockEntity, GoatHornBlockEntityRenderState> {
    protected final BlockModelResolver blockModelResolver;
    private Map<Direction, float[]> PIXEL_MAPPINGS = new HashMap<>();


    public GoatHornBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.blockModelResolver = context.blockModelResolver();
        PIXEL_MAPPINGS.put(Direction.SOUTH, new float[]{0, (float) (0.2 - 0.135)});
        PIXEL_MAPPINGS.put(Direction.NORTH, new float[]{0, (float) (- 0.2 + 0.135)});
        PIXEL_MAPPINGS.put(Direction.EAST, new float[]{(float) ( 0.2 - 0.135), 0});
        PIXEL_MAPPINGS.put(Direction.WEST, new float[]{(float) ( -0.2 + 0.135), 0});
    }

    @Override
    public GoatHornBlockEntityRenderState createRenderState() {
        return new GoatHornBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(@NonNull GoatHornBlockEntity blockEntity,
                                   @NonNull GoatHornBlockEntityRenderState state,
                                   float tickProgress,
                                   @NonNull Vec3 cameraPos,
                                   @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlayCommand) {
        state.direction = blockEntity.getFacing();
        BlockEntityRenderState.extractBase(blockEntity, state, crumblingOverlayCommand);
        if (blockEntity.getTorch().is(Blocks.AIR)) state.displayBlockModel.clear();
        else this.blockModelResolver.update(state.displayBlockModel, blockEntity.getTorch(), BLOCK_DISPLAY_CONTEXT);
    }

    @Override
    public void submit(GoatHornBlockEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        BlockModelRenderState displayBlockModel = state.displayBlockModel;
        Direction facing = state.direction;
        if (!displayBlockModel.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate( PIXEL_MAPPINGS.get(facing)[0], 0.325, PIXEL_MAPPINGS.get(facing)[1]);
            poseStack.scale(1f, 1f, 1f);
            displayBlockModel.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        }
    }
}
