package net.greenjab.nekomasfixed.render.block.entity;

import net.greenjab.nekomasfixed.registry.block.cauldron.SoupCauldronBlock;
import net.greenjab.nekomasfixed.registry.block.entity.SoupCauldronBlockEntity;
import net.greenjab.nekomasfixed.render.block.entity.state.SoupCauldronBlockEntityRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.ArrayList;
import java.util.List;

public class SoupCauldronBlockEntityRenderer implements BlockEntityRenderer<SoupCauldronBlockEntity, SoupCauldronBlockEntityRenderState> {
    private final ItemModelResolver itemModelManager;

    public SoupCauldronBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.itemModelManager = ctx.itemModelResolver();
    }

    @Override
    public @NonNull SoupCauldronBlockEntityRenderState createRenderState() {
        return new SoupCauldronBlockEntityRenderState();
    }

    public void extractRenderState(@NonNull SoupCauldronBlockEntity blockEntity, @NonNull SoupCauldronBlockEntityRenderState state, float f, @NonNull Vec3 vec3d, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlayCommand) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, f, vec3d, crumblingOverlayCommand);
        int i = (int)blockEntity.getBlockPos().asLong();
        state.inputItems = new ArrayList<>();
        assert blockEntity.getLevel() != null;
        state.animationTime = blockEntity.getLevel().getGameTime() + f;
        state.stirProgress = SoupCauldronBlock.getAnimationProgressRetriever(blockEntity).getFallback().get(f);

        for(int j = 0; j < blockEntity.getInputs().size(); ++j) {
            ItemStackRenderState itemRenderState = new ItemStackRenderState();
            this.itemModelManager.updateForTopItem(itemRenderState, blockEntity.getInputs().get(j), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, i + j);
            state.inputItems.add(itemRenderState);
        }
        state.tint = SoupCauldronBlock.blendFoodColors(state.stirProgress, blockEntity.getInputs());

    }

    @Override
    public void submit(SoupCauldronBlockEntityRenderState state, @NonNull PoseStack matrices, @NonNull SubmitNodeCollector queue, @NonNull CameraRenderState cameraState) {
        List<ItemStackRenderState> list = state.inputItems;
        for(int i = 0; i < list.size(); ++i) {
            ItemStackRenderState itemRenderState = list.get(i);
            if (!itemRenderState.isEmpty() && state.stirProgress<1) {
                matrices.pushPose();
                float bob = (float)Math.sin(state.animationTime * 0.1f) * 0.02f;
                float stir = state.stirProgress*state.stirProgress;
                matrices.translate(0.5F, 1F + bob - stir*0.08f, 0.5F);
                Direction direction2 = Direction.from2DDataValue((i + Direction.NORTH.get2DDataValue()) % 4);
                matrices.mulPose(Axis.YN.rotationDegrees(720*stir - direction2.toYRot()));
                matrices.mulPose(Axis.XN.rotationDegrees(-70.0F));
                matrices.translate(-0.23*(1-stir), -0.1, 0.0F);
                matrices.scale(0.275F, 0.375F, 0.275F);
                itemRenderState.submit(matrices, queue, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
                matrices.popPose();
            }
        }
    }

}
