package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.greenjab.nekomasfixed.target_access.SignAccess;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.block.entity.AbstractSignBlockEntityRenderer;
import net.minecraft.client.render.block.entity.state.SignBlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSignBlockEntityRenderer.class)
public class AbstractSignBlockEntityRendererMixin {
    @Inject(
            method = "updateRenderState(Lnet/minecraft/block/entity/SignBlockEntity;Lnet/minecraft/client/render/block/entity/state/SignBlockEntityRenderState;FLnet/minecraft/util/math/Vec3d;Lnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;)V",
            at = @At("TAIL")
    )
    private void updateRenderState(SignBlockEntity signBlockEntity, SignBlockEntityRenderState signBlockEntityRenderState, float f, Vec3d vec3d, ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlayCommand, CallbackInfo ci) {
        ((SignAccess) signBlockEntityRenderState).nekomasfixed$setHideBackground(((SignAccess) signBlockEntity).nekomasfixed$isBackgroundHidden());
    }

    @WrapOperation(
        method = "render(Lnet/minecraft/client/render/block/entity/state/SignBlockEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/block/BlockState;Lnet/minecraft/block/AbstractSignBlock;Lnet/minecraft/block/WoodType;Lnet/minecraft/client/model/Model$SinglePartModel;Lnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;)V",
                at = @At(target = "Lnet/minecraft/client/render/block/entity/AbstractSignBlockEntityRenderer;renderSign(Lnet/minecraft/client/util/math/MatrixStack;ILnet/minecraft/block/WoodType;Lnet/minecraft/client/model/Model$SinglePartModel;Lnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;)V", value = "INVOKE")
    )
    private void render(AbstractSignBlockEntityRenderer instance, MatrixStack matrices, int lightmapCoords, WoodType woodType, Model.SinglePartModel model, ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay, OrderedRenderCommandQueue queue, Operation<Void> original, SignBlockEntityRenderState renderState, MatrixStack m, BlockState blockState, AbstractSignBlock block) {
        if (((SignAccess) renderState).nekomasfixed$isBackgroundHidden()) {
            if (block instanceof WallSignBlock) {
                matrices.translate(0f, 0f, -0.062f);
                matrices.scale(1f, 1f, 0f);
            }
        } else {
            original.call(instance, matrices, lightmapCoords, woodType, model, crumblingOverlay, queue);
        }
    }
}
