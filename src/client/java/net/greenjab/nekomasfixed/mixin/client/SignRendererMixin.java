package net.greenjab.nekomasfixed.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.greenjab.nekomasfixed.target_access_class.SignAccess;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SignRenderer.class)
public class SignRendererMixin {
    @Inject(
        method = "renderSignWithText(Lnet/minecraft/world/level/block/entity/SignBlockEntity;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/SignBlock;Lnet/minecraft/world/level/block/state/properties/WoodType;Lnet/minecraft/client/model/Model;)V",
        at = @At(target = "Lnet/minecraft/client/renderer/blockentity/SignRenderer;renderSign(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/world/level/block/state/properties/WoodType;Lnet/minecraft/client/model/Model;)V", value = "INVOKE")
    )
    private void render(SignBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, BlockState state, SignBlock block, WoodType woodType, Model model, CallbackInfo ci) {
        if (model instanceof SignRenderer.SignModel signModel) {
            signModel.root.visible = true;
            if (((SignAccess) entity).nekomasfixed$isBackgroundHidden()) {
                signModel.root.visible = false;
                if (entity.getBlockState().getBlock() instanceof WallSignBlock) {
                    matrices.translate(0f, 0f, -0.062f);
                    matrices.scale(1f, 1f, 0f);
                }
            }
        } else if (model instanceof HangingSignRenderer.HangingSignModel hangingSignModel) {
            hangingSignModel.root.visible = !((SignAccess) entity).nekomasfixed$isBackgroundHidden();
        }
    }
}
