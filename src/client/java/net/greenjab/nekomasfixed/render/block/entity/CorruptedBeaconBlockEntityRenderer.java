package net.greenjab.nekomasfixed.render.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.greenjab.nekomasfixed.registry.block.entity.CorruptedBeaconBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class CorruptedBeaconBlockEntityRenderer implements BlockEntityRenderer<CorruptedBeaconBlockEntity> {
    public CorruptedBeaconBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(CorruptedBeaconBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.getLevel() == null) return;
        long gameTime = blockEntity.getLevel().getGameTime();
        List<BeaconBlockEntity.BeaconBeamSection> sections = blockEntity.getBeamSections();
        int yOffset = 0;
        for (int i = 0; i < sections.size(); i++) {
            BeaconBlockEntity.BeaconBeamSection section = sections.get(i);
            int height = i == sections.size() - 1 ? BeaconRenderer.MAX_RENDER_Y : section.getHeight();
            BeaconRenderer.renderBeaconBeam(poseStack, bufferSource, BeaconRenderer.BEAM_LOCATION, partialTick, 1.0F, gameTime, yOffset, height, section.getColor(), 0.2F, 0.25F);
            yOffset += section.getHeight();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(CorruptedBeaconBlockEntity blockEntity) {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 256;
    }

    @Override
    public boolean shouldRender(CorruptedBeaconBlockEntity blockEntity, Vec3 cameraPos) {
        return Vec3.atCenterOf(blockEntity.getBlockPos()).multiply(1.0F, 0.0F, 1.0F).closerThan(cameraPos.multiply(1.0F, 0.0F, 1.0F), this.getViewDistance());
    }
}
