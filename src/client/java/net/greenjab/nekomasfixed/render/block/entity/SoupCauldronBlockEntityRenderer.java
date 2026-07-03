package net.greenjab.nekomasfixed.render.block.entity;

import net.greenjab.nekomasfixed.registry.block.cauldron.SoupCauldronBlock;
import net.greenjab.nekomasfixed.registry.block.entity.SoupCauldronBlockEntity;
import net.greenjab.nekomasfixed.render.block.entity.state.SoupCauldronBlockEntityRenderState;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SoupCauldronBlockEntityRenderer implements BlockEntityRenderer<SoupCauldronBlockEntity, SoupCauldronBlockEntityRenderState> {
    private final ItemModelManager itemModelManager;

    public SoupCauldronBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.itemModelManager = ctx.itemModelManager();
    }

    @Override
    public SoupCauldronBlockEntityRenderState createRenderState() {
        return new SoupCauldronBlockEntityRenderState();
    }

    @Override
    public void updateRenderState(SoupCauldronBlockEntity blockEntity, SoupCauldronBlockEntityRenderState state, float f, Vec3d vec3d, ModelCommandRenderer.@Nullable CrumblingOverlayCommand crumblingOverlayCommand) {
        BlockEntityRenderer.super.updateRenderState(blockEntity, state, f, vec3d, crumblingOverlayCommand);
        int i = (int)blockEntity.getPos().asLong();
        state.inputItems = new ArrayList<>();
        assert blockEntity.getWorld() != null;
        state.animationTime = blockEntity.getWorld().getTime() + f;
        state.stirProgress = SoupCauldronBlock.getAnimationProgressRetriever(blockEntity).getFallback().get(f);

        for(int j = 0; j < blockEntity.getInputs().size(); ++j) {
            ItemRenderState itemRenderState = new ItemRenderState();
            this.itemModelManager.clearAndUpdate(itemRenderState, blockEntity.getInputs().get(j), ItemDisplayContext.FIXED, blockEntity.getWorld(), null, i + j);
            state.inputItems.add(itemRenderState);
        }
        state.tint = SoupCauldronBlock.blendFoodColors(state.stirProgress, blockEntity.getInputs());

    }

    @Override
    public void render(SoupCauldronBlockEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        List<ItemRenderState> list = state.inputItems;
        for(int i = 0; i < list.size(); ++i) {
            ItemRenderState itemRenderState = list.get(i);
            if (!itemRenderState.isEmpty() && state.stirProgress<1) {
                matrices.push();
                float bob = (float)Math.sin(state.animationTime * 0.1f) * 0.02f;
                float stir = state.stirProgress*state.stirProgress;
                matrices.translate(0.5F, 1F + bob - stir*0.08f, 0.5F);
                Direction direction2 = Direction.fromHorizontalQuarterTurns((i + Direction.NORTH.getHorizontalQuarterTurns()) % 4);
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(720*stir - direction2.getPositiveHorizontalDegrees()));
                matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(-70.0F));
                matrices.translate(-0.23*(1-stir), -0.1, 0.0F);
                matrices.scale(0.275F, 0.375F, 0.275F);
                itemRenderState.render(matrices, queue, state.lightmapCoordinates, OverlayTexture.DEFAULT_UV, 0);
                matrices.pop();
            }
        }
    }

}
