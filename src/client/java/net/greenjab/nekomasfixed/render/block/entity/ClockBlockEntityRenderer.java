package net.greenjab.nekomasfixed.render.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.HashCommon;
import net.greenjab.nekomasfixed.registry.block.FloorClockBlock;
import net.greenjab.nekomasfixed.registry.block.WallClockBlock;
import net.greenjab.nekomasfixed.registry.block.entity.ClockBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class ClockBlockEntityRenderer implements BlockEntityRenderer<ClockBlockEntity> {

    public ClockBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(ClockBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int packedOverlay) {
        BlockState blockState = blockEntity.getBlockState();
        boolean wall = blockState.getBlock() instanceof WallClockBlock;
        float yaw;
        if (wall) {
            yaw = RotationSegment.convertToDegrees(RotationSegment.convertToSegment(blockState.getValue(WallClockBlock.FACING).getOpposite()));
        } else {
            yaw = RotationSegment.convertToDegrees(blockState.getValue(FloorClockBlock.ROTATION));
        }

        int dayTime = blockEntity.getShowsTime() && !blockEntity.hasBell()
                ? (int) ((blockEntity.getLevel().getDayTime() + 6000) % 24000) : -1;
        int timer = blockEntity.getTimer();

        poseStack.pushPose();

        // Floating label above the clock while it shows the time or is counting down.
        // Drawn before the centering translate so the anchor sits at block coordinates.
        String text = null;
        if (dayTime != -1) {
            int hour = dayTime / 1000;
            int min = ((dayTime % 1000) * 60) / 1000;
            text = (hour < 10 ? "0" : "") + hour + ":" + (min < 10 ? "0" : "") + min;
        } else {
            int time = timer + 20;
            if (time > 20) {
                int min = time / 1200;
                int sec = (time - min * 1200) / 20;
                text = (min != 0 ? min + " Minute" + (min != 1 ? "s" : "") + ", " : "") + sec + " Second" + (sec != 1 ? "s" : "");
            }
        }
        if (text != null) {
            Vec3 pos = wall
                    ? new Vec3(-0.4 * Math.sin(yaw * Math.PI / 180.0F) + 0.5, 1.0, 0.4 * Math.cos(yaw * Math.PI / 180.0F) + 0.5)
                    : new Vec3(0.5, 1.0, 0.5);
            poseStack.pushPose();
            poseStack.translate((float) pos.x, (float) pos.y, (float) pos.z);
            // Bill the label to the camera (like a name tag / main's submitNameTag) so it rotates
            // to always face the player instead of staying fixed in world space.
            poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
            poseStack.scale(0.025F, -0.025F, 0.025F);
            Matrix4f matrix = new Matrix4f(poseStack.last().pose());
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.font.drawInBatch(text, -minecraft.font.width(text) / 2.0F, 0.0F,
                    0xFFFFFFFF, true,
                    matrix, buffer, Font.DisplayMode.NORMAL, 0, packedLight);
            poseStack.popPose();
        }

        // Center the clock/stand/bell at the block (renderers draw relative to the block corner).
        poseStack.translate(0.5F, 0.5F, 0.5F);
        if (!wall && timer > -ClockBlockEntity.timerDuration && timer < 0) {
            poseStack.mulPose(Axis.YP.rotationDegrees(10 * (timer % 2 == 0 ? 1 : -1)));
        }

        // Clock face.
        poseStack.pushPose();
        Vec3 clockPos = new Vec3(0, wall ? 0 : -0.15, wall ? 0.46875 : -0.1);
        poseStack.mulPose(Axis.YP.rotationDegrees(-yaw));
        poseStack.translate((float) clockPos.x, (float) clockPos.y, (float) clockPos.z);
        if (!wall) poseStack.mulPose(Axis.XP.rotationDegrees(30));
        float clockScale = wall ? 1 : 0.8F;
        poseStack.scale(clockScale, clockScale, clockScale);
        this.renderClockFace(blockEntity, poseStack, buffer, packedLight, packedOverlay);
        poseStack.popPose();

        // Stand (floor only).
        if (!wall) {
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees(-yaw));
            poseStack.translate(0.0F, -0.35F, 0.2F);
            poseStack.mulPose(Axis.XP.rotationDegrees(-30));
            poseStack.scale(1.0F, 1.6F, 1.0F);
            this.renderItem(blockEntity, Items.SPRUCE_FENCE_GATE.getDefaultInstance(), poseStack, buffer, packedLight, packedOverlay, 1);
            poseStack.popPose();
        }

        // Bell (floor only, once added).
        if (!wall && blockEntity.hasBell()) {
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees(-yaw));
            poseStack.translate(0.0F, 0.35F, 0.1F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180));
            poseStack.scale(0.5F, 0.5F, 0.5F);
            this.renderItem(blockEntity, Items.BELL.getDefaultInstance(), poseStack, buffer, packedLight, packedOverlay, 1);
            poseStack.popPose();
        }

        poseStack.popPose();
    }

    private void renderItem(ClockBlockEntity blockEntity, ItemStack stack, PoseStack poseStack,
                            MultiBufferSource buffer, int packedLight, int packedOverlay, int seedOffset) {
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED,
                packedLight, packedOverlay, poseStack, buffer, blockEntity.getLevel(),
                HashCommon.long2int(blockEntity.getBlockPos().asLong()) + seedOffset);
    }

    // Renders the vanilla clock item so its `time` property animates the face. That property bails
    // to clock_00 when entity is null (what froze block-rendered faces), so pass the player here.
    private void renderClockFace(ClockBlockEntity blockEntity, PoseStack poseStack,
                                 MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getItemRenderer().renderStatic(minecraft.player, Items.CLOCK.getDefaultInstance(),
                ItemDisplayContext.FIXED, false, poseStack, buffer, blockEntity.getLevel(),
                packedLight, packedOverlay, HashCommon.long2int(blockEntity.getBlockPos().asLong()));
    }
}