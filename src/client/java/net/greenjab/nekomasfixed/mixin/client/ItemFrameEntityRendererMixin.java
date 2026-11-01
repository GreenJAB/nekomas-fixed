package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.greenjab.nekomasfixed.registry.entity.ClearItemFrameEntity;
import net.greenjab.nekomasfixed.target_access.ItemFrameAccess;
import net.greenjab.nekomasfixed.util.MixinGlobals;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ItemFrameEntityRenderer;
import net.minecraft.client.render.entity.state.ItemFrameEntityRenderState;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.state.property.Properties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemFrameEntityRenderer.class)
public abstract class ItemFrameEntityRendererMixin<T extends ItemFrameEntity> extends EntityRenderer<T, ItemFrameEntityRenderState> {
    protected ItemFrameEntityRendererMixin(EntityRendererFactory.Context context) {
        super(context);
    }

    @Inject(
            method = "updateRenderState(Lnet/minecraft/entity/decoration/ItemFrameEntity;Lnet/minecraft/client/render/entity/state/ItemFrameEntityRenderState;F)V",
            at = @At("TAIL")
    )
    private void updateRenderState(T itemFrameEntity, ItemFrameEntityRenderState itemFrameEntityRenderState, float f, CallbackInfo ci) {
        ((ItemFrameAccess)itemFrameEntityRenderState).nekomasfixed$setClear(itemFrameEntity instanceof ClearItemFrameEntity);
    }

    @WrapOperation(
            method = "render(Lnet/minecraft/client/render/entity/state/ItemFrameEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/model/BlockStateManagers;getStateForItemFrame(ZZ)Lnet/minecraft/block/BlockState;")
    )
    private BlockState getStateForItemFrame(boolean hasGlow, boolean hasMap, Operation<BlockState> original, ItemFrameEntityRenderState itemFrameEntityRenderState) {
        if (((ItemFrameAccess)itemFrameEntityRenderState).nekomasfixed$isClear()) {
            return MixinGlobals.CLEAR_ITEM_FRAME.getDefaultState().with(Properties.MAP, hasMap);
        }
        return original.call(hasGlow, hasMap);
    }

    @ModifyArg(
            method = "render(Lnet/minecraft/client/render/entity/state/ItemFrameEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;submitBlockStateModel(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/RenderLayer;Lnet/minecraft/client/render/model/BlockStateModel;FFFIII)V"
            ),
            index = 1
    )
    private RenderLayer renderLayer(RenderLayer original) {
        return RenderLayers.itemEntityTranslucentCull(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
    }
}
