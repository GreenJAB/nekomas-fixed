package net.greenjab.nekomasfixed.render.other;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.state.ParticleGroupRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.world.phys.Vec3;
import com.mojang.blaze3d.vertex.PoseStack;
import org.jspecify.annotations.NonNull;

import java.util.List;

@Environment(EnvType.CLIENT)
public class NumberParticleRenderer extends ParticleGroup<NumberParticle> {
    public NumberParticleRenderer(ParticleEngine particleManager) {
        super(particleManager);
    }

    @Override
    public @NonNull ParticleGroupRenderState extractRenderState(@NonNull Frustum frustum, @NonNull Camera camera, float tickProgress) {
        return new NumberParticleRenderer.Result(
                this.particles
                        .stream()
                        .map(numberParticle -> NumberParticleRenderer.State.create(numberParticle, camera, tickProgress))
                        .toList()
        );
    }

    @Environment(EnvType.CLIENT)
    record Result(List<NumberParticleRenderer.State> states) implements ParticleGroupRenderState {
        @Override
        public void submit(@NonNull SubmitNodeCollector orderedRenderCommandQueue, @NonNull CameraRenderState cameraRenderState) {
            for (NumberParticleRenderer.State state : this.states) {
                orderedRenderCommandQueue.submitNameTag(state.matrices, new Vec3(0, 0, 0), 0, Component.nullToEmpty(state.damage), true, state.color, 100.6789, cameraRenderState);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public  record State(String damage, PoseStack matrices, RenderType renderLayer, int color) {

        public static NumberParticleRenderer.State create(NumberParticle particle, Camera camera, float tickProgress) {
            PoseStack matrixStack = new PoseStack();
            matrixStack.pushPose();
            Vec3 pos = particle.getBoundingBox().getCenter().subtract(camera.position());
            float age = particle.getAge()+tickProgress;
            float ageScale = (float) (Math.sin(Math.min(age,8)/5)*Math.min(0.5+particle.getDamage()/10.0, 2));

            int ii = ARGB.colorFromFloat(Math.max(Math.min((particle.getLifetime()-age)/8f, 1), 0), 1.0F, 1.0F, 1.0F);
            matrixStack.translate(pos);
            matrixStack.scale(ageScale, ageScale, ageScale);

            String dmg = String.format("%.1f", Math.round(particle.getDamage() * 10) / 10.0);
            if (dmg.toCharArray()[dmg.length()-1]=='0') dmg = dmg.substring(0, dmg.length()-2);
            return new NumberParticleRenderer.State(dmg, matrixStack, RenderTypes.entityTranslucent(NekomasFixed.id("textures/particle/number.png")), ii);
        }
    }
}
