package net.greenjab.nekomasfixed.render.block.entity;

import net.greenjab.nekomasfixed.registry.block.entity.HollowOakLogBlockEntity;
import net.greenjab.nekomasfixed.render.block.entity.state.HollowOakLogBlockEntityRenderState;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.state.CampfireBlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HeldItemContext;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;

public class HollowOakLogBlockEntityRenderer implements BlockEntityRenderer<HollowOakLogBlockEntity, HollowOakLogBlockEntityRenderState>{

    @Override
    public HollowOakLogBlockEntityRenderState createRenderState() {
        return new HollowOakLogBlockEntityRenderState();
    }

    @Override
    public void updateRenderState(CampfireBlockEntity campfireBlockEntity, CampfireBlockEntityRenderState campfireBlockEntityRenderState, float f, Vec3d vec3d, ModelCommandRenderer.@Nullable CrumblingOverlayCommand crumblingOverlayCommand) {
        super.updateRenderState(campfireBlockEntity, campfireBlockEntityRenderState, f, vec3d, crumblingOverlayCommand);
        campfireBlockEntityRenderState.facing = (Direction)campfireBlockEntity.getCachedState().get(CampfireBlock.FACING);
        int i = (int)campfireBlockEntity.getPos().asLong();
        campfireBlockEntityRenderState.cookedItemStates = new ArrayList();

        for(int j = 0; j < campfireBlockEntity.getItemsBeingCooked().size(); ++j) {
            ItemRenderState itemRenderState = new ItemRenderState();
            this.itemModelManager.clearAndUpdate(itemRenderState, (ItemStack)campfireBlockEntity.getItemsBeingCooked().get(j), ItemDisplayContext.FIXED, campfireBlockEntity.getWorld(), (HeldItemContext)null, i + j);
            campfireBlockEntityRenderState.cookedItemStates.add(itemRenderState);
        }

    }

    @Override
    public void render(HollowOakLogBlockEntityRenderState state, MatrixStack matrixStack, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        MinecraftClient client = MinecraftClient.getInstance();
        BlockRenderManager blockRenderManager = client.getBlockRenderManager();
        BlockState stateOfBlock = state.blockState;
        if(stateOfBlock==null)return;
        matrixStack.push();
        matrixStack.translate(0.1, 0.35, 0.1);
        matrixStack.scale(0.89f, 0.5f, 0.89f);

        blockRenderManager.renderBlockAsEntity(
                stateOfBlock,
                matrixStack,
                client.getBufferBuilders().getEntityVertexConsumers(),
                state.lightmapCoordinates,
                OverlayTexture.DEFAULT_UV
        );

        matrixStack.pop();

    }
}
