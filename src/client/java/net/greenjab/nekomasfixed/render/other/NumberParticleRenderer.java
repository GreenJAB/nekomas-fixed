package net.greenjab.nekomasfixed.render.other;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.Vec3d;

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
                        .map(/* method_74275 */ numberParticle -> NumberParticleRenderer.State.create(numberParticle, camera, tickProgress))
                        .toList()
        );
    }

    @Environment(EnvType.CLIENT)
    record Result(List<NumberParticleRenderer.State> states) implements Submittable {
        @Override
        public void submit(OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState) {
            for (NumberParticleRenderer.State state : this.states) {
                orderedRenderCommandQueue.submitLabel(state.matrices, new Vec3d(0, 0, 0), 0, Text.of(state.damage), true, state.color, 100.6789, cameraRenderState);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public  record State(String damage, MatrixStack matrices, RenderLayer renderLayer, int color) {

        public static NumberParticleRenderer.State create(NumberParticle particle, Camera camera, float tickProgress) {
            MatrixStack matrixStack = new MatrixStack();
            matrixStack.push();
            Vec3d pos = particle.getBoundingBox().getCenter().subtract(camera.getCameraPos());
            float age = particle.getAge()+tickProgress;
            //float ageScale = Math.min(age/5f, 1);
            float ageScale = (float) (Math.sin(Math.min(age,8)/5)*Math.min(0.5+particle.getDamage()/10.0, 2));

            int ii = ColorHelper.fromFloats(Math.max(Math.min((particle.getMaxAge()-age)/8f, 1), 0), 1.0F, 1.0F, 1.0F);
            matrixStack.translate(pos);
            matrixStack.scale(ageScale, ageScale, ageScale);

            String dmg = String.format("%.1f", Math.round(particle.getDamage() * 10) / 10.0);
            if (dmg.toCharArray()[dmg.length()-1]=='0') dmg = dmg.substring(0, dmg.length()-2);
            return new NumberParticleRenderer.State(dmg, matrixStack, RenderLayers.entityTranslucent(NekomasFixed.id("textures/particle/number.png")), ii);
        }
    }
}
