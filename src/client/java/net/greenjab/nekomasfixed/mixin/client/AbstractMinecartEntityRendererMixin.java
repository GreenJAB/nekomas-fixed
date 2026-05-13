package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.render.entity.model.CustomMinecartEntityModel;
import net.greenjab.nekomasfixed.render.entity.state.CustomMinecartEntityRenderState;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.AbstractMinecartEntityRenderer;
import net.minecraft.client.render.entity.model.MinecartEntityModel;
import net.minecraft.client.render.entity.state.MinecartEntityRenderState;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractMinecartEntityRenderer.class)
public class AbstractMinecartEntityRendererMixin {

    @Redirect(method = "<init>", at = @At(value = "NEW", target = "(Lnet/minecraft/client/model/ModelPart;)Lnet/minecraft/client/render/entity/model/MinecartEntityModel;"))
    private static MinecartEntityModel replaceMinecartModel(ModelPart modelPart) {
        return new CustomMinecartEntityModel(modelPart);
    }

    @Inject(method = "updateRenderState(Lnet/minecraft/entity/vehicle/AbstractMinecartEntity;Lnet/minecraft/client/render/entity/state/MinecartEntityRenderState;F)V", at = @At(value = "TAIL"))
    private  static <T extends AbstractMinecartEntity, S extends MinecartEntityRenderState> void replaceMinecartState(T abstractMinecartEntity, S minecartEntityRenderState, float f, CallbackInfo ci) {
        if (minecartEntityRenderState instanceof CustomMinecartEntityRenderState c) {
            c.wheelRotation = 360 * ((c.x-(int)c.x) * (MathHelper.angleBetween(c.lerpedYaw, 180)<90?1:-1) +
                    (c.z-(int)c.z) * (MathHelper.angleBetween(c.lerpedYaw, 90)<90?1:-1));
        }
    }
}