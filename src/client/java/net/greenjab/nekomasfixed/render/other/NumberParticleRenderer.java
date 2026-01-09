package net.greenjab.nekomasfixed.render.other;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.model.ElderGuardianParticleModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.ElderGuardianEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Unit;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.joml.Quaternionf;

import java.util.List;

@Environment(EnvType.CLIENT)
public class NumberParticleRenderer extends ParticleRenderer<NumberParticle> {
    public NumberParticleRenderer(ParticleManager particleManager) {
        super(particleManager);
    }

    @Override
    public Submittable render(Frustum frustum, Camera camera, float tickProgress) {
        return new NumberParticleRenderer.Result(
                this.particles
                        .stream()
                        .map(/* method_74275 */ elderGuardianParticle -> NumberParticleRenderer.State.create(elderGuardianParticle, camera, tickProgress))
                        .toList()
        );
    }

    @Environment(EnvType.CLIENT)
    record Result(List<NumberParticleRenderer.State> states) implements Submittable {
        @Override
        public void submit(OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState) {
            for (NumberParticleRenderer.State state : this.states) {
                orderedRenderCommandQueue.submitModel(
                        state.model,
                        Unit.INSTANCE,
                        state.matrices,
                        state.renderLayer,
                        LightmapTextureManager.MAX_LIGHT_COORDINATE,
                        OverlayTexture.DEFAULT_UV,
                        state.color,
                        null,
                        0,
                        null
                );
                //state.matrices.push();
                //applyTextTransforms(state.matrices);
                //int i = getTextColor(signText);
                int i = DyeColor.GREEN.getSignColor();
                int j = 4 * 10 / 2;
                int k;
                boolean bl;
                int l;
                k = i;
                bl =false;
                l = 15728880;

                OrderedText orderedText = Text.of("62331237").asOrderedText();
                //float f = -this.textRenderer.getWidth(orderedText) / 2;
                float f = 100;
                orderedRenderCommandQueue.submitText(state.matrices, f, 10 - j, orderedText, false, TextRenderer.TextLayerType.POLYGON_OFFSET, l, k, 0, 0);

                orderedRenderCommandQueue.submitLabel(state.matrices, new Vec3d(0, 0, 1), 1, Text.of(String.valueOf(state.damage)), true, l, 100, cameraRenderState);

                //state.matrices.pop();

            }
        }
    }

    private static void applyTextTransforms(MatrixStack matrices) {

        float f = 0.015625F * 100;
        matrices.scale(f, -f, f);
    }
    @Environment(EnvType.CLIENT)
    public  record State(double damage, Model<Unit> model, MatrixStack matrices, RenderLayer renderLayer, int color) {

        public static NumberParticleRenderer.State create(NumberParticle particle, Camera camera, float tickProgress) {
    /*@Environment(EnvType.CLIENT)
    record State(double damage, MatrixStack matrices, int color) {

        public static NumberParticleRenderer.State create(NumberParticle particle, Camera camera, float tickProgress) {
            /*float f = (particle.age + tickProgress) / particle.maxAge;
            float f  =0.5f;
            float g = 0.05F + 0.5F * MathHelper.sin(f * (float) Math.PI);
            int i = ColorHelper.fromFloats(g, 1.0F, 1.0F, 1.0F);
            MatrixStack matrixStack = new MatrixStack();
            matrixStack.push();
            matrixStack.multiply(camera.getRotation());
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(60.0F - 150.0F * f));
            float h = 0.42553192F;
            matrixStack.scale(h, -h, -h);
            matrixStack.translate(0.0F, -0.56F, 3.5F);



            return new NumberParticleRenderer.State(particle.getDamage(), matrixStack, i);*/

            float f = 0.5f;
            float g = 0.05F + 0.5F * MathHelper.sin(f * (float) Math.PI);
            int i = ColorHelper.fromFloats((float) (particle.getDamage()/10.0), 1.0F, 1.0F, 1.0F);
            MatrixStack matrixStack = new MatrixStack();
            matrixStack.push();
            matrixStack.multiply(camera.getRotation());
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(60.0F - 150.0F * f));
            float h = 0.42553192F;
            matrixStack.scale(0.42553192F, -0.42553192F, -0.42553192F);
            matrixStack.translate(0.0F, -0.56F, 3.5F);
            return new NumberParticleRenderer.State(particle.getDamage(), new ElderGuardianParticleModel(MinecraftClient.getInstance().getLoadedEntityModels().getModelPart(EntityModelLayers.ELDER_GUARDIAN)), matrixStack, RenderLayers.entityTranslucent(ElderGuardianEntityRenderer.TEXTURE), i);
        }
    }
}
