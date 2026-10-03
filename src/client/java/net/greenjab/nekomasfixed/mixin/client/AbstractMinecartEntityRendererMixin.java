package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.greenjab.nekomasfixed.render.entity.model.CustomMinecartModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.object.cart.MinecartModel;
import net.minecraft.client.renderer.entity.AbstractMinecartRenderer;
import net.minecraft.resources.Identifier;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(AbstractMinecartRenderer.class)
public class AbstractMinecartEntityRendererMixin {

    @Unique private static final Identifier NEW_MINECART_LOCATION = Identifier.withDefaultNamespace("textures/entity/minecart/new_minecart.png");

    @WrapOperation(method = "<init>", at = @At(value = "NEW", target = "(Lnet/minecraft/client/model/geom/ModelPart;)Lnet/minecraft/client/model/object/cart/MinecartModel;"))
    private static MinecartModel useCustomMinecartModel(ModelPart root, Operation<MinecartModel> original) {
        return new CustomMinecartModel(root);
    }

    @WrapOperation(method = "submit(Lnet/minecraft/client/renderer/entity/state/MinecartRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V", at =
    @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/AbstractMinecartRenderer;MINECART_LOCATION:Lnet/minecraft/resources/Identifier;", opcode = Opcodes.GETSTATIC))
    private static Identifier useCustomMinecartTexture(Operation<Identifier> original) {
        return NEW_MINECART_LOCATION;
    }

}